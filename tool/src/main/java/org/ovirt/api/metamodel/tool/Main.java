/*
 * Copyright oVirt Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.ovirt.api.metamodel.tool;

import java.lang.reflect.Method;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for bootstrapping the CDI container, creating the application entry point and running it
 * with the command line arguments.
 */
public class Main {
    private static final Logger log;

    static {
        // loads logging.properties from the classpath
        String path = Main.class.getClassLoader().getResource("logging.properties").getFile();
        System.setProperty("java.util.logging.config.file", path);

        // initialize logger
        log = LoggerFactory.getLogger(Main.class);

    }

    public static void main(String[] args) {
        // if (args.length < 1) {
        //     log.error("The first argument must be the fully qualified name of the tool class.");
        //     System.exit(1);
        // }
    
        // String toolName = args[0];
        // String[] toolArgs = new String[args.length - 1];
        // System.arraycopy(args, 1, toolArgs, 0, toolArgs.length);
        // try {
        //     // Dynamically load the tool class
        //     Class<?> toolClass = Class.forName(toolName);

        //     // Create and initialize CDI container with the tool class
        //     Weld weld = new Weld();
        //     try (WeldContainer container = weld.initialize()) {
        //         // Get the CDI-managed instance
        //         Object toolBean = container.select(toolClass).get();
        //         // Find and invoke its run(String[]) method
        //         Method runMethod = toolClass.getMethod("run", String[].class);
        //         runMethod.invoke(toolBean, (Object) toolArgs);
        //     }
        //     weld.shutdown();
        // } catch (ClassNotFoundException e) {
        //     log.error("Can't find tool class \"{}\".", toolName, e);
        //     System.exit(1);
        // } catch (Exception e) {
        //     log.error("Error while executing the \"run\" method of tool class \"{}\".", toolName, e);
        //     System.exit(1);
        // }
        // The first argument must be the name of the tool class:
        if (args.length < 1) {
            log.error("The first argument must be the fully qualified name of the tool class.");
            System.exit(1);
        }
        String toolName = args[0];

        // The rest of the arguments are passed to the tool:
        String[] toolArgs = new String[args.length - 1];
        System.arraycopy(args, 1, toolArgs, 0, toolArgs.length);

        // Create the CDI container:
        Weld weld = new Weld();
        try (WeldContainer container = weld.initialize()) {
            Tool tool = container.select(Tool.class).get();
            tool.run(toolArgs);
        } catch (Exception e) {
            log.error("Error while executing the \"run\" method of tool class \"{}\".", toolName, e);
            System.exit(1);
        }
        // When the tool finishes, shutdown the CDI container:
        // weld.shutdown();
    }
}
