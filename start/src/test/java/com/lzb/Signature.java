package com.lzb;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import static com.lzb.WorldFirstApi.*;

/**
 * 签名<br/>
 * Created on : 2023-09-24 14:43
 * @author lizebin
 */
@Getter
@Builder(toBuilder = true)
public class Signature {

    /**
     * name and value separator
     */
    private static final String NAME_VALUE_SEPARATOR = "=";

    /**
     * comma
     */
    private static final String COMMA = ",";

    /**
     * algorithm
     */
    private static final String ALGORITHM = "algorithm";

    /**
     * signature
     */
    private static final String SIGNATURE = "signature";

    /**
     * keyVersion
     */
    private static final String KEY_VERSION = "2";

    /**
     * RSA256
     */
    private static final String RSA_256 = "RSA256";

    /**
     * 验签公钥
     */
    @Builder.Default
    private String verifyPublicKey = WorldFirstApi.WORLD_FIRST_PUBLIC_KEY;

    @NonNull
    private String httpMethod;

    @NonNull
    private String uri;

    @NonNull
    private String requestBody;


    public String sign(String requestTime) throws Exception {
        String reqContent = httpMethod + " " + uri + "\n" + CLIENT_ID + "." + requestTime + "." + requestBody;
        System.out.println("reqContent is " + "\n" + reqContent);

        String originalString = signWithSHA256RSA(reqContent, CIDER_PRIVATE_KEY);

        return URLEncoder.encode(originalString, StandardCharsets.UTF_8);
    }

    /**
     * Generate base64 encoded signature using the sender's private key
     *
     * @param reqContent:    the original content to be signed by the sender
     * @param strPrivateKey: the private key which should be base64 encoded
     */
    private static String signWithSHA256RSA(String reqContent, String strPrivateKey) throws Exception {
        java.security.Signature privateSignature = java.security.Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(getPrivateKeyFromBase64String(strPrivateKey));
        privateSignature.update(reqContent.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = privateSignature.sign();

        return Base64.getEncoder().encodeToString(bytes);
    }

    private static PrivateKey getPrivateKeyFromBase64String(String privateKeyString) throws Exception {
        byte[] b1 = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    private static boolean verifySignatureWithSHA256RSA(String rspContent, String signature, String strPk) throws Exception {
        PublicKey publicKey = getPublicKeyFromBase64String(strPk);

        java.security.Signature publicSignature = java.security.Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(rspContent.getBytes(StandardCharsets.UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(signature);
        return publicSignature.verify(signatureBytes);
    }


    private static PublicKey getPublicKeyFromBase64String(String publicKeyString) throws Exception {
        byte[] b1 = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(b1);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(X509publicKey);
    }

    String getAlgorithm() {
        return RSA_256;
    }

    String getKeyVersion() {
        return KEY_VERSION;
    }
}
