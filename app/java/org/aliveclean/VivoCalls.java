package org.aliveclean;

import java.lang.reflect.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对 Vivo 引擎（打包在 engine.apk 里的厂商代码）的反射调用封装：按类名加载类型、构造实例，
 * 并缓存 Method 来调用它的成员。
 *
 * <p>引擎里的成员大多是包私有或被隐藏的，方法/字段又常常声明在父类上，所以这里不能直接 new 或强转：
 * 每次都要从具体类沿父类链查找声明，再 setAccessible(true)；构造与调用抛出的
 * InvocationTargetException 还要拆出原始异常，上层才能按引擎自己的异常类型处理。</p>
 */
final class VivoCalls {
    private final ClassLoader loader;
    private final ConcurrentHashMap<String,Method> methods=new ConcurrentHashMap<>();
    VivoCalls(VivoEngineRuntime runtime){loader=runtime.getClassLoader();}
    Class<?> type(String name)throws Exception{return loader.loadClass(name);}
    Object make(String name,Class<?>[]types,Object...args)throws Exception{
        Constructor<?> c=type(name).getDeclaredConstructor(types);c.setAccessible(true);
        try{return c.newInstance(args);}catch(InvocationTargetException e){throw failure(e);}
    }
    Object call(Object target,String name,Class<?>[]types,Object...args)throws Exception{
        String key=target.getClass().getName()+"#"+name+java.util.Arrays.toString(types);
        Method method=methods.get(key);
        if(method==null){
            for(Class<?> c=target.getClass();c!=null;c=c.getSuperclass()){
                try{method=c.getDeclaredMethod(name,types);break;}catch(NoSuchMethodException ignored){}
            }
            if(method==null)throw new NoSuchMethodException(key);
            method.setAccessible(true);methods.put(key,method);
        }
        try{return method.invoke(target,args);}catch(InvocationTargetException e){throw failure(e);}
    }
    Object call(Object target,String name)throws Exception{return call(target,name,new Class<?>[0]);}
    Object get(Object target,String name)throws Exception{return field(target,name).get(target);}
    void set(Object target,String name,Object value)throws Exception{field(target,name).set(target,value);}
    private Field field(Object target,String name)throws Exception{
        for(Class<?> c=target.getClass();c!=null;c=c.getSuperclass()){
            try{Field f=c.getDeclaredField(name);f.setAccessible(true);return f;}catch(NoSuchFieldException ignored){}
        }
        throw new NoSuchFieldException(target.getClass().getName()+"#"+name);
    }
    private static Exception failure(InvocationTargetException e){
        Throwable cause=e.getCause();return cause instanceof Exception?(Exception)cause:new Exception(cause);
    }
}
