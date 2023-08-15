/*
 * Copyright Mihir Paldhikar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mihirpaldhikar.servlet.container;

import com.mihirpaldhikar.servlet.container.configs.ContainerConfig;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.EmptyResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Creates an instance of Servlet Container.
 *
 * @author Mihir Paldhikar
 * @see Container
 * @since 1.0.0
 */
public class ServletContainer implements Container {
    private final ContainerConfig containerConfig;

    /**
     * Creates an instance of Servlet Container from the Container Config.
     *
     * @param containerConfig Servlet Configuration to run Container.
     * @see Container
     * @since 1.0.0
     */
    public ServletContainer(ContainerConfig containerConfig) {
        this.containerConfig = containerConfig;
    }

    /**
     * Finds the web directory root for getting web resources.
     *
     * @param root    The Project root
     * @param context Context for Catalina
     * @return WebResourceRoot
     * @see WebResourceRoot
     * @since 1.0.0
     */
    private static WebResourceRoot getWebResourceRoot(File root, StandardContext context) {
        File additionWebInfClassesFolder = new File(root.getAbsolutePath(), "target/classes");
        WebResourceRoot resources = new StandardRoot(context);

        WebResourceSet resourceSet;
        if (additionWebInfClassesFolder.exists()) {
            resourceSet = new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClassesFolder.getAbsolutePath(), "/");
        } else {
            resourceSet = new EmptyResourceSet(resources);
        }
        resources.addPreResources(resourceSet);
        return resources;
    }


    /**
     * Starts the Servlet Container with the provided  Container Configs.
     *
     * @see Container
     * @since 1.0.0
     */
    @Override
    public void start() {
        try {
            Tomcat tomcat = new Tomcat();
            Connector connector = new Connector();
            File root = getRoot(containerConfig.className());
            Path tempPath = Files.createTempDirectory("tomcat-base-directory");

            tomcat.setBaseDir(tempPath.toString());
            connector.setPort(containerConfig.port());

            System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
            File webContentFolder = new File(root.getAbsolutePath(), containerConfig.sourceDirectoryPath());

            if (!webContentFolder.exists()) {
                webContentFolder = Files.createTempDirectory("default-base-directory").toFile();
            }

            StandardContext context = (StandardContext) tomcat.addWebapp("", webContentFolder.getAbsolutePath());
            context.setParentClassLoader(Class.forName(containerConfig.className()).getClassLoader());

            WebResourceRoot resources = getWebResourceRoot(root, context);
            context.setResources(resources);


            System.out.println("Server Started On http://localhost:" + containerConfig.port());

            tomcat.getService().addConnector(connector);
            tomcat.start();
            tomcat.getServer().await();

        } catch (LifecycleException | IOException | ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }
}
