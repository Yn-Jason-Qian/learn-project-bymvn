package net.stealthcat.util.redis;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @author y_qiana
 * @date 2018/8/23
 */
public class PreRedisReadAspect {

    private int limit = 100;

    private ConcurrentMap<Method, PreRedisReadConfig> redisReadConfigMap = new ConcurrentHashMap<>();
    /**
     * 支持常规的List和Object返回
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取被增强方法
        Signature signature = joinPoint.getSignature();
        if (!MethodSignature.class.isAssignableFrom(signature.getClass())) {
            return joinPoint.proceed();
        }
        Method method = ((MethodSignature) signature).getMethod();

        // 获取读取redis的配置
        PreRedisReadConfig redisReadConfig = redisReadConfigMap.get(method);
        if (redisReadConfig == null) {
            redisReadConfig = new PreRedisReadConfig(method);
            redisReadConfigMap.put(method, redisReadConfig);
        }
        if (!redisReadConfig.legalConfig) {
            return joinPoint.proceed();
        }

        // 组合查询的索引
        List<String> indexList = combineIndexes(redisReadConfig.prefix, joinPoint.getArgs());
        if (CollectionUtils.isEmpty(indexList)) {
            return joinPoint.proceed();
        }

        List<List<String>> indexPartitionList = Lists.partition(indexList, limit);

        Jedis jedis = redisReadConfig.jedisPool.getResource();
        if (jedis == null || !jedis.isConnected()) {
            return joinPoint.proceed();
        }

        List result = new ArrayList();
        for (List<String> indexPartition : indexPartitionList) {
            List<String> redisValues = jedis.mget(indexPartition.toArray(new String[0]));
            for (String redisValue : redisValues) {
                if (redisReadConfig.annotation.dataType() == DataType.ARR) {
                    result.addAll(JSON.parseArray(redisValue, redisReadConfig.resultClass));
                } else {
                    result.add(JSON.parseObject(redisValue, redisReadConfig.resultClass));
                }
            }
        }
        jedis.close();

        if (CollectionUtils.isEmpty(result)) {
            return joinPoint.proceed();
        }

        return null;
    }

    private static List<String> combineIndexes (String prefix, Object[] args) {
        if (StringUtils.isBlank(prefix) || ArrayUtils.isEmpty(args)) {
            return Collections.emptyList();
        }

        Set<String> result = null;

        for (Object arg : args) {
            // 如果有参数值为空，则不判断，一般原始方法里会判断参数是否为空，然后返回默认空值
            if (arg == null) {
                return Collections.emptyList();
            }

//            if (arg instanceof DalHints) {
//                continue;
//            }

            // 笛卡尔积
            if (CollectionUtils.isEmpty(result)) {
                if (Collection.class.isAssignableFrom(arg.getClass())) {
                    result = new HashSet<>();
                    for (Object o : ((Collection) arg)) {
                        result.add(prefix + "_" + o.toString());
                    }
                } else {
                    result = Sets.newHashSet(arg.toString());
                }
                continue;
            }

            Set<String> temp = new HashSet<>();
            if (Collection.class.isAssignableFrom(arg.getClass())) {
                for (Object o : ((Collection) arg)) {
                    for (String s : result) {
                        temp.add(s + "_" + o.toString());
                    }
                }
            } else {
                for (String s : result) {
                    temp.add(s + "_" + arg.toString());
                }
            }
            result = temp;
        }
        if (result == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(result);
    }

    private static class PreRedisReadConfig {
        PreRedisRead annotation;
        Method method;
        String prefix;
        Class resultClass;
        boolean isResultList;
        boolean legalConfig;
        JedisPool jedisPool;

        public PreRedisReadConfig(Method method) {
            this.method = method;
            this.annotation = method.getAnnotation(PreRedisRead.class);
            if (annotation == null) {
                legalConfig = false;
                return;
            }

            this.prefix = annotation.name() + "_" + String.join("_", annotation.indexes());

            Type genericReturnType = method.getGenericReturnType();
            if (genericReturnType instanceof ParameterizedType) {
                if (List.class.isAssignableFrom((Class) ((ParameterizedType) genericReturnType).getRawType())) {
                    resultClass = (Class) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
                    isResultList = true;
                }
            } else if (genericReturnType instanceof Class){
                resultClass = ((Class) genericReturnType);
            }
            if (resultClass == null) {
                legalConfig = false;
            }

            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            jedisPool = new JedisPool(config, annotation.host());
        }

    }

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());

        System.out.println(list.stream().map(Object::toString).collect(Collectors.toSet()));
    }
}
