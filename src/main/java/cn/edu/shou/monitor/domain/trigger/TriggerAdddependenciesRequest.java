package cn.edu.shou.monitor.domain.trigger;
import cn.edu.shou.monitor.domain.base.RequestBase;
import cn.edu.shou.monitor.domain.base.Trigger;
public class TriggerAdddependenciesRequest extends RequestBase{
	public TriggerAdddependenciesRequest() {
		super("trigger.adddependencies");
	}
	private TriggerAdddependenciesParams params = new TriggerAdddependenciesParams();
	public void setParams(TriggerAdddependenciesParams params) {
		this.params = params;
	}
	public TriggerAdddependenciesParams getParams() {
		return params;
	}
	public static class TriggerAdddependenciesParams extends Trigger{
		private String triggerid;
		private String dependsOnTriggerid;
		public void setTriggerid(String triggerid) {
			this.triggerid = triggerid;
		}
		public String getTriggerid() {
			return triggerid;
		}
		public void setDependsOnTriggerid(String dependsOnTriggerid) {
			this.dependsOnTriggerid = dependsOnTriggerid;
		}
		public String getDependsOnTriggerid() {
			return dependsOnTriggerid;
		}
	}
}
