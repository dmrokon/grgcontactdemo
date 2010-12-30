package grg.example.contact.shared;

import java.io.IOException;

import org.restlet.client.representation.Representation;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface ContactResource {
	
	@Delete
	public void removeContact();
	
	@Put
	public void storeContact(Representation entity);
	
	@Get("Xml")
	public DomRepresentation toXML() throws IOException;
	
	

}
