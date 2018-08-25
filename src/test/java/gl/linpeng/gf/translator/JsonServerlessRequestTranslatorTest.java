package gl.linpeng.gf.translator;

import gl.linpeng.gf.base.JsonDTO;
import gl.linpeng.gf.base.ServerlessDTO;
import gl.linpeng.gf.base.ServerlessRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Json test
 */
public class JsonServerlessRequestTranslatorTest {

    JsonServerlessRequestTranslator<ServerlessDTO> translator;
    ServerlessRequest.Builder builder;

    @Before
    public void setUp() throws Exception {
        translator = new JsonServerlessRequestTranslator();
        translator.settClass(JsonDTO.class);
        builder = new ServerlessRequest.Builder();
    }

    @Test
    public void translate() {
        // Their is a Json
        String jsonText = "{\"content\":\"abcdef\"}";
        ServerlessRequest request = builder.setRawBody(jsonText).build();
        JsonDTO dto = (JsonDTO) translator.translate(request);
        Assert.assertTrue(dto != null);
    }
}