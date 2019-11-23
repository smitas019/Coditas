/**
 * 
 */
package com.app.git.api.rest.main;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.app.git.api.rest.exception.GitApiBaseException;
import com.app.git.api.rest.model.User;
import com.app.git.api.rest.services.GitApiRestfulServices;

/**
 * @author suhas
 * This class is responsible for mapping rest request to match method.
 */

@Path("/git")
@Consumes(MediaType.APPLICATION_XML)				
@Produces(MediaType.APPLICATION_XML)	
public class GitApiRestfulServiceManager {

	GitApiRestfulServices gitApiRestfulServices = new GitApiRestfulServices();
	
	@GET
	@Path("/{user}/get")
	public User getUserProjects(@PathParam("user") String userId) {
		User userObj=null;
			try {
				userObj=gitApiRestfulServices.getApiResponse(userId);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (GitApiBaseException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println(e);
			}
			return userObj;
	
	}
}
