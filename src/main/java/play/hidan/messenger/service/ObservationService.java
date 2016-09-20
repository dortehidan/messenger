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
import com.couchbase.client.java.transcoder.JsonTranscoder;
import com.google.gson.Gson;

import play.hidan.messenger.database.DatabaseClass;
import play.hidan.messenger.model.Message;
import play.hidan.messenger.model.Observation;

public class ObservationService {

	private Map<String,Observation> observations = DatabaseClass.getObservations();
	
	private Bucket bucket = DatabaseClass.bucketOpen();
	
	public ObservationService() {
		
	}
	
	public List<Observation> getAllObservations(String systemId, String userId){

		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = new Gson();
		observations.clear();
		
		if (userId==null){
			return new ArrayList<Observation> (observations.values());
		}
		
		Statement statement;
		
		if (systemId==null){
			statement = select("resourceType","docId","systemId", "subType", "userId", "observationState","comment","observationCreated")
					.from(i(bucket.name()))
					.where(x("resourceType").eq(x("$resType"))
					.and(x("userId").eq(x("$userId"))));
			
		} else {
			statement = select("resourceType","docId","systemId", "subType", "userId", "observationState","comment","observationCreated")
					.from(i(bucket.name()))
					.where(x("resourceType").eq(x("$resType"))
					.and(x("userId").eq(x("$userId")))
					.and(x("systemId").eq(x("$systemId"))));			
			
		}
		
		JsonObject placeholderValues = JsonObject.create()
								.put("resType", "observation")
								.put("systemId", systemId)
								.put("userId", userId);
		
		
		String s = "Select docId from " + bucket.name() + " where resourceType";
		N1qlQuery q = N1qlQuery.parameterized(statement, placeholderValues);
		
						
		for (N1qlQueryRow row : bucket.query(q)) {

			JsonObject j = row.value();
			Observation o = new Observation(false);
			
			JsonTranscoder trans = new JsonTranscoder();
			JsonObject jsonObj = JsonObject.create();
			String jString=null;
			try {
				jString = trans.jsonObjectToString(j);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			o = gson.fromJson(jString,o.getClass());
			
			
/*			String ds = j.getString("observationCreated");
			Date date = null;
			try {
				date = dateFormat.parse(ds);
				o.setObservationCreated(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		
			o.setResourceType(j.getString("resourceType"));
			
			o.setDocId(j.getString("docId"));
			o.setSubType(j.getString("subType"));
			o.setUserId(j.getString("userId"));
			o.setObservationState(j.getString("observationState"));
			o.setComment(j.getString("comment") + " Date: " + ds);
			o.setSystemId(j.getString("systemId"));
*/						
			observations.put(o.getDocId(), o);
			
		}
		
		return new ArrayList<Observation> (observations.values());
	}
	
	public List<Observation> getObservationsBySubtype(String systemId, String userId, String subType){

		Gson gson = new Gson();
		observations.clear();
		
		if (userId==null){
			return new ArrayList<Observation> (observations.values());
		}

		if (subType==null){
			return new ArrayList<Observation> (observations.values());
		}

		
		Statement statement;
		
		if (systemId==null){
				statement = select("resourceType","docId","systemId", "subType", "userId", "observationState","comment","observationCreated")
						.from(i(bucket.name()))
						.where(x("resourceType").eq(x("$resType"))
						.and(x("userId").eq(x("$userId")))
						.and(x("subType").eq(x("$subType"))));
		} else {
			statement = select("resourceType","docId","systemId", "subType", "userId", "observationState","comment","observationCreated")
					.from(i(bucket.name()))
					.where(x("resourceType").eq(x("$resType"))
					.and(x("userId").eq(x("$userId")))
					.and(x("systemId").eq(x("$systemId")))
					.and(x("subType").eq(x("$subType"))));
		}
		
		JsonObject placeholderValues = JsonObject.create()
								.put("resType", "observation")
								.put("systemId", systemId)
								.put("subType", subType)
								.put("userId", userId);
		
		
		N1qlQuery q = N1qlQuery.parameterized(statement, placeholderValues);
		
						
		for (N1qlQueryRow row : bucket.query(q)) {

			JsonObject j = row.value();
			Observation o = new Observation(false);
			
			JsonTranscoder trans = new JsonTranscoder();
			JsonObject jsonObj = JsonObject.create();
			String jString=null;
			try {
				jString = trans.jsonObjectToString(j);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			o = gson.fromJson(jString,o.getClass());

			observations.put(o.getDocId(), o);
			
		}
		
		return new ArrayList<Observation> (observations.values());
	}

		
	public Observation getObservationById(String id) {
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Statement statement = select("resourceType","docId","subType", "userId", "observationState","comment","observationCreated","valueComponents")
				.from(i(bucket.name()))
		    	.where(x("resourceType").eq(x("$resourceType")).and(x("docId").eq(x("$docId"))));

		JsonObject placeholderValues = JsonObject.create()
									.put("docId", id)
									.put("resourceType", "observation");
				
		N1qlQuery q = N1qlQuery.parameterized(statement, placeholderValues);
		
		observations.clear();
		Gson gson = new Gson();
						
		for (N1qlQueryRow row : bucket.query(q)) {

			JsonObject j = row.value();
			Observation o = new Observation(false);
			
			JsonTranscoder trans = new JsonTranscoder();
			JsonObject jsonObj = JsonObject.create();
			String jString=null;
			try {
				jString = trans.jsonObjectToString(j);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			o = gson.fromJson(jString,o.getClass());
			
			
			/*String ds = j.getString("observationCreated");
			Date date = null;
			try {
				date = dateFormat.parse(ds);
				o.setObservationCreated(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		
			o.setResourceType(j.getString("resourceType"));
			
			o.setDocId(j.getString("docId"));
			o.setSubType(j.getString("subType"));
			o.setUserId(j.getString("userId"));
			o.setObservationState(j.getString("observationState"));
			o.setComment(j.getString("comment"));
			*/			
			observations.put(o.getDocId(), o);
			
		}
	
		return observations.get(id);
	}
	
	public Observation addObservation(Observation observation){
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		
		observation.setDocId("obs:" + UUID.randomUUID().toString());
		observation.setResourceType("observation");
		if (observation.getObservationCreated() == null) {
			observation.setObservationCreated(d);
		}
		
		Gson gson = new Gson();
		String json = gson.toJson(observation);
		
		JsonTranscoder trans = new JsonTranscoder();
		JsonObject jsonObj = JsonObject.create();
		try {
			jsonObj = trans.stringToJsonObject(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*JsonObject jdoc = JsonObject.create()
		 .put("docId", observation.getDocId())		
		 .put("resourceType", observation.getRessourceType())
		 .put("observationCreated", dateFormat.format(observation.getObservationCreated()))
		 .put("subType", observation.getSubType())
		 .put("system", observation.getSystem())
		 .put("userId", observation.getUserId())		 
		 .put("observationState", observation.getObservationState())
		 .put("valueDouble", observation.getValueDouble())
		 .put("unit", observation.getUnit())
		 .put("comment", observation.get Comment())
		 ;*/
		 

		// Store the Document
        //bucket.upsert(JsonDocument.create(observation.getDocId(), jdoc));
        bucket.upsert(JsonDocument.create(observation.getDocId(), jsonObj));

		return observation;
	}
	
//	public Message updateMassage(Message message){
//		if (message.getId()<=0) {
//			return null;
//		}
//		messages.put(message.getId(),message);
//		return message;
//	}
	
	/*public Observation removeObservation(String id) {
		
		JsonDocument jdoc = JsonDocument.create(id);
		jdoc = bucket.remove(id);
		
		
		
		return observation;
	}*/
	
}
