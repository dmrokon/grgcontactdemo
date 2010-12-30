package grg.example.contact.shared;

import java.io.IOException;

import org.restlet.client.representation.Representation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;



public interface ContactsResource {

	@Post
	public DomRepresentation insertContact(Representation contact);
	
	@Get("Xml")
	public DomRepresentation toXML() throws IOException;
}
