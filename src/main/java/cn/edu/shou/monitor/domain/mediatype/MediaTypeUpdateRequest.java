package cn.edu.shou.monitor.domain.mediatype;
import java.util.*;

import cn.edu.shou.monitor.domain.base.MediaType;
import cn.edu.shou.monitor.domain.base.RequestBase;
public class MediaTypeUpdateRequest extends RequestBase{
	public MediaTypeUpdateRequest() {
		super("mediatype.update");
	}
	private List<MediaType> params;
	public void setParams(List<MediaType> params) {
		this.params = params;
	}
	public List<MediaType> getParams() {
		 if(params==null){
			params   = new ArrayList<MediaType>();
			return params;
		}
		 return params;
	}
}
