package com.lzb;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class Demo {

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
    private static final String KEY_VERSION = "keyVersion";

    /**
     * RSA256
     */
    private static final String RSA_256 = "RSA256";



    private static final String signPrivateKey =  "MIIEvwIBADANBgkqhkiG...*****";

    private static final String verifyPublicKey = "MIIBIjANBg...*****";

    public static void main(String[] args) throws Exception {
        String httpMethod = "POST";
        String uriWithQueryString = "/amsin/api/v1/business/account/inquiryBalance";
        String clientId = "*****";
        String timeString = "2022-03-02T15:03:30+08:00";
        String content = "{\"transferFactor\":{\"transferFundType\":\"GLOBAL_WORLDFIRST\"},\"currency\":\"USD\"}";

        String signature = sign(httpMethod, uriWithQueryString, clientId, timeString, content, signPrivateKey);

        String signatureHeaderPayload = ALGORITHM +
                NAME_VALUE_SEPARATOR +
                RSA_256 +
                COMMA +
                KEY_VERSION +
                NAME_VALUE_SEPARATOR +
                "1" +
                COMMA +
                SIGNATURE +
                NAME_VALUE_SEPARATOR +
                signature;
        System.out.println("signatureHeaderPayload:\n" + signatureHeaderPayload);

        boolean res = verify(httpMethod, uriWithQueryString, clientId, timeString, content, signature, verifyPublicKey);
        if (res) {
            System.out.println("verify success.");
        }
    }

    /**
     * Sign the contents of the merchant request
     *
     * @param httpMethod         http method                e.g., POST, GET
     * @param uriWithQueryString query string in url        e.g., if your request url is https://{domain_name}.com/ams/api/pay/query uriWithQueryString should be /ams/api/pay/query not https://{domain_name}.com/ams/api/pay/query
     * @param clientId           clientId issued by WorldFirst  e.g., *****
     * @param timeString         "request-time" in request  e.g., 2020-01-03T14:36:27+08:00
     * @param reqBody            json format request        e.g., "{"paymentRequestId":"*****","refundRequestId":"*****","refundAmount":{"currency":"USD","value":"123"},"extendInfo":{"":""}}"
     * @param merchantPrivateKey your private key
     */
    public static String sign(
            String httpMethod,
            String uriWithQueryString,
            String clientId,
            String timeString,
            String reqBody,
            String merchantPrivateKey) throws Exception {
        // 1. construct the request content
        String reqContent = httpMethod + " " + uriWithQueryString + "\n" + clientId + "." + timeString + "." + reqBody;
        System.out.println("reqContent is " + "\n" + reqContent);

        // 2. sign with your private key
        String originalString = signWithSHA256RSA(reqContent, merchantPrivateKey);
        //  System.out.println("originalString is " + originalString);

        // 4. return the encoded String
        return URLEncoder.encode(originalString, "UTF-8");
    }

    /**
     * Check the response of WorldFirst
     *
     * @param httpMethod         http method                  e.g., POST, GET
     * @param uriWithQueryString query string in url          e.g., if your request url is https://{domain_name}.com/ams/api/pay/query uriWithQueryString should be /ams/api/pay/query not https://{domain_name}.com/ams/api/pay/query
     * @param clientId           clientId issued by WorldFirst    e.g., *****
     * @param timeString         "response-time" in response  e.g., 2020-01-02T22:36:32-08:00
     * @param rspBody            json format response         e.g., "{"acquirerId":"xxx","refundAmount":{"currency":"CNY","value":"123"},"refundFromAmount":{"currency":"JPY","value":"234"},"refundId":"xxx","refundTime":"2020-01-03T14:36:32+08:00","result":{"resultCode":"SUCCESS","resultMessage":"success","resultStatus":"S"}}"
     * @param worldfirstPublicKey    public key from WorldFirst
     */
    public static boolean verify(
            String httpMethod,
            String uriWithQueryString,
            String clientId,
            String timeString,
            String rspBody,
            String signature,
            String worldfirstPublicKey) throws Exception {
        // 1. construct the response content
        String responseContent = httpMethod + " " + uriWithQueryString + "\n" + clientId + "." + timeString + "." + rspBody;

        // 2. decode the signature string
        String decodedString = URLDecoder.decode(signature, "UTF-8");

        // 3. verify the response with WorldFirst's public key
        return verifySignatureWithSHA256RSA(responseContent, decodedString, worldfirstPublicKey);

    }


    /**
     * Generate base64 encoded signature using the sender's private key
     *
     * @param reqContent:    the original content to be signed by the sender
     * @param strPrivateKey: the private key which should be base64 encoded
     */
    private static String signWithSHA256RSA(String reqContent, String strPrivateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
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

    /**
     * Verify if the received signature is correctly generated with the sender's public key
     *
     * @param rspContent: the original content signed by the sender and to be verified by the receiver.
     * @param signature:  the signature generated by the sender
     * @param strPk:      the public key string-base64 encoded
     */
    private static boolean verifySignatureWithSHA256RSA(String rspContent, String signature, String strPk) throws Exception {
        PublicKey publicKey = getPublicKeyFromBase64String(strPk);

        Signature publicSignature = Signature.getInstance("SHA256withRSA");
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
}