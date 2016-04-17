package cn.edu.shou.monitor.domain.triggerprototype;
import cn.edu.shou.monitor.domain.base.RequestBase;
import cn.edu.shou.monitor.domain.base.TriggerPrototype;
public class TriggerPrototypeCreateRequest extends RequestBase{
	public TriggerPrototypeCreateRequest() {
		super("triggerprototype.create");
	}
	private TriggerPrototypeCreateParams params = new TriggerPrototypeCreateParams();
	public void setParams(TriggerPrototypeCreateParams params) {
		this.params = params;
	}
	public TriggerPrototypeCreateParams getParams() {
		return params;
	}
	public static class TriggerPrototypeCreateParams extends TriggerPrototype{
	}
}
