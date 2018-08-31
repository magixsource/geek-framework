package gl.linpeng.gf.plugin;

import org.apache.bval.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Plugin manager
 *
 * @author lin.peng
 * @since 1.0
 **/
public class PluginManager {

    public static final Logger logger = LoggerFactory.getLogger(PluginManager.class);

    /**
     * plugin holder in plugin manager
     */
    public Map<String, Plugin> pluginHolder = new HashMap<>();

    /**
     * Load plugins
     *
     * @param plugins names of plugin
     */
    public void loadPlugin(String plugins) {
        String[] pluginNames = plugins.split(",");
        for (String pluginName : pluginNames) {
            logger.debug("ready to load plugin {}", pluginName);
            load(pluginName);
            logger.debug("loaded plugin {}", pluginName);
        }
    }

    /**
     * load plugin class by classloader
     *
     * @param pluginName plugin name
     */
    private void load(String pluginName) {
        String className = this.getClass().getPackage().getName() + "." + pluginName.toLowerCase() + "." + StringUtils.capitalize(pluginName) + "Plugin";
        logger.debug("loading plugin, className is {}", className);
        try {
            Class clz = this.getClass().getClassLoader().loadClass(className);
            Plugin plugin = (Plugin) clz.newInstance();
            plugin.onLoad();
            pluginHolder.put(pluginName.toLowerCase(), plugin);
        } catch (ClassNotFoundException e) {
            logger.error("fail to load plugin {} , cause by {} ", pluginName, e.getMessage());
            //e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.error("fail to load plugin {} , cause by {} ", pluginName, e.getMessage());
            //e.printStackTrace();
        } catch (InstantiationException e) {
            logger.error("fail to load plugin {} , cause by {} ", pluginName, e.getMessage());
            //e.printStackTrace();
        }
    }
}
