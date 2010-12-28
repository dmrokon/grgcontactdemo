package grg.example.contact.client;

import org.restlet.client.resource.ClientProxy;
import org.restlet.client.resource.Get;
import org.restlet.client.resource.Result;

public interface DemoResourceProxy extends ClientProxy {


	
	@Get
	public void GetName(Result<String> callback);
}
