package grg.example.contact.shared;

import org.restlet.resource.Get;

public interface ContactResource {

	@Get
	public String Retrieve();
}
