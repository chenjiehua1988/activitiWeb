package com.ai.nppm.activitiWeb.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Author: cjh
 * Date: 2017/11/27
 * Time: 10:50
 * Description：该类的作用
 * To change this template use File | Settings | File Templates.
 */

public interface PPMFlowService {

    /**
     * 批量保存tache_detail
     * @param list
     */
    void batchSaveTacheDetail(List<Map> list);

    /**
     * 批量保存transition_detail
     * @param list
     */
    void batchSaveTransitionDetail(List<Map> list);

    /**
     * 保存flow_detail
     * @param map
     */
    void saveFlowDetail(Map map);

    /**
     * 查询flow_detail
     * @param map
     * @return
     */
    List<Map> queryFlowDetail(Map map);
    List<Map> queryFlowDetailCount(Map map);
    List<Map> queryFlowInstCount(Map map);

    /**
     * 查询所有的流程 去重
     * @param map
     * @return
     */
    List<Map> queryFlowKeys(Map map);

    /**
     * 查询tache_detail
     * @param map
     * @return
     */
    List<Map> queryTacheDetails(Map map);

    /**
     * 查询transition_detail
     * @param map
     * @return
     */
    List<Map> queryTransitionDetails(Map map);

    /**
     * 删除ppm模型数据
     * @param map
     */
    void removePPMDataByFlowId(Map map);

    /**
     * 更新tache_detail
     * @param map
     */
    void updateTacheDetail(Map map);

    /**
     * 更新transition_detail
     * @param map
     */
    void updateTransitionDetail(Map map);

    /**
     * 获取flow_detail序列
     * @return
     */
    Long getFlowDetailSeq();

    /**
     * 查询角色列表
     * @param map
     * @return
     */
    List<Map> queryRoles(Map map);

    /**
     * 查询某一个环节的审批人列表
     * @param map
     * @return
     */
    List<Map> queryStaffsByTacheId(Map map);

    /**
     * 查询角色下的员工
     * @param map
     * @return
     */
    List<Map> queryStaffsByRoleId(Map map);

    /**
     * 新增环节审批人
     * @param map
     */
    void saveFlowOperator(Map map);

    /**
     * 删除环节审批人
     * @param map
     */
    void removeFlowOperator(Map map);

    /**
     * 更新环节审批人
     * @param map
     */
    void updateFlowOperator(Map map);

}
