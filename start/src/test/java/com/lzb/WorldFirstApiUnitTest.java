package com.lzb;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 阿里万里汇对接<br/>
 * Created on : 2023-09-24 13:59
 * @author lizebin
 */
class WorldFirstApiUnitTest extends BaseUnitTest {

    public static final String CLIENT_ID = "3J5Y8Y382Y3K3105035";
    public static final String WORLD_FIRST_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmVT7qqmcSBsw0yOjYRN4qe76gJkE+LOoZYBkTz89oUJKMB9Gh4a+PLkBkcNzsp77FSnryXSv3xHgpvAfm1Z+25BA1jivKfN+81z6ECRh6Iw64pOky88/vOYo2NHwZ3woOtQmwSs155oIpfiulGwEA/Nb0fwT7nKm8Ir6UC3+jHIyNQqowLo+LAafoPP+lHAkkduSnm4iasJAF/buM82InhCvSpywz9qMjMOcfRUsVTPyzh+9pQHkDFGgE7UqD3vJfANdq54dZllghp5PEnG3/k2x1nYgERCjBIHwTbTsaQfZ0kDAH7VWYCatccf8KYUzVS1J5ug/1nhLUs6xdKK+2QIDAQAB";
    public static final String CIDER_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiBMwCt/FA7NlSX0x0oYaxgAgbJy61K5MuvsxBNm7QvOzKNcC17kxoVh17gl1jKmZt+OVaWyzzxxg1P1KBtJvdIWRvt87XD31uYRe75F6NTWi0Okc6mJ0TgHYaZX25eufWiIDzR+rsyolaL3F+INfoXvIbygP8YG/wxdYqssRiUq546j2vlIIgmY9LpByEJwQcLEzaJqcw1lpfoogOLBa/opRVn5FavVaI58gHMqWCcc9LIohrqywyZehv6bV/1h0cZ1MN/7nnCF1S16iAZqL1Tqgo+RMKDzJ2zvRyMSl+N8ZXcmWVDalreaV4+mi8rZBBooJ4mEmXEFKHIB09+ALwQIDAQAB";
    public static final String CIDER_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCIEzAK38UDs2VJfTHShhrGACBsnLrUrky6+zEE2btC87Mo1wLXuTGhWHXuCXWMqZm345VpbLPPHGDU/UoG0m90hZG+3ztcPfW5hF7vkXo1NaLQ6RzqYnROAdhplfbl659aIgPNH6uzKiVovcX4g1+he8hvKA/xgb/DF1iqyxGJSrnjqPa+UgiCZj0ukHIQnBBwsTNompzDWWl+iiA4sFr+ilFWfkVq9VojnyAcypYJxz0siiGurLDJl6G/ptX/WHRxnUw3/uecIXVLXqIBmovVOqCj5EwoPMnbO9HIxKX43xldyZZUNq客户Wt5pXj6aLytkEGigniYSZcQUocgHT34AvBAgMBAAECggEAJKe2i1dYBPUwVamJ4EILuEaUdW2KznvdY4kC3WGhlhl7q4av11ily+a+bc7SgX+2TtjZiLqlfScR1o4cgNXy/Bp29km//csbBExqHnK7ztWR9GC3T1QSLGlG2Lpy9eCQ3oDHMVxUrkCuLxbf21/YRPHJPlg2Y0ImW/FQC0IEiUzZUr5Owg7zNzDYcyjt1A0I9w2GCxgaZDxxoNE/AwD5cp0dc3+LT/QKdIVY/hxQZ3BlE541pk5LsLQN5639DyivQx4LouTTu5LaYOdd9EW/f/9YxUOnYwBA2A1Yd/OVJhyuAI8fV31hp+vFNlcRKnGQg5I69L24E+LKUmOuC/d4AQKBgQC8rNzleFiSyxgT/oKUk7RNjvBnbmP2ADiWeAsAxWo+nmDcrSj1QxJ/SdQ5r30An0onaZ3R2kD1Oz7t4s/H+yFNhA6/ZEWe8zAxYqGBIgCoNpDPgY382eF5CJpea9zXSKxv5XuIlFbtpNh1HBBEp2uUKjSx9448fcEzn/SogMeCIQKBgQC4oWJzHtrm/toojLIAczTGkFZuSs7GIyuk3XI4L3BF6ZCdV57SPJpYGhe7twk3+bBEE8pyvZjcFOtMaqk4kxyUh3N6+ixw/uy2cPbqs/yOlv6bgC5kyXmf/lzh7a6vxtJnJ1Cxvxv/TZ/CYLbVjp+jEhAqi0+NTPwvHvxgumqVoQKBgBDyujkGsXYmNjh9kT9FcVnSMDgqS2JqrKqfh+V+1kdftLW9/ELjzoKOoDi6UdE/fcrCiwGxyEn/E20NBbsiDODIrwZ0PGjF0ZtuD7Ho2wRBOorZSWbgL4fOxQccS08vYQYAKDOhl5lrSGJkrfVs0JYToH4oDafTaAp6IOEOCF3hAoGBAI5AWZgVB9e+N9vSOzs5iEoM32ru3E2wv2mw7NX5Rum2wSNZZRbadpi20d5hcgrcqEBrdynchq/atkgUTfk272hIzGLN6fvNjhgrBTNkJYy/LcAljJk+2iS5WFsCQ/tOlsG/et7YPfwAlLyww23bC4ga3LXKzCo0TIEJwK3OM1zBAoGBAICf031sETlA3ICd1oynfFuLDIp7erRxZgWmh2fgASY1a98LR7nvm4F7XgUHeinILIiHLrdaQtS439DydEC8wf2Q+GlcQqyv048He+8vgjNoz7CP9kqYyIex567K/MJTrRkh3KHMSyX1oaSXrgvW4/6SYvCX2hlxXeOnLJie6LVe";

    @Test
    @DisplayName("构造header")
    void should_constructor_header() {
        Header header = Header.builder()
                .clientId(CLIENT_ID)
                .signature("signature")
                .requestTime(OffsetDateTime.of(2023, 1, 2, 2, 2, 2, 2, ZoneOffset.ofHours(8)))
                .build();
        assertJSON(header.formatToMap());
    }



}
