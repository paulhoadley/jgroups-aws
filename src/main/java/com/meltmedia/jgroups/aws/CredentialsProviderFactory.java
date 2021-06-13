package com.meltmedia.jgroups.aws;

import com.amazonaws.auth.AWSCredentialsProvider;
import org.jgroups.logging.Log;
import org.jgroups.logging.LogFactory;
import org.jgroups.util.Util;

public class CredentialsProviderFactory {
  private final Log log;

  public CredentialsProviderFactory() {
    this(LogFactory.getLog(AWS_PING.class));
  }

  public CredentialsProviderFactory(final Log log) {
    this.log = log;
  }

  /**
   * Loads a new instance of the credential provider, using the same class loading rules from org.jgroups.Util.loadClass(String, Class).
   * 
   * @param credentialProviderClass the class name of the AWSCredentialsProvider to load.
   * @return an instance of the credential provider
   * @throws ClassNotFoundException if the implementation could not be found.
   * @throws InstantiationException if the implementation does not have a no argument constructor.
   */
  public AWSCredentialsProvider createCredentialsProvider(final String credentialProviderClass) throws Exception {
    try {
      final Class<?> credsProviderClazz = Util.loadClass(credentialProviderClass, getClass());
      return (AWSCredentialsProvider) credsProviderClazz.newInstance();
    } catch (ClassNotFoundException e) {
      throw new Exception("unable to load credentials provider class " + credentialProviderClass);
    } catch (InstantiationException e) {
      log.error("an instance of " + credentialProviderClass + " could not be created. Please check that it implements" +
          " interface AWSCredentialsProvider and that is has a public empty constructor !");
      throw e;
    }
  }
}
