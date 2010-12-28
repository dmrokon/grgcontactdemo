package grg.example.contact.client;

import org.restlet.client.resource.Result;
import org.restlet.client.resource.ClientProxy;
import org.restlet.client.resource.Get;

public interface ContactResourceProxy extends ClientProxy {

	@Get	
	public void Retrieve(Result<String> callback);
}
