package com.lzb;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * 请求头<br/>
 * Created on : 2023-09-24 14:03
 * @author lizebin
 */
@Getter
@Builder
public class Header {

    @NonNull
    private String clientId;

    @NonNull
    private String signature;

    @NonNull
    private OffsetDateTime requestTime;

    @Builder.Default
    private String contentType = "application/json";

    public Map<String, String> formatToMap() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("ClientId", "Client-Id:" + clientId);
        headerMap.put("Signature", "Signature:algorithm=RSA256, keyVersion=2, signature=" + signature);
        headerMap.put("Content-Type", "Content-Type:" + contentType);
        headerMap.put("Request-Time", "Request-Time:" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX").format(requestTime));
        return headerMap;
    }
}
