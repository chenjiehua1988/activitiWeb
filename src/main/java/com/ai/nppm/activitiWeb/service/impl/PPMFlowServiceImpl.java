package com.ai.nppm.activitiWeb.service.impl;

import com.ai.nppm.activitiWeb.dao.PPMFlowDAO;
import com.ai.nppm.activitiWeb.service.PPMFlowService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * Author: cjh
 * Date: 2017/11/27
 * Time: 10:58
 * Description：该类的作用
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PPMFlowServiceImpl implements PPMFlowService {


    @Autowired
    private PPMFlowDAO ppmFlow;

    @Autowired
    private RepositoryService repositoryService;
    private static final SimpleDateFormat FORMAT2= new SimpleDateFormat("MMddHHmmss");

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

}
