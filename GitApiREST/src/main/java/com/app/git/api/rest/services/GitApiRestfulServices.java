/**
 * 
 */
package com.app.git.api.rest.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.git.api.rest.exception.GitApiBaseException;
import com.app.git.api.rest.model.Project;
import com.app.git.api.rest.model.User;

/**
 * @author M7597230 This class contains below functionality. --> Call 'GitHub'
 *         and 'GitLab' rest API. Parse the response from both API and store in
 *         single object
 */
public class GitApiRestfulServices {

	private final String PROJECTS = "/projects";
	private final String HTTPS_GITLAB_URL = "https://gitlab.com/api/v4/users/";
	private final String HTTPS_GITHUB_URL = "https://api.github.com/users/";

	//final JsonParser parse = new JsonParser();

	/**
	 * @param user
	 * @return This method returns user and his projects in 'GitHub' and 'GitLab'
	 *         repo.
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws GitApiBaseException
	 */
	public User getApiResponse(String user) throws MalformedURLException, IOException, GitApiBaseException {
		final User userObj = new User();
		String errorMsg = null;
		userObj.setUserName(user);
		boolean isException = false;
		// call github rest api.
		try {
			callGitHubService(userObj);
		} catch (GitApiBaseException e) {
			errorMsg = e.getErrorMsg();
			isException = true;
		}
		// call gitlab rest api.
		try {
			callGitLabService(userObj);
		} catch (GitApiBaseException e) {
			// System.out.println(e.getErrorMsg());
			if (isException) {
				e.setErrorMsg(e.getErrorMsg() + " " + errorMsg);
				throw e;
			}
		}
		return userObj;
	}

	/**
	 * @param user
	 * @throws MalformedURLException
	 * @throws IOException           This method give rest call to github api.
	 * @throws GitApiBaseException
	 */
	public void callGitHubService(User userObj) throws MalformedURLException, IOException, GitApiBaseException {
		// get HttpsURLConnection object contains object reference for calling github
		// api.
		HttpsURLConnection myURLConnection = getHttpUrlConnectionObj(
				HTTPS_GITHUB_URL + userObj.getUserName() + PROJECTS);
		// set request headers
		setRequestHeaders(myURLConnection);
		// populate user's project from github repo.
		populateUserObject(myURLConnection, "GitHub", userObj);
	}

	/**
	 * @param userObj
	 * @throws MalformedURLException
	 * @throws IOException           This method give rest call to gitlab api.
	 * @throws GitApiBaseException
	 */
	public void callGitLabService(User userObj) throws MalformedURLException, IOException, GitApiBaseException {
		// get HttpsURLConnection object contains object reference for calling gitlab
		// api.
		HttpsURLConnection myURLConnection = getHttpUrlConnectionObj(
				HTTPS_GITLAB_URL + userObj.getUserName() + PROJECTS);
		myURLConnection.setRequestMethod("GET");
		// populate user's project from gitlab repo.
		populateUserObject(myURLConnection, "GitLab", userObj);
	}

	/**
	 * @param myURLConnection
	 * @param type
	 * @throws IOException This method populate user's project.
	 */
	private void populateUserObject(HttpsURLConnection myURLConnection, String type, User userObj)
			throws GitApiBaseException, IOException {
		// GET Response Code once api done execution
		int responseCode = myURLConnection.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpsURLConnection.HTTP_OK) { // success
			// process api response from buffer stream to string variable.
			String responseStr = getGitApiRestResponseAsString(myURLConnection).toString();
			// parse response json
			parseGitApiResonseJson(responseStr, type, userObj);
		} else {
			// fail
			throw new GitApiBaseException(responseCode, "User Does Not Exist on " + type + "!");
		}
	}

	/**
	 * @param myURLConnection
	 * @throws ProtocolException This method set request headers.
	 */
	private void setRequestHeaders(HttpsURLConnection myURLConnection) throws ProtocolException {
		myURLConnection.setRequestMethod("GET");
		// set request headers.
		myURLConnection.setRequestProperty("Accept", "application/vnd.github.inertia-preview+json");
	}

	/**
	 * @param httpUrl
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException           This method returns HttpsURLConnection object.
	 */
	private HttpsURLConnection getHttpUrlConnectionObj(String httpUrl) throws MalformedURLException, IOException {
		return (HttpsURLConnection) new URL(httpUrl).openConnection();
	}

	/**
	 * @param myURLConnection
	 * @return
	 * @throws IOException This method return api response in string variable.
	 */
	private StringBuffer getGitApiRestResponseAsString(HttpsURLConnection myURLConnection) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		// read api response line by line.
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response;
	}

	/**
	 * @param input
	 * @param key   This method parse response json using gson library.
	 * @throws GitApiBaseException
	 */
	private void parseGitApiResonseJson(String input, String key, User userObj) throws GitApiBaseException {
		JSONArray jsonArr = new JSONArray(input);
		int length = jsonArr.length();
		if(jsonArr!=null && length>0) {
			for (int i = 0; i < length; i++) {
				JSONObject jsonObj = jsonArr.getJSONObject(i);
				String projectName2 = (String) jsonObj.get("name");
				setUserObject(key, projectName2, userObj);
			}
		}
		else {
			throw new GitApiBaseException(202,"User Exist on " + key+" but User has not created any project"); 
			}
		}

	/**
	 * @param key
	 * @param UserName
	 * @return This method set user object properties.
	 */
	private void setUserObject(String key, String projectName, User user) {
		Project project = new Project();
		project.setProjectName(projectName);
		project.setProjectPresentOn(key);
		user.getProject().add(project);
	}

}
