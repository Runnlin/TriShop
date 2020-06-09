package cn.hstc.trishop.config;

import io.swagger.annotations.AuthorizationScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.OAuth;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.hstc.trishop"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Tri-Shop APIs")
                .description("20171153班第三组项目\n" +
                        "<b>所有的符号{:””’’,}都必须使用英文字符；\n" +
                        "若一个大括号里有多个参数，在最后一个参数之前的参数最后都要加上逗号\n" +
                        "因为参数名和参数值(字符串类型)使用双引号括起来，所以参数名和参数值内部不能出现双引号</b>\n")
                .version("1.2")
                .build();
    }
}
