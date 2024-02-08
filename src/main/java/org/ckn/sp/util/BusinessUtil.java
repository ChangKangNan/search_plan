package org.ckn.sp.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.ckn.sp.constants.SearchTypeEnum;
import org.ckn.sp.fm.bean.SearchConfigInfo;
import org.ckn.sp.fm.bean.SearchTableColumn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.ckn.sp.constants.StrConstants.*;

/**
 * @author ckn
 */
public class BusinessUtil {
    public static SearchConfigInfo getBaseInfo(String remarks){
        String[] messages = remarks.split(StrUtil.SPACE);
        SearchConfigInfo searchConfigInfo = new SearchConfigInfo();
        for (int i = 0; i < messages.length; i++) {
            String message = messages[i];
            if (i == 0) {
                searchConfigInfo.setSearchName(message.trim().replaceAll("\\(BLACK\\)", StrUtil.SPACE));
                continue;
            }
            if(message.contains("=")){
                String[] kv = message.split("=");
                String key = kv[0];
                String val = kv[1];
                val = val.replaceAll("\\(BLACK\\)", " ");
                if (key.equals("limit")) {
                    if (StrUtil.isBlank(searchConfigInfo.getSearchValueSource())) {
                        searchConfigInfo.setSearchValueSource(VALUES);
                    }
                    searchConfigInfo.setLimitSearchValues(val);
                }else {
                    BeanUtil.fillBeanWithMap(Collections.singletonMap(key, val), searchConfigInfo, true, false);
                }
            }else {
                SearchTypeEnum searchConditionsEnum = SearchTypeEnum.getSearchType(message.trim());
                searchConfigInfo.setSearchType(searchConditionsEnum.searchType);
                searchConfigInfo.setLimitSearchConditions(searchConditionsEnum.limitSearchConditions);
                if (message.equals(URL)) {
                    searchConfigInfo.setSearchValueSource(URL);
                }else if (message.equals(DICT)) {
                    searchConfigInfo.setSearchValueSource("dictionary");
                }else {
                    searchConfigInfo.setSearchValueSource("in");
                }
            }
        }
        if (StrUtil.isBlank(searchConfigInfo.getSearchType())) {
            searchConfigInfo.setSearchType(SearchTypeEnum.input.searchType);
            searchConfigInfo.setLimitSearchConditions(SearchTypeEnum.input.limitSearchConditions);
        }
        if (StrUtil.isBlank(searchConfigInfo.getSearchValueSource())) {
            searchConfigInfo.setSearchValueSource(IN);
        }

        return searchConfigInfo;
    }
}
