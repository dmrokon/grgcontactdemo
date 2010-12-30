package grg.example.contact.server;

import grg.example.contact.shared.Contact;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.data.LocalReference;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;


public class GRGServerApplication extends Application {

	/** The list of items is persisted in memory. */  
	private final ConcurrentMap<String, Contact> Contacts = new ConcurrentHashMap<String, Contact>(); 
	
	@Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());
        getConnectorService().getClientProtocols().add(Protocol.FILE);

        // Serve the files generated by the GWT compilation step.
        File warDir = new File("");
        if (!"war".equals(warDir.getName())) {
            warDir = new File(warDir, "war/");
        }

        Directory dir = new Directory(getContext(), LocalReference
                .createFileReference(warDir));
        router.attachDefault(dir);
        router.attach("/contacts", ContactsServerResource.class);
        router.attach("/contacts/{name}", ContactServerResource.class);
        return router;
    }
	
	/**  
     * Returns the list of registered items.  
     *   
     * @return the list of registered items.  
     */  
    public ConcurrentMap<String, Contact> getContacts() {   
        return Contacts;   
    }   
}
