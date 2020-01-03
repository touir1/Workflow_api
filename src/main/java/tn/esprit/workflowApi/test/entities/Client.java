package tn.esprit.workflowApi.test.entities;

public class Client {
	private String cin;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;

	public Client() {
		super();

	}

	public Client(String cin, String firstName, String lastName) {
		this();

		this.cin = cin;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Client(String cin, String firstName, String lastName, String phoneNumber, String email) {
		this(cin, firstName, lastName);
		
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cin == null) ? 0 : cin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (cin == null) {
			if (other.cin != null)
				return false;
		} else if (!cin.equals(other.cin))
			return false;
		return true;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Client [cin=" + cin + ", prenom=" + firstName + ", nom=" + lastName + ", telephone="
				+ phoneNumber + ", email=" + email + "]";
	}
	
	

}
