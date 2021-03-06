package com.ai.nppm.activitiWeb.controller;

import com.ai.nppm.activitiWeb.service.PPMFlowService;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/flow")
public class FlowController {
	
	private Logger logger = LoggerFactory.getLogger(FlowController.class);

	@Autowired
	private PPMFlowService ppmFlow;

	@Autowired
	private RepositoryService repositoryService;
	


	@RequestMapping(value = "deploy")
	@ResponseBody
	public String deploy(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		JSONObject jsonObject= new JSONObject();
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

			ProcessDefinition processDefinition= repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();

			//给model表设置流程key
			String key= processDefinition.getKey();
			modelData.setKey(key);
			repositoryService.saveModel(modelData);
			jsonObject.put("processId", processDefinition.getId());

		} catch (Exception e) {
			logger.error("部署流程异常：", e);
			res= "error";
		}

		jsonObject.put("res", res);
		return jsonObject.toString();
	}


	@RequestMapping(value = "queryFlows")
	@ResponseBody
	public String queryFlows(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			String index= map.get("index").toString();
			String size= map.get("size").toString();
			String flowKey= null;
			long count= 0;
			List<ProcessDefinition> processDefinitionList= new ArrayList<ProcessDefinition>(0);
			if (map.containsKey("flowKey")&& StringUtils.isNotEmpty(map.get("flowKey").toString()))
			{
				flowKey= map.get("flowKey").toString();
				count= repositoryService.createProcessDefinitionQuery().processDefinitionKey(flowKey).count();
				processDefinitionList= repositoryService.createProcessDefinitionQuery().processDefinitionKey(flowKey).orderByProcessDefinitionKey().asc().listPage(Integer.parseInt(index), Integer.parseInt(size));
			}
			else
			{
				count= repositoryService.createProcessDefinitionQuery().count();
				processDefinitionList= repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionKey().asc().listPage(Integer.parseInt(index), Integer.parseInt(size));
			}

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
					jsonObject.put("deploymentId", processDefinition.getDeploymentId());

					//查询流程定义是否已经有了可编辑模型
					long modelCount1= repositoryService.createModelQuery().modelName(processDefinition.getName()).count();
					long modelCount2= repositoryService.createModelQuery().modelKey(processDefinition.getKey()).count();
					jsonObject.put("hasModel", modelCount1+ modelCount2== 0?false: true);

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


	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			String deploymentId= map.get("deploymentId").toString();

			repositoryService.deleteDeployment(deploymentId);

		} catch (Exception e) {
			logger.error("流程删除异常：", e);
			res= "error";
		}

		return res;
	}

	//转换流程定义到可编辑模型
	@RequestMapping(value = "flowToModel")
	@ResponseBody
	private String flowToModel(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map)
	{
		String res= "success";

		try {
			String processId= map.get("processId").toString();

			ProcessDefinition processDefinition =    repositoryService
					.createProcessDefinitionQuery()
					.processDefinitionId(processId).singleResult();

			InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
			XMLInputFactory xif = XMLInputFactory.newInstance();
			InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
			XMLStreamReader xtr = xif.createXMLStreamReader(in);
			BpmnModel bpmnModel = (new BpmnXMLConverter()).convertToBpmnModel(xtr);
			if (bpmnModel.getMainProcess() != null && bpmnModel.getMainProcess().getId() != null) {
				if (bpmnModel.getLocationMap().size() >0) {

					BpmnJsonConverter converter = new BpmnJsonConverter();
					ObjectNode modelNode = converter.convertToJson(bpmnModel);
					Model modelData = repositoryService.newModel();
					ObjectNode modelObjectNode = (new ObjectMapper()).createObjectNode();
					modelObjectNode.put("name", processDefinition.getName());
					modelObjectNode.put("revision", 1);
					modelObjectNode.put("description", processDefinition.getName());
					modelData.setMetaInfo(modelObjectNode.toString());
					modelData.setName(processDefinition.getName());
					repositoryService.saveModel(modelData);
					repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));

				}
			}
		} catch (Exception e) {

			logger.error("转换流程定义到可编辑模型异常", e);
			res= "error";
		}

		return res;
	}



	@RequestMapping(value = "queryFlowDetails")
	@ResponseBody
	public String queryFlowDetails(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			Long total=0L;
			List<Map> count= ppmFlow.queryFlowDetailCount(new HashMap());
			if (count!= null&&count.size()==1)
			{
				total= Long.valueOf(count.get(0).get("total").toString());
			}
			List<Map> mapList= ppmFlow.queryFlowDetail(map);


			JSONArray jsonArray= new JSONArray();
			if (mapList!= null)
			{
				JSONObject jsonObject= null;
				Map para= new HashMap();
				for (int i = 0; i < mapList.size(); i++) {
					Map flowDetail = mapList.get(i);

					jsonObject= new JSONObject();
					jsonObject.put("id", flowDetail.get("flowId"));
					jsonObject.put("name", flowDetail.get("name"));
					jsonObject.put("key", flowDetail.get("key"));
					jsonObject.put("version", flowDetail.get("version"));

					//查询ppm流程是否已经有流程实例
					para.put("flowId", flowDetail.get("flowId"));
					long flowInstCount= Long.valueOf(ppmFlow.queryFlowInstCount(para).get(0).get("total").toString());
					jsonObject.put("hasFlowInst", flowInstCount== 0?false: true);

					jsonArray.add(jsonObject);
				}
			}

			JSONObject jsonObject= new JSONObject();
			jsonObject.put("total", total);
			jsonObject.put("data", jsonArray);
			res= jsonObject.toString();

		} catch (Exception e) {
			logger.error("查询ppm流程列表异常：", e);
		}

		return res;
	}

	@RequestMapping(value = "queryFlowKeys")
	@ResponseBody
	public String queryFlowKeys(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			List<Map> mapList= ppmFlow.queryFlowKeys(new HashMap());


			JSONArray jsonArray= new JSONArray();
			if (mapList!= null)
			{
				JSONObject jsonObject= null;
				for (int i = 0; i < mapList.size(); i++) {
					Map tacheDetail = mapList.get(i);

					jsonObject= new JSONObject();
					jsonObject.put("name", tacheDetail.get("name"));
					jsonObject.put("key", tacheDetail.get("key"));

					jsonArray.add(jsonObject);
				}
			}

			JSONObject jsonObject= new JSONObject();
			jsonObject.put("total", mapList.size());
			jsonObject.put("data", jsonArray);
			res= jsonObject.toString();

		} catch (Exception e) {
			logger.error("查询流程列表异常：", e);
		}

		return res;
	}

	@RequestMapping(value = "queryTacheDetails")
	@ResponseBody
	public String queryTacheDetails(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			List<Map> mapList= ppmFlow.queryTacheDetails(map);


			JSONArray jsonArray= new JSONArray();
			if (mapList!= null)
			{
				JSONObject jsonObject= null;
				for (int i = 0; i < mapList.size(); i++) {
					Map tacheDetail = mapList.get(i);

					jsonObject= new JSONObject();
					jsonObject.put("tacheId", tacheDetail.get("tacheId"));
					jsonObject.put("tacheName", tacheDetail.get("tacheName"));
					jsonObject.put("activityName", tacheDetail.get("activityName"));
					jsonObject.put("tacheSpecCd", tacheDetail.get("tacheSpecCd"));
					jsonObject.put("tacheTypeCd", tacheDetail.get("tacheTypeCd"));

					jsonArray.add(jsonObject);
				}
			}

			JSONObject jsonObject= new JSONObject();
			jsonObject.put("total", mapList.size());
			jsonObject.put("data", jsonArray);
			res= jsonObject.toString();

		} catch (Exception e) {
			logger.error("查询ppm流程节点列表异常：", e);
		}

		return res;
	}

	@RequestMapping(value = "queryTransitionDetails")
	@ResponseBody
	public String queryTransitionDetails(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			List<Map> mapList= ppmFlow.queryTransitionDetails(map);


			JSONArray jsonArray= new JSONArray();
			if (mapList!= null)
			{
				JSONObject jsonObject= null;
				for (int i = 0; i < mapList.size(); i++) {
					Map transitionDetail = mapList.get(i);

					jsonObject= new JSONObject();
					jsonObject.put("transitionId", transitionDetail.get("transitionId"));
					jsonObject.put("transName", transitionDetail.get("transName"));
					jsonObject.put("transitionType", transitionDetail.get("transitionType"));

					jsonArray.add(jsonObject);
				}
			}

			JSONObject jsonObject= new JSONObject();
			jsonObject.put("total", mapList.size());
			jsonObject.put("data", jsonArray);
			res= jsonObject.toString();

		} catch (Exception e) {
			logger.error("查询ppm流程流向列表异常：", e);
		}

		return res;
	}

	@RequestMapping(value = "updateTacheDetail")
	@ResponseBody
	public String updateTacheDetail(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			ppmFlow.updateTacheDetail(map);

		} catch (Exception e) {
			logger.error("更新tacheDetail异常：", e);
			res= "error";
		}

		return res;
	}


	@RequestMapping(value = "updateTransitionDetail")
	@ResponseBody
	public String updateTransitionDetail(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			ppmFlow.updateTransitionDetail(map);

		} catch (Exception e) {
			logger.error("更新transitionDetail异常：", e);
			res= "error";
		}

		return res;
	}


	@RequestMapping(value = "queryRoles")
	@ResponseBody
	public String queryRoles(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			List<Map> mapList= ppmFlow.queryRoles(new HashMap());


			JSONArray jsonArray= new JSONArray();
			if (mapList!= null)
			{
				JSONObject jsonObject= null;
				for (int i = 0; i < mapList.size(); i++) {
					Map role = mapList.get(i);

					jsonObject= new JSONObject();
					jsonObject.put("roleId", role.get("roleId"));
					jsonObject.put("roleName", role.get("roleName"));

					jsonArray.add(jsonObject);
				}
			}

			JSONObject jsonObject= new JSONObject();
			jsonObject.put("total", mapList.size());
			jsonObject.put("data", jsonArray);
			res= jsonObject.toString();

		} catch (Exception e) {
			logger.error("查询角色列表异常：", e);
		}

		return res;
	}

	@RequestMapping(value = "queryStaffsByTacheId")
	@ResponseBody
	public String queryStaffsByTacheId(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			List<Map> mapList= ppmFlow.queryStaffsByTacheId(map);


			JSONArray jsonArray= new JSONArray();
			if (mapList!= null)
			{
				JSONObject jsonObject= null;
				for (int i = 0; i < mapList.size(); i++) {
					Map staff = mapList.get(i);

					jsonObject= new JSONObject();
					jsonObject.put("staffName", staff.get("staffName"));
					jsonObject.put("staffId", staff.get("staffId"));
					jsonObject.put("id", staff.get("id"));
					jsonObject.put("isDefault", staff.get("isDefault"));

					jsonArray.add(jsonObject);
				}
			}

			JSONObject jsonObject= new JSONObject();
			jsonObject.put("total", mapList.size());
			jsonObject.put("data", jsonArray);
			res= jsonObject.toString();

		} catch (Exception e) {
			logger.error("查询员工列表异常：", e);
		}

		return res;
	}

	@RequestMapping(value = "queryStaffsByRoleId")
	@ResponseBody
	public String queryStaffsByRoleId(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			List<Map> mapList= ppmFlow.queryStaffsByRoleId(map);


			com.alibaba.fastjson.JSONArray jsonArray= new com.alibaba.fastjson.JSONArray();
			if (mapList!= null)
			{
				jsonArray= JSON.parseArray(JSON.toJSONString(mapList));
			}

			com.alibaba.fastjson.JSONObject jsonObject= new com.alibaba.fastjson.JSONObject();
			jsonObject.put("total", mapList.size());
			jsonObject.put("data", jsonArray);
			res= jsonObject.toString();

		} catch (Exception e) {
			logger.error("查询员工列表异常：", e);
		}

		return res;
	}

	@RequestMapping(value = "saveFlowOperator")
	@ResponseBody
	public String saveFlowOperator(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			ppmFlow.saveFlowOperator(map);

		} catch (Exception e) {
			logger.error("保存flow_operator异常：", e);
			res= "error";
		}

		return res;
	}

	@RequestMapping(value = "removeFlowOperator")
	@ResponseBody
	public String removeFlowOperator(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			ppmFlow.removeFlowOperator(map);

		} catch (Exception e) {
			logger.error("删除flow_operator异常：", e);
			res= "error";
		}

		return res;
	}

	@RequestMapping(value = "updateFlowOperator")
	@ResponseBody
	public String updateFlowOperator(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			ppmFlow.updateFlowOperator(map);

		} catch (Exception e) {
			logger.error("更新flow_operator异常：", e);
			res= "error";
		}

		return res;
	}

	@RequestMapping(value = "removePPMDataByFlowId")
	@ResponseBody
	public String removePPMDataByFlowId(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {

		String res= "success";
		try {

			ppmFlow.removePPMDataByFlowId(map);

		} catch (Exception e) {
			logger.error("删除ppm业务流程数据异常：", e);
			res= "error";
		}

		return res;
	}

}
