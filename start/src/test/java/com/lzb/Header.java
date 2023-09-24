package com.lzb;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.text.StrFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import static com.lzb.WorldFirstApi.CLIENT_ID;

/**
 * 请求头<br/>
 * Created on : 2023-09-24 14:03
 * @author lizebin
 */
@Getter
@Builder
public class Header {

    @NonNull
    private Signature signature;

    @NonNull
    private OffsetDateTime requestTime;

    @Builder.Default
    private String contentType = "application/json";

    public Map<String, String> formatToMap() throws Exception {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("ClientId", "Client-Id:" + CLIENT_ID);
        headerMap.put("Signature", StrFormatter.format("Signature:algorithm={}, keyVersion={}, signature={}",
                signature.getAlgorithm(),
                signature.getKeyVersion(),
                signature.sign(getRequestTimeFormat())));
        headerMap.put("Content-Type", "Content-Type:" + contentType);
        headerMap.put("Request-Time", "Request-Time:" + getRequestTimeFormat());
        return headerMap;
    }

    @NotNull
    private String getRequestTimeFormat() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX").format(requestTime);
    }
}
