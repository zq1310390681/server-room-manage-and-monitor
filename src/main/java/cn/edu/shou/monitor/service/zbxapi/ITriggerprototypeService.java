package cn.edu.shou.monitor.service.zbxapi;
import java.util.List;

import cn.edu.shou.monitor.domain.base.TriggerPrototype;
import cn.edu.shou.monitor.domain.triggerprototype.TriggerPrototypeCreateRequest;
import cn.edu.shou.monitor.domain.triggerprototype.TriggerPrototypeDeleteRequest;
import cn.edu.shou.monitor.domain.triggerprototype.TriggerPrototypeGetRequest;
import cn.edu.shou.monitor.domain.triggerprototype.TriggerPrototypeUpdateRequest;
public interface ITriggerprototypeService {
	public Object triggerPrototypeCreate(TriggerPrototypeCreateRequest triggerPrototypeCreate);
	public Object addTriggerPrototype(TriggerPrototype triggerPrototype);
	public Object triggerPrototypeDelete(TriggerPrototypeDeleteRequest triggerPrototypeDelete);
	public Object triggerPrototypeGet(TriggerPrototypeGetRequest triggerPrototypeGet);
	public Object triggerPrototypeUpdate(TriggerPrototypeUpdateRequest triggerPrototypeUpdate);
	List<TriggerPrototype> triggerPrototypeGetToBean(TriggerPrototypeGetRequest triggerPrototypeGet);
}
