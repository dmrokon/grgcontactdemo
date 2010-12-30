package grg.example.contact.client;

import java.io.IOException;

import org.restlet.representation.Representation;
import org.restlet.client.resource.ClientProxy;
import org.restlet.client.resource.Get;
import org.restlet.client.resource.Post;
import org.restlet.client.resource.Result;

public interface ContactsResourceProxy extends ClientProxy {
	
	@Post
	public void insertContact(Representation representation, Result<org.restlet.representation.Representation> result);
	
	@Get("Xml")
	public void toXML(Result<Representation> callback) throws IOException;
}
