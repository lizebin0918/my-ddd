package com.lzb.adapter.web.intercepter;

import com.lzb.component.dto.ResponseDto;
import com.lzb.component.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @Autowired
    RedissonClient redissonClient;

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseDto<Void> argumentMissingError(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
       return null;
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDto<Void> methodArgumentNotValidException(HttpServletRequest request, HttpServletResponse response, Object handler, MethodArgumentNotValidException ex) {
        return null;
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseDto<Void> constraintViolationException (HttpServletRequest request, HttpServletResponse response, Object handler, ConstraintViolationException ex) {
        /*ResponseDto<Void> baseResponse = new ResponseDto<Void>();
        baseResponse.setCode("PARAM_ERROR");
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(constraintViolation -> {
            errors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        });
        baseResponse.setMsg(errors.toString());
        Map<String, String> map = new HashMap<>();
        map.put("uri", request.getRequestURI());
        map.put("msg", ex.getMessage());
        MonitorUtil.monitorWithTags(runtimeMetricsKey, map);
        return baseResponse;*/
        return null;
    }


    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseDto<Void> argumentMissingError(HttpServletRequest request, HttpServletResponse response, Object handler, HttpRequestMethodNotSupportedException ex) {
        /*ResponseDto<Void> baseResponse = new ResponseDto<Void>();
        baseResponse.setCode("REQUEST_METHOD_ERROR");
        String message = ex.getMessage();
        baseResponse.setMsg(message);
        Map<String, String> map = new HashMap<>();
        map.put("uri", request.getRequestURI());
        map.put("msg", ex.getMessage());
        MonitorUtil.monitorWithTags(runtimeMetricsKey, map);
        return baseResponse;*/
        return null;
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseDto<Void> methodArgumentTypeMismatchException(HttpServletRequest request, HttpServletResponse response, Object handler, MethodArgumentTypeMismatchException ex) {
        /*ResponseDto<Void> baseResponse = new ResponseDto<Void>();
        baseResponse.setCode("PARAM_ERROR");
        String message = ex.getMessage();
        baseResponse.setMsg(message);
        Map<String, String> map = new HashMap<>();
        map.put("uri", request.getRequestURI());
        map.put("msg", ex.getMessage());
        MonitorUtil.monitorWithTags(runtimeMetricsKey, map);
        return baseResponse;*/
        return null;
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BindException.class)
    public ResponseDto<Void> bindError(HttpServletRequest request, HttpServletResponse response, Object handler, BindException ex) {
        /*String errMessage = ex.getFieldErrors().stream().map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage()).collect(Collectors.joining(","));
        ResponseDto<Void> baseResponse = new ResponseDto<Void>();
        baseResponse.setCode("PARAM_ERROR");
        baseResponse.setMsg(errMessage);
        if (log.isWarnEnabled()) {
            log.warn("server param error ", ex);
        }
        Map<String, String> map = new HashMap<>();
        map.put("uri", request.getRequestURI());
        map.put("msg", ex.getMessage());
        MonitorUtil.monitorWithTags(runtimeMetricsKey, map);
        return baseResponse;*/
        return null;
    }

    /**
     * 抓取所有的错误
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseDto<Void> defaultErrorHandle(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
        /*log.error("defaultErrorHandle server error ", ex);
        ResponseDto<Void> baseResponse = new ResponseDto<Void>();
        baseResponse.setCode("SERVICE_ERROR");
        if (plutusConfig.isPrd()) {
            baseResponse.setMsg("Server busy");
            String uri = request.getRequestURI();
            Map<String, String[]> parameterMap = request.getParameterMap();
            String parameterJson = JSON.toJSONString(parameterMap);
            String body = new String(StreamUtils.copyToByteArray(request.getInputStream()), request.getCharacterEncoding());

            // 删掉多余的转义字符
            String errorStackMsg = ExceptionUtil.getStackTrace(ex).replaceAll("\\n\\tat","\nat");
            if (errorStackMsg.getBytes(StandardCharsets.UTF_8).length > FEISHU_MESSAGE_HASH_MAX_LENGTH) {
                errorStackMsg = errorStackMsg.substring(0,  new String(new byte[FEISHU_MESSAGE_HASH_MAX_LENGTH]).length());
            }
            String key = uri + parameterJson + body;
            String requestKey = DigestUtils.md5DigestAsHex(key.getBytes(StandardCharsets.UTF_8));
            RAtomicLong atomicLong = redissonClient.getAtomicLong(PLUTUS_EXCEPTION_KEY + requestKey);
            if (atomicLong.isExists()) {
                return baseResponse;
            } else {
                atomicLong.set(1);
                atomicLong.expire(5, TimeUnit.MINUTES);
            }


            Optional<GitUtil.GitlabUser> gitlabUserInfoOpt = GitUtil.getGitlabUserInfoNotEx(ex);

            ExFeishuContent exFeishuContent = new ExFeishuContent();
            if (gitlabUserInfoOpt.isPresent()) {
                GitUtil.GitlabUser gitlabUser = gitlabUserInfoOpt.get();
                if (StringUtils.isEmpty(gitlabUser.getFeishuOpenId())) {
                    exFeishuContent.addText(gitlabUser.getName() + " " + gitlabUser.getEmail());
                } else {
                    exFeishuContent.addAt(gitlabUser.getFeishuOpenId(), gitlabUser.getName());
                }
            }

            exFeishuContent.addAtAll();
            exFeishuContent.addText("请求param: ", parameterJson);
            exFeishuContent.addText("skywalking Context: ", Objects.toString(TraceContext.traceId(), ""));
            exFeishuContent.addText("操作人: ", RequestThreadUtils.getUserOptional().map(UserInfo::getName).orElse(""));
            exFeishuContent.addText("请求body: ", StringEscapeUtils.unescapeJson(body).replaceAll(System.lineSeparator(), ""));
            exFeishuContent.addText("异常: ", errorStackMsg);
            feishuRobot.sendRichText("接口" + uri, FeishuContent.EXCEPTION, FeishuRobot.FeishuWebhookEnum.UNCAUGHT_EXCEPTION, JSON.toJSONString(exFeishuContent.getContent()));
            Map<String, String> map = new HashMap<>();
            map.put("uri", request.getRequestURI());
            map.put("msg", errorStackMsg);
            MonitorUtil.monitorWithTags(runtimeMetricsKey, map);
        } else {
            baseResponse.setMsg("错误消息:" + ex.getMessage());
        }
        return baseResponse;*/
        return null;
    }


    /**
     * 所有的业务错误
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BizException.class)
    public ResponseDto<Void> bizExceptionHandle(HttpServletRequest request, HttpServletResponse response, Object handler, BizException bizException) {
        /*ResponseDto<Void> baseResponse = new ResponseDto<Void>();
        baseResponse.setCode(bizException.getCode());
        String serverBusy = Optional.ofNullable(bizException.getMessage()).orElse("Server busy");
        baseResponse.setMsg(serverBusy);
        if (log.isDebugEnabled()) {
            log.debug("business err {} ", bizException.getMessage());
        }
        Map<String, String> map = new HashMap<>();
        map.put("uri", request.getRequestURI());
        map.put("msg", serverBusy);
        MonitorUtil.monitorWithTags(bizMetricsKey, map);
        return baseResponse;*/
        return null;
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(NumberFormatException.class)
    public ResponseDto<Void> numberFormatExceptionHandle(HttpServletRequest request,
                                                         HttpServletResponse response,
                                                         Object handler, NumberFormatException numberFormatException) {
       /* ResponseDto<Void> baseResponse = new ResponseDto<Void>();
        baseResponse.setCode("NUMBER_FORMAT_ERROR");
        String msg = "输入的数据格式不对";
        baseResponse.setMsg(msg);
        if (log.isDebugEnabled()) {
            log.debug("numberFormatException err {} ", numberFormatException.getMessage());
        }
        Map<String, String> map = new HashMap<>();
        map.put("uri", request.getRequestURI());
        map.put("msg", msg);
        MonitorUtil.monitorWithTags(runtimeMetricsKey, map);
        return baseResponse;*/
        return null;
    }

    /**
     * POST请求参数格式转化异常
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseDto<Void> httpMessageNotReadableExceptionHandle(HttpServletRequest request,
                                                                   HttpServletResponse response,
                                                                   Object handler, HttpMessageNotReadableException httpMessageNotReadableException) {
        /*ResponseDto<Void> baseResponse = new ResponseDto<Void>();
        baseResponse.setCode("DATA_FORMAT_ERROR");
        String msg = "数据格式错误";
        baseResponse.setMsg(msg);
        if (log.isDebugEnabled()) {
            log.debug("httpMessageNotReadableException err {} ", httpMessageNotReadableException.getMessage());
        }
        Map<String, String> map = new HashMap<>();
        map.put("uri", request.getRequestURI());
        map.put("msg", msg);
        MonitorUtil.monitorWithTags(runtimeMetricsKey, map);
        return baseResponse;*/
        return null;
    }

}
