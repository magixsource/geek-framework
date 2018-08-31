package gl.linpeng.gf.config;

/**
 * Default values in whole function
 *
 * @author lin.peng
 * @since 1.0
 **/
public class FunctionConfig {

    public static final String PROFILE_DEV = "dev";
    public static final String PROFILE_PROD = "prod";

    public static final String Charset = "utf-8";

    /**
     * dev or prod
     */
    public String profile = PROFILE_DEV;

    public Cors cors = new Cors();

    /**
     * Plugin names
     */
    public String plugins;

    /**
     * Determine is function run in dev mode
     *
     * @return true if function is run in dev mode
     */
    public boolean isDev() {
        return PROFILE_DEV.equalsIgnoreCase(this.profile);
    }


    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Cors getCors() {
        return cors;
    }

    public void setCors(Cors cors) {
        this.cors = cors;
    }

    public String getPlugins() {
        return plugins;
    }

    public void setPlugins(String plugins) {
        this.plugins = plugins;
    }

    /**
     * Http CORS config object
     */
    public class Cors {
        public Boolean enabled = Boolean.TRUE;
        public String methods = "*";
        public String headers = "";
        public String origin = "*";

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getMethods() {
            return methods;
        }

        public void setMethods(String methods) {
            this.methods = methods;
        }

        public String getHeaders() {
            return headers;
        }

        public void setHeaders(String headers) {
            this.headers = headers;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }
    }
}
