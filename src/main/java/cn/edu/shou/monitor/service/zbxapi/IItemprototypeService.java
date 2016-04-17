package cn.edu.shou.monitor.service.zbxapi;
import java.util.List;

import cn.edu.shou.monitor.domain.base.ItemPrototype;
import cn.edu.shou.monitor.domain.itemprototype.ItemPrototypeCreateRequest;
import cn.edu.shou.monitor.domain.itemprototype.ItemPrototypeDeleteRequest;
import cn.edu.shou.monitor.domain.itemprototype.ItemPrototypeExistsRequest;
import cn.edu.shou.monitor.domain.itemprototype.ItemPrototypeGetRequest;
import cn.edu.shou.monitor.domain.itemprototype.ItemPrototypeIsreadableRequest;
import cn.edu.shou.monitor.domain.itemprototype.ItemPrototypeIswritableRequest;
import cn.edu.shou.monitor.domain.itemprototype.ItemPrototypeUpdateRequest;
public interface IItemprototypeService {
	public Object itemPrototypeCreate(ItemPrototypeCreateRequest itemPrototypeCreate);
	public Object itemPrototypeDelete(ItemPrototypeDeleteRequest itemPrototypeDelete);
	public Object itemPrototypeExists(ItemPrototypeExistsRequest itemPrototypeExists);
	public Object itemPrototypeGet(ItemPrototypeGetRequest itemPrototypeGet);
	public Object itemPrototypeIsreadable(ItemPrototypeIsreadableRequest itemPrototypeIsreadable);
	public Object itemPrototypeIswritable(ItemPrototypeIswritableRequest itemPrototypeIswritable);
	public Object itemPrototypeUpdate(ItemPrototypeUpdateRequest itemPrototypeUpdate);
	public List<ItemPrototype> itemPrototypeGetToBean(ItemPrototypeGetRequest itemPrototypeGet);
}
