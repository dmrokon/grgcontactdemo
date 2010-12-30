package grg.example.contact.shared;

import java.io.IOException;

import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

public interface ContactResource {
	
	@Delete
	public void removeContact();
	
	@Put
	public void storeContact(Representation entity);
	
	@Get("Xml")
	public Representation toXML() throws IOException;
	
	

}
