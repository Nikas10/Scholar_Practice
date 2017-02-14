/*
 * Abramenko Nikita, February, 2017
 */
package com.Practice.command;

/**
 * This is a Hello service class
 * It is a bit different from a service, it does
 * not implement anything. Still, it has some methods
 * to provide
 *
 * @version 1.0
 * @author  Abramenko Nikita(Nikas)
 */
public class HelloCommand {

    /**
     * This is a default class constructor, which may be omitted
     */
    public HelloCommand(){};

    /**
     * This is a command method, which will be provided by the bundle
     *
     * @param arg Method receives a String argument for printing out a
     *            message, which contains this argument
     */
    public void hello(String arg)
    {
        System.out.println("Hello, "+arg+"!");
    }

    /**
     * This is a command method overload, which will be provided by the bundle
     */
    public void hello()
    {
        System.out.println("Hello, Master!");
    }

}
