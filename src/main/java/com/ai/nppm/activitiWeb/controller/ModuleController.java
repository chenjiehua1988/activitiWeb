package com.ai.nppm.activitiWeb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import com.ai.nppm.activitiWeb.service.PPMFlowService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import static com.ai.nppm.activitiWeb.util.ActivitiUtil.isTache;
import static com.ai.nppm.activitiWeb.util.ActivitiUtil.isTransition;

@Controller
@RequestMapping("/model")
public class ModuleController {
	
	private Logger logger = LoggerFactory.getLogger(ModuleController.class);
	private static final SimpleDateFormat FORMAT= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat FORMAT2= new SimpleDateFormat("MMddHHmmss");

	@Autowired
	private PPMFlowService ppmFlowService;

	@Autowired
	private RepositoryService repositoryService;


	@RequestMapping(value = "create")
	@ResponseBody
	public String create(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			String name= map.get("name").toString();
//			String key= map.get("key").toString();
			String description= map.get("description").toString();


		  ObjectMapper objectMapper = new ObjectMapper();
		  ObjectNode editorNode = objectMapper.createObjectNode();
		  editorNode.put("id", "canvas");
		  editorNode.put("resourceId", "canvas");
		  ObjectNode stencilSetNode = objectMapper.createObjectNode();
		  stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		  editorNode.put("stencilset", stencilSetNode);
		  Model modelData = repositoryService.newModel();

		  ObjectNode modelObjectNode = objectMapper.createObjectNode();
		  modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		  modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		  description = StringUtils.defaultString(description);
		  modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
		  modelData.setMetaInfo(modelObjectNode.toString());
		  modelData.setName(name);
//		  modelData.setKey(StringUtils.defaultString(key));

		  repositoryService.saveModel(modelData);
		  repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

		} catch (Exception e) {
		  logger.error("����ģ��ʧ�ܣ�", e);
		  res= "error";
		}

		return res;
	  }

	@RequestMapping(value = "queryModels")
	@ResponseBody
	public String queryModels(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			String index= map.get("index").toString();
			String size= map.get("size").toString();
			long count= repositoryService.createModelQuery().count();
			List<Model> modelList= repositoryService.createModelQuery().orderByCreateTime().desc().listPage(Integer.parseInt(index), Integer.parseInt(size));

			JSONArray jsonArray= new JSONArray();
			if (modelList!= null)
			{
				JSONObject jsonObject= null;
				for (int i = 0; i < modelList.size(); i++) {
					Model model = modelList.get(i);

					jsonObject= new JSONObject();
					JSONObject metaInfo= JSONObject.fromObject(model.getMetaInfo());
					jsonObject.put("description", metaInfo.getString("description"));
					jsonObject.put("id", model.getId());
					jsonObject.put("name", model.getName());
					jsonObject.put("key", model.getKey());
					jsonObject.put("createTime", FORMAT.format(model.getCreateTime()));
					jsonObject.put("lastUpdateTime", FORMAT.format(model.getLastUpdateTime()));

					jsonArray.add(jsonObject);
				}
			}

			JSONObject jsonObject= new JSONObject();
			jsonObject.put("total", count);
			jsonObject.put("data", jsonArray);
			res= jsonObject.toString();

		} catch (Exception e) {
			logger.error("��ѯģ���б��쳣��", e);
		}

		return res;
	}


	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			String modelIdList= map.get("modelIdList").toString();
			JSONArray jsonArray= JSONArray.fromObject(modelIdList);

			for (int i = 0; i < jsonArray.size(); i++) {

				String modelId= jsonArray.getString(i);
				repositoryService.deleteModel(modelId);
			}

		} catch (Exception e) {
			logger.error("��ѯģ���б��쳣��", e);
			res= "error";
		}

		return res;
	}

	@RequestMapping(value = "transferToPPMModel")
	@ResponseBody
	@Transactional
	public String transferToPPMModel(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map)
	{
		String res= "success";

		try {
			String processId= map.get("processId").toString();

			List<Map> flowDetailList= null;

			ProcessDefinition processDefinition= repositoryService
					.createProcessDefinitionQuery()
					.processDefinitionId(processId).singleResult();
			//��ȡ������Դ
			InputStream inputStream = repositoryService.getResourceAsStream(
					processDefinition.getDeploymentId(),processDefinition.getResourceName());
			//����ת������
			BpmnXMLConverter converter = new BpmnXMLConverter();
			//��ȡxml�ļ�
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader(inputStream);
			//��xml�ļ�ת����BpmnModel
			BpmnModel bpmnModel = converter.convertToBpmnModel(reader);
			//��֤bpmnModel�Ƿ�Ϊ��
			Process process = bpmnModel.getMainProcess();


			//���� �ڵ㻹��������
			List<FlowElement> collection= (List<FlowElement>) process.getFlowElements();
			List<FlowElement> collectionTache= new ArrayList<FlowElement>();
			List<FlowElement> collectionSequence= new ArrayList<FlowElement>();
			for (int i = 0; i < collection.size(); i++) {
				FlowElement flowElement = collection.get(i);
				if (isTache(flowElement))
				{
					collectionTache.add(flowElement);
				}
				else if(isTransition(flowElement))
				{
					collectionSequence.add(flowElement);
				}
			}


			//����һЩ����
			Date now= new Date();
			int index= 1;
			StartEvent startEvent= null;

			//1������flow_detail
			Map flowDetail= new HashMap();
			flowDetail.put("flowId", FORMAT2.format(now));
			flowDetail.put("fPdKey", processDefinition.getKey());
			flowDetail.put("name", processDefinition.getName());

			//2���������е����̽ڵ�  tache_detail
			Map<String, Map<String, Object>> tacheMap= new HashMap<String, Map<String, Object>>();
			for (FlowElement flowElement: collectionTache) {

				String tacheId= FORMAT2.format(now)+ index;
				Map tacheDetail= new HashMap();
				tacheDetail.put("tacheId", tacheId);
				tacheDetail.put("flowId", FORMAT2.format(now));
				tacheDetail.put("tacheName", flowElement.getName());
				tacheDetail.put("activityName", flowElement.getId());
				tacheDetail.put("tacheDesc", flowElement.getName());
				tacheDetail.put("tacheSpecCd", "1");
				tacheDetail.put("tacheTypeCd", "1");

				//�ж��Ƿ����Զ�����
				if(StringUtils.isNotEmpty(flowElement.getName())&&StringUtils.containsIgnoreCase(flowElement.getName(), "�Զ�"))
				{
					tacheDetail.put("tacheTypeCd", "-7");
				}

				//�ж��Ƿ�����ڷ�������˵��ɼ�
				if(StringUtils.isNotEmpty(flowElement.getName())&&("�Ʒ�����".equals(flowElement.getName())||"CRM����".equals(flowElement.getName())))
				{
					tacheDetail.put("tacheSpecCd", "6");
				}

				if (flowElement instanceof StartEvent)
				{
					//��ʼ
					tacheDetail.put("tacheTypeCd", "START_EVENT");
					//���濪ʼ�ڵ�
					startEvent= (StartEvent) flowElement;
				}
				else if (flowElement instanceof EndEvent)
				{
					//����
					tacheDetail.put("tacheTypeCd", "END_EVENT");
				}
				else if (flowElement instanceof UserTask)
				{
					UserTask userTask= (UserTask) flowElement;
					List<SequenceFlow> list= userTask.getIncomingFlows();
					for (int i = 0; i < list.size(); i++) {
						SequenceFlow sequenceFlow = list.get(i);
						if(sequenceFlow.getSourceRef().equals(startEvent.getId()))
						{
							//�ҵ���
							tacheDetail.put("tacheTypeCd", "-2");
							break;
						}
					}
				}

				tacheDetail.put("weight", index);

				tacheMap.put(flowElement.getId(), tacheDetail);
				index++;
			}

			//3���������е�������  transition_detail
			//��Ҫ���浽���ݿ��transition�б�
			List<Map> transitionList= new ArrayList<Map>();
			for (FlowElement flowElement: collectionSequence)
			{
				SequenceFlow sequenceFlow= (SequenceFlow) flowElement;
				String sourceRef= sequenceFlow.getSourceRef();

				//��Դ�����ǽڵ�
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

			//4������֮ǰ��ɾ������������
			flowDetailList= ppmFlowService.queryFlowDetail(flowDetail);
			if (flowDetailList!= null&&flowDetailList.size()> 0)
			{
				String flowId= flowDetailList.get(0).get("flowId").toString();

				ppmFlowService.removePPMDataByFlowId(Long.valueOf(flowId));
			}


			//5������ҵ�����ݵ����ݿ� flow_detail,tache_detail,transition_detail
			ppmFlowService.saveFlowDetail(flowDetail);

			List<Map> list= new ArrayList<Map>();
			for (String key: tacheMap.keySet()) {
				Map tacheDetail= tacheMap.get(key);

				list.add(tacheDetail);
			}
			ppmFlowService.batchSaveTacheDetail(list);

			ppmFlowService.batchSaveTransitionDetail(transitionList);
		} catch (Exception e) {

			logger.error("ת����������ģ�����ݵ�PPMҵ��ģ�������쳣", e);
			res= "error";
		}

		return res;
	}



	/**
	 * ������ʵ�Ľ����ڵ��б�
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
		//�����������̽ڵ㣬�ҵ� sourceRef��Ϊ��ʼ�ڵ��������
		for (Iterator<FlowElement> iterator = collection.iterator(); iterator.hasNext(); ) {
			FlowElement flowElement = iterator.next();
			//�ҵ�������
			if (isTransition(flowElement))
			{
				SequenceFlow sequenceFlow= (SequenceFlow) flowElement;
				if(sourceRef.equals(sequenceFlow.getSourceRef()))
				{
					//�ҵ���  ��ȡ�����ڵ�
					String targetRef= sequenceFlow.getTargetRef();
					//�ж��Ƿ��� �ڵ�
					if (tacheMap.containsKey(targetRef))
					{
						Map<String, Object> map= new HashMap<String, Object>();
						map.put("targetRefName", tacheMap.get(targetRef).get("tacheName").toString());
						map.put("targetRefId", tacheMap.get(targetRef).get("tacheId").toString());

						list.add(map);
					}
					else
					{
						//�����ڵ�����Ƿǽڵ����ͣ����� ���أ����������ҵ� �ڵ�
						getActualTargetTacheList(list, tacheMap, collection, targetRef);
					}
				}
			}
		}

	}
}
