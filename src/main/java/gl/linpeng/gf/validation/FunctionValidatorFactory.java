package gl.linpeng.gf.validation;


import org.apache.bval.jsr.ApacheValidationProvider;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Function validator factory
 *
 * @author lin.peng
 * @since 1.0
 */
public enum FunctionValidatorFactory {

    SINGLE_INSTANCE {
        ValidatorFactory avf = Validation.byProvider(ApacheValidationProvider.class).configure().buildValidatorFactory();

        @Override
        public Validator getValidator() {
            return avf.getValidator();
        }

    };

    /**
     * get a validator
     *
     * @return validator
     */
    public abstract Validator getValidator();
}
