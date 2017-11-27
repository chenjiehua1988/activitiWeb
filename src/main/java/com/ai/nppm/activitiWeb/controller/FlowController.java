package com.ai.nppm.activitiWeb.controller;

import com.ai.nppm.activitiWeb.dao.PPMFlowDAO;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/flow")
public class FlowController {
	
	private Logger logger = LoggerFactory.getLogger(FlowController.class);
	private static final SimpleDateFormat FORMAT= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private PPMFlowDAO ppmFlow;

	@Autowired
	private RepositoryService repositoryService;
	


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

}
