package org.ckn.sp.controller;

import org.ckn.sp.dto.PlanFilterDTO;
import org.ckn.sp.dto.SearchPlanDTO;
import org.ckn.sp.dto.SearchQueryDTO;
import org.ckn.sp.dto.UserPlanDTO;
import org.ckn.sp.service.ISearchPlanService;
import org.ckn.sp.vo.PageInfo;
import org.ckn.sp.vo.SearchPageConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 搜索配置控制器
 * 获取用户页面配置信息
 */
@RestController
@RequestMapping("/searchPlan")
public class SearchPlanController {

    @Autowired
    ISearchPlanService searchPlanService;

    @GetMapping("/queryPlans")
    List<UserPlanDTO> queryPlan(UserPlanDTO userPlanDTO) {
        return searchPlanService.queryPlan(userPlanDTO);
    }


    @PostMapping("/query")
    public PageInfo query(@RequestBody SearchQueryDTO queryDTO) {
       return searchPlanService.query(queryDTO);
    }

    /**
     * 保存用户方案
     *
     */
    @PostMapping
    public SearchPlanDTO save(@Validated @RequestBody SearchPlanDTO saveDTO) {
        searchPlanService.savePlan(saveDTO);
        return saveDTO;
    }

    @DeleteMapping
    public Boolean del(Long planId){
        searchPlanService.delPlan(planId);
        return true;
    }

    /**
     * 获取用户页面搜索配置
     */
    @GetMapping("/config")
    public SearchPageConfigVO get(String pageTag) {
        return searchPlanService.getUserPageSearchConfig(pageTag);
    }

    /**
     * 根据用户方案ID获取筛选字段列表
     */
    @GetMapping("/getFilterColumnByPlanId")
    public List<Map<String,String>> getFilterColumnByPlanId(Long planId) {
        return searchPlanService.getFilterColumnByPlanId(planId);
    }

    /**
     * 根据用户方案ID保存筛选项
     */
    @PostMapping("/savePlanFilter")
    public PlanFilterDTO savePlanFilter(@RequestBody PlanFilterDTO queryDTO) {
        return searchPlanService.savePlanFilter(queryDTO);
    }

    @GetMapping("/getPlanFilter/{planId}")
    public PlanFilterDTO getPlanFilter(@PathVariable Long planId) {
        return searchPlanService.getPlanFilter(planId);
    }

    @PostMapping("/querySum")
    public PageInfo querySum(@RequestBody SearchQueryDTO queryDTO) {
        return searchPlanService.querySum(queryDTO);
    }

}
