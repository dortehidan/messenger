package play.hidan.messenger.service;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.couchbase.client.java.*;
import com.couchbase.client.java.document.*;
import com.couchbase.client.java.document.json.*;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.client.java.query.Statement;

import play.hidan.messenger.database.DatabaseClass;
import play.hidan.messenger.model.Message;

public class MessageService {

	private Map<Long,Message> messages = DatabaseClass.getMessages();
	
	private Bucket bucket = DatabaseClass.bucketOpen();
	
	public MessageService() {
		//messages.put(1L, new Message(1,"Hallo World", "Dorte"));
		//messages.put(2L, new Message(2,"Hallo Copenhagen", "Dorte"));
		
	}
	
	public List<Message> getAllMessages(){
		//bucket.get(arg0)
		
		Statement statement = select("id", "message", "author").from(i(bucket.name()));
		//JsonObject placeholderValues = JsonObject.create().put("id", id);
		N1qlQuery q = N1qlQuery.simple(statement);
		
		messages.clear();
		
		for (N1qlQueryRow row : bucket.query(q)) {

			JsonObject j = row.value();
			Message m = new Message(j.getString("id"));
			m.setAuthor(j.getString("author"));
			m.setMessage(j.getString("message"));
			messages.put(m.getId(), m);
			
		}
		
		return new ArrayList<Message> (messages.values());
	}
	
	public Message getMessageById(long id) {
		
		
		Statement statement = select("id", "message", "author").from(i(bucket.name())).where(x("id").eq(x("$id")));
		JsonObject placeholderValues = JsonObject.create().put("id", String.valueOf(id));
		N1qlQuery q = N1qlQuery.parameterized(statement, placeholderValues);
		
		messages.clear();
		
		for (N1qlQueryRow row : bucket.query(q)) {

			JsonObject j = row.value();
			Message m = new Message(j.getString("id"));
			m.setAuthor(j.getString("author"));
			m.setMessage(j.getString("message"));
			messages.put(m.getId(), m);
			
		}
	
		return messages.get(id);
	}
	
	public Message addMessage(Message message){
		//message.setId(messages.size() + 1);
		//messages.put(message.getId(),message);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		
		message.setDocId(message.getRessourceName() + ":" + String.valueOf(message.getId()));
		
		JsonObject jdoc = JsonObject.create()
		 .put("resourceName", message.getRessourceName())
		 .put("message", message.getMessage())
		 .put("id", String.valueOf(message.getId()))
         .put("author", message.getAuthor())
         .put("created", dateFormat.format(message.getCreated()))
         .put("docId", message.getDocId());
		
		// Store the Document
        bucket.upsert(JsonDocument.create(message.getDocId(), jdoc));

		return message;
	}
	
	public Message updateMassage(Message message){
		if (message.getId()<=0) {
			return null;
		}
		messages.put(message.getId(),message);
		return message;
	}
	
	public void removeMessage(Long id) {
		messages.remove(id);
	}
	
}
