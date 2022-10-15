package cn.auroralab.devtrack;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.auroralab.devtrack.mapper")
public class DevTrackSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevTrackSpringBootApplication.class, args);
    }
}
