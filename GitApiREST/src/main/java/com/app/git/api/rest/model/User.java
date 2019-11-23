/**
 * 
 */
package com.app.git.api.rest.model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author suhas
 *
 */
@XmlRootElement	
public class User {
	
	private String userName;
	private Set<Project> project=new HashSet<Project>();
	/**
	 * @return the user
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param user the user to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the project
	 */
	public Set<Project> getProject() {
		return project;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(Set<Project> project) {
		this.project = project;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(project, userName);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(project, other.project) && Objects.equals(userName, other.userName);
	}
	
	

}
