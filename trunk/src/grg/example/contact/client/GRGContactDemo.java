package grg.example.contact.client;

import org.restlet.client.data.MediaType;
import org.restlet.client.data.Preference;
import org.restlet.client.resource.Result;
import org.restlet.data.Form;
import org.restlet.representation.Representation; 


import grg.example.contact.shared.Contact;
import grg.example.contact.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GRGContactDemo implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	private final ContactResourceProxy contactresource = GWT.create(ContactResourceProxy.class);
	private final ContactsResourceProxy contactsresource = GWT.create(ContactsResourceProxy.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button createButton = new Button("Create");
		final Button updateButton = new Button("Update");
		final TextBox nameField = new TextBox();
		final TextBox descField = new TextBox();
		final Label errorLabel = new Label();

		// We can add style names to widgets
		createButton.addStyleName("newButton");
		updateButton.addStyleName("newButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("descFieldContainer").add(descField);
		RootPanel.get("createButtonContainer").add(createButton);
		RootPanel.get("updateButtonContainer").add(updateButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				createButton.setEnabled(true);
				createButton.setFocus(true);
			}
		});
		
		createButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				contactsresource.getClientResource().setReference("/restlet/contacts");
				contactsresource.getClientResource().getClientInfo()
                .getAcceptedMediaTypes().add(
                        new Preference<MediaType>(
                                MediaType.APPLICATION_ALL_XML));
				
				/*demoresource.Retrieve(new Result<String>() {
                    public void onFailure(Throwable caught) {
                        dialogBox.setText("Get contact");
                        textToServerLabel.setText("Error: "
                                + caught.getMessage());
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }

                    public void onSuccess(String str) {
                        nameField.setText(str);
                    }
                });*/
				
				String name = nameField.getText();
				String desc = descField.getText();
				
				Contact contact = new Contact(name,desc);
				contactsresource.insertContact(getRepresentation(contact), 
						new Result<Representation>(){

					@Override
					public void onFailure(Throwable caught) {
						dialogBox.setText("Get contact");
                        textToServerLabel.setText("Error: "
                                + caught.getMessage());
                        dialogBox.center();
                        closeButton.setFocus(true);
						
					}

					@Override
					public void onSuccess(Representation result) {
						dialogBox.setText("Inserted!");
						 dialogBox.center();
						 closeButton.setFocus(true);
					}
					
				});				
				
				
			}
		});

		
		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				//sendNameToServer();
				
				contactresource.getClientResource().setReference("/restlet/contacts/"+nameField.getText());
				contactresource.getClientResource().getClientInfo()
                .getAcceptedMediaTypes().add(
                        new Preference<MediaType>(
                                MediaType.APPLICATION_ALL_XML));
				
				String name = nameField.getText();
				String desc = descField.getText();
				Contact contact = new Contact(name,desc);
				contactresource.storeContact(getRepresentation(contact), new Result<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						dialogBox.setText("Get contact");
                        textToServerLabel.setText("Error: "
                                + caught.getMessage());
                        dialogBox.center();
                        closeButton.setFocus(true);
						
					}

					@Override
					public void onSuccess(Void result) {
						dialogBox.setText("Updated!");
						 dialogBox.center();
						 closeButton.setFocus(true);
						
					}
					
				});
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				createButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.greetServer(textToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		updateButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}
	

	/**  
     * Returns the Representation of an item.  
     *   
     * @param item  
     *            the item.  
     *   
     * @return The Representation of the item.  
     */  
    public static Representation getRepresentation(Contact item) {   
        // Gathering informations into a Web form.   
        Form form = new Form();   
        form.add("name", item.getName());   
        form.add("description", item.getDescription());   
        return form.getWebRepresentation();   
    }   
}
