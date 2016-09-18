package play.hidan.messenger.model;

import java.util.Date;

public class User {
	
	
	//ressource information	
	private String docId; // obs::bp::unique id (could be GUID)
	private String ressourceType; //like "User"

	
	private String userid;
	private String userName;
	private String firstName;
	private String lastName;
	private String fullName;
	
	private String gender;
	private Date birthDate;
	
	
	
	public User() {
		this.ressourceType = "user";
		this.birthDate = new Date();
		//this.docId = "usr:";
	}

		
	public User(String userName) {
		
		this.userName = userName;
		this.ressourceType = "user";
	}



	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
		
	
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}


	public String getDocId() {
		return docId;
	}


	public void setDocId(String docId) {
		this.docId = docId;
	}


	public String getRessourceType() {
		return ressourceType;
	}


	public void setRessourceType(String ressourceType) {
		this.ressourceType = ressourceType;
	}
	
	
	

}
