/**
 *
 * Copyright (C) 2009 Global Cloud Specialists, Inc. <info@globalcloudspecialists.com>
 *
 * ====================================================================
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
 * ====================================================================
 */
package org.jclouds.rackspace.cloudservers.decorators;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.net.URI;

import javax.ws.rs.HttpMethod;

import org.jclouds.http.HttpRequest;
import org.jclouds.http.functions.config.ParserModule;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Tests behavior of {@code AddServerNameAsJsonEntity}
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "cloudservers.AddServerNameAsJsonEntityTest")
public class AddServerNameAsJsonEntityTest {

   Injector injector = Guice.createInjector(new ParserModule());

   @Test(expectedExceptions = IllegalStateException.class)
   public void testPostIsIncorrect() {
      AddServerNameAsJsonEntity binder = new AddServerNameAsJsonEntity();
      injector.injectMembers(binder);
      HttpRequest request = new HttpRequest(HttpMethod.POST, URI.create("http://localhost"));
      binder.decorateRequest(request, ImmutableMap.of("name", "foo"));
   }

   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testMustBeString() {
      AddServerNameAsJsonEntity binder = new AddServerNameAsJsonEntity();
      injector.injectMembers(binder);
      HttpRequest request = new HttpRequest(HttpMethod.POST, URI.create("http://localhost"));
      binder.decorateRequest(request, new File("foo"));
   }

   @Test
   public void testCorrect() {
      AddServerNameAsJsonEntity binder = new AddServerNameAsJsonEntity();
      injector.injectMembers(binder);
      HttpRequest request = new HttpRequest(HttpMethod.PUT, URI.create("http://localhost"));
      binder.decorateRequest(request, "foo");
      assertEquals("{\"server\":{\"name\":\"foo\"}}", request.getEntity());
   }

   @Test(expectedExceptions = { NullPointerException.class, IllegalStateException.class })
   public void testNullIsBad() {
      AddServerNameAsJsonEntity binder = new AddServerNameAsJsonEntity();
      injector.injectMembers(binder);
      HttpRequest request = new HttpRequest(HttpMethod.PUT, URI.create("http://localhost"));
      binder.decorateRequest(request, null);
   }
}