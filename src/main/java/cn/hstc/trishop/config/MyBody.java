//package cn.hstc.trishop.config;
//
//import org.springframework.core.MethodParameter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
//
//import java.lang.annotation.*;
//
//
///**
// * 自定义的json转对象方法，旨在解决json中有回车的问题
// */
//@Target({ElementType.PARAMETER})
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//public @interface MyBody {
//    boolean required() default true;
//}
