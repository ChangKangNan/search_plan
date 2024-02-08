package org.ckn.sp.strategy;

import cn.hutool.core.collection.CollUtil;
import io.netty.util.concurrent.FastThreadLocal;
import org.ckn.sp.annotation.InsertStrategyOrder;
import org.ckn.sp.fm.bean.SearchConfig;
import org.ckn.sp.strategy.api.IGenerateStrategy;
import org.ckn.sp.strategy.api.ISplitStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ckn
 */
@Component
public class GenerateInsertStrategy implements IGenerateStrategy {

    private static final FastThreadLocal<List<ISplitStrategy>> handleList = new FastThreadLocal<>();

    @Override
    public void handle(SearchConfig searchConfig) {
        List<ISplitStrategy> splitStrategies = handleList.get();
        if(CollUtil.isEmpty(splitStrategies)){
            return;
        }
        splitStrategies.forEach(strategy -> strategy.split(searchConfig));
    }


    public static void addInsertStrategy(ISplitStrategy strategy) {
        List<ISplitStrategy> splitStrategies = handleList.get() == null ? new ArrayList<>() : handleList.get();
        splitStrategies.add(strategy);
        splitStrategies.sort((x, y) -> {
            int index_x = x.getClass().getAnnotation(InsertStrategyOrder.class).index();
            int index_y = y.getClass().getAnnotation(InsertStrategyOrder.class).index();
            return index_x - index_y;
        });
        handleList.set(splitStrategies);
    }
}
