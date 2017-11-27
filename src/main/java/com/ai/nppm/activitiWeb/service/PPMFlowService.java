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

    void batchSave(List<Map> list);
}
