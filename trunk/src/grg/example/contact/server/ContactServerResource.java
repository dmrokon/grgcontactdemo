package grg.example.contact.server;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import grg.example.contact.shared.ContactResource;

public class ContactServerResource extends ServerResource implements ContactResource {

	@Get
	public String Retrieve(){
		return "Bonjour Viet Nam";
	}
}
