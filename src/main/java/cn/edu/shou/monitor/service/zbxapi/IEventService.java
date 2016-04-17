package cn.edu.shou.monitor.service.zbxapi;
import java.util.List;

import cn.edu.shou.monitor.domain.base.Event;
import cn.edu.shou.monitor.domain.event.EventGetRequest;
/**
 * @ClassName: IEventService
 * @Description: event接口
 * @author 李庆雷
 * @date 2013-9-23 上午11:47:57
 */
public interface IEventService {
	/**
	 * @Title: get
	 * @Description: 获得event信息（json格式）
	 * @param get
	 * @return Object
	 * @throws
	 */
	public Object get(EventGetRequest get);

	/**
	 * @Title: getEventToBean
	 * @Description: 获取event的信息（bean）
	 * @param get
	 * @return List<Event>
	 * @throws
	 */
	public List<Event> getEventToBean(EventGetRequest get);
}
