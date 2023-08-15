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

package com.mihirpaldhikar.servlet.container.configs;

/**
 * Creates a configuration for the Servlet Container.
 *
 * @param port                The Port number on which server will run.
 * @param className           The class name of the entry point of the program.
 * @param sourceDirectoryPath The path of the web source directory.
 * @author Mihir Paldhikar
 * @since 1.0.0
 */
public record ServletContainerConfig(int port, String className, String sourceDirectoryPath) {
}
