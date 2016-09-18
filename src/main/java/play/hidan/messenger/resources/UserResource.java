package play.hidan.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import play.hidan.messenger.model.User;
import play.hidan.messenger.service.UserService;

@Path("/users")
public class UserResource {
	
	 UserService userService = new UserService();
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public List<User> getAllUsers() {
	        return userService.getAllUsers();
	    }
		
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public User addUser(User user) {
			return userService.addUser(user);
		}
		
		@GET
		@Path("/{userId}")
		@Produces(MediaType.APPLICATION_JSON)
		public User getUser(@PathParam("userId") String id) {
			return userService.getUserById(id);
		}

}
