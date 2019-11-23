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

import com.app.git.api.rest.exception.GitApiBaseException;
import com.app.git.api.rest.model.Project;
import com.app.git.api.rest.model.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author M7597230				
 * This class contains below functionality.
 * --> Call 'GitHub' and 'GitLab' rest API. Parse the response from both API and store in single object
 */
public class GitApiRestfulServices {
	
	private  final String PROJECTS = "/projects";
	private  final String HTTPS_GITLAB_URL = "https://gitlab.com/api/v4/users/";
	private  final String HTTPS_GITHUB_URL = "https://api.github.com/users/";
	
	final JsonParser parse =new JsonParser();
	
	/**
	 * @param user
	 * @return
	 * This method returns user and his projects in 'GitHub' and 'GitLab' repo.
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws GitApiBaseException 
	 */
	public User getApiResponse(String user) throws MalformedURLException, IOException, GitApiBaseException {
		final User userObj = new User();
		userObj.setUserName(user);
		//call github rest api.
		callGitHubService(userObj);
		//call gitlab rest api.
		callGitLabService(userObj);
		return userObj;
	}

	
	/**
	 * @param user
	 * @throws MalformedURLException 
	 * @throws IOException
	 * This method give rest call to github api.
	 * @throws GitApiBaseException 
	 */
	public  void callGitHubService(User userObj) throws MalformedURLException, IOException, GitApiBaseException{
		//get HttpsURLConnection object contains object reference for calling github api.
		HttpsURLConnection myURLConnection = getHttpUrlConnectionObj
				(HTTPS_GITHUB_URL+userObj.getUserName()+PROJECTS);
		//set request headers
		setRequestHeaders(myURLConnection);
		//populate user's project from github repo.
		populateUserObject(myURLConnection,"GitHub",userObj);
	}
	
	/**
	 * @param userObj
	 * @throws MalformedURLException 
	 * @throws IOException
	 * This method give rest call to gitlab api.
	 * @throws GitApiBaseException 
	 */
	public void callGitLabService(User userObj) throws MalformedURLException, IOException, GitApiBaseException {
		//get HttpsURLConnection object contains object reference for calling gitlab api.
		HttpsURLConnection myURLConnection = getHttpUrlConnectionObj(
				HTTPS_GITLAB_URL+userObj.getUserName()+PROJECTS);
		myURLConnection.setRequestMethod("GET");
		//populate user's project from gitlab repo.
		populateUserObject(myURLConnection,"GitLab",userObj);
	}

	/**
	 * @param myURLConnection
	 * @param type
	 * @throws IOException
	 * This method populate user's project.
	 */
	private  void populateUserObject(HttpsURLConnection myURLConnection, String type,
			User userObj)
			throws GitApiBaseException, IOException{
		//GET Response Code once api done execution
		int responseCode = myURLConnection.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpsURLConnection.HTTP_OK) { // success
			//process api response from buffer stream to string variable. 
			String responseStr = getGitApiRestResponseAsString(myURLConnection).toString();
			//parse response json
			parseGitApiResonseJson(responseStr.substring(1, responseStr.length()-1),type,
					userObj);
		} else {
			//fail
			throw new GitApiBaseException("GET request not worked.");
		}
	}

	/**
	 * @param myURLConnection
	 * @throws ProtocolException
	 * This method set request headers.
	 */
	private  void setRequestHeaders(HttpsURLConnection myURLConnection)
			throws ProtocolException {
		myURLConnection.setRequestMethod("GET");
		//set request headers.
		myURLConnection.setRequestProperty("Accept", "application/vnd.github.inertia-preview+json");
	}


	/**
	 * @param httpUrl
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * This method returns HttpsURLConnection object.
	 */
	private  HttpsURLConnection getHttpUrlConnectionObj(String httpUrl)
			throws MalformedURLException, IOException {
		return (HttpsURLConnection) new URL(httpUrl).openConnection();
	}

	/**
	 * @param myURLConnection
	 * @return
	 * @throws IOException
	 * This method return api response in string variable. 
	 */
	private  StringBuffer getGitApiRestResponseAsString(
			HttpsURLConnection myURLConnection) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				myURLConnection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		//read api response line by line.
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response;
	}
	/**
	 * @param input
	 * @param key
	 * This method parse response json using gson library.
	 * @throws GitApiBaseException 
	 */
	private  void parseGitApiResonseJson(String input,
			String key,User userObj) throws GitApiBaseException{
		//input="{\"owner_url\":\"https://api.github.com/users/smitas019\",\"url\":\"https://api.github.com/Users/3554612\",\"html_url\":\"https://github.com/users/smitas019/Users/1\",\"columns_url\":\"https://api.github.com/Users/3554612/columns\",\"id\":3554612,\"node_id\":\"MDc6UHJvamVjdDM1NTQ2MTI=\",\"name\":\"User_1\",\"body\":\"\",\"number\":1,\"state\":\"open\",\"creator\":{\"login\":\"smitas019\",\"id\":47202339,\"node_id\":\"MDQ6VXNlcjQ3MjAyMzM5\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/47202339?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/smitas019\",\"html_url\":\"https://github.com/smitas019\",\"followers_url\":\"https://api.github.com/users/smitas019/followers\",\"following_url\":\"https://api.github.com/users/smitas019/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/smitas019/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/smitas019/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/smitas019/subscriptions\",\"organizations_url\":\"https://api.github.com/users/smitas019/orgs\",\"repos_url\":\"https://api.github.com/users/smitas019/repos\",\"events_url\":\"https://api.github.com/users/smitas019/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/smitas019/received_events\",\"type\":\"User\",\"site_admin\":false},\"created_at\":\"2019-11-20T05:34:54Z\",\"updated_at\":\"2019-11-20T05:35:15Z\"}\r\n" + 
			//	"";
		JsonElement jsonElement = parse.parse(input);
		if(jsonElement.isJsonObject())
		{
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			String projectName = jsonObject.get("name").getAsString();
			//set user object properties from json 
			setUserObject(key,projectName,userObj);
		}else {
			throw new GitApiBaseException("User Does not Exist!");
		}
	}

	/**
	 * @param key
	 * @param UserName
	 * @return
	 * This method set user object properties.
	 */
	private void setUserObject(String key, String projectName,User user) {
		Project project= new Project();
		project.setProjectName(projectName);
		project.setProjectPresentOn(key);
		user.getProject().add(project);
	}
	
	

}
