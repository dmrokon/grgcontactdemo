package grg.example.contact.client;

import java.io.IOException;

import org.restlet.client.resource.Result;
import org.restlet.client.resource.ClientProxy;
import org.restlet.client.resource.Get;
import org.restlet.representation.Representation;
import org.restlet.client.resource.Delete;
import org.restlet.client.resource.Put;

public interface ContactResourceProxy extends ClientProxy {

	@Delete
	public void removeContact(Result<Void> callback);
	
	@Put
	public void storeContact(Representation entity, Result<Void> callback);
	
	@Get("Xml")
	public void toXML(Result<Representation> callback) throws IOException;
	

}
