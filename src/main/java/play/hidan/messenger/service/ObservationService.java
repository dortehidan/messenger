package play.hidan.messenger.service;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.couchbase.client.java.*;
import com.couchbase.client.java.document.*;
import com.couchbase.client.java.document.json.*;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.client.java.query.Statement;

import play.hidan.messenger.database.DatabaseClass;
import play.hidan.messenger.model.Message;
import play.hidan.messenger.model.Observation;

public class ObservationService {

	private Map<String,Observation> observations = DatabaseClass.getObservations();
	
	private Bucket bucket = DatabaseClass.bucketOpen();
	
	public ObservationService() {
		
	}
	
	public List<Observation> getAllObservations(){
		//bucket.get(arg0)
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//String resType = "observation";
//		Statement statement = select("docid", "subType", "userid", "observationstate","valueDouble","comment").from(i(bucket.name())).where(x("ressourcetype").eq(x("observation")));
//		Statement statement = select("docid","subType", "userid", "observationstate","comment","value","observationcreated").from(i(bucket.name())); //.where(x("ressourcetype").eq(x("observation")));
		Statement statement = select("resourceType","docId","subType", "userId", "observationState","comment","valueDouble","observationCreated").from(i(bucket.name())).where(x("resourceType").eq(x("$resType")));
		JsonObject placeholderValues = JsonObject.create().put("resType", "observation");
		//N1qlQuery q = N1qlQuery.simple(statement);
		N1qlQuery q = N1qlQuery.parameterized(statement, placeholderValues);
		
		observations.clear();
						
		for (N1qlQueryRow row : bucket.query(q)) {

			JsonObject j = row.value();
			Observation o = new Observation(false);
			
			
			String ds = j.getString("observationCreated");
			Date date = null;
			try {
				date = dateFormat.parse(ds);
				o.setObservationCreated(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
//			o.setValue(j.getString("value"));
			
			if (j.getDouble("valueDouble") != null) {
				o.setValueDouble(j.getDouble("valueDouble"));
			};
			
			o.setRessourceType(j.getString("resourceType"));
			
			o.setDocId(j.getString("docId"));
			o.setSubType(j.getString("subType"));
			o.setUserId(j.getString("userId"));
			o.setObservationState(j.getString("observationState"));
			
						
			o.setComment(j.getString("comment"));
						
			observations.put(o.getDocId(), o);
			
		}
		
		return new ArrayList<Observation> (observations.values());
	}

		
	/*public Message getMessageById(long id) {
		
		
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
	}*/
	
	public Observation addObservation(Observation observation){
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		
		observation.setDocId("obs:" + UUID.randomUUID().toString());
		observation.setRessourceType("observation");
		if (observation.getObservationCreated() == null) {
			observation.setObservationCreated(d);
		}
		
		JsonObject jdoc = JsonObject.create()
		 .put("docId", observation.getDocId())		
		 .put("resourceType", observation.getRessourceType())
		 .put("observationCreated", dateFormat.format(observation.getObservationCreated()))
		 .put("subType", observation.getSubType())
		 .put("system", observation.getSystem())
		 .put("userId", observation.getUserId())		 
		 .put("observationState", observation.getObservationState())
		 .put("valueDouble", observation.getValueDouble())
		 .put("unit", observation.getUnit())
		 .put("comment", observation.getComment());
		 

		// Store the Document
        bucket.upsert(JsonDocument.create(observation.getDocId(), jdoc));

		return observation;
	}
	
//	public Message updateMassage(Message message){
//		if (message.getId()<=0) {
//			return null;
//		}
//		messages.put(message.getId(),message);
//		return message;
//	}
	
//	public void removeMessage(Long id) {
//		messages.remove(id);
//	}
	
}
