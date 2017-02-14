/*
 * Abramenko Nikita, February, 2017
 */
package com.Practice.service.impl;

import com.Practice.service.InfoService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a news parser service, made with the use
 * of OSGi DS. This class uses org.json lib to parse
 * json web page.
 * org.json package is needed to be installed in Apache Felix
 *
 * @version 1.0
 * @author Abramenko Nikita(Nikas)
 */
@Component(name = "com.Practice.service.InfoServiceLenta")
@Service(InfoService.class)
public class LentaServiceImpl implements InfoService {
    /**
     * This is a default class constructor, which may be omitted
     */
    public LentaServiceImpl(){}

    /**
     * This is a public service method implementation which will be provided by the bundle
     * this method extracts data from a web source and parses it, returning
     * the list of words from the news titles.
     *
     * @param url URL is required as the source of information for the method.
     *            if omitted, null will be a return
     * @return List<String>, which contains words from the news titles
     */
    public List<String> getData(String url) {

        return parseJsonHeaders(url);
    }

    /**
     * This is a public service method implementation which will be provided by the bundle
     * this method extracts data from a web source and parses it, returning
     * the list of words from the news titles.     *
     *
     * @return List<String>, which contains words from the news titles
     */
    public List<String> getData() {

        return parseJsonHeaders("https://api.lenta.ru/lists/latest");
    }

    /**
     * This is a private method, which contains the bundle's
     * business logic
     *
     * @param url URL is required as the source of information for the method.
     *            if omitted, null will be a return
     * @return List<String>, which contains words from the news titles
     */
    private List<String> parseJsonHeaders(String url) {
        List<String> result = new ArrayList<String>();
        try {
            /* The business logic: create an internet connection,
             * retrieve json object, parse it to the list of sentences
             * (titles) and parse them to make the list of words
             */
            URL site = new URL(url);
            URLConnection connection = site.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String inputLine;
            /*Reading json*/
            while ((inputLine = in.readLine()) != null)
                builder.append(inputLine);
            in.close();
            String json = builder.toString();
            /*Parsing json*/
            JSONObject obj = new JSONObject(json);
            JSONArray headlines = obj.getJSONArray("headlines");
            for (int i=0;i<headlines.length();i++)
            {
                JSONObject headline = headlines.getJSONObject(i);
                JSONObject info = headline.getJSONObject("info");
                result.add(info.getString("title"));
            }
        }
        catch (Exception e)
        {
            /*if an Exception occurs, finishing work*/
            return null;
        }
        return result;
    }
}
