/*
 * Abramenko Nikita, february, 2017
 */
package com.Practice.service;

import java.util.List;

/**
 * This is a news service interface, which is implemented by two
 * independent bundles
 *
 * @version 1.0
 * @author Abramenko Nikita(Nikas)
 */
public interface InfoService {
    /**
     * This is a public service method which will be provided by the bundle
     * this method extracts data from a web source and parses it, returning
     * the list of words from the news titles.
     *
     * @param url URL is required as the source of information for the method.
     *            if omitted, null will be a return
     * @return List<String>, which contains words from the news titles
     */
    List<String> getData(String url);

    /**
     * This is a getData() method overload
     *
     * @return List<String>, which contains words from the news titles
     */
    List<String> getData();
}
