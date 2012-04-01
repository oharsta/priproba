/**
 * Copyright 2010
 */
package com.zilverline;

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 * {@link Test} for {@link AbstractMockHttpServerTest}
 * 
 * 
 */
public class MockHttpServerTest extends AbstractMockHttpServerTest {

  /**
   * Test the MockHttpServer.
   */
  @Test
  public void testMockHappyFlow() throws Exception {
    ClassPathResource responseResource = new ClassPathResource("test.json");
    super.setResponseResource(responseResource);
    InputStream is = new URL("http://localhost:8088/testUrl").openStream();
    Assert.assertEquals(IOUtils.toString(responseResource.getInputStream()),
        IOUtils.toString(is));
  }

}
