import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class JwtUtil {


    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 从token中解析登录用户的基本信息
     * @param token http header中的jwt token
     * @param secret jwt secret
     * @return 如果成功解析到token中的用户信息则返回这些信息，否则返回null
     */
    public static LoginUser verify(String token, String secret) {
        if(StringUtils.isBlank(token) || StringUtils.isBlank(secret)) {
            return null;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            LoginUser user = new LoginUser();
            user.setCustomerNumber(jwt.getClaim("CustomerNumber").asLong());
            user.setEmail(jwt.getClaim("LoginName").asString());
            user.setName(jwt.getClaim("ContactWith").asString());
            return user;
        } catch (IllegalArgumentException | JWTVerificationException e) {
            logger.warn("Token not valid!", e);
            return null;
        }
    }

    /**
     * 生成一个jwt token (单元测试用)
     * @param user 登录用户
     * @param secret jwt secret
     * @param tokenLifeMilliseconds token life in milliseconds
     * @return jwt token
     */
    public static String generate(LoginUser user, String secret, Long tokenLifeMilliseconds) {
        if(user == null || StringUtils.isBlank(secret)) {
            return null;
        }
        if(tokenLifeMilliseconds <= 0) {
            tokenLifeMilliseconds = 2 * 60 * 60 * 1000L;
        }
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Date start = new Date();
        Date expire = new Date(start.getTime() + tokenLifeMilliseconds);
        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuedAt(start)
                .withExpiresAt(expire)
                .withClaim("CustomerNumber", user.getCustomerNumber())
                .withClaim("LoginName", user.getEmail())
                .withClaim("ContactWith", user.getName())
                .sign(algorithm);
    }
}
