package com.codedata.rbac.utils;

import com.codedata.rbac.config.WeatherJwtProperties;
import lombok.RequiredArgsConstructor;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Base64;
@Component
@RequiredArgsConstructor
public class JwtTokenUtils {
    private final WeatherJwtProperties jwtProperties;

    public String generateToken() throws Exception {
        // 读取私钥并解析
        String privateKeyPem = jwtProperties.getPrivateKey()
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyPem);
        PKCS8EncodedKeySpec specKey = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = new EdDSAPrivateKey(specKey);

        // 构造 Header 和 Payload
        String headerJson = String.format("{\"alg\": \"EdDSA\", \"kid\": \"%s\"}", jwtProperties.getKid());
        long iat = ZonedDateTime.now(ZoneOffset.UTC).toEpochSecond() - 30;
        long exp = iat + jwtProperties.getTokenExpireSeconds();
        String payloadJson = String.format("{\"sub\": \"%s\", \"iat\": %d, \"exp\": %d}", jwtProperties.getSub(), iat, exp);

        // 编码 Header 和 Payload
        String headerEncoded = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(headerJson.getBytes(StandardCharsets.UTF_8));
        String payloadEncoded = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));
        String data = headerEncoded + "." + payloadEncoded;

        // 签名
        EdDSAParameterSpec edSpec = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
        Signature signature = new EdDSAEngine(MessageDigest.getInstance(edSpec.getHashAlgorithm()));
        signature.initSign(privateKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        byte[] sigBytes = signature.sign();

        String sigEncoded = Base64.getUrlEncoder().withoutPadding().encodeToString(sigBytes);
        return data + "." + sigEncoded;
    }
}
