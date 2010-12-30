package grg.example.contact.server;

import java.io.IOException;

import grg.example.contact.shared.Contact;
import grg.example.contact.shared.ContactsResource;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ContactsServerResource extends BaseResource implements ContactsResource {

	/**
	 * Handle Post request : create new contact
	 */
	@Post
	public Representation insertContact(Representation contact){
		Representation result = null;
		// Lay du lieu
		Form form = new Form(contact);
		String name = form.getFirstValue("name");
		String desc = form.getFirstValue("description");
		
		// Kiem tra va luu 
		if(!getContacts().containsKey(name) && getContacts().putIfAbsent(name, new Contact(name,desc)) == null){
			setStatus(Status.SUCCESS_CREATED);
			Representation rep = new StringRepresentation("Contact Created",MediaType.TEXT_PLAIN);
			rep.setLocationRef(getRequest().getResourceRef().getIdentifier()+"/"+name);
			result = rep;
		}
		else{
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			result = generateErrorRepresentation("Contact "+name+" does not exist!","1");
		}
		
		return result;
	}

	private Representation generateErrorRepresentation(String errorMessage,   
            String errorCode) {
		DomRepresentation result = null;
		try{
			
			result = new DomRepresentation(MediaType.TEXT_XML);
			Document d = result.getDocument();
			
			Element eltError = d.createElement("error");
			d.appendChild(eltError);
			Element eltCode = d.createElement("code");
			Element eltMessage = d.createElement("message");
			
			eltCode.appendChild(d.createTextNode(errorCode));
			eltMessage.appendChild(d.createTextNode(errorMessage));
			eltError.appendChild(eltCode);
			eltError.appendChild(eltMessage);
			
			d.normalizeDocument();
			
		}catch(IOException e){
			e.printStackTrace();   
		}
		
		
		return result;
	}
	
	/**
	 * Handle GET Request : return xml list contacts
	 * 
	 */
	@Get("Xml")
	public Representation toXML() throws IOException{
		DomRepresentation result = new DomRepresentation(MediaType.TEXT_XML);
		
		try{
			Document d = result.getDocument();
			Element r = d.createElement("contacts");
			d.appendChild(r);
			for(Contact contact : getContacts().values()){
				Element eltcontact = d.createElement("contact");
				Element eltname = d.createElement("name");
				Element eltdesc= d.createElement("description");
				
				eltname.appendChild(d.createTextNode(contact.getName()));
				eltdesc.appendChild(d.createTextNode(contact.getDescription()));
				eltcontact.appendChild(eltname);
				eltcontact.appendChild(eltdesc);
				r.appendChild(eltcontact);
			}
			d.normalizeDocument();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return result;
		
	}
}









