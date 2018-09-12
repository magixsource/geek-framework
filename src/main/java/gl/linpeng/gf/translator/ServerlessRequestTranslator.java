package gl.linpeng.gf.translator;

import gl.linpeng.gf.base.ServerlessRequest;

/**
 * Serverless request translator
 *
 * @author lin.peng
 * @since 1.0
 **/
public interface ServerlessRequestTranslator<T extends Object> {

    /**
     * translate base request to DTO
     *
     * @param request base request object
     * @return Serverless data translate object
     */
    T translate(ServerlessRequest request);
}
