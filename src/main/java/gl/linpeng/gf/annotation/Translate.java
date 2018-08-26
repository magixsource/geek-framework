package gl.linpeng.gf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Function Translate markup
 *
 * @author lin.peng
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Translate {

    String value() default "";

    /**
     * Is enabled auto translate
     *
     * @return true if enabled
     */
    boolean enabled() default true;
}
