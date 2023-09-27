package org.ckn.sp.provider;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import org.ckn.sp.dto.SearchQueryDTO;
import org.ckn.sp.service.impl.SearchPlanServiceImpl;

import static cn.ft.ckn.fastmapper.constants.SQLConstants.ORDER_BY;

/**
 * @author ckn
 */
public class SearchOrderByProvider {
    public static void handle(SearchQueryDTO searchQueryDTO) {
        StrBuilder strBuilder=new StrBuilder(SearchPlanServiceImpl.QUERY_SQL_LOCAL.get());
        String orderBy = searchQueryDTO.getOrderBy();
        String direction = searchQueryDTO.getDesc() ? " DESC " : " ASC ";
        if (StrUtil.isNotBlank(orderBy)) {
            strBuilder.append(ORDER_BY).append(orderBy).append(direction).append(" ");
        }
        SearchPlanServiceImpl.QUERY_SQL_LOCAL.set(strBuilder.toString());
    }
}
