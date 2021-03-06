package com.ai.nppm.activitiWeb.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Author: cjh
 * Date: 2017/11/25
 * Time: 17:05
 * Description：该类的作用
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface PPMFlowDAO {

    void saveTacheDetail(Map map);
    void saveFlowDetail(Map map);
    void saveTransitionDetail(Map map);
    void removePPMDataByFlowId(Map map);
    List<Map> queryFlowDetail(Map map);
    List<Map> queryFlowDetailCount(Map map);
    List<Map> queryFlowInstCount(Map map);
    List<Map> queryFlowKeys(Map map);
    List<Map> queryTacheDetails(Map map);
    List<Map> queryTransitionDetails(Map map);
    void updateTacheDetail(Map map);
    void updateTransitionDetail(Map map);
    Long getFlowDetailSeq();
    List<Map> queryRoles(Map map);
    List<Map> queryStaffsByTacheId(Map map);
    List<Map> queryStaffsByRoleId(Map map);
    void saveFlowOperator(Map map);
    void removeFlowOperator(Map map);
    void updateFlowOperator(Map map);

}
