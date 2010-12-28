package grg.example.contact.shared;

import org.restlet.resource.Get;

public interface DemoResource {


	
	@Get
	public String GetName();
}
