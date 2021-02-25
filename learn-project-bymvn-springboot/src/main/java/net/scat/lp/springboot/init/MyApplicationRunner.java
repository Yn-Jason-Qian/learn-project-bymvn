package net.scat.lp.springboot.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class MyApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("System start, source args = " + Arrays.toString(args.getSourceArgs()));
        log.info("Non option args = " + args.getNonOptionArgs());
        for (String optionName : args.getOptionNames()) {
            log.info("Option args' name = " + optionName + ", value=" + args.getOptionValues(optionName));
        }
    }
}
