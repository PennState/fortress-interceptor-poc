package edu.psu.swe.fortress.poc.interceptor;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import edu.psu.javaee.libraries.oauth.data.TokenInfoDto;
import edu.psu.javaee.services.propertiesservice.services.PropertiesService;
import edu.psu.javaee.services.propertiesservice.util.PropertyServiceFactory;
import edu.psu.swe.fortress.poc.interceptor.Oauth2Protected;

@Provider
@Oauth2Protected
public class Oauth2Authenticator implements ContainerRequestFilter
{
  @Context
  ResourceInfo resourceInfo_;
  
  @Inject
  private HttpServletRequest request_;
  
  private String authEnpoint_;
  
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException
  {
    String user = request_.getRemoteUser();

    String bearerToken = requestContext.getHeaderString("Authorization");

    try
    {
      String authUrl = authEnpoint_ + "/" + bearerToken;
      
      Client client = ClientBuilder.newClient();
      TokenInfoDto dto =  client.target(authUrl)
                          .request().accept("application/json")
                          .get(TokenInfoDto.class);
      
      //TODO - What do we do here?  The DTO has the application, should we use it somehow?

    }
    catch(WebApplicationException exception)
    {
      if (exception.getResponse().getStatusInfo().equals(Status.NOT_FOUND))
      {
        throw new WebApplicationException(Response.status(Status.FORBIDDEN).entity("No valid bearer token for " + user).build());
      }
      else
      {
        throw new WebApplicationException(Response.status(Status.FORBIDDEN).entity("Unable to authenticate " + exception.getLocalizedMessage()).build());
      }
    }
  }
  
  @PostConstruct
  private void init() throws NamingException
  {
    PropertiesService propertiesService = PropertyServiceFactory.getService();
    Properties properties = propertiesService.getProperties("oauth2.properties");
    authEnpoint_ = properties.getProperty("oauth2.authorization.server.endpoint");
  }
}
