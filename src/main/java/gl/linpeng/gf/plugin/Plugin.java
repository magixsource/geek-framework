package gl.linpeng.gf.plugin;

import java.util.Map;

/**
 * Plugin interface
 *
 * @author lin.peng
 * @since 1.0
 */
public interface Plugin {

    /**
     * invoke when plugin is ready to load
     */
    void onLoad();

    /**
     * invoke when plugin is calling
     *
     * @param params incoming params
     * @return plug handle result
     */
    Object onCall(Map<String, Object> params);

    /**
     * invoke when plugin is destroy
     */
    void onDestroy();
}
