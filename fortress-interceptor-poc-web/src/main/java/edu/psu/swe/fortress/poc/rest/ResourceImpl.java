package edu.psu.swe.fortress.poc.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import edu.psu.swe.fortress.poc.interceptor.FortressProtected;

@FortressProtected(permissions={"foo", "bar"})
@Path("tests")
public class ResourceImpl
{
  @GET
  @Produces("application/text")
  @FortressProtected(permissions={"foo", "bar", "foo2"})
  public String getHello()
  {
    System.out.println("In the GET method");
    //FortressProtected annotation = this.getClass().getAnnotation(edu.psu.swe.fortress.poc.interceptor.FortressProtected.class);
  
    //System.out.println(annotation.toString());
    
    return "hello";
  }
  
}
