/**
 * Copyright 2010
 */
package com.zilverline;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.springframework.core.io.Resource;

/**
 * Very simple {@link Server} to mock http traffic
 * 
 */
public class MockHttpServer {

  /*
   * The Jetty Server responsible for listening to requests
   */
  private Server server;

  private MockHandler handler;

  /**
   * Construct MockHtppServer
   * 
   * @param port
   *          the port for starting up the HTTP server
   */
  public MockHttpServer(int port) {
    super();
    initServer(port);

  }

  /*
   * Initialize the server
   */
  private void initServer(int port) {
    this.server = new Server();
    SocketConnector connector = new SocketConnector();
    connector.setPort(port);
    this.server.addConnector(connector);
    this.handler = new MockHandler();
    this.server.setHandler(handler);
  }

  /**
   * Start the server in a non-blocking mode. The separate thread will be killed
   * when the test class finishes
   */
  public void startServer() {
    doStartServer(true);
  }

  /**
   * Start the server in a non-blocking mode. The separate thread will be killed
   * when the test class finishes
   * 
   * @param async
   *          if {@literal true} it will start with a short delay
   */
  private void doStartServer(boolean async) {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          server.start();
          // just wait until the server gets killed
          System.in.read();
        } catch (Exception e) {
          stopServer();
          throw new RuntimeException(e);
        }
      }
    });
    if (async) {
      thread.start();
      // give the server some time to start
      sleep(150);
    } else {
      thread.run();
    }
  }

  /*
   * Sleep for some time
   */
  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      stopServer();
      throw new RuntimeException(e);
    }
  }

  /**
   * Stop the server
   */
  public void stopServer() {
    try {
      this.server.stop();
      this.server.join();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  /**
   * @param responseResource
   *          the responseResource to set
   */
  public void setResponseResource(Resource responseResource) {
    this.handler.setResponseResource(responseResource);
  }

}
