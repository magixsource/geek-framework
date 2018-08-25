package gl.linpeng.gf.translator;

import gl.linpeng.gf.base.PlainTextDTO;
import gl.linpeng.gf.base.ServerlessRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class PlainTextServerlessRequestTranslatorTest {
    PlainTextServerlessRequestTranslator translator;
    ServerlessRequest.Builder builder;

    @Before
    public void setUp() {
        translator = new PlainTextServerlessRequestTranslator();
        builder = new ServerlessRequest.Builder();
    }

    @Test
    public void translate() {
        String text = "sfdsfdajflafjld";
        ServerlessRequest request = builder.setRawBody(text).build();
        PlainTextDTO dto = translator.translate(request);
        Assert.assertTrue(dto != null);
        Assert.assertEquals(dto.getContent(), text);
    }
}
