package play.hidan.messenger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
 
public class Observation {

	//ressource information	
	private String docId; // obs::bp::unique id (could be GUID)
	private String resourceType; //like "Observation"
	private String subType; //like "blood-pressure"
	
	//identifier
	private String systemId;  // "Diabetes-app"
	//private String deviceId; // like mobile phone mac-address
	
	//Subject
	private String userId; //AH
	
	//private Location location;
	// this is just a comment to see changes in Git.
	
	//component
	private String observationState; //like "morning", "two hours after",etc
	private Date observationCreated;
	
	//Valuequantity
	private ArrayList<ValueComponent> valueComponents = new ArrayList<>();
	private String interpretation; // like High, Low or Normal for blood pressure etc
	
	private String comment;
	
		
	
	public Observation() {
		this.docId = "obs:" + UUID.randomUUID().toString();
		this.resourceType = "observation";
		this.observationCreated = new Date();
		
	}

	public Observation(Boolean newObservation ) {
		// this is used for existing observations
	}
	
	
	public ArrayList<ValueComponent> getValueComponents() {
		return valueComponents;
	}

	public void setValueComponents(ArrayList<ValueComponent> valueComponents) {
		this.valueComponents = valueComponents;
	}


	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}


	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getObservationState() {
		return observationState;
	}

	public void setObservationState(String observationState) {
		this.observationState = observationState;
	}

	public Date getObservationCreated() {
		return observationCreated;
	}

	public void setObservationCreated(Date observationCreated) {
		this.observationCreated = observationCreated;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getInterpretation() {
		return interpretation;
	}

	public void setInterpretation(String interpretation) {
		this.interpretation = interpretation;
	}

	@Override
	public String toString() {
		return "Observation [subType=" + subType + ", system=" + systemId
				+ ", userId=" + userId + ", observationState="
				+ observationState + ", observationCreated="
				+ observationCreated + "]";
	}


	
	
	
	
	
}
