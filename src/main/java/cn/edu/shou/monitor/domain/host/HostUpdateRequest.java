package cn.edu.shou.monitor.domain.host;
import java.util.*;

import cn.edu.shou.monitor.domain.base.Host;
import cn.edu.shou.monitor.domain.base.HostGroup;
import cn.edu.shou.monitor.domain.base.HostInterface;
import cn.edu.shou.monitor.domain.base.HostInventory;
import cn.edu.shou.monitor.domain.base.Macro;
import cn.edu.shou.monitor.domain.base.RequestBase;
import cn.edu.shou.monitor.domain.base.Template;
public class HostUpdateRequest extends RequestBase{
	public HostUpdateRequest() {
		super("host.update");
	}
	private HostUpdateParams params = new HostUpdateParams();
	public void setParams(HostUpdateParams params) {
		this.params = params;
	}
	public HostUpdateParams getParams() {
		return params;
	}
	public static class HostUpdateParams extends Host{
		private List<HostGroup> groups;
		private List<HostInterface> interfaces;
		private HostInventory inventory;
		private List<Macro> macros;
		private List<Template> templates;
		private List<Template> templates_clear;
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
		public void setInterfaces(List<HostInterface> interfaces) {
			this.interfaces = interfaces;
		}
		public List<HostInterface> getInterfaces() {
			 if(interfaces==null){
				interfaces   = new ArrayList<HostInterface>();
				return interfaces;
			}
			 return interfaces;
		}
		public void setInventory(HostInventory inventory) {
			this.inventory = inventory;
		}
		public HostInventory getInventory() {
			return inventory;
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
		public void setTemplates_clear(List<Template> templates_clear) {
			this.templates_clear = templates_clear;
		}
		public List<Template> getTemplates_clear() {
			 if(templates_clear==null){
				templates_clear   = new ArrayList<Template>();
				return templates_clear;
			}
			 return templates_clear;
		}
	}
}
