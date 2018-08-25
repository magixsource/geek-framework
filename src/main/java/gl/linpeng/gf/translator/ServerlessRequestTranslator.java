package gl.linpeng.gf.translator;

import gl.linpeng.gf.base.ServerlessDTO;
import gl.linpeng.gf.base.ServerlessRequest;

/**
 * Serverless Request translator interface
 *
 * @author lin.peng
 * @version 1.0
 */
public interface ServerlessRequestTranslator<T extends ServerlessDTO> {

    /**
     * translate base request to DTO
     *
     * @param request base request object
     * @return Serverless data translate object
     */
    T translate(ServerlessRequest request);
}
