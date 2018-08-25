package gl.linpeng.gf.utils;

import java.lang.reflect.Method;

/**
 * Reflection Util
 *
 * @author lin.peng
 * @since 1.0
 **/
public class ReflectionUtil {

    public static Class getClass(String var0, ClassLoader var1) {
        if (null == var1) {
            var1 = ReflectionUtil.class.getClassLoader();
        }

        try {
            return Class.forName(var0, false, var1);
        } catch (Throwable var3) {
            return null;
        }
    }

    public static Object invoke(Object var0, String var1, String[] var2, Object[] var3, ClassLoader var4) throws Exception {
        Class[] var5 = new Class[var2.length];

        for (int var6 = 0; var6 < var2.length; ++var6) {
            var5[var6] = getClass(var2[var6], var4);
            if (null == var5[var6]) {
                throw new Exception("Class: '" + var2[var6] + "' not found");
            }
        }

        return invoke(var0, var1, var5, var3);
    }

    public static Object invoke(Object var0, String var1, Class[] var2, Object[] var3) throws Exception {
        try {
            Method var4 = getMethod(var0, var1, var2, true);
            return var4.invoke(var0, var3);
        } catch (Throwable var5) {

            throw new Exception(var5);
        }
    }

    public static Method getMethod(Object var0, String var1, Class[] var2, boolean var3) throws Throwable {
        try {
            if (null == var2) {
                var2 = new Class[0];
            }

            Method var4 = var0.getClass().getMethod(var1, var2);


            return var4;
        } catch (NoSuchMethodException var5) {
            if (var3) {
                throw var5;
            }


        } catch (Throwable var6) {
            if (var3) {
                throw var6;
            }

        }

        return null;
    }
}
