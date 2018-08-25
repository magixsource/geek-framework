package gl.linpeng.gf.annotation;

import java.lang.annotation.*;

/**
 * Json request markup
 *
 * @author lin.peng
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface JsonRequest {
}
