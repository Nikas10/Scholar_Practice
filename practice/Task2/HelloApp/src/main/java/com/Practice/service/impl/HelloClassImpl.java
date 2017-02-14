/*
 *  Abramenko Nikita, February, 2017
 */
package com.Practice.service.impl;

import com.Practice.service.HelloClass;

/**
 * This is a HelloWorld interface implementation
 *
 * @version 1.0
 * @author Abramenko Nikita(Nikas)
 */
public class HelloClassImpl implements HelloClass {

    /**
     * A simple HelloWorld method implementation
     *
     * @return a "HelloWorld" message
     */
    public String sayHello() {
        System.out.println("inside method :Hello OSGI World");
        return "Hello OSGi World";
    }
}
