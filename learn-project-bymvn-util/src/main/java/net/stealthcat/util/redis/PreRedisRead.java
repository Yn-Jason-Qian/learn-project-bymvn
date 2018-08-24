package net.stealthcat.util.redis;

import java.lang.annotation.*;

/**
 * @author y_qiana
 * @date 2018/8/23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PreRedisRead {

    String name();

    String[] indexes();

    boolean getFromDBWhenLess() default false;

    String host();

    DataType dataType();

//    boolean autoSwitchRedisCluster() default true;
}
