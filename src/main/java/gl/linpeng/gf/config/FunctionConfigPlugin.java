package gl.linpeng.gf.config;

import com.alibaba.fastjson.JSON;
import gl.linpeng.gf.utils.ReflectionUtil;
import org.apache.bval.util.StringUtils;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Scan and load config in whole function life cycle
 *
 * @author lin.peng
 * @since 1.0
 **/
public class FunctionConfigPlugin {

    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(FunctionConfigPlugin.class);

    public static final String CONFIG_FILE = "config.properties";

    public static final Set<Class> BASIC_TYPE;

    static {
        BASIC_TYPE = new HashSet<>();
        BASIC_TYPE.add(String.class);
        BASIC_TYPE.add(Integer.class);
        BASIC_TYPE.add(Long.class);
        BASIC_TYPE.add(Character.class);
        BASIC_TYPE.add(Byte.class);
        BASIC_TYPE.add(Double.class);
        BASIC_TYPE.add(Float.class);
        BASIC_TYPE.add(Boolean.class);
    }

    private Properties properties;

    /**
     * Scan class path and load config
     */
    public void scan() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(CONFIG_FILE));
        } catch (IOException e) {
            logger.error("error while scan config, {}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Merge to config object
     *
     * @param config function config object
     */
    public void mergeTo(FunctionConfig config) {
        // init config object
        if (config == null) {
            config = new FunctionConfig();
        }
        if (this.properties == null) {
            scan();
        }

        // all fields in config
        autoSetValue(config, null);

        logger.debug(" ==== {} ", JSON.toJSONString(config, true));
    }

    private void autoSetValue(Object object, String prefix) {
        Field[] fields = object.getClass().getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (prefix != null) {
                fieldName = prefix.trim().concat(fieldName);
            }
            if (BASIC_TYPE.contains(field.getType())) {
                if (properties.containsKey(fieldName)) {
                    String value = properties.getProperty(fieldName);
                    Class fieldType = field.getType();
                    convertTo(fieldType, field, value, object);
                }
            } else {
                // get sub object
                Object innerObject = getObject(field, object);
                String innerPrefix = fieldName.toLowerCase().concat(".");
                autoSetValue(innerObject, innerPrefix);
                // set value for parent object
                setValueFor(object, field, innerObject);
            }

        }
    }

    /**
     * Set value for origin object
     *
     * @param object      origin object
     * @param field       field of origin object
     * @param innerObject field instance of origin object
     */
    private void setValueFor(Object object, Field field, Object innerObject) {
        String setter = "set" + StringUtils.capitalize(field.getName());
        try {
            ReflectionUtil.invoke(object, setter, new Class[]{field.getType()}, new Object[]{innerObject});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get field instance of object
     *
     * @param field  object field
     * @param object object
     * @return field instance
     */
    private Object getObject(Field field, Object object) {
        String methodPrefix = "get";
        if (Boolean.class.equals(field.getType())) {
            methodPrefix = "is";
        }
        String getter = methodPrefix + StringUtils.capitalize(field.getName());

        try {
            return ReflectionUtil.invoke(object, getter, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Convert properties value to object
     *
     * @param fieldType field type of object
     * @param field     field
     * @param value     properties value
     * @param object    object
     */
    private void convertTo(Class fieldType, Field field, String value, Object object) {
        String fieldName = field.getName();
        String setter = "set" + StringUtils.capitalize(fieldName);
        Class[] classes = new Class[]{fieldType};
        Object[] objects = null;
        if (String.class.equals(fieldType)) {
            objects = new String[]{value};
        } else if (Integer.class.equals(fieldType)) {
            objects = new Integer[]{Integer.valueOf(value)};
        } else if (Boolean.class.equals(fieldType)) {
            objects = new Boolean[]{Boolean.valueOf(value)};
        } else {
            logger.error("Can't convert properties {},{}", fieldName, value);
            throw new UnsupportedOperationException("Can't convert properties " + value);
        }

        try {
            ReflectionUtil.invoke(object, setter, classes, objects);
        } catch (Exception e) {
            logger.error("Can't invoke method {}", setter);
            e.printStackTrace();
        }
    }
}
