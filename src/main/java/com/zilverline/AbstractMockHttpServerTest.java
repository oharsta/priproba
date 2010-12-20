/**
 * Copyright 2010
 */
package com.zilverline;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.core.io.Resource;

/**
 * Tests subclassing {@link AbstractMockHttpServerTest} can communicate with a
 * mock Http endpoint that behavious like a real server.
 * 
 * The idea behind this class is that http responses can be tested (regardless
 * of the input request).
 * 
 * 
 * 
 */
public abstract class AbstractMockHttpServerTest {
  /*
   * The Server that handles the Soap calls
   */
  private static MockHttpServer server = new MockHttpServer(8088);

  @BeforeClass
  public static void startServer() {
    server.startServer();
  }

  @AfterClass
  public static void stopServer() {
    server.stopServer();
  }

  /**
   * Set the expected response on the Handler
   * 
   * @param responseResource
   *          the resource of the file containing the xml response
   */
  protected void setResponseResource(Resource responseResource) {
    server.setResponseResource(responseResource);
  }

}
