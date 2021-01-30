package net.scat.lp.springboot.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan(basePackages = "net.scat.lp.springboot.dao")
public class DataSourceConfig {
}
