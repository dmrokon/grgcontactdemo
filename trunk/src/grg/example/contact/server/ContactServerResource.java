package grg.example.contact.server;

import java.io.IOException;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;

import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import grg.example.contact.shared.Contact;
import grg.example.contact.shared.ContactResource;

public class ContactServerResource extends BaseResource implements ContactResource {

	
	Contact contact;
	String name;
	
	@Override  
    protected void doInit() throws ResourceException {   
        // Get the "itemName" attribute value taken from the URI template   
        // /items/{itemName}.   
        name = (String) getRequest().getAttributes().get("name");   
  
        // Get the item directly from the "persistence layer".   
        contact = getContacts().get(name);   
  
        setExisting(name != null);   
    }  
	
	@Delete
	public void removeContact(){
		if(contact!=null){
			getContacts().remove(name);
		}
		
		setStatus(Status.SUCCESS_NO_CONTENT);
	}
	
	@Put
	public void storeContact(Representation entity){
		if(contact == null){
			contact = new Contact(name);
		}
		
		Form form = new Form(entity);
		contact.setDescription(form.getFirstValue("description"));
		
		if(getContacts().putIfAbsent(name, contact) == null){
			setStatus(Status.SUCCESS_CREATED);
		}
		else{
			setStatus(Status.SUCCESS_OK);
		}
		
	}
		
	@Get("Xml")
	public Representation toXML() throws IOException{
		DomRepresentation result = new DomRepresentation(MediaType.TEXT_XML);
		try{
			Document d = result.getDocument();
			Element eltContact = d.createElement("contact");
			d.appendChild(eltContact);
			Element eltName = d.createElement("name");
			Element eltDesc = d.createElement("description");
			eltName.appendChild(d.createTextNode(contact.getName()));
			eltDesc.appendChild(d.createTextNode(contact.getDescription()));
			eltContact.appendChild(eltName);
			eltContact.appendChild(eltDesc);
			d.normalizeDocument();
			return result;
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return null;
		
	}
}

















