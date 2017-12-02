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
     * @param flowId
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
}
