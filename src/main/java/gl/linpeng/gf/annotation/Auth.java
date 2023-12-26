package gl.linpeng.gf.annotation;

import java.lang.annotation.*;

/**
 * Mark this method should be a auth method
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Auth {
}
