/*
 * Abramenko Nikita, February, 2017
 */
package com.Practice.service.impl;

import com.Practice.service.HelloService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

/**
 * This is a service-component class, made with the use
 * of OSGi Declarative Service (DS). It implements HelloService
 * interface.
 *
 * @version 1.0
 * @author Abramenko Nikita(Nikas)
 */
@Component(name = "com.Practice.service.HelloService")
@Service(HelloService.class)
public class HelloServiceImpl implements HelloService {

    /**
     * A simple HelloWorld method implementation
     *
     * @return a "HelloWorld" message
     */
    public String sayHello() {
        return "Hello World DS Edition";
    }
}
