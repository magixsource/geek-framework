package gl.linpeng.gf;

/**
 * Constant class
 *
 * @author lin.peng
 * @since 1.0
 **/
public class C {

    public enum Http {
        ;

        public final static String contentType = "content-type";
        public static final String TIMESTAMP = "ts";

        public enum StatusCode {
            OK(200), CREATED(201), BAD_REQUEST(400), UNAUTHORIZED(401), FORBIDDEN(403), NOT_FOUND(404), METHOD_NOT_ALLOW(405), INTERNAL_ERROR(500), SERVICE_UNAVAILABLE(503);

            private final int v;

            StatusCode(int v) {
                this.v = v;
            }

            public int v() {
                return this.v;
            }
        }

        public enum ContentType {
            ;

            public static final String JSON = "application/json";
            public static final String TEXT = "text/plain";
        }
    }
}
