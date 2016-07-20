package xmu.edu.a3plus5.zootv.network;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;


public class PlatformFactory {
    private static final Map<String, Object> objectMap = new HashMap<String, Object>();

    public synchronized static <T extends BasePlatform> T createPlatform(Class<T> c) {
        BasePlatform platform = null;
        String className = null;
        className = c.getName();
        try {
            if (!objectMap.containsKey(className)) {
                Class class1 = Class.forName(className);
                // 获得无参构造
                Constructor constructor = class1.getDeclaredConstructor();
                // 设置无参构造是可访问的
                constructor.setAccessible(true);
                // 产生一个实例对象。
                platform = (BasePlatform) constructor.newInstance();
                objectMap.put(className, platform);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) objectMap.get(className);
    }

    public synchronized static <T extends BasePlatform> T createPlatform(String platformName)
    {
        switch (platformName) {
            case BasePlatform.DouYu:
                return (T) createPlatform(DouYuPlatform.class);
            case BasePlatform.Panda:
                return (T) createPlatform(PandaPlatform.class);
            case BasePlatform.Zoo:
                return (T) createPlatform(ZooPlatform.class);
            case BasePlatform.ZhanQi:
                return (T) createPlatform(ZhanQiPlatform.class);
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        BasePlatform douYuPlatform = PlatformFactory.createPlatform(BasePlatform.DouYu);
        System.out.println(douYuPlatform.getAllCategory());
    }
}