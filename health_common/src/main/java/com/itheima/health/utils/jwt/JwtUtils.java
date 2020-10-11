package com.itheima.health.utils.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @create: 2018-05-26 15:43
 *
 *   从磁盘中获取公钥和私钥   对信息进行加密和解密
 *
 **/
public class JwtUtils {

    public  static final  String   TOKEN_NAME= "Authentication";
    public  static final  String   TOKEN_PREFIX= "BEARE";

    /**
     * 私钥加密token
     *
     * @param userInfo      载荷中的数据
     * @param privateKey    私钥
     * @param expireMinutes 过期时间，单位分钟/秒
     * @return
     * @throws Exception
     */
    public static String generateToken(UserInfo userInfo, PrivateKey privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim(JwtConstants.JWT_KEY_ID, userInfo.getId())
                .claim(JwtConstants.JWT_KEY_USER_NAME, userInfo.getUsername())
                .claim(JwtConstants.JWT_KEY_USER_ROLE, userInfo.getRoles())
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    public static String generateTokenInSeconds(UserInfo userInfo, PrivateKey privateKey, int expire) throws Exception {
        return Jwts.builder()
                .claim(JwtConstants.JWT_KEY_ID, userInfo.getId())
                .claim(JwtConstants.JWT_KEY_USER_NAME, userInfo.getUsername())
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     * 私钥加密token
     *
     * @param userInfo      载荷中的数据
     * @param privateKey    私钥字节数组
     * @param expireMinutes 过期时间，单位分
     * @return
     * @throws Exception
     */
    public static String generateToken(UserInfo userInfo, byte[] privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim(JwtConstants.JWT_KEY_ID, userInfo.getId())
                .claim(JwtConstants.JWT_KEY_USER_NAME, userInfo.getUsername())
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.RS256, RsaUtils.getPrivateKey(privateKey))
                .compact();
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥字节数组
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, byte[] publicKey) throws Exception {
        return Jwts.parser().setSigningKey(RsaUtils.getPublicKey(publicKey))
                .parseClaimsJws(token);
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static UserInfo getInfoFromToken(String token, PublicKey publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new UserInfo(
                ObjectUtils.toLong(body.get(JwtConstants.JWT_KEY_ID)),
                ObjectUtils.toString(body.get(JwtConstants.JWT_KEY_USER_NAME)),
                ObjectUtils.toString(body.get(JwtConstants.JWT_KEY_USER_ROLE))
        );
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static UserInfo getInfoFromToken(String token, byte[] publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new UserInfo(
                ObjectUtils.toLong(body.get(JwtConstants.JWT_KEY_ID)),
                ObjectUtils.toString(body.get(JwtConstants.JWT_KEY_USER_NAME)),null
        );
    }

    public static void main(String[] args) throws Exception {
        // 公钥和私钥的地址
        String publicKeyFilename = "D:/keys/id_rsa.pub";
        String privateKeyFilename = "D:/keys/id_rsa";
        // 获取公钥和私钥
        PublicKey publicKey = RsaUtils.getPublicKey(publicKeyFilename);
        PrivateKey privateKey = RsaUtils.getPrivateKey(privateKeyFilename);
         //  定义荷载：加密用户对象    生成token
        UserInfo userInfo = new UserInfo(2L, "Jack","a-b-c");
        String token = generateToken(userInfo, privateKey, 5);
//        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MywibmFtZSI6IkphY2sgTWEiLCJleHAiOjE1MTYyMzkwMjJ9.gom8PyFSPbNwkikLekYGpndgPTM8_REXvsk7IQ-ErtAY8m2vyIBAwD1k3GcA2xGCan-Orf65EtX0REbtuOtb2qlNyXrxtd-Hv-ttX94_SnPGf8rpnwiMIbul3fzdUoV4bNdhDnMP2hmhNVSmemvw_AQgfnqlgVNd1MY3IHIwHNE";
        System.out.println("token = " + token);
        //   从token中 获取用户信息， 解密操作
        UserInfo user = getInfoFromToken(token, publicKey);
        System.out.println(user);
    }
}
