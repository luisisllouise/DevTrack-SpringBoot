package cn.auroralab.devtrack;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@MapperScan("cn.auroralab.devtrack.mapper")
public class DevTrackSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevTrackSpringBootApplication.class, args);
    }
}
