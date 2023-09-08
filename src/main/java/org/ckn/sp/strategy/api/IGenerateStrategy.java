package org.ckn.sp.strategy.api;

import org.ckn.sp.fm.bean.SearchConfig;

/**
 * @author ckn
 */
public interface IGenerateStrategy {
    void handle(SearchConfig searchConfig);
}
