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
			
			// Couchbase 4.5
			//cluster = CouchbaseCluster.create("ec2-54-200-95-47.us-west-2.compute.amazonaws.com");
		    //bucket = cluster.openBucket("memylifePOC");

			// Couchbase 4.0.0
			cluster = CouchbaseCluster.create("ec2-52-34-116-157.us-west-2.compute.amazonaws.com");
		    bucket = cluster.openBucket("memylifePOC");
		    
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
