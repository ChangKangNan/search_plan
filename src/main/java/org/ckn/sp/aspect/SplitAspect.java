package org.ckn.sp.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.ckn.sp.annotation.InsertStrategyOrder;
import org.ckn.sp.annotation.UpdateStrategyOrder;
import org.ckn.sp.strategy.GenerateInsertStrategy;
import org.ckn.sp.strategy.GenerateUpdateStrategy;
import org.ckn.sp.strategy.SearchConfigComponentStrategy;
import org.ckn.sp.strategy.api.ISplitStrategy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ckn
 */
@Component
@Slf4j
@Aspect
public class SplitAspect {

    private final String BASE_PACKAGE = "org.ckn.sp.strategy";
    private final String RESOURCE_PATTERN = "/**/*.class";

    @Pointcut("@annotation(org.ckn.sp.annotation.LoadSplitConfig)")
    public void loadConfig() {
    }

    @Before("loadConfig()")
    public void before(JoinPoint joinPoint) {
        //spring工具类，可以获取指定路径下的全部类
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    ClassUtils.convertClassNameToResourcePath(BASE_PACKAGE) + RESOURCE_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            //MetadataReader 的工厂类
            MetadataReaderFactory factory = new CachingMetadataReaderFactory(resourcePatternResolver);
            Map<String, ISplitStrategy> initialObj = new HashMap<>();
            for (Resource resource : resources) {
                //用于读取类信息
                MetadataReader reader = factory.getMetadataReader(resource);
                //扫描到的class
                String classname = reader.getClassMetadata().getClassName();
                Class<?> clazz = Class.forName(classname);
                //判断是否有指定主解
                InsertStrategyOrder insertStrategyOrder = clazz.getAnnotation(InsertStrategyOrder.class);
                boolean assignableFrom = ISplitStrategy.class.isAssignableFrom(clazz);
                if (insertStrategyOrder != null && assignableFrom) {
                    ISplitStrategy splitStrategy = initialObj.get(classname);
                    if (splitStrategy == null) {
                        Object instance = clazz.newInstance();
                        initialObj.put(classname, (ISplitStrategy) instance);
                        GenerateInsertStrategy.addInsertStrategy((ISplitStrategy) instance);
                    } else {
                        GenerateInsertStrategy.addInsertStrategy(splitStrategy);
                    }
                }
                UpdateStrategyOrder updateStrategyOrder = clazz.getAnnotation(UpdateStrategyOrder.class);
                if (updateStrategyOrder != null && assignableFrom) {
                    ISplitStrategy splitStrategy = initialObj.get(classname);
                    if (splitStrategy == null) {
                        Object instance = clazz.newInstance();
                        initialObj.put(classname, (ISplitStrategy) instance);
                        GenerateUpdateStrategy.addUpdateStrategy((ISplitStrategy) instance);
                    } else {
                        GenerateUpdateStrategy.addUpdateStrategy(splitStrategy);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("配置类加载异常:{}", e.getMessage());
        }
    }
}
