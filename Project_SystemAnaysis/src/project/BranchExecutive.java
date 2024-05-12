package project;

import java.io.Serializable;

public class BranchExecutive implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String surname;
	private String username;
	private Branch belongingBranch;
	private String password;

	public BranchExecutive(String name, String surname, String username, Branch belongingBranch, String password) {
		super();
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.belongingBranch = belongingBranch;
		this.password = password;

	}
	
	public Branch getBelongingBranch() {
		return belongingBranch;
	}

	public void setBelongingBranch(Branch belongingBranch) {
		this.belongingBranch = belongingBranch;
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
