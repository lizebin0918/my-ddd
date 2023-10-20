package com.lzb.adapter.web.controller.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lzb.adapter.web.annotation.MyResponseBody;
import com.lzb.component.utils.json.JsonUtils;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <br/>
 * Created on : 2023-09-12 19:58
 * @author lizebin
 */
@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TestOrderController {

    /**
     * 测试RequestBody参数
     * @param order
     * @return
     */
    @MyResponseBody
    @PostMapping("/test")
    public TestOrder test(@RequestBody @Validated TestOrder order) {
        return order;
    }

    /**
     * body + 参数混合
     * @param order
     * @param uid
     * @param ids
     * @return
     */
    @MyResponseBody
    @PostMapping("/test1")
    public Map<String, Object> test1(@RequestBody @Validated TestOrder order, @RequestParam int uid, @RequestParam List<String> ids) {
        return Map.of("order", order, "uid", uid, "ids", ids);
    }

    @MyResponseBody
    @GetMapping("/test2")
    public Map<String, Object> test2(/*@ModelAttribute*/ TestQuery testQuery, String name, String[] names) {
        var map = new HashMap<String, Object>();
        map.put("testQuery", testQuery);
        map.put("name", name);
        map.put("names", names);
        return map;
    }

    @MyResponseBody
    @GetMapping("/name")
    public String getByName(@Size(min = 4, max = 10) String name) {
        return name;
    }

    /**
     * 请求多个参数
     * @param name
     * @param age
     * @return
     */
    @GetMapping("/testMultipleParamter")
    public String testMultipleParamter(String name, String age) {
        return name + "|" + age;
    }

    /**
     * 多个请求对象
     * @param name
     * @param height
     * @return
     */
    @MyResponseBody
    @GetMapping("/testMultipleObjectParamter")
    public Map<String, Object> testMultipleObjectParamter(FullName name, Height height) {
        return Map.of("name", name, "height", height);
    }

    @GetMapping("/testSleep")
    public String testSleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @PostMapping("/ids")
    public String ids(@RequestBody Ids ids) {
        return JsonUtils.toJSONString(ids);
    }

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("文件名 {} byteSize {}", file.getOriginalFilename(), file.getBytes().length);
        return "success";
    }

}
