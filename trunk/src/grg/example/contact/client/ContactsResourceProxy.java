package grg.example.contact.client;

import org.restlet.client.resource.ClientProxy;
import org.restlet.client.resource.Get;
import org.restlet.client.resource.Result;

public interface ContactsResourceProxy extends ClientProxy {


	
	@Get
	public void GetName(Result<String> callback);
}
