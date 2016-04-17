package cn.edu.shou.monitor.service.zbxapi;
import cn.edu.shou.monitor.domain.mediatype.MediaTypeCreateRequest;
import cn.edu.shou.monitor.domain.mediatype.MediaTypeDeleteRequest;
import cn.edu.shou.monitor.domain.mediatype.MediaTypeGetRequest;
import cn.edu.shou.monitor.domain.mediatype.MediaTypeUpdateRequest;
public interface IMediatypeService {
	public Object mediaTypeCreate(MediaTypeCreateRequest mediaTypeCreate);
	public Object mediaTypeDelete(MediaTypeDeleteRequest mediaTypeDelete);
	public Object mediaTypeGet(MediaTypeGetRequest mediaTypeGet);
	public Object mediaTypeUpdate(MediaTypeUpdateRequest mediaTypeUpdate);
}
