package grg.example.contact.server;

import grg.example.contact.shared.DemoResource;

import org.restlet.client.resource.Get;
import org.restlet.resource.ServerResource;

public class DemoServerResource extends ServerResource implements DemoResource {


	
	@Get
	public String GetName(){
		String name = (String) getRequest().getAttributes().get("name");
		return name;
	}
	

}
