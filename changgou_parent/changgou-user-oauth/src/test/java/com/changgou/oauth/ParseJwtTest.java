package com.changgou.oauth;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

public class ParseJwtTest {

    @Test
    public void parseJwt(){
        //基于公钥去解析jwt
        String jwt ="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU5Mjk2Mjg3MCwiYXV0aG9yaXRpZXMiOlsiYWNjb3VudGFudCIsInVzZXIiLCJzYWxlc21hbiJdLCJqdGkiOiI0OTk0YzhkMS01ODM1LTQ3MzktODQyMi0zNGQwNmYxMmJlMjYiLCJjbGllbnRfaWQiOiJjaGFuZ2dvdSIsInVzZXJuYW1lIjoiaGVpbWEifQ.n6OEI7vRKW_JLrMP6zE-la-5QmtTC-Hy8-hnCVfPoQRfrBpi5bm0wZzwwOaSORq-IJtriLnKc-BVuKOSLOI6lSv4R2ASWjAch8ad_8HmhInQW-XsDcJCsfY23nL5N8qCPJZllbG3t3fauajerNksvGuV78ThNg6EAdBKcfXMqoMlFyLKBNJAFVhnpW3Z_cS8YObsd-YFh-aUYvB2HbCxOo6mkWfsudisullnWTV6GjtASNCVYaVJsdpDbn3H3D7Gak06Co_pN0MtBfACYlQaGSqQVgcDlW3wNIGPSwGbVaZEh21rkyyxpRRpUSuELnNkcCdc7K5JNqQQLK-gkTgUFQ";
        String publicKey ="-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvFsEiaLvij9C1Mz+oyAmt47whAaRkRu/8kePM+X8760UGU0RMwGti6Z9y3LQ0RvK6I0brXmbGB/RsN38PVnhcP8ZfxGUH26kX0RK+tlrxcrG+HkPYOH4XPAL8Q1lu1n9x3tLcIPxq8ZZtuIyKYEmoLKyMsvTviG5flTpDprT25unWgE4md1kthRWXOnfWHATVY7Y/r4obiOL1mS5bEa/iNKotQNnvIAKtjBM4RlIDWMa6dmz+lHtLtqDD2LF1qwoiSIHI75LQZ/CNYaHCfZSxtOydpNKq8eb1/PGiLNolD4La2zf0/1dlcr5mkesV570NxRmU1tFm8Zd3MZlZmyv9QIDAQAB-----END PUBLIC KEY-----";

        Jwt token = JwtHelper.decodeAndVerify(jwt, new RsaVerifier(publicKey));

        String claims = token.getClaims();
        System.out.println(claims);
    }
}
