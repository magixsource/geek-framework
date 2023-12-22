package gl.linpeng.gf.utils;

import com.auth0.jwt.interfaces.Claim;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TokenUtilTest {

    private String secretKey = "123rO2HAiOjE3MDM4MTQ3NTUsIm82";


    @Test
    public void testGenerateToken() {
        Map<String, String> payload = new HashMap<>();
        payload.put("name", "geek");
        payload.put("age", "20");
        String token = TokenUtil.generateToken(secretKey, payload);
        Assert.assertNotNull(token);
    }

    @Test
    public void testVerifyToken() {
        Map<String, String> payload = new HashMap<>();
        payload.put("name", "geek");
        payload.put("age", "20");
        String token = TokenUtil.generateToken(secretKey, payload);
        Assert.assertTrue(TokenUtil.verifyToken(secretKey, token));
    }

    @Test
    public void testGetPayload() {
        Map<String, String> payload = new HashMap<>();
        payload.put("name", "geek");
        payload.put("age", "20");
        String token = TokenUtil.generateToken(secretKey, payload);
        Map<String, Claim> claimMap = TokenUtil.getPayload(secretKey, token);
        Assert.assertNotNull(claimMap);
        Assert.assertEquals("geek", claimMap.get("name").asString());
    }

}
