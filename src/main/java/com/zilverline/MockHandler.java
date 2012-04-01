/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.zilverline;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.springframework.core.io.Resource;

/**
 * 
 *
 */
public class MockHandler extends AbstractHandler {

  /*
   * The resource of the (XML, JSON etc) file that should be returned. It must
   * be accessible from the classpath (e.g. test/resources/xxx.json)
   */
  private Resource responseResource;

  /*
   * Check the response to render back
   */
  private void invariant() {
    if (this.responseResource == null) {
      throw new RuntimeException("No responseResource set");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
   * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
   * javax.servlet.http.HttpServletResponse)
   */
  @Override
  public void handle(String target, Request baseRequest,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    invariant();
    ServletOutputStream outputStream = response.getOutputStream();
    IOUtils.copy(responseResource.getInputStream(), outputStream);
    outputStream.flush();
  }

  /**
   * @param responseResource2
   */
  public void setResponseResource(Resource responseResource) {
    this.responseResource = responseResource;
  }

}
