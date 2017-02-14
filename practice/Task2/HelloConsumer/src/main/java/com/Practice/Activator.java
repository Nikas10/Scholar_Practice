/*
 * Abramenko Nikita, February, 2017
 */
package com.Practice;

import com.Practice.service.HelloClass;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * This is a Bundle Activator implementation class
 * for launching the bundle, getting a service reference
 * and use the service
 *
 * @version 1.0
 * @author Abramenko Nikita(Nikas)
 */
public class Activator implements BundleActivator {
    /**
     * This variable is responsible for the service reference
     */
    ServiceReference helloServiceReference;

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
        helloServiceReference = context.getServiceReference(HelloClass.class.getName());
        HelloClass helloService = (HelloClass)context.getService(helloServiceReference);
        System.out.println(helloService.sayHello());
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
    public void stop(BundleContext context) throws Exception {
        context.ungetService(helloServiceReference);
    }
}