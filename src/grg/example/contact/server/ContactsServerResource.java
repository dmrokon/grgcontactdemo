package grg.example.contact.server;

import grg.example.contact.shared.ContactsResource;

import org.restlet.client.resource.Get;
import org.restlet.resource.ServerResource;

public class ContactsServerResource extends ServerResource implements ContactsResource {


	
	@Get
	public String GetName(){
		String name = (String) getRequest().getAttributes().get("name");
		return name;
	}
	

}
