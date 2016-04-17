package cn.edu.shou.monitor.service.zbxapi;
import java.util.List;

import cn.edu.shou.monitor.domain.base.Template;
import cn.edu.shou.monitor.domain.template.TemplateCreateRequest;
import cn.edu.shou.monitor.domain.template.TemplateDeleteRequest;
import cn.edu.shou.monitor.domain.template.TemplateExistsRequest;
import cn.edu.shou.monitor.domain.template.TemplateGetRequest;
import cn.edu.shou.monitor.domain.template.TemplateGetobjectsRequest;
import cn.edu.shou.monitor.domain.template.TemplateIsreadableRequest;
import cn.edu.shou.monitor.domain.template.TemplateIswritableRequest;
import cn.edu.shou.monitor.domain.template.TemplateMassaddRequest;
import cn.edu.shou.monitor.domain.template.TemplateMassremoveRequest;
import cn.edu.shou.monitor.domain.template.TemplateMassupdateRequest;
import cn.edu.shou.monitor.domain.template.TemplateUpdateRequest;
public interface ITemplateService {
	public Object create(TemplateCreateRequest create);
	public Object delete(TemplateDeleteRequest delete);
	public Object exists(TemplateExistsRequest exists);
	public Object getobjects(TemplateGetobjectsRequest getobjects);
	public Object get(TemplateGetRequest get);
	public Object isreadable(TemplateIsreadableRequest isreadable);
	public Object iswritable(TemplateIswritableRequest iswritable);
	public Object massadd(TemplateMassaddRequest massadd);
	public Object massremove(TemplateMassremoveRequest massremove);
	public Object massupdate(TemplateMassupdateRequest massupdate);
	public Object update(TemplateUpdateRequest update);
	List<Template> getTemplateToBean(TemplateGetRequest get);
}
