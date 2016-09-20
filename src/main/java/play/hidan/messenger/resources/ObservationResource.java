package play.hidan.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import play.hidan.messenger.model.Message;
import play.hidan.messenger.model.Observation;
import play.hidan.messenger.service.MessageService;
import play.hidan.messenger.service.ObservationService;

@Path("/observations")
public class ObservationResource {

	 ObservationService observationService = new ObservationService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Observation> getObservation(@HeaderParam("customSystemId") String systemId,
										 @HeaderParam("customUserId") String userId) {
        return observationService.getAllObservations(systemId,userId);
    }

	@GET
	@Path("/subtype/{subtype}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Observation> getObservationBySubtype(@PathParam("subtype") String subType,
											@HeaderParam("customSystemId") String systemId,
											@HeaderParam("customUserId") String userId) {
        return observationService.getObservationsBySubtype(systemId, userId, subType);
    }

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Observation addObservation(Observation observation) {
		return observationService.addObservation(observation);
	}
	
//	@PUT
//	@Path("/{messageId}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Message updateMessage(@PathParam("messageId") long id, Message message) {
//		message.setId(id);
//		return messageservice.updateMassage(message);
//	}
//	
//	@DELETE
//	@Path("/{messageId}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public void deleteMessage(@PathParam("messageId") long id) {
//		messageservice.removeMessage(id);
//	}
//	
//	
	@GET
	@Path("/{observationId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Observation getObservation(@PathParam("observationId") String id) {
		return observationService.getObservationById(id);
	}
}
