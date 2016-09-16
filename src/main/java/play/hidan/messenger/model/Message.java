package play.hidan.messenger.model;



import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {

	private String docId;
	private String ressourceName;
	private long id;
	private String message;
	private Date created;
	private String author;
	
	public Message() {
		this.ressourceName = "Message";
		this.created = new Date();
		
	}
	
	public Message(String idstring) {
		Long idLong = Long.parseLong(idstring);
		this.setId(idLong);
		
	}

	public Message(long id, String message, String author) {
		this.ressourceName = "Message";
		this.id = id;
		this.message = message;
		this.author = author;
		this.created = new Date();
		this.docId = ressourceName + ":" + String.valueOf(id);
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}


	public String getRessourceName() {
		return ressourceName;
	}


	public void setRessourceName(String ressourceName) {
		this.ressourceName = ressourceName;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}
	
	
	
	
	
	
}
