package com.ai.nppm.activitiWeb.service.impl;

import com.ai.nppm.activitiWeb.dao.PPMFlowDAO;
import com.ai.nppm.activitiWeb.service.PPMFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
/**
 * Created by IntelliJ IDEA.
 * Author: cjh
 * Date: 2017/11/29
 * Time: 10:33
 * Description：该类的作用
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PPMFlowServiceImpl implements PPMFlowService {

    @Autowired
    private PPMFlowDAO ppmFlow;

    @Override
    public void batchSaveTacheDetail(List<Map> list) {

        if (list!= null)
        {
            for (int i = 0; i < list.size(); i++) {
                Map map = list.get(i);

                ppmFlow.saveTacheDetail(map);
            }
        }
    }

    @Override
    public void saveFlowDetail(Map map) {

        ppmFlow.saveFlowDetail(map);
    }

    @Override
    public void batchSaveTransitionDetail(List<Map> list) {
        if (list!= null)
        {
            for (int i = 0; i < list.size(); i++) {
                Map map = list.get(i);

                ppmFlow.saveTransitionDetail(map);
            }
        }
    }


    @Override
    public List<Map> queryFlowDetail(Map map) {

        return ppmFlow.queryFlowDetail(map);
    }

    @Override
    public void removePPMDataByFlowId(Map map) {

        ppmFlow.removePPMDataByFlowId(map);
    }

    @Override
    public List<Map> queryTacheDetails(Map map) {

        return ppmFlow.queryTacheDetails(map);
    }

    @Override
    public List<Map> queryTransitionDetails(Map map) {
        return ppmFlow.queryTransitionDetails(map);
    }

    @Override
    public void updateTacheDetail(Map map) {

        ppmFlow.updateTacheDetail(map);
    }

    @Override
    public void updateTransitionDetail(Map map) {

        ppmFlow.updateTransitionDetail(map);
    }
}
