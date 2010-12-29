package grg.example.contact.shared;

import org.restlet.resource.Get;

public interface ContactsResource {


	
	@Get
	public String GetName();
}
