package cn.edu.shou.monitor.domain.action;

import java.util.*;

import cn.edu.shou.monitor.domain.base.Action;
import cn.edu.shou.monitor.domain.base.ActionCondition;
import cn.edu.shou.monitor.domain.base.ActionOperation;
import cn.edu.shou.monitor.domain.base.RequestBase;

public class ActionCreateRequest extends RequestBase{
	public ActionCreateRequest() {
		super("action.create");
	}
	private ActionCreateParams params = new ActionCreateParams();
	public void setParams(ActionCreateParams params) {
		this.params = params;
	}
	public ActionCreateParams getParams() {
		return params;
	}
	public static class ActionCreateParams extends Action{
		private List<ActionOperation> operations;
		private List<ActionCondition> conditions;
		public void setOperations(List<ActionOperation> operations) {
			this.operations = operations;
		}
		public List<ActionOperation> getOperations() {
			 if(operations==null){
				operations   = new ArrayList<ActionOperation>();
				return operations;
			}
			 return operations;
		}
		public void setConditions(List<ActionCondition> conditions) {
			this.conditions = conditions;
		}
		public List<ActionCondition> getConditions() {
			 if(conditions==null){
				conditions   = new ArrayList<ActionCondition>();
				return conditions;
			}
			 return conditions;
		}
	}
}
