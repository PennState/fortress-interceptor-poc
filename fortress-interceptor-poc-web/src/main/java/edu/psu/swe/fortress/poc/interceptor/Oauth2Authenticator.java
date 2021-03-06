package edu.psu.swe.fortress.poc.interceptor;

import java.io.IOException;
import java.util.Date;
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

@Provider
@Oauth2Protected
public class Oauth2Authenticator implements ContainerRequestFilter
{
  @Context
  ResourceInfo resourceInfo_;
  
  @Inject
  private HttpServletRequest request_;
  
  private String authEnpoint_;
  private String applicationName_;
  private String bearerToken_ = null;
  private String sharedSecret_;
  private String refreshToken_;
  private Date bearerTokenExpiration_;
  
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException
  {
    String user = request_.getRemoteUser();

    String bearerToken = requestContext.getHeaderString("Authorization");

    try
    {
      String authUrl = authEnpoint_ + "/tokenInfo/" + bearerToken;
      
      Client client = ClientBuilder.newClient();
      TokenInfoDto dto =  client.target(authUrl)
                          .request().accept("application/json")
                          .get(TokenInfoDto.class);
      
      //TODO - What do we do here?  The DTO has the application, should we use it somehow?
      if (!dto.getScopes().contains(applicationName_))
      {
        requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
      }
    }
    catch(WebApplicationException exception)
    {
      if (exception.getResponse().getStatusInfo().equals(Status.UNAUTHORIZED))
      {
        throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).entity("No valid bearer token for " + user).build());
      }
      else
      {
        throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).entity("Unable to authenticate " + exception.getLocalizedMessage()).build());
      }
    }
  }
  
  @PostConstruct
  private void init() throws NamingException
  {
    PropertiesService propertiesService = PropertyServiceFactory.getService();
    Properties properties = propertiesService.getProperties("oauth2.properties");
    authEnpoint_ = properties.getProperty("oauth2.authorization.server.endpoint");
    applicationName_ = properties.getProperty("oauth2.authorization.application.name");
    sharedSecret_ = properties.getProperty("oath2.authorization.shared.secret");
  }
}
