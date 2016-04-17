package cn.edu.shou.monitor.domain.template;
import java.util.*;

import cn.edu.shou.monitor.domain.base.Host;
import cn.edu.shou.monitor.domain.base.HostGroup;
import cn.edu.shou.monitor.domain.base.Macro;
import cn.edu.shou.monitor.domain.base.RequestBase;
import cn.edu.shou.monitor.domain.base.Template;
public class TemplateCreateRequest extends RequestBase{
	public TemplateCreateRequest() {
		super("template.create");
	}
	private TemplateCreateParams params = new TemplateCreateParams();
	public void setParams(TemplateCreateParams params) {
		this.params = params;
	}
	public TemplateCreateParams getParams() {
		return params;
	}
	public static class TemplateCreateParams extends Template{
		private List<HostGroup> groups;
		private List<Template> templates;
		private List<Macro> macros;
		private List<Host> hosts;
		public void setGroups(List<HostGroup> groups) {
			this.groups = groups;
		}
		public List<HostGroup> getGroups() {
			 if(groups==null){
				groups   = new ArrayList<HostGroup>();
				return groups;
			}
			 return groups;
		}
		
		public void setTemplates(List<Template> templates) {
			this.templates = templates;
		}
		public List<Template> getTemplates() {
			 if(templates==null){
				templates   = new ArrayList<Template>();
				return templates;
			}
			 return templates;
		}
		public void setMacros(List<Macro> macros) {
			this.macros = macros;
		}
		public List<Macro> getMacros() {
			 if(macros==null){
				macros   = new ArrayList<Macro>();
				return macros;
			}
			 return macros;
		}
		public void setHosts(List<Host> hosts) {
			this.hosts = hosts;
		}
		public List<Host> getHosts() {
			 if(hosts==null){
				hosts   = new ArrayList<Host>();
				return hosts;
			}
			 return hosts;
		}
	}
}
