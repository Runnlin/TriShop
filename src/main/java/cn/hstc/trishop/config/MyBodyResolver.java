//package cn.hstc.trishop.config;
//
//import org.springframework.core.MethodParameter;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.ModelAndViewContainer;
//import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver;
//
//import java.util.List;
//
//public class MyBodyResolver extends AbstractMessageConverterMethodArgumentResolver {
//
//    public MyBodyResolver(List<HttpMessageConverter<?>> converters) {
//        super(converters);
//    }
//    public MyBodyResolver(List<HttpMessageConverter<?>> converters, List<Object> requestResponseBodyAdvice) {
//        super(converters, requestResponseBodyAdvice);
//    }
//
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        return parameter.hasParameterAnnotation(MyBody.class);//绑定注解
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        String token = webRequest.getHeader("token");
//        // 判断头部token是否存在
//        if (null == token || token.isEmpty()) {
//            throw new Exception("token is null");
//        }
//
//        return readWithMessageConverters(webRequest, parameter, parameter.getParameterType());
//    }
//}
