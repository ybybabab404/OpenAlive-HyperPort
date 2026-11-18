//! Independent GLES frame graph. Resources stay on the EGL thread.
use crate::{blur, gl::*, math, shaders};
use std::ffi::c_void;
mod frame_rear { include!("frame_rear.rs"); }

// SceneMotion owns interpolation and matrices. No second timeline runs on the native side.
const FRAME_FLOATS:usize=60;
struct Frame([f32;FRAME_FLOATS]);
impl Frame {
    fn ratio(&self)->[f32;3]{self.0[0..3].try_into().unwrap()}
    fn lens(&self)->[f32;10]{self.0[3..13].try_into().unwrap()}
    fn matrix(&self,offset:usize)->math::Mat{self.0[offset..offset+16].try_into().unwrap()}
}

const COPY_V: &str = "#version 300 es\nin vec3 a_position;in vec2 a_texCoord0;out vec2 uv;uniform vec2 crop;uniform float flip;void main(){gl_Position=vec4(a_position,1.);uv=(a_texCoord0-.5)*crop+.5;uv.y=mix(uv.y,1.-uv.y,flip);}";
const COPY_F: &str = "#version 300 es\nprecision highp float;in vec2 uv;uniform sampler2D photo;out vec4 color;void main(){color=texture(photo,uv);}";

unsafe fn program(base: &str) -> Result<Program, String> {
    let v = format!("shader/{base}_vertex.glsl");
    let alternate = format!("shader/{base}_vert.glsl");
    let f = format!("shader/{base}_frag.glsl");
    Program::new(shaders::source(&v).or_else(|| shaders::source(&alternate)).ok_or(&*v)?,
        shaders::source(&f).ok_or(&*f)?).map_err(|e| format!("{base}: {e}"))
}

#[derive(Clone,Copy,Default)]
struct Image { id:u32, width:i32, height:i32 }


fn fnv(h:u64,v:u64)->u64{(h^v).wrapping_mul(1099511628211)}
fn bits(v:f32)->u64{v.to_bits() as u64}
struct Scene {
    quad:Quad, copy:Program, lock_program:Program, aod_program:Program,
    home_program:Program, home_effect_program:Program, blend:Program, down:Program, gaussian:Program, up:Program,
    targets:Vec<Target>, images:[Image;6], color:[f32;3], frame:Option<[f32;36]>, effects:Option<[f32;20]>, frame_crop:[f32;4],
    w:i32, h:i32, aod:i32, lock:i32, home:i32,
    /// 上一次模糊结果的身份：(源槽位, 源内容签名, radius 位模式, limit 位模式)。
    /// 命中就说明「模糊的输入一个字节都没变」，四个全尺寸 pass 可以整段跳过；
    /// targets[4]/[5] 每次都会被覆盖，所以只留最近一份。
    blur_cache:Option<(i32,u64,u32,u32)>,
    /// 本帧模糊源的「内容签名」，在 draw() 开头算一次。只包含真正决定 lock/home 那张图内容的输入：
    /// 照片来源与尺寸、aspect/flip、底色、effect 参数、灰度/遮罩纹理、玻璃样式。
    sig_lock:u64, sig_home:u64,
    /// 模糊核缓存：key 是 (radius,limit) 的位模式，值是 (factor, weights, offsets)。
    kernel_key:Option<(u32,u32)>, kernel:(f32,Vec<f32>,Vec<f32>),
}

impl Scene {
    unsafe fn new(w:i32,h:i32,aod:i32,lock:i32,home:i32)->Result<Self,String> {
        let mut targets=Vec::new();
        for _ in 0..6 { targets.push(Target::new(w,h)?); }
        let lock_path=match lock {
            1=>"photo/lock/straightGlass/straight_glass",2=>"photo/lock/curveGlass/curve_glass",
            3=>"photo/lock/groundGlass/ground_glass",5=>"photo/lock/fillMask/fill_mask",
            _=>"photo/lock/original/original",
        };
        let aod_path=if aod==101 {"photo/aod/fullAod/full_aod"}else{"photo/aod/lensPhoto/lens_photo"};
        let blend_path=match aod {0=>"photo/blend/lens/lens_blend",1..=5=>"photo/blend/chromakey/chromakey_blend",101=>"photo/blend/fullCommon/full_common_blend",_=>"photo/blend/common/common_blend"};
        let aod_program=if (1..=5).contains(&aod) {
            // The shared quad uses bottom-up UVs; source masks use top-down RGB
            // and the existing photo targets are bottom-origin FBOs.
            let vertex="#version 300 es\nin vec3 a_position;in vec2 a_texCoord0;uniform mat4 u_modelMatrix;uniform mat4 u_contentMatrix;out vec2 v_snapshot_texCoord;out vec2 v_content_texCoord;out vec2 v_video_texCoord;void main(){gl_Position=u_modelMatrix*vec4(a_position,1.);v_snapshot_texCoord=a_texCoord0;v_video_texCoord=a_texCoord0;v_content_texCoord=(u_contentMatrix*vec4(a_texCoord0,0.,1.)).xy;v_content_texCoord.y=1.-v_content_texCoord.y;}";
            let fragment=shaders::source("shader/photo/aod/chromakey/chromakey_photo_frag.glsl").unwrap()
                .replace("#extension GL_OES_EGL_image_external_essl3 : require", "")
                .replace("samplerExternalOES", "sampler2D")
                .replace("uniform sampler2D u_photo;", "uniform sampler2D u_photo;uniform sampler2D u_frame_photo;uniform bool u_pair;uniform mat3 u_frame_crop;uniform mat3 u_rear_transform;")
                .replace("vec3 finalColor = vec3(0.0);",
                    "vec2 frameUv=(u_frame_crop*vec3(v_snapshot_texCoord-vec2(0.5,348.5/720.0),1.0)).xy;vec3 frameColor=u_pair?texture(u_frame_photo,frameUv).rgb:vec3(0.0);vec3 finalColor=vec3(0.0);")
                // The rear paper rotates and translates independently inside the
                // mask. Unproject its own aperture before applying the user's crop.
                .replace("finalColor = blendNormal(finalColor, u_first_color, videoColor.r);",
                    "vec3 rearColor=u_first_color;if(u_pair&&videoColor.r>0.0){vec2 rearUv=(u_frame_crop*u_rear_transform*vec3(v_snapshot_texCoord,1.0)).xy;rearColor=texture(u_frame_photo,rearUv).rgb;}finalColor=blendNormal(finalColor,rearColor,videoColor.r);")
                .replace("vec3 contentColor = texture(u_photo, v_content_texCoord).rgb;",
                    "vec3 contentColor=u_pair?frameColor:texture(u_photo,v_content_texCoord).rgb;");
            Program::new(vertex,&fragment)?
        }else{program(aod_path)?};
        let home_path="photo/launcher/original/original";
        let home_effect_path=match home {
            7=>"photo/lock/straightGlass/straight_glass",
            8=>"photo/lock/curveGlass/curve_glass",
            9=>"photo/lock/groundGlass/ground_glass",
            _=>"photo/launcher/original/original",
        };
        Ok(Self {
            quad:Quad::new(),copy:Program::new(COPY_V,COPY_F)?,lock_program:program(lock_path)?,
            aod_program,home_program:program(home_path)?,home_effect_program:program(home_effect_path)?,blend:program(blend_path)?,
            down:program("blur/down_sample")?,gaussian:program("blur/gaussian")?,up:program("blur/up_sample")?,
            targets,images:[Image::default();6],color:[0.15,0.27,0.32],frame:None,effects:None,frame_crop:[0.5,0.5,1.,0.],w,h,aod,lock,home,blur_cache:None,sig_lock:0,sig_home:0,kernel_key:None,kernel:(1.,Vec::new(),Vec::new()),
        })
    }
    fn copy(&mut self,input:u32,target:usize,aspect:f32,flip:bool) {
        self.targets[target].bind_only();self.copy.bind();self.copy.texture("photo",0,input);
        let screen=self.w as f32/self.h as f32;
        let crop=if aspect>screen{[screen/aspect,1.]}else{[1.,aspect/screen]};
        self.copy.v2("crop",crop[0],crop[1]);self.copy.f("flip",if flip{1.}else{0.});self.quad.draw();
    }
    /// `source` 是模糊源的身份（槽位号 + 内容签名）。模糊是纯函数：同一个源、同样的
    /// radius/limit 一定得到同样的结果，所以命中缓存时直接复用上一份 targets[5]。
    /// 注意 targets[4]/[5] 被所有模糊共用，缓存只保留最近一次；同帧两次不同源的调用会互相顶掉，
    /// 但那只是少命中一次，不会出错误结果。
    fn blurred(&mut self,input:u32,source:(i32,u64),radius:f32,limit:f32)->u32 {
        if radius<2. { return input; }
        let key=(source.0,source.1,radius.to_bits(),limit.to_bits());
        if self.blur_cache==Some(key) { return self.targets[5].texture; }
        // 核只由 radius/limit 决定：参数没变就复用，省掉每帧重算与两次 uniform 数组上传。
        if self.kernel_key!=Some((radius.to_bits(),limit.to_bits())) {
            let(factor,weights,offsets)=blur::kernel_with_limit(radius,limit);
            self.kernel_key=Some((radius.to_bits(),limit.to_bits()));
            self.kernel=(factor,weights,offsets);
        }
        let factor=self.kernel.0;let s=1./factor;
        let matrix=[s,0.,0.,0.,s,0.,(1.-s)/2.,(1.-s)/2.,1.];
        self.targets[4].bind();self.down.bind();self.down.f("u_scale",s);self.down.texture("u_photo",0,input);self.quad.draw();
        for (src,dst,horizontal) in [(4,5,1),(5,4,0)] {
            self.targets[dst].bind();self.gaussian.bind();self.gaussian.texture("u_blur_texture",0,self.targets[src].texture);
            self.gaussian.f("u_scale",s);self.gaussian.m3("u_texture_matrix",&matrix);
            self.gaussian.array("u_weights",&self.kernel.1);self.gaussian.array("u_offsets",&self.kernel.2);self.gaussian.i("u_radius",self.kernel.1.len() as i32);
            self.gaussian.i("u_horizontal",horizontal);self.gaussian.v2("u_step",1./self.w as f32,1./self.h as f32);
            self.gaussian.f("u_texture_min",(1.-s)/2.);self.gaussian.f("u_texture_max",(1.+s)/2.);self.quad.draw();
        }
        self.targets[5].bind();self.up.bind();self.up.texture("u_photo",0,self.targets[4].texture);self.up.m3("u_texture_matrix",&matrix);self.quad.draw();
        self.blur_cache=Some(key);
        self.targets[5].texture
    }
    unsafe fn lock_pass(&mut self,frame:&Frame) {
        let effect=self.effect_values(frame,0);
        // lock==4 是纯色填充，必须清；其余样式都由全屏 quad 不透明覆盖，无需 clear。
        if self.lock==4 {
            self.targets[1].bind();glClearColor(self.color[0],self.color[1],self.color[2],1.);glClear(0x4000);return;
        }
        self.targets[1].bind_only();
        let p=&mut self.lock_program;p.bind();p.texture("u_lockTex",0,self.targets[0].texture);p.texture("u_texture",0,self.targets[0].texture);
        p.v4("u_display_uv_rect",[0.,0.,1.,1.]);p.v3("u_color",self.color);
        if self.lock==1||self.lock==2 {
            let g=&effect;
            p.f("u_tiling",g[0]);p.f("u_lineWidth",g[1]);p.f("u_lineOffset",g[2]);p.f("u_maskThreshold",g[3]);p.v2("u_offsetStrength",g[4],g[5]);p.f("u_lineFrequency",40.);p.f("u_lineAmplitude",0.045);
        } else if self.lock==3 {
            let gray=self.images[1];p.texture("u_grayTex",1,gray.id);p.texture("u_maskTex",2,self.images[2].id);
            p.v2("u_maskOffset",effect[8],effect[9]);p.f("u_minGray",effect[6]);p.f("u_maskThreshold",effect[7]);
            p.v2("u_dudv",1./gray.width.max(1) as f32,1./gray.height.max(1) as f32);
        }
        self.quad.draw();
    }
    /// `lens_src` 是 aod==0（镜头）这一路要拿来做"模糊+镜头变形"的取样源：
    /// 息屏→锁屏时是锁屏层（targets[1]），息屏→桌面时是桌面层（targets[3]）。
    /// 用错源就会把"锁屏那份画面"和"桌面那份画面"两张不同的图叠在一起 —— 用户看到的双层。
    fn aod_pass(&mut self,frame:&Frame,decor:u32,lens_src:u32) {
        if self.aod<0 {self.targets[2].bind();return;}
        if (1..=5).contains(&self.aod) {
            let Some(f)=self.frame else{self.targets[2].bind();return;};
            let source=self.blurred(self.targets[1].texture,(1,self.sig_lock),f[33],12.);
            self.targets[2].bind();let p=&mut self.aod_program;p.bind();
            p.texture("u_photo",0,source);
            // Paired photos expand into the current screen's texture selection.
            let endpoint=if self.aod==1&&self.images[5].id!=0&&frame.ratio()[1]>0.{1}else{3};
            p.texture("u_launcher_effect",1,self.targets[endpoint].texture);
            let image=self.images[5];p.i("u_pair",if self.aod==1&&image.id!=0{1}else{0});
            if image.id!=0{
                p.texture("u_frame_photo",3,image.id);
                // Square selection in original image coordinates. The front
                // mask's photo width is 320/720 of the square mesh. Keep image
                // aspect while its lower edge is covered by the paper border.
                let [cx,cy,size,angle]=self.frame_crop;
                let pixels=image.width.min(image.height) as f32*size*720./320.;
                let (s,c)=angle.to_radians().sin_cos();
                let x=pixels/image.width as f32;let y=pixels/image.height as f32;
                p.m3("u_frame_crop",&[c*x,-s*y,0.,s*x,c*y,0.,cx,cy,1.]);
                p.m3("u_rear_transform",&frame_rear::matrix(f[34]));
            }
            p.texture("u_snapshot",2,self.images[0].id);p.texture("u_green_screen",2,self.images[0].id);
            p.i("u_use_snapshot",1);p.f("u_photo_alpha",f[32]);p.v3("u_first_color",self.color);
            p.m4("u_modelMatrix",f[0..16].try_into().unwrap());
            p.m4("u_contentMatrix",f[16..32].try_into().unwrap());
            p.m4("u_video_transform",&math::ID);self.quad.draw();return;
        }
        if self.aod==101 {
            self.targets[2].bind_only();let p=&mut self.aod_program;p.bind();p.texture("u_lockTex",0,self.targets[1].texture);
            p.texture("u_grayTex1",1,self.images[3].id);p.f("u_alpha1",frame.0[21]);self.quad.draw();return;
        }
        let l=frame.lens();
        // lens_src 指向哪一层，就用哪一层的签名：源不同、签名必须不同，否则会把 A 层的模糊结果用到 B 层上。
        let lens_sig=if lens_src==self.targets[3].texture{(3,self.sig_home)}else{(1,self.sig_lock)};
        let source=self.blurred(lens_src,lens_sig,l[0],12.);
        self.targets[2].bind_only();let p=&mut self.aod_program;p.bind();p.texture("u_photo",0,source);p.texture("u_lens_decorator",1,decor);
        p.f("u_lens_scale",l[1]);p.f("u_zoom",1./(l[1].min(0.5)*4.));p.f("u_aspect_ratio",self.h as f32/self.w as f32);p.f("u_aspect_ratio_reciprocal",self.w as f32/self.h as f32);
        p.f("u_distortion",l[2]/l[1]);p.f("u_decorator_alpha",l[3]);p.v3("u_translation",[l[5],l[6],0.]);p.f("u_dark_strength",0.);
        p.f("u_ripple_radius",l[8]);p.f("u_ripple_boundary",0.12);
        // Adapt official top-origin sampling to the graph's bottom-origin FBO.
        let matrix=frame.matrix(25);
        let matrix=math::mul(math::translate(0.,1.),math::mul(math::scale(1.,-1.),matrix));
        p.m4("u_texture_matrix",&matrix);p.m4("u_decorator_matrix",&frame.matrix(41));self.quad.draw();
    }
    unsafe fn home_pass(&mut self,frame:&Frame) {
        let effect=self.effect_values(frame,10);
        self.targets[3].bind_only();
        let style=self.home.saturating_sub(6);
        if style==0 {
            let p=&mut self.home_program;p.bind();p.texture("u_texture",0,self.targets[0].texture);p.v3("u_color",self.color);p.v4("u_display_uv_rect",[0.,0.,1.,1.]);self.quad.draw();return;
        }
        let p=&mut self.home_effect_program;p.bind();p.texture("u_lockTex",0,self.targets[0].texture);p.texture("u_texture",0,self.targets[0].texture);
        p.v4("u_display_uv_rect",[0.,0.,1.,1.]);p.v3("u_color",self.color);
        if style==1||style==2 {
            p.f("u_tiling",effect[0]);p.f("u_lineWidth",effect[1]);p.f("u_lineOffset",effect[2]);p.f("u_maskThreshold",effect[3]);p.v2("u_offsetStrength",effect[4],effect[5]);p.f("u_lineFrequency",40.);p.f("u_lineAmplitude",0.045);
        } else {
            let gray=self.images[1];p.texture("u_grayTex",1,gray.id);p.texture("u_maskTex",2,self.images[2].id);
            p.v2("u_maskOffset",effect[8],effect[9]);p.f("u_minGray",effect[6]);p.f("u_maskThreshold",effect[7]);p.v2("u_dudv",1./gray.width.max(1) as f32,1./gray.height.max(1) as f32);
        }
        self.quad.draw();
    }
    fn effect_values(&self,frame:&Frame,offset:usize)->[f32;10] {
        if let Some(values)=self.effects {return values[offset..offset+10].try_into().unwrap();}
        let mut values=[0.;10];values[..8].copy_from_slice(&frame.0[13..21]);values[8..].copy_from_slice(&frame.0[23..25]);values
    }
    unsafe fn draw(&mut self,photo:u32,decor:u32,aspect:f32,frame:&Frame) {
        // 模糊缓存的身份：只喂进真正影响「被模糊的那张图」的输入。多算无害（最多少命中一次），
        // 少算才会让画面停在旧内容上，所以这里把 pass 里用到的参数都算进来。
        let lock_effect=self.effect_values(frame,0);
        let home_effect=self.effect_values(frame,10);
        let mut h=1469598103934665603u64;
        for v in [photo as u64,self.images[0].id as u64,self.images[1].id as u64,self.images[2].id as u64,
                  bits(aspect),bits(self.w as f32),bits(self.h as f32),self.lock as u64] {
            h=fnv(h,v);
        }
        for v in self.color.iter().copied().chain(lock_effect.iter().copied()) { h=fnv(h,bits(v)); }
        self.sig_lock=h;
        let mut h=1469598103934665603u64;
        for v in [self.images[4].id as u64,self.images[1].id as u64,self.images[2].id as u64,
                  bits(self.w as f32),bits(self.h as f32),self.home as u64] {
            h=fnv(h,v);
        }
        for v in self.color.iter().copied().chain(home_effect.iter().copied()) { h=fnv(h,bits(v)); }
        self.sig_home=h;
        let l=frame.lens();
        let ratio=frame.ratio();
        let aod_visible=ratio[0]>0.;
        let lock_visible=ratio[1]>0.||(aod_visible&&self.aod>=0);
        let home_visible=ratio[2]>0.;
        // 息屏→桌面：镜头层的取样源必须是桌面层，否则镜头里是"锁屏那份画面"、
        // 外面是"桌面那份画面"，两张不同的图叠着淡出 = 双层。
        let lens_from_home=self.aod==0&&aod_visible&&home_visible&&ratio[1]<=0.;
        if lock_visible||home_visible {self.copy(photo,0,aspect,true);}
        if lock_visible&&!lens_from_home{self.lock_pass(frame);}
        // 先画目标层，镜头层才有东西可取样。
        if home_visible&&lens_from_home{
            let home=self.images[4];
            if home.id!=0{self.copy(home.id,0,home.width as f32/home.height as f32,true);}
            self.home_pass(frame);
        }
        if !(1..=5).contains(&self.aod)&&aod_visible{
            let lens_src=if lens_from_home{self.targets[3].texture}else{self.targets[1].texture};
            self.aod_pass(frame,decor,lens_src);
        }
        // aod==101（fullCommon）的 blend 只取 aod 与 lock 两张，画 home 纯属浪费。
        if (home_visible&&!lens_from_home||(1..=5).contains(&self.aod)&&aod_visible)&&self.aod!=101{
            let home=if (1..=5).contains(&self.aod)&&self.images[5].id!=0{Image::default()}else{self.images[4]};
            if home.id!=0{self.copy(home.id,0,home.width as f32/home.height as f32,true);}
            self.home_pass(frame);
        }
        if (1..=5).contains(&self.aod)&&aod_visible{self.aod_pass(frame,decor,self.targets[1].texture);}
        glBindFramebuffer(0x8D40,0);glViewport(0,0,self.w,self.h);self.blend.bind();
        self.blend.texture("u_aod_effect",0,self.targets[2].texture);self.blend.texture("u_lock_effect",1,self.targets[1].texture);self.blend.texture("u_launcher_effect",2,self.targets[3].texture);
        self.blend.v3("u_ratio",ratio);self.blend.f("u_dark_strength",frame.0[57]);self.blend.i("u_support_lock_launcher_blend",frame.0[58] as i32);
        // Official blend samples top-origin textures; this frame graph uses bottom-origin FBOs.
        self.blend.v2("u_translation",l[5],-l[6]);self.blend.f("u_lens_scale",l[1]);self.blend.f("u_aspect_ratio",self.h as f32/self.w as f32);self.quad.draw();
    }
}

#[no_mangle]
pub unsafe extern "system" fn Java_org_aliveclean_NativeScene_create(_: *mut c_void,_:*mut c_void,w:i32,h:i32,mode:i32,aod:i32,lock:i32,home:i32)->i64 {
    if w<=0||h<=0||!(0..=2).contains(&mode)||![-1,0,1,2,3,4,5,101].contains(&aod)||!(0..=5).contains(&lock)||!(6..=9).contains(&home) {return 0;}
    match Scene::new(w,h,aod,lock,home){Ok(s)=>Box::into_raw(Box::new(s)) as i64,Err(e)=>{log(&e);0}}
}
#[no_mangle]
pub unsafe extern "system" fn Java_org_aliveclean_NativeScene_texture(_: *mut c_void,_:*mut c_void,handle:i64,slot:i32,id:i32,w:i32,h:i32) {
    if handle!=0 && (0..6).contains(&slot) && id>0 && w>0 && h>0 {let s=&mut *(handle as *mut Scene);s.images[slot as usize]=Image{id:id as u32,width:w,height:h};s.blur_cache=None;}
}
#[no_mangle]
pub unsafe extern "system" fn Java_org_aliveclean_NativeScene_frameCrop(_: *mut c_void,_:*mut c_void,handle:i64,x:f32,y:f32,size:f32,angle:f32) {
    if handle!=0&&[x,y,size,angle].iter().all(|v|v.is_finite())&&
        (0.0..=1.0).contains(&x)&&(0.0..=1.0).contains(&y)&&(0.05..=1.0).contains(&size)&&(-45.0..=45.0).contains(&angle){
        (*(handle as *mut Scene)).frame_crop=[x,y,size,angle];
    }
}
#[no_mangle]
pub unsafe extern "C" fn alive_scene_frame(handle:i64,values:*const f32,count:usize)->u8 {
    if handle==0||values.is_null()||count!=36{return 0;}
    let mut data=[0.;36];std::ptr::copy_nonoverlapping(values,data.as_mut_ptr(),36);
    if data.iter().any(|v|!v.is_finite())||data[35]!=1.||!(0.0..=1.0).contains(&data[32]){return 0;}
    (*(handle as *mut Scene)).frame=Some(data);1
}
#[no_mangle]
pub unsafe extern "C" fn alive_scene_effects(handle:i64,values:*const f32,count:usize)->u8 {
    if handle==0||values.is_null()||count!=20{return 0;}
    let mut data=[0.;20];std::ptr::copy_nonoverlapping(values,data.as_mut_ptr(),20);
    if data.iter().any(|v|!v.is_finite()){return 0;}
    (*(handle as *mut Scene)).effects=Some(data);1
}
#[cfg(target_os="android")]
#[no_mangle]
pub unsafe extern "system" fn Java_org_aliveclean_NativeScene_effects(env:*mut jni_sys::JNIEnv,_:jni_sys::jclass,handle:i64,buffer:jni_sys::jobject)->u8 {
    if env.is_null()||buffer.is_null(){return 0;}let jni=&**env;
    if jni.GetDirectBufferCapacity.unwrap()(env,buffer)!=80{return 0;}
    alive_scene_effects(handle,jni.GetDirectBufferAddress.unwrap()(env,buffer).cast(),20)
}
#[cfg(target_os="android")]
#[no_mangle]
pub unsafe extern "system" fn Java_org_aliveclean_NativeScene_frame(env:*mut jni_sys::JNIEnv,_:jni_sys::jclass,handle:i64,buffer:jni_sys::jobject)->u8 {
    if env.is_null()||buffer.is_null(){return 0;}let jni=&**env;
    if jni.GetDirectBufferCapacity.unwrap()(env,buffer)!=144{return 0;}
    alive_scene_frame(handle,jni.GetDirectBufferAddress.unwrap()(env,buffer).cast(),36)
}
#[no_mangle]
pub unsafe extern "system" fn Java_org_aliveclean_NativeScene_tint(_: *mut c_void,_:*mut c_void,handle:i64,color:i32) {
    if handle!=0 {(*(handle as *mut Scene)).color=[((color>>16)&255) as f32/255.,((color>>8)&255) as f32/255.,(color&255) as f32/255.];}
}
#[no_mangle]
pub unsafe extern "C" fn alive_scene_render(handle:i64,photo:i32,decor:i32,aspect:f32,values:*const f32,count:usize)->u8 {
    if handle==0||photo<=0||decor<=0||!aspect.is_finite()||aspect<=0.||values.is_null()||count!=FRAME_FLOATS{return 0;}
    let mut frame=Frame([0.;FRAME_FLOATS]);
    std::ptr::copy_nonoverlapping(values,frame.0.as_mut_ptr(),FRAME_FLOATS);
    if frame.0.iter().any(|v|!v.is_finite())||frame.0[4]<=0.||frame.0[59]!=1.{return 0;}
    (*(handle as *mut Scene)).draw(photo as u32,decor as u32,aspect,&frame);1
}

#[cfg(target_os="android")]
#[no_mangle]
pub unsafe extern "system" fn Java_org_aliveclean_NativeScene_render(env:*mut jni_sys::JNIEnv,_:jni_sys::jclass,handle:i64,photo:i32,decor:i32,aspect:f32,buffer:jni_sys::jobject)->u8 {
    if env.is_null()||buffer.is_null(){return 0;}
    let jni=&**env;
    let capacity=jni.GetDirectBufferCapacity.unwrap()(env,buffer);
    if capacity!=(FRAME_FLOATS*4) as i64{return 0;}
    let address=jni.GetDirectBufferAddress.unwrap()(env,buffer);
    alive_scene_render(handle,photo,decor,aspect,address.cast(),FRAME_FLOATS)
}
#[no_mangle]
pub unsafe extern "system" fn Java_org_aliveclean_NativeScene_destroy(_: *mut c_void,_:*mut c_void,handle:i64) {if handle!=0 {drop(Box::from_raw(handle as *mut Scene));}}
