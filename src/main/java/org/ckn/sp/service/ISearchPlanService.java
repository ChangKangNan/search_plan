package org.ckn.sp.service;

import org.ckn.sp.dto.PlanFilterDTO;
import org.ckn.sp.dto.SearchPlanDTO;
import org.ckn.sp.dto.SearchQueryDTO;
import org.ckn.sp.dto.UserPlanDTO;
import org.ckn.sp.vo.PageInfo;
import org.ckn.sp.vo.SearchPageConfigVO;

import java.util.List;
import java.util.Map;

/**
 * @author ckn
 */
public interface ISearchPlanService {
    /**
     * 保存用户方案
     */
    void savePlan(SearchPlanDTO searchSavePlanDTO);

    /**
     * 生成默认方案
     */
    void generateDefault(String pagTag);

    /**
     * 重新生成刷新其他用户方案
     */
    void generateUserOverride(String pageTag);

    /**
     *获取所有资源文件
     */
    Map<String,String> getAllResource();

    /**
     *保存用户方案筛选项
     */
    PlanFilterDTO savePlanFilter(PlanFilterDTO planFilterDTO);

    /**
     *获取用户保存的筛选项配置信息
     */
    PlanFilterDTO getPlanFilter(Long planId);

    /**
     * 获取筛选字段
     */
    List<Map<String, String>> getFilterColumnByPlanId(Long planId);

    /**
     * 删除方案
     */
    Boolean delPlan(Long planId);

    /**
     *页面配置
     */
    SearchPageConfigVO getUserPageSearchConfig(String pageTag);

    /**
     *统一查询接口
     */
    PageInfo query(SearchQueryDTO queryDTO);

    /**
     *查询合计
     */
    PageInfo querySum(SearchQueryDTO searchQueryDTO);

    /**
     * 查询已保存的用户方案
     */
    List<UserPlanDTO> queryPlan(UserPlanDTO userPlanDTO);
}
