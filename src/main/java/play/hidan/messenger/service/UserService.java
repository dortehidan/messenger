package play.hidan.messenger.service;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.i;
import static com.couchbase.client.java.query.dsl.Expression.x;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import play.hidan.messenger.database.DatabaseClass;
import play.hidan.messenger.model.Message;
import play.hidan.messenger.model.Observation;
import play.hidan.messenger.model.User;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.client.java.query.Statement;

public class UserService {

	private Map<String,User> users = DatabaseClass.getUsers();
 	private Bucket bucket = DatabaseClass.bucketOpen();
	
 	public UserService() {
	
	}
	
 	
 	public List<User> getAllUsers(){

 		Statement statement = select("userId", "userName", "firstName", "lastName", "fullName", "gender")
 				.from(i(bucket.name()))
			    .where(x("resourceType").eq(x("$resourceType")));

 		JsonObject placeholderValues = JsonObject.create()
 										.put("resourceType", "user");
 		
 		N1qlQuery q = N1qlQuery.parameterized(statement, placeholderValues);
 		
 		users.clear();
 		
 		for (N1qlQueryRow row : bucket.query(q)) {

 			JsonObject j = row.value();
 			User u = new User(j.getString("userId"));
 			u.setUserName(j.getString("userName"));
 			u.setFirstName(j.getString("firstName"));
 			u.setLastName(j.getString("lastName"));
 			u.setFullName(j.getString("fullName"));
 			u.setGender(j.getString("gender"));
 			users.put(u.getUserid(), u);
 			
 		}
		
		return new ArrayList<User> (users.values());
	}

	
	public User getUserById(String id) {
	
	
 		Statement statement = select("userId", "userName", "firstName", "lastName", "fullName", "gender")
			.from(i(bucket.name()))
		    .where(x("resourceType").eq(x("$resourceType")).and(x("userName").eq(x("$userName"))));

		JsonObject placeholderValues = JsonObject.create()
									.put("userName", id)
									.put("resourceType", "user");
	
		N1qlQuery q = N1qlQuery.parameterized(statement, placeholderValues);
	
		users.clear();
		
		for (N1qlQueryRow row : bucket.query(q)) {
	
			JsonObject j = row.value();
			User u = new User(j.getString("userName"));
			u.setUserid(j.getString("userId"));
			u.setFirstName(j.getString("firstName"));
			u.setLastName(j.getString("lastName"));
			u.setFullName(j.getString("fullName"));
			u.setGender(j.getString("gender"));
			users.put(u.getUserName(), u);
			
		}
	
		return users.get(id);
  }
	
	
	public User addUser(User user){
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		user.setDocId("usr:" + user.getUserName());
		user.setUserid(UUID.randomUUID().toString());
		user.setRessourceType("user");
		user.setFullName(user.getFirstName()+ " " + user.getLastName());
		String bd_str = dateFormat.format(user.getBirthDate());
		
		
		JsonObject jdoc = JsonObject.create()
		 .put("resourceType", user.getRessourceType())
		 .put("docId", user.getDocId())
		 .put("userId", user.getUserid())
		 .put("userName",  user.getUserName())
		 .put("firstName", user.getFirstName())
		 .put("lastName", user.getLastName())
		 .put("fullName", user.getFullName())
		 .put("gender", user.getGender())
		 .put("birthDate", bd_str);
		
		// Store the Document
        bucket.upsert(JsonDocument.create(user.getDocId(), jdoc));

		return user;
	}
 	
	
}
