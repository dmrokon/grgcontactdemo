package grg.example.contact.shared;

import java.io.IOException;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;



public interface ContactsResource {

	@Post
	public Representation insertContact(Representation contact);
	
	@Get("Xml")
	public Representation toXML() throws IOException;
}
