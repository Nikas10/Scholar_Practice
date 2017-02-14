/*
 * Abramenko Nikita, February, 2017
 */
package com.Practice.service.impl;

import com.Practice.service.InfoService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a news parser service, made with the use
 * of OSGi DS. This class uses java JAXP to parse
 * xml web page.
 *
 * @version 1.0
 * @author Abramenko Nikita(Nikas)
 */
@Component(name = "com.Practice.service.InfoServiceAif")
@Service(InfoService.class)
public class AifServiceImpl implements InfoService
{
    /**
     * This is a default class constructor, which may be omitted
     */
    public AifServiceImpl(){};

    /**
     * This is a public service method implementation which will be provided by the bundle
     * this method extracts data from a web source and parses it, returning
     * the list of words from the news titles.
     *
     * @param url URL is required as the source of information for the method.
     *            if omitted, null will be a return
     * @return List<String>, which contains words from the news titles
     */
    @Override
    public List<String> getData(String url) {

        return parseXML(url);
    }

    /**
     * This is a public service method implementation which will be provided by the bundle
     * this method extracts data from a web source and parses it, returning
     * the list of words from the news titles.
     *
     * @return List<String>, which contains words from the news titles
     */
    @Override
    public List<String> getData() {

        return parseXML("http://www.aif.ru/rss/news.php");
    }

    /**
     * This is a private method, which contains the bundle's
     * business logic
     *
     * @param url URL is required as the source of information for the method.
     *            if omitted, null will be a return
     * @return List<String>, which contains words from the news titles
     */
    private List<String> parseXML(String url)
    {
        /* The business logic: create an internet connection,
             * retrieve xml object, parse it to the list of sentences
             * (titles) and parse them to make the list of words
             */
        List<String> result = new ArrayList<String>();
        try {
            URL site = new URL(url);
            URLConnection connection = site.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String inputLine;
            /*Reading xml*/
            while ((inputLine = in.readLine()) != null) {
                builder.append(inputLine);
            }
            in.close();
            String xml = builder.toString();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            /*Parsing xml*/
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getDocumentElement().getElementsByTagName("item");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    result.add(element.getElementsByTagName("title").item(0).getTextContent());
                }
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
