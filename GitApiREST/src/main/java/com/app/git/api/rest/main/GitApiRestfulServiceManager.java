/**
 * 
 */
package com.app.git.api.rest.main;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import com.app.git.api.rest.exception.GitApiBaseException;
import com.app.git.api.rest.model.User;
import com.app.git.api.rest.services.GitApiRestfulServices;

/**
 * @author suhas
 * This class is responsible for mapping rest request to match method.
 */

@Path("/git")
//@Consumes(MediaType.APPLICATION_JSON)				
@Produces(MediaType.APPLICATION_JSON)	
public class GitApiRestfulServiceManager {

	GitApiRestfulServices gitApiRestfulServices = new GitApiRestfulServices();
	
	@GET
	@Path("/{user}/get")
	public Response getUserProjects(@PathParam("user") String userId) {
		User userObj=null;
			try {
				userObj=gitApiRestfulServices.getApiResponse(userId);
				
			} catch (MalformedURLException e) {
				return getResponse(e,Response.Status.FORBIDDEN);
			} catch (IOException e) {
				return getResponse(e,Response.Status.BAD_REQUEST);
			} catch (GitApiBaseException e) {
				return Response
					      .status(e.getErrorCode())
					      .entity(e.getMessage())
					      .type("text/plain")
					      .build();
			} catch (Exception e) {
				return Response
					      .status(1000)
					      .entity(e.getMessage())
					      .type("text/plain")
					      .build();
			}
			return Response
				      .status(Response.Status.OK)
				      .entity(userObj)
				      .build();
	
	}

	/**
	 * @param e
	 * @return
	 */
	private Response getResponse(Exception e,StatusType status) {
		return Response
			      .status(status)
			      .entity(e.getMessage())
			      .build();
	}
}
