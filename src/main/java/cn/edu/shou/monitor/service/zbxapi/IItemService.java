package cn.edu.shou.monitor.service.zbxapi;
import java.util.List;

import cn.edu.shou.monitor.domain.base.Item;
import cn.edu.shou.monitor.domain.item.ItemCreateRequest;
import cn.edu.shou.monitor.domain.item.ItemDeleteRequest;
import cn.edu.shou.monitor.domain.item.ItemExistsRequest;
import cn.edu.shou.monitor.domain.item.ItemGetRequest;
import cn.edu.shou.monitor.domain.item.ItemGetobjectsRequest;
import cn.edu.shou.monitor.domain.item.ItemIsreadableRequest;
import cn.edu.shou.monitor.domain.item.ItemIswritableRequest;
import cn.edu.shou.monitor.domain.item.ItemUpdateRequest;
import org.json.JSONObject;

/**
 * @ClassName: IItemService
 * @Description: 监控项接口
 * @author 李庆雷
 * @date 2013-9-23 下午12:00:51
 */
public interface IItemService {
	public Object create(ItemCreateRequest create);
	public Object delete(ItemDeleteRequest delete);
	public Object exists(ItemExistsRequest exists);
	public Object getobjects(ItemGetobjectsRequest getobjects);
	/**
	 * @Title: get
	 * @Description: 获取监控项信息（json）
	 * @param get
	 * @return Object
	 * @throws
	 */
	public JSONObject get(ItemGetRequest get);
	
	/**
	 * @Title: getItemToBean
	 * @Description: 获取监控项信息（bean）
	 * @param get
	 * @return List<Item>
	 * @throws
	 */
	public List<Item> getItemToBean(ItemGetRequest get);
	public Object isreadable(ItemIsreadableRequest isreadable);
	public Object iswritable(ItemIswritableRequest iswritable);
	public Object update(ItemUpdateRequest update);
}
