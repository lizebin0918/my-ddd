package com.lzb.component.env;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import static com.lzb.component.env.Constants.*;

@Component
public class EnvHelper {

    @Value("${spring.profiles.active:dev}")
    private String env;

    public boolean isPrd() {
        return Objects.equals(PRD, env);
    }

    public boolean isTest() {
        return Objects.equals(TEST, env);
    }

    public boolean isDev() {
        return Objects.equals(DEV, env);
    }

    public boolean isLocal() {
        return Objects.equals(LOCAL, env);
    }

}