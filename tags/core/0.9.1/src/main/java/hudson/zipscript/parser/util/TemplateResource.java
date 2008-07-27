package hudson.zipscript.parser.util;

import hudson.zipscript.resource.Resource;
import hudson.zipscript.template.Template;

public class TemplateResource {

	public long lastModified;
	public Template template;
	public Resource resource;

	public TemplateResource (Template template, Resource resource){
		this.template = template;
		this.resource = resource;
		this.lastModified = System.currentTimeMillis();
	}
}