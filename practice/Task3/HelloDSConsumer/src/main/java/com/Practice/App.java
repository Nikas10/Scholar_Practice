/*
 * Abramenko Nikita, February, 2017
 */
package com.Practice;

import com.Practice.service.HelloService;
import org.apache.felix.scr.annotations.*;

/**
 * This is a HelloService service consumer class, created with
 * the help of OSGi Declarative Service (DS)
 *
 * @version 1.0
 * @author Abramenko Nikita(Nikas)
 */
@Component(name = "HelloConsumer", immediate = true)
@Service(App.class)
public class App 
{
    /**
     * This is a service reference variable, defined by @Reference annotation
     */
    @Reference
            (
                    name = "helloService",
                    referenceInterface = HelloService.class,
                    bind = "setService",
                    unbind = "unsetService"
            )
    private HelloService helloService;

    /**
     * This is the service activation method, which tells my bundle
     * what to do on activation. In my case, it just invokes
     * consumed service method to make sure it works
     */
    @Activate
    protected void activate() {
        System.out.println(helloService.sayHello());
    }

    /**
     *  This is the service deactivation method, which tells my bundle
     *  what to do on it's deactivation. May be omitted due to the lack of
     *  actions
     */
    @Deactivate
    protected void deactivate() {}

    /**
     * This is a service reference bind method implementation, which is needed to set up the reference,
     * like simple class set() method
     *
     * @param hellService Method receives a service implementation object and sets
     *                    the class reference variable
     */
    protected void setService(HelloService hellService)
    {
        this.helloService = hellService;
    }

    /**
     * This is a service reference unbind method implementation, which is needed to unset the reference,
     * like simple class set() method
     *
     * @param helloService Method receives a service implementation object and sets
     *                    the class reference variable
     */
    protected void unsetService(HelloService helloService)
    {
        this.helloService = null;
    }

}
