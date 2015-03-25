package edu.psu.swe.fortress.poc.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import edu.psu.swe.fortress.poc.interceptor.FortressAuthorizer;
import edu.psu.swe.fortress.poc.interceptor.FortressProtected;

@ApplicationPath("")
public class FortressTestApp extends Application
{
  private Set<Class<?>> clazzez_ = new HashSet<>();
  {
    clazzez_.add(ResourceImpl.class);
    clazzez_.add(ResourceImpl2.class);
    clazzez_.add(FortressProtected.class);
    clazzez_.add(FortressAuthorizer.class);
  }
  public Set<Class<?>> getClasses()
  {
    return clazzez_;
  }
}
