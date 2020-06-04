package cn.hstc.trishop;

import cn.hstc.trishop.pojo.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileProperties.class
})
public class TrishopApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrishopApplication.class, args);
    }
}
