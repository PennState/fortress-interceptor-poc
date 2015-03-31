package edu.psu.swe.fortress.poc.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import edu.psu.swe.fortress.poc.interceptor.FortressProtected;
import edu.psu.swe.fortress.poc.interceptor.Oauth2Protected;

@FortressProtected(permissions={"foo", "bar"})
@Path("tests")
public class ResourceImpl
{
  @GET
  @Produces("application/text")
  @Oauth2Protected
  @FortressProtected(permissions={"foo", "bar", "foo2"})
  public String getHello()
  {
    System.out.println("In the GET method");
    
    return "hello";
  }
  
}
