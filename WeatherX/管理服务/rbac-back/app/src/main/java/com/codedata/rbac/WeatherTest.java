package com.codedata.rbac;

import com.codedata.rbac.common.utils.JsonUtils;
import com.codedata.rbac.entity.WeatherDay;
import com.codedata.rbac.utils.WeatherDescriptionUtils;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.List;

public class WeatherTest {


    public static void getTooken() throws Exception {

        // Private key
        String privateKeyString = "-----BEGIN PRIVATE KEY-----" +
                "MC4CAQAwBQYDK2VwBCIEIOY/0PtNA6oezh+sG5Z8LJuGvMUQY7g87jZlNgs4T9+9" +
                "-----END PRIVATE KEY-----";
        privateKeyString = privateKeyString.trim().replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").trim();
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec encoded = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = new EdDSAPrivateKey(encoded);

        // Header
        String headerJson = "{\"alg\": \"EdDSA\", \"kid\": \"T9WJGY2WE4\"}";

        // Payload
        long iat = ZonedDateTime.now(ZoneOffset.UTC).toEpochSecond() - 30;
        long exp = iat + 3600;
        String payloadJson = "{\"sub\": \"2KTHAY5W3R\", \"iat\": " + iat + ", \"exp\": " + exp + "}";

        // Base64url header+payload
        String headerEncoded = Base64.getUrlEncoder().encodeToString(headerJson.getBytes(StandardCharsets.UTF_8));
        String payloadEncoded = Base64.getUrlEncoder().encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));
        String data = headerEncoded + "." + payloadEncoded;

        EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);

        // Sign
        final Signature s = new EdDSAEngine(MessageDigest.getInstance(spec.getHashAlgorithm()));
        s.initSign(privateKey);
        s.update(data.getBytes(StandardCharsets.UTF_8));
        byte[] signature = s.sign();

        String signatureString = Base64.getUrlEncoder().encodeToString(signature);

        System.out.println("Signature: \n" + signatureString);

        // Print Token
        String jwt = data + "." + signatureString;
        System.out.println("JWT: \n" + jwt);
    }

    public static void main(String[] args) throws Exception {
        String rawText = "[\n" +
                "        {\n" +
                "            \"fxDate\": \"2025-07-21\",\n" +
                "            \"sunrise\": \"05:03\",\n" +
                "            \"sunset\": \"19:26\",\n" +
                "            \"moonrise\": \"00:54\",\n" +
                "            \"moonset\": \"16:40\",\n" +
                "            \"moonPhase\": \"残月\",\n" +
                "            \"moonPhaseIcon\": \"807\",\n" +
                "            \"tempMax\": \"30\",\n" +
                "            \"tempMin\": \"24\",\n" +
                "            \"iconDay\": \"305\",\n" +
                "            \"textDay\": \"小雨\",\n" +
                "            \"iconNight\": \"307\",\n" +
                "            \"textNight\": \"大雨\",\n" +
                "            \"wind360Day\": \"0\",\n" +
                "            \"windDirDay\": \"北风\",\n" +
                "            \"windScaleDay\": \"1-3\",\n" +
                "            \"windSpeedDay\": \"3\",\n" +
                "            \"wind360Night\": \"0\",\n" +
                "            \"windDirNight\": \"北风\",\n" +
                "            \"windScaleNight\": \"1-3\",\n" +
                "            \"windSpeedNight\": \"3\",\n" +
                "            \"humidity\": \"93\",\n" +
                "            \"precip\": \"15.3\",\n" +
                "            \"pressure\": \"1009\",\n" +
                "            \"vis\": \"23\",\n" +
                "            \"cloud\": \"90\",\n" +
                "            \"uvIndex\": \"4\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"fxDate\": \"2025-07-22\",\n" +
                "            \"sunrise\": \"05:03\",\n" +
                "            \"sunset\": \"19:26\",\n" +
                "            \"moonrise\": \"01:47\",\n" +
                "            \"moonset\": \"17:48\",\n" +
                "            \"moonPhase\": \"残月\",\n" +
                "            \"moonPhaseIcon\": \"807\",\n" +
                "            \"tempMax\": \"32\",\n" +
                "            \"tempMin\": \"24\",\n" +
                "            \"iconDay\": \"305\",\n" +
                "            \"textDay\": \"小雨\",\n" +
                "            \"iconNight\": \"305\",\n" +
                "            \"textNight\": \"小雨\",\n" +
                "            \"wind360Day\": \"0\",\n" +
                "            \"windDirDay\": \"北风\",\n" +
                "            \"windScaleDay\": \"1-3\",\n" +
                "            \"windSpeedDay\": \"3\",\n" +
                "            \"wind360Night\": \"0\",\n" +
                "            \"windDirNight\": \"北风\",\n" +
                "            \"windScaleNight\": \"1-3\",\n" +
                "            \"windSpeedNight\": \"3\",\n" +
                "            \"humidity\": \"85\",\n" +
                "            \"precip\": \"5.0\",\n" +
                "            \"pressure\": \"1009\",\n" +
                "            \"vis\": \"24\",\n" +
                "            \"cloud\": \"78\",\n" +
                "            \"uvIndex\": \"3\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"fxDate\": \"2025-07-23\",\n" +
                "            \"sunrise\": \"05:04\",\n" +
                "            \"sunset\": \"19:25\",\n" +
                "            \"moonrise\": \"02:49\",\n" +
                "            \"moonset\": \"18:45\",\n" +
                "            \"moonPhase\": \"残月\",\n" +
                "            \"moonPhaseIcon\": \"807\",\n" +
                "            \"tempMax\": \"32\",\n" +
                "            \"tempMin\": \"25\",\n" +
                "            \"iconDay\": \"305\",\n" +
                "            \"textDay\": \"小雨\",\n" +
                "            \"iconNight\": \"151\",\n" +
                "            \"textNight\": \"多云\",\n" +
                "            \"wind360Day\": \"0\",\n" +
                "            \"windDirDay\": \"北风\",\n" +
                "            \"windScaleDay\": \"1-3\",\n" +
                "            \"windSpeedDay\": \"3\",\n" +
                "            \"wind360Night\": \"0\",\n" +
                "            \"windDirNight\": \"北风\",\n" +
                "            \"windScaleNight\": \"1-3\",\n" +
                "            \"windSpeedNight\": \"3\",\n" +
                "            \"humidity\": \"78\",\n" +
                "            \"precip\": \"0.4\",\n" +
                "            \"pressure\": \"1006\",\n" +
                "            \"vis\": \"25\",\n" +
                "            \"cloud\": \"40\",\n" +
                "            \"uvIndex\": \"5\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"fxDate\": \"2025-07-24\",\n" +
                "            \"sunrise\": \"05:05\",\n" +
                "            \"sunset\": \"19:24\",\n" +
                "            \"moonrise\": \"03:58\",\n" +
                "            \"moonset\": \"19:31\",\n" +
                "            \"moonPhase\": \"新月\",\n" +
                "            \"moonPhaseIcon\": \"800\",\n" +
                "            \"tempMax\": \"33\",\n" +
                "            \"tempMin\": \"24\",\n" +
                "            \"iconDay\": \"305\",\n" +
                "            \"textDay\": \"小雨\",\n" +
                "            \"iconNight\": \"104\",\n" +
                "            \"textNight\": \"阴\",\n" +
                "            \"wind360Day\": \"0\",\n" +
                "            \"windDirDay\": \"北风\",\n" +
                "            \"windScaleDay\": \"1-3\",\n" +
                "            \"windSpeedDay\": \"3\",\n" +
                "            \"wind360Night\": \"0\",\n" +
                "            \"windDirNight\": \"北风\",\n" +
                "            \"windScaleNight\": \"1-3\",\n" +
                "            \"windSpeedNight\": \"3\",\n" +
                "            \"humidity\": \"67\",\n" +
                "            \"precip\": \"0.0\",\n" +
                "            \"pressure\": \"1005\",\n" +
                "            \"vis\": \"25\",\n" +
                "            \"cloud\": \"7\",\n" +
                "            \"uvIndex\": \"11\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"fxDate\": \"2025-07-25\",\n" +
                "            \"sunrise\": \"05:06\",\n" +
                "            \"sunset\": \"19:24\",\n" +
                "            \"moonrise\": \"05:09\",\n" +
                "            \"moonset\": \"20:08\",\n" +
                "            \"moonPhase\": \"蛾眉月\",\n" +
                "            \"moonPhaseIcon\": \"801\",\n" +
                "            \"tempMax\": \"33\",\n" +
                "            \"tempMin\": \"23\",\n" +
                "            \"iconDay\": \"101\",\n" +
                "            \"textDay\": \"多云\",\n" +
                "            \"iconNight\": \"104\",\n" +
                "            \"textNight\": \"阴\",\n" +
                "            \"wind360Day\": \"0\",\n" +
                "            \"windDirDay\": \"北风\",\n" +
                "            \"windScaleDay\": \"1-3\",\n" +
                "            \"windSpeedDay\": \"3\",\n" +
                "            \"wind360Night\": \"0\",\n" +
                "            \"windDirNight\": \"北风\",\n" +
                "            \"windScaleNight\": \"1-3\",\n" +
                "            \"windSpeedNight\": \"3\",\n" +
                "            \"humidity\": \"66\",\n" +
                "            \"precip\": \"0.0\",\n" +
                "            \"pressure\": \"1003\",\n" +
                "            \"vis\": \"25\",\n" +
                "            \"cloud\": \"1\",\n" +
                "            \"uvIndex\": \"10\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"fxDate\": \"2025-07-26\",\n" +
                "            \"sunrise\": \"05:07\",\n" +
                "            \"sunset\": \"19:23\",\n" +
                "            \"moonrise\": \"06:20\",\n" +
                "            \"moonset\": \"20:38\",\n" +
                "            \"moonPhase\": \"蛾眉月\",\n" +
                "            \"moonPhaseIcon\": \"801\",\n" +
                "            \"tempMax\": \"33\",\n" +
                "            \"tempMin\": \"24\",\n" +
                "            \"iconDay\": \"101\",\n" +
                "            \"textDay\": \"多云\",\n" +
                "            \"iconNight\": \"151\",\n" +
                "            \"textNight\": \"多云\",\n" +
                "            \"wind360Day\": \"0\",\n" +
                "            \"windDirDay\": \"北风\",\n" +
                "            \"windScaleDay\": \"1-3\",\n" +
                "            \"windSpeedDay\": \"3\",\n" +
                "            \"wind360Night\": \"0\",\n" +
                "            \"windDirNight\": \"北风\",\n" +
                "            \"windScaleNight\": \"1-3\",\n" +
                "            \"windSpeedNight\": \"3\",\n" +
                "            \"humidity\": \"71\",\n" +
                "            \"precip\": \"0.0\",\n" +
                "            \"pressure\": \"1004\",\n" +
                "            \"vis\": \"25\",\n" +
                "            \"cloud\": \"1\",\n" +
                "            \"uvIndex\": \"10\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"fxDate\": \"2025-07-27\",\n" +
                "            \"sunrise\": \"05:07\",\n" +
                "            \"sunset\": \"19:22\",\n" +
                "            \"moonrise\": \"07:27\",\n" +
                "            \"moonset\": \"21:04\",\n" +
                "            \"moonPhase\": \"蛾眉月\",\n" +
                "            \"moonPhaseIcon\": \"801\",\n" +
                "            \"tempMax\": \"33\",\n" +
                "            \"tempMin\": \"24\",\n" +
                "            \"iconDay\": \"305\",\n" +
                "            \"textDay\": \"小雨\",\n" +
                "            \"iconNight\": \"150\",\n" +
                "            \"textNight\": \"晴\",\n" +
                "            \"wind360Day\": \"0\",\n" +
                "            \"windDirDay\": \"北风\",\n" +
                "            \"windScaleDay\": \"1-3\",\n" +
                "            \"windSpeedDay\": \"3\",\n" +
                "            \"wind360Night\": \"0\",\n" +
                "            \"windDirNight\": \"北风\",\n" +
                "            \"windScaleNight\": \"1-3\",\n" +
                "            \"windSpeedNight\": \"3\",\n" +
                "            \"humidity\": \"69\",\n" +
                "            \"precip\": \"0.0\",\n" +
                "            \"pressure\": \"1002\",\n" +
                "            \"vis\": \"24\",\n" +
                "            \"cloud\": \"16\",\n" +
                "            \"uvIndex\": \"9\"\n" +
                "        }\n" +
                "    ]";
        List<WeatherDay> weatherDays = JsonUtils.jsonToArrayList(rawText, WeatherDay.class);
        for (WeatherDay weatherDay : weatherDays) {
            System.out.println(weatherDay);
        }
        String s = WeatherDescriptionUtils.generateForecast(weatherDays);
        System.out.println(s);
        getTooken();
    }

}
