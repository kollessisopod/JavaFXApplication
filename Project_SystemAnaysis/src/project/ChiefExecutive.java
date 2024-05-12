package project;

import java.io.Serializable;

public class ChiefExecutive implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String surname;
	private String username;
	private String password;

	public ChiefExecutive(String name, String surname, String username, String password) {
		super();
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getUsername() {
		return username;
	}
	
	
}
