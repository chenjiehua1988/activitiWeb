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
