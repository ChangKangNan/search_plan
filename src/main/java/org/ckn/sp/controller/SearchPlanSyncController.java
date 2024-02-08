package org.ckn.sp.controller;

import org.ckn.sp.service.IPlanSpiltService;
import org.ckn.sp.service.ISearchPlanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author ckn
 * @date 2023/1/12
 */
@RestController
@RequestMapping("/sync")
public class SearchPlanSyncController {
    @Resource
    ISearchPlanService searchPlanService;
    @Resource
    IPlanSpiltService planSpiltService;

    @PutMapping
    public boolean sync(String resourceName) {
        planSpiltService.syncSearchPlan(resourceName);
        return true;
    }

    @GetMapping("/resources/get")
    public Map<String,String> getAllResource() {
        return searchPlanService.getAllResource();
    }
}
