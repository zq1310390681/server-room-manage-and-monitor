package cn.edu.shou.monitor.service.zbxapi;
import java.util.List;

import cn.edu.shou.monitor.domain.base.Trigger;
import cn.edu.shou.monitor.domain.trigger.TriggerAdddependenciesRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerCreateRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerDeleteRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerDeletedependenciesRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerExistsRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerGetRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerGetobjectsRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerIsreadableRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerIswritableRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerUpdateRequest;
import org.json.JSONObject;

public interface ITriggerService {
	public Object adddependencies(TriggerAdddependenciesRequest adddependencies);
	public Object create(TriggerCreateRequest create);
	public Object addTrigger(Trigger trigger);
	public Object deletedependencies(TriggerDeletedependenciesRequest deletedependencies);
	public Object delete(TriggerDeleteRequest delete);
	public Object exists(TriggerExistsRequest exists);
	public Object getobjects(TriggerGetobjectsRequest getobjects);
	public JSONObject get(TriggerGetRequest get);
	public Object isreadable(TriggerIsreadableRequest isreadable);
	public Object iswritable(TriggerIswritableRequest iswritable);
	public Object update(TriggerUpdateRequest update);
	public List<Trigger> getTriggerBean(TriggerGetRequest get);
	
}
