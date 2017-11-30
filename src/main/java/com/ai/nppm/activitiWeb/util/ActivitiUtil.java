package com.ai.nppm.activitiWeb.util;

import org.activiti.bpmn.model.*;

/**
 * Created by IntelliJ IDEA.
 * Author: cjh
 * Date: 2017/11/29
 * Time: 18:00
 * Description：该类的作用
 * To change this template use File | Settings | File Templates.
 */
public class ActivitiUtil {

    //判断是否是流程节点  开始、结束、用户任务
    public static final boolean isTache(FlowElement flowElement)
    {
        boolean res= false;
        if(flowElement instanceof StartEvent)
        {
            res= true;
        }
        else if(flowElement instanceof EndEvent)
        {
            res= true;

        }
        else if(flowElement instanceof UserTask)
        {
            res= true;
        }

        return res;
    }

    //判断是否是流程线
    public static final boolean isTransition(FlowElement flowElement)
    {
        boolean res= false;
        if(flowElement instanceof SequenceFlow)
        {
            res= true;
        }

        return res;
    }
}
