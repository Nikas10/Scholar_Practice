/*
 * Abramenko Nikita, February, 2017
 */
package com.Practice;

import com.Practice.command.HelloCommand;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import java.util.Hashtable;

/**
 * This is a Bundle Activator implementation class
 * for launching the bundle and registering the command
 *
 * @version 1.0
 * @author Abramenko Nikita(Nikas)
 */
public class Activator implements BundleActivator {

    /**
     * This is an Activator's start method, which needs to be implemented
     * to make the class work.
     *
     * @param context Method receives Bundle context for later use inside
     *                this method
     * @throws Exception This method can throw an Exception during the registration
     *                of service
     */
    public void start(BundleContext context) throws Exception {
        /*
         * Registering a command is similar to registering a service,
         * the only difference is the use of propetries, such as
         * osgi.command.scope and function. We need to just set them up
         * and use them in registerService method
         */
        Hashtable props = new Hashtable();
        props.put("osgi.command.scope", "practice");
        props.put("osgi.command.function", new String[] {"hello"});
        context.registerService(HelloCommand.class.getName(), new HelloCommand(), props);
    }

    /**
     * This is an Activator's stop method, which needs to be implemented
     * to make the class work.
     *
     * @param context Method receives Bundle context for later use inside
     *                this method
     * @throws Exception This method can throw an Exception during the ungetting
     *                of the service
     */
    public void stop(BundleContext context) throws Exception {}
}