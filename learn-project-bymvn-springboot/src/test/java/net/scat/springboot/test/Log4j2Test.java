package net.scat.springboot.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class Log4j2Test {

    @Test
    public void test() {
        log.info("test");
    }

}
