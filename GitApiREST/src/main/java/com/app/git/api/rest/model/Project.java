/**
 * 
 */
package com.app.git.api.rest.model;

import java.util.Objects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author suhas
 *
 */
//@XmlRootElement
public class Project {
	
	private String projectName;
	private String projectPresentOn;
	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return the projectPresentOn
	 */
	public String getProjectPresentOn() {
		return projectPresentOn;
	}
	/**
	 * @param projectPresentOn the projectPresentOn to set
	 */
	public void setProjectPresentOn(String projectPresentOn) {
		this.projectPresentOn = projectPresentOn;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(projectName, projectPresentOn);
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
		Project other = (Project) obj;
		return Objects.equals(projectName, other.projectName)
				&& Objects.equals(projectPresentOn, other.projectPresentOn);
	}

	
}
