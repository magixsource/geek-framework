package gl.linpeng.gf.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;

/**
 * jwt token工具类
 */
public class TokenUtil {

    public static final String ISSUER = "geek";
    public static final long EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    // 生成jwt token
    public static String generateToken(String secretKey, Map<String, String> payload) {
        String token = "";
        try {
            Date date = new Date();
            Date expiresDate = new Date(date.getTime() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTCreator.Builder builder = JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(date)
                .withExpiresAt(expiresDate);
            payload.forEach(builder::withClaim);
            return builder.sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    // 获取jwt token中的payload
    public static Map<String, Claim> getPayload(String secretKey, String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 验证jwt token
    public static boolean verifyToken(String secretKey, String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
