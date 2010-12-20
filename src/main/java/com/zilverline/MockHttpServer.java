/**
 * Copyright 2010
 */
package com.zilverline;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.handler.AbstractHandler;
import org.springframework.core.io.Resource;

/**
 * Very simple {@link Server} to mock http traffic
 * 
 */
public class MockHttpServer extends AbstractHandler {

	/*
	 * The Jetty Server responsible for opening the Socket
	 */
	private Server server;

	/*
	 * The resource of the (XML, JSON etc) file that should be returned. It is
	 * accessible through the server instance and must be accessible from the
	 * classpath
	 */
	private Resource responseResource;

	/**
	 * Construct MockHtppServer
	 * 
	 * @param port
	 *            the port for starting up the HTTP server
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
		this.server.addHandler(this);
	}

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, int dispatch) throws IOException,
			ServletException {
		invariant();
		ServletOutputStream outputStream = response.getOutputStream();
		IOUtils.copy(responseResource.getInputStream(), outputStream);
		outputStream.flush();

	}

	/**
	 * Start the server in a non-blocking mode. The separate thread will be
	 * killed when the test class finishes
	 */
	public void startServer() {
		new Thread(new Runnable() {
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
		}).start();
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

	/*
	 * Check the response to render back
	 */
	private void invariant() {
		if (this.responseResource == null) {
			throw new RuntimeException("No responseResource set");
		}
	}

	/**
	 * @param responseResource
	 *            the responseResource to set
	 */
	public void setResponseResource(Resource responseResource) {
		this.responseResource = responseResource;
	}
}
