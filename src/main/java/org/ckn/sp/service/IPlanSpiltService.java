package org.ckn.sp.service;

/**
 * @author ckn
 * 查询方案配置生成服务
 */
public interface IPlanSpiltService {
    void split(String sqlPath);
    /**
     *同步查询方案
     */
    Boolean syncSearchPlan(String resourceName);
}
