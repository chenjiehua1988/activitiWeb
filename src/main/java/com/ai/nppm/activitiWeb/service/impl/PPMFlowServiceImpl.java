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

    @Override
    public void transferToPPMModel(String processId) throws Exception{

        ProcessDefinition processDefinition= repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId(processId).singleResult();
        //获取流程资源的名称
        String sourceName = processDefinition.getResourceName();
        //获取流程资源
        InputStream inputStream = repositoryService.getResourceAsStream(
                processDefinition.getDeploymentId(),sourceName);
        //创建转换对象
        BpmnXMLConverter converter = new BpmnXMLConverter();
        //读取xml文件
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(inputStream);
        //将xml文件转换成BpmnModel
        BpmnModel bpmnModel = converter.convertToBpmnModel(reader);
        //验证bpmnModel是否为空
        Process process = bpmnModel.getMainProcess();
        Collection<FlowElement> collection= process.getFlowElements();

        Date now= new Date();

        //1、保存flow_detail
        Map flowDetail= new HashMap();
        flowDetail.put("flowId", FORMAT2.format(now));
        flowDetail.put("fPdKey", processDefinition.getKey());

        //2、保存所有的流程节点  tache_detail
        Map<String, Map<String, Object>> tacheMap= new HashMap<String, Map<String, Object>>();
        //需要保存到数据库的transition列表
        List<Map> transitionList= new ArrayList<Map>();


        int index= 1;
        StartEvent startEvent= null;
        for (Iterator<FlowElement> iterator = collection.iterator(); iterator.hasNext(); ) {
            FlowElement flowElement = iterator.next();

            //保存所有的流程节点
            if (isTache(flowElement))
            {
                String tacheId= FORMAT2.format(now)+ index;
                Map tacheDetail= new HashMap();
                tacheDetail.put("tacheId", tacheId);
                tacheDetail.put("flowId", FORMAT2.format(now));
                tacheDetail.put("tacheName", flowElement.getName());
                tacheDetail.put("activityName", flowElement.getId());
                tacheDetail.put("tacheDesc", flowElement.getName());
                tacheDetail.put("tacheSpecCd", "1");
                tacheDetail.put("tacheTypeCd", "1");
                if (flowElement instanceof StartEvent)
                {
                    //开始
                    tacheDetail.put("tacheTypeCd", "START_EVENT");
                    //保存开始节点
                    startEvent= (StartEvent) flowElement;
                }
                else if (flowElement instanceof EndEvent)
                {
                    //结束
                    tacheDetail.put("tacheTypeCd", "END_EVENT");
                }
                else if (flowElement instanceof UserTask)
                {
                    //第二个节点
                    UserTask userTask= (UserTask) flowElement;
                    List<SequenceFlow> list= userTask.getIncomingFlows();
                    for (int i = 0; i < list.size(); i++) {
                        SequenceFlow sequenceFlow = list.get(i);
                        if(sequenceFlow.getSourceRef().equals(startEvent.getId()))
                        {
                            //找到了
                            tacheDetail.put("tacheTypeCd", "-2");
                            break;
                        }
                    }
                }

                tacheDetail.put("weight", index);

                tacheMap.put(flowElement.getId(), tacheDetail);
                index++;
            }
        }

        //3、保存所有的流程线  transition_detail
        for (Iterator<FlowElement> iterator = collection.iterator(); iterator.hasNext(); ) {
            FlowElement flowElement = iterator.next();

            if(isTransition(flowElement))
            {
                SequenceFlow sequenceFlow= (SequenceFlow) flowElement;
                String sourceRef= sequenceFlow.getSourceRef();

                //来源必须是节点
                if(tacheMap.containsKey(sourceRef))
                {
                    String sourceRefName= tacheMap.get(sourceRef).get("tacheName").toString();
                    String sourceRefId= tacheMap.get(sourceRef).get("tacheId").toString();
                    List<Map<String, Object>> actualTargetTacheList= new ArrayList<Map<String, Object>>();
                    getActualTargetTacheList(actualTargetTacheList, tacheMap, collection, sourceRef);

                    for (int i = 0; i < actualTargetTacheList.size(); i++) {
                        Map<String, Object> stringObjectMap = actualTargetTacheList.get(i);

                        String transitionId= FORMAT2.format(now)+ index;
                        String transName= sourceRefName+ "->"+ stringObjectMap.get("targetRefName").toString();
                        String targetRefId= stringObjectMap.get("targetRefId").toString();

                        Map transitionDetail= new HashMap();
                        transitionDetail.put("transitionId", transitionId);
                        transitionDetail.put("transName", transName);
                        transitionDetail.put("transitionName", transName);
                        transitionDetail.put("transitionDesc", transName);
                        transitionDetail.put("transitionType", "0");
                        transitionDetail.put("fromTacheId", sourceRefId);
                        transitionDetail.put("toTacheId", targetRefId);
                        transitionDetail.put("flowId", FORMAT2.format(now));
                        transitionDetail.put("transitionSeq", index);

                        transitionList.add(transitionDetail);

                        index++;
                    }
                }
            }
        }

        //4、保存之前先删除表中老数据
        System.out.println(this.queryFlowDetail(flowDetail));


        //5、保存业务数据到数据库 flow_detail,tache_detail,transition_detail
        this.saveFlowDetail(flowDetail);

        List<Map> list= new ArrayList<Map>();
        for (String key: tacheMap.keySet()) {
            Map tacheDetail= tacheMap.get(key);

            list.add(tacheDetail);
        }
        this.batchSaveTacheDetail(list);

        this.batchSaveTransitionDetail(transitionList);
    }


    //判断是否是流程节点  开始、结束、用户任务
    private boolean isTache(FlowElement flowElement)
    {
        boolean res= false;
        if(flowElement instanceof StartEvent||flowElement instanceof EndEvent||flowElement instanceof UserTask)
        {
            res= true;
        }

        return res;
    }

    //判断是否是流程线
    private boolean isTransition(FlowElement flowElement)
    {
        boolean res= false;
        if(flowElement instanceof SequenceFlow)
        {
            res= true;
        }

        return res;
    }

    /**
     * 构建真实的结束节点列表
     * @param list
     * @param tacheMap
     * @param collection
     * @param sourceRef
     */
    private void getActualTargetTacheList(
            List<Map<String, Object>> list,
            Map<String, Map<String, Object>> tacheMap,
            Collection<FlowElement> collection,
            String sourceRef)
    {
        //遍历所有流程节点，找到 sourceRef做为开始节点的流程线
        for (Iterator<FlowElement> iterator = collection.iterator(); iterator.hasNext(); ) {
            FlowElement flowElement = iterator.next();
            //找到流程线
            if (isTransition(flowElement))
            {
                SequenceFlow sequenceFlow= (SequenceFlow) flowElement;
                if(sourceRef.equals(sequenceFlow.getSourceRef()))
                {
                    //找到了  获取结束节点
                    String targetRef= sequenceFlow.getTargetRef();
                    //判断是否是 节点
                    if (tacheMap.containsKey(targetRef))
                    {
                        Map<String, Object> map= new HashMap<String, Object>();
                        map.put("targetRefName", tacheMap.get(targetRef).get("tacheName").toString());
                        map.put("targetRefId", tacheMap.get(targetRef).get("tacheId").toString());

                        list.add(map);
                    }
                    else
                    {
                        //结束节点如果是非节点类型，比如 网关，继续往下找到 节点
                        getActualTargetTacheList(list, tacheMap, collection, targetRef);
                    }
                }
            }
        }

    }

    @Override
    public Long queryFlowDetail(Map map) {

        return ppmFlow.queryFlowDetail(map);
    }
}
