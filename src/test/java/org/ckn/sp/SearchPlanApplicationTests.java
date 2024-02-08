package org.ckn.sp;

import cn.hutool.core.date.DateUtil;
import org.ckn.sp.dto.SearchQueryDTO;
import org.ckn.sp.service.IPlanSpiltService;
import org.ckn.sp.service.ISearchPlanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
class SearchPlanApplicationTests {
    @Resource
    IPlanSpiltService planSpiltService;

    @Autowired
    ISearchPlanService searchPlanService;

    @Test
    public void splitSQL(){
        planSpiltService.split("采销匹配外贸查询发票明细.sql");
    }

    @Test
    public void testSearch(){
        SearchQueryDTO searchQueryDTO =new SearchQueryDTO();
        searchQueryDTO.setPageTag("cai_xiao_pi_pei_wai_mao_cha_xun_fa_piao_ming_xi");
        searchQueryDTO.setSearchPlanId(1L);
        List<SearchQueryDTO.QueryInfo> queryInfoList =new ArrayList<>();
        searchQueryDTO.setQueryInfoList(queryInfoList);
        searchPlanService.query(searchQueryDTO);
    }

}
