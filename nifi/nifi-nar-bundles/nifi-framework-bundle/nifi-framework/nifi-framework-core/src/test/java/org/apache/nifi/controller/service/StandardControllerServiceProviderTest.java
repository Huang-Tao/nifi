/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.controller.service;

import org.apache.nifi.controller.ControllerService;
import org.apache.nifi.controller.StandardFlowServiceTest;
import org.apache.nifi.nar.ExtensionManager;
import org.apache.nifi.nar.NarClassLoaders;
import org.apache.nifi.reporting.InitializationException;
import org.apache.nifi.util.NiFiProperties;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StandardControllerServiceProviderTest {
	
    private ControllerService proxied;
    private ControllerService implementation;
    
    @BeforeClass
    public static void setupSuite() throws Exception {
        System.setProperty(NiFiProperties.PROPERTIES_FILE_PATH, StandardFlowServiceTest.class.getResource("/conf/nifi.properties").getFile());
        NiFiProperties properties = NiFiProperties.getInstance();
        NarClassLoaders.load(properties);
    	ExtensionManager.discoverExtensions();
    }

    @Before
    public void setup() throws Exception {
    	String id = "id";
    	String clazz = "org.apache.nifi.controller.service.util.TestControllerService";  
    	ControllerServiceProvider provider = new StandardControllerServiceProvider();
    	ControllerServiceNode node = provider.createControllerService(clazz,id,true);
    	proxied = node.getProxiedControllerService();
    	implementation = node.getControllerServiceImplementation();
    }
	
    @Test (expected=UnsupportedOperationException.class)
    public void testCallProxiedOnPropertyModified() {	
		proxied.onPropertyModified(null, "oldValue", "newValue");
    }
    
    @Test
    public void testCallImplementationOnPropertyModified() {	
    	implementation.onPropertyModified(null, "oldValue", "newValue");
    }
    
    @Test (expected=UnsupportedOperationException.class)
    public void testCallProxiedInitialized() throws InitializationException {	
		proxied.initialize(null);
    }
    
    @Test
    public void testCallImplementationInitialized() throws InitializationException {	
    	implementation.initialize(null);
    }
}
