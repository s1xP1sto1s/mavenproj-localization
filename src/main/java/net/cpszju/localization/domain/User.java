package net.cpszju.localization.domain;

import java.io.Serializable;
import java.util.Set;

public class User implements Serializable {
	private int id;
	private String name;
	private String password;
	private int sex;
	private String desciption;
	
	private Set<Login> logins;
	private Set<Position> positons;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
	public Set<Login> getLogins() {
		return logins;
	}
	public void setLogins(Set<Login> logins) {
		this.logins = logins;
	}
	public Set<Position> getPositons() {
		return positons;
	}
	public void setPositons(Set<Position> positons) {
		this.positons = positons;
	} 
}
