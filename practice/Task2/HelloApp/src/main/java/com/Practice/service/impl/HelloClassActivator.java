/*
 *  Abramenko Nikita, February, 2017
 */
package com.Practice.service.impl;

import com.Practice.service.HelloClass;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * This is a Bundle Activator implementation class
 * for launching the bundle and register the service
 *
 * @version 1.0
 * @author Abramenko Nikita(Nikas)
 */
public class HelloClassActivator implements BundleActivator {

    /**
     * This variable is responsible for our service registration
     */
    ServiceRegistration helloServiceRegistration;

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
        HelloClass helloService = new HelloClassImpl();
        helloServiceRegistration =context.registerService(HelloClass.class.getName(), helloService, null);
    }

    /**
     * This is an Activator's stop method, which needs to be implemented
     * to make the class work.
     *
     * @param context Method receives Bundle context for later use inside
     *                this method
     * @throws Exception This method can throw an Exception during the unregistration
     *                of service
     */
    public void stop(BundleContext context) throws Exception {
        helloServiceRegistration.unregister();
    }
}