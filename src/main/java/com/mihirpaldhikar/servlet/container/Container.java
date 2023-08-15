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

import java.io.File;
import java.net.URISyntaxException;

/**
 * An Interface for creating Servlet Container.
 *
 * @author Mihir Paldhikar
 * @since 1.0.0
 */
public interface Container {
    /**
     * Find the root of the project for fetching source files.
     *
     * @param className The name of Main Class
     * @return File
     * @since 1.0.0
     */
    default File getRoot(String className) {
        try {
            File root;
            String runningJarPath = Class.forName(className).getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replaceAll("\\\\", "/");
            int lastIndexOf = runningJarPath.lastIndexOf("/target/");
            if (lastIndexOf < 0) {
                root = new File("");
            } else {
                root = new File(runningJarPath.substring(0, lastIndexOf));
            }
            return root;
        } catch (URISyntaxException | ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void start();
}
