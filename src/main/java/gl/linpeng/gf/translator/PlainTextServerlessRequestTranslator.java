package gl.linpeng.gf.translator;

import com.alibaba.fastjson.JSON;
import gl.linpeng.gf.base.PlainTextDTO;
import gl.linpeng.gf.base.ServerlessRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Plain text serverless request translator
 * translate request body to plain text
 *
 * @author lin.peng
 * @since 1.0
 **/
public class PlainTextServerlessRequestTranslator implements ServerlessRequestTranslator<PlainTextDTO> {

    public static final Logger LOGGER = LoggerFactory.getLogger(PlainTextServerlessRequestTranslator.class);

    @Override
    public PlainTextDTO translate(ServerlessRequest request) {
        LOGGER.debug("Request {}", JSON.toJSONString(request));

        String text = request.getBody();
        PlainTextDTO dto = PlainTextDTO.build();
        dto.setContent(text);
        dto.setRawBody(text);
        return dto;
    }

    public void settClass(Class tClass) {
    }
}
