package com.ai.nppm.activitiWeb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/model")
public class ModuleController {
	
	private Logger logger = LoggerFactory.getLogger(ModuleController.class);
	private static final SimpleDateFormat FORMAT= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private RepositoryService repositoryService;
	
	@RequestMapping(value = "create")
	@ResponseBody
	public String create(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		JSONObject jsonObject= new JSONObject();
		jsonObject.put("modelId", "-1");
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

		  if(modelData.getId()!= null&&!"".equals(modelData.getId()))
		  {
		  	jsonObject.put("modelId", modelData.getId());
		  }

		} catch (Exception e) {
		  logger.error("创建模型失败：", e);
		}

		return jsonObject.toString();
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
			logger.error("查询模型列表异常：", e);
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
			logger.error("查询模型列表异常：", e);
			res= "error";
		}

		return res;
	}

	@RequestMapping(value = "deploy")
	@ResponseBody
	public String deploy(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			String modelId= map.get("modelId").toString();

			Model modelData = repositoryService.getModel(modelId);
			JsonNode modelNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
			byte[] bpmnBytes = null;
			BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			bpmnBytes = new BpmnXMLConverter().convertToXML(model);
			String processName = modelData.getName()+ ".bpmn20.xml";
			Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes,"utf-8")).deploy();

		} catch (Exception e) {
			logger.error("部署流程异常：", e);
			res= "error";
		}

		return res;
	}


	@RequestMapping(value = "queryFlows")
	@ResponseBody
	public String queryFlows(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			String index= map.get("index").toString();
			String size= map.get("size").toString();
			long count= repositoryService.createProcessDefinitionQuery().count();
			List<ProcessDefinition> processDefinitionList= repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionKey().asc().listPage(Integer.parseInt(index), Integer.parseInt(size));

			JSONArray jsonArray= new JSONArray();
			if (processDefinitionList!= null)
			{
				JSONObject jsonObject= null;
				for (int i = 0; i < processDefinitionList.size(); i++) {
					ProcessDefinition processDefinition = processDefinitionList.get(i);

					jsonObject= new JSONObject();
					jsonObject.put("id", processDefinition.getId());
					jsonObject.put("name", processDefinition.getName());
					jsonObject.put("key", processDefinition.getKey());
					jsonObject.put("version", processDefinition.getVersion());

					jsonArray.add(jsonObject);
				}
			}

			JSONObject jsonObject= new JSONObject();
			jsonObject.put("total", count);
			jsonObject.put("data", jsonArray);
			res= jsonObject.toString();

		} catch (Exception e) {
			logger.error("查询流程定义列表异常：", e);
		}

		return res;
	}
}
