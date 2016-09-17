package play.hidan.messenger.database;

import java.util.HashMap;


import java.util.Map;





import com.couchbase.client.java.*;

import play.hidan.messenger.model.Message;
import play.hidan.messenger.model.Observation;
import play.hidan.messenger.model.Profile;

public class DatabaseClass {
	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<Long, Profile> profiles = new HashMap<>();
	private static Map<String, Observation> observations = new HashMap<>();
	
	private static Bucket bucket = null;
	private static Cluster cluster = null;
	
	public static Bucket bucketOpen()  {
		
		if (bucket!=null) {
			return bucket;
		}
		try {
			
			//TO-DO
			// get ref to db in a configfile.
			
			
			// Couchbase 4.0.0
			cluster = CouchbaseCluster.create(clusterRef);
		    bucket = cluster.openBucket(bucketName);
		    
		    // Create a N1QL Primary Index (but ignore if it exists)
	        bucket.bucketManager().createN1qlPrimaryIndex(true, false);
	        
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
			
		}
		return bucket;
	}
	
	public static Map<Long, Message> getMessages(){
		return messages;
	}
	
	public static Map<Long, Profile> getProfiles(){
		return profiles;
	}
	
	public static Map<String, Observation> getObservations(){
		return observations;
	}
	
}
