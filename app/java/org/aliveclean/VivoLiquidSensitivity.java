package org.aliveclean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * 调节液态壁纸的灵敏度：在引擎的传感器回调对象外面套一层动态代理，把回调收到的三个分量乘以增益，
 * 关闭时把原来的回调对象装回传感器。
 */
final class VivoLiquidSensitivity implements AutoCloseable {
    private final VivoCalls api;
    private final Object sensor,original;
    private volatile float gain;

    VivoLiquidSensitivity(VivoCalls api,Object sensor,int relative)throws Exception{
        this.api=api;this.sensor=sensor;original=api.get(sensor,"b");relative(relative);
        Class<?> callback=api.type("e4.a");
        Object scaled=Proxy.newProxyInstance(callback.getClassLoader(),new Class<?>[]{callback},(proxy,method,args)->{

            float factor=gain;
            if(method.getName().equals("p")&&factor!=1f){
                args=new Object[]{(Float)args[0]*factor,(Float)args[1]*factor,(Float)args[2]*factor};
            }
            try{return method.invoke(original,args);}
            catch(InvocationTargetException error){throw error.getCause();}
        });
        api.set(sensor,"b",scaled);
    }

    void relative(int value){gain=VivoSensorGain.multiplier(value);}
    @Override public void close()throws Exception{api.set(sensor,"b",original);}
}
