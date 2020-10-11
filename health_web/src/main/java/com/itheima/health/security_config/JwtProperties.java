package com.itheima.health.security_config;

import com.itheima.health.utils.jwt.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;


@Slf4j
@Data
@ConfigurationProperties(prefix = "health.jwt")
public class JwtProperties {
    private String pubKeyPath; // 公钥地址
    private String priKeyPath;// 私钥地址
    private int expire;// 过期时间
    // 公钥
    private PublicKey publicKey;
    // 私钥
    private PrivateKey privateKey;

    // <bean id="" class="" init-method="" />
    @PostConstruct
    public void init() {
        try {
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
            System.out.println(this.publicKey+"===============");
        } catch (Exception e){
            log.error("【授权中心】加载公钥和私钥失败！", e);
            throw new RuntimeException(e);
        }
    }
}
