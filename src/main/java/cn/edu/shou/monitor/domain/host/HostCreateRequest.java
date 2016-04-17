package cn.edu.shou.monitor.domain.host;

import java.util.*;

import cn.edu.shou.monitor.domain.base.Host;
import cn.edu.shou.monitor.domain.base.HostGroup;
import cn.edu.shou.monitor.domain.base.HostInterface;
import cn.edu.shou.monitor.domain.base.HostInventory;
import cn.edu.shou.monitor.domain.base.Macro;
import cn.edu.shou.monitor.domain.base.RequestBase;
import cn.edu.shou.monitor.domain.base.Template;

public class HostCreateRequest extends RequestBase{
	public HostCreateRequest() {
		super("host.create");
	}
	private HostCreateParams params = new HostCreateParams();
	public void setParams(HostCreateParams params) {
		this.params = params;
	}
	public HostCreateParams getParams() {
		return params;
	}
	public static class HostCreateParams extends Host{
		private List<HostGroup> groups;
		private List<HostInterface> interfaces;
		private List<Template> templates;
		private List<Macro> macros;
		private HostInventory inventory;
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
		public void setInventory(HostInventory inventory) {
			this.inventory = inventory;
		}
		public HostInventory getInventory() {
			return inventory;
		}
	}
}
