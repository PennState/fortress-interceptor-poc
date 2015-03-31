package edu.psu.swe.fortress.poc.interceptor;

import java.io.IOException;

import javax.inject.Inject;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

//import org.apache.directory.fortress.core.SecurityException;
//import org.apache.directory.fortress.core.rbac.AccessMgrImpl;
//import org.apache.directory.fortress.core.rbac.Permission;
//import org.apache.directory.fortress.core.rbac.Session;
//import org.apache.directory.fortress.core.rbac.User;


@Provider
@FortressProtected
public class FortressAuthorizer implements ContainerRequestFilter
{
  @Context
  ResourceInfo resourceInfo_;
  
  @Inject
  private HttpServletRequest request_;
  
//  @Inject 
//  private AccessMgrImpl accessManager_;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException
  {
//    String user = request_.getRemoteUser();
//
//    Class<?> clazz = resourceInfo_.getResourceClass();
//    FortressProtected classAnnotation = clazz.getAnnotation(edu.psu.swe.fortress.poc.interceptor.FortressProtected.class);
//   
//    Session session = null;
//    
//    try
//    {
//      session = accessManager_.createSession(new User(user), true);
//    }
//    catch (SecurityException e)
//    {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//    
//    List<Permission> fortressPermissions = null;
//    
//    try
//    {
//      fortressPermissions = accessManager_.sessionPermissions(session);
//    }
//    catch (SecurityException e)
//    {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//    
//    List<String> fortressPermissionNames = fortressPermissions.parallelStream()
//                                                              .map(p -> p.getObjName())
//                                                              .collect(Collectors.toList());
//    boolean isPermitted = false;
//    if (classAnnotation != null)
//    {
//      List<String> permissions = Arrays.asList(classAnnotation.permissions());
//      isPermitted = fortressPermissionNames.parallelStream().anyMatch(s -> permissions.contains(s));
//    }
//    
//    Method resourceMethod = resourceInfo_.getResourceMethod();
//    FortressProtected methodAnnotation = resourceMethod.getAnnotation(FortressProtected.class);
//    if (methodAnnotation != null) 
//    {
//      List<String> permissions = Arrays.asList(classAnnotation.permissions());
//      isPermitted = fortressPermissionNames.parallelStream().anyMatch(s -> permissions.contains(s));
//    }
//    
//    if (!isPermitted)
//    {
//      requestContext.abortWith(Response.status(Status.FORBIDDEN).entity("User " + user + " is not permitted to access this resource").build());
//    }
  }
}
