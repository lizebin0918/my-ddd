package com.lzb;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.lzb.WorldFirstApi.*;

/**
 * 阿里万里汇对接<br/>
 * Created on : 2023-09-24 13:59
 * @author lizebin
 */
class WorldFirstApiUnitTest extends BaseUnitTest {

    @Test
    @DisplayName("构造header")
    void should_constructor_header() throws Exception {

        Header header = Header.builder()
                .signature(getSignature())
                .requestTime(OffsetDateTime.of(2023, 1, 2, 2, 2, 2, 2, ZoneOffset.ofHours(8)))
                .build();
        assertJSON(header.formatToMap());
    }

    private static Signature getSignature() {
        return Signature.builder()
                .httpMethod("POST")
                .uri("/amsin/api/v1/business/account/inquiryBalance")
                .requestBody("{\"transferFactor\":{\"transferFundType\":\"GLOBAL_WORLDFIRST\"},\"currency\":\"USD\"}")
                .build();
    }

    @Test
    @DisplayName("构造签名")
    void should_build_signature() throws Exception {
        String signature = Signature.builder()
                .httpMethod("POST")
                .uri("/amsin/api/v1/business/account/inquiryBalance")
                .requestBody("{\"transferFactor\":{\"transferFundType\":\"GLOBAL_WORLDFIRST\"},\"currency\":\"USD\"}")
                .build()
                .sign("2022-03-02T15:03:30+08:00");
        assertThat("F%2FOWHMzXqh8519ckwpAHrUDSrV2h%2B7ErKne0yL8Pg03TooyMDHotfcSsJzKBs3p1SSIZ7Ey1AkC8p%2FxFL3n51EkTPbwp"
                + "La5gAyPBPsZg9imNt1lFfJFgA4%2FnkSfBQuEwBkKZQCQTyDsn3Pkm1mU6N4TRyQzTt9RKabnm2AYRlP%2BjLL8Nbzh%2Bap0VsMcvt0qQyJ"
                + "2YhMVENlKw9Mxv5zXLca1opv5sE9wwPdB0SI5Sv3lJKV1c90T8L4AHOQwWg2tp%2BbRJL6PHqXosnwzezDTSdrAW2kV9x8wtc%2BRIcGNnDGQ"
                + "SPGBLlVpqHGg20%2FGJEO5CD65B0KNaNB92oAlk%2FgH2qw%3D%3D").isEqualTo(signature);
    }

}
