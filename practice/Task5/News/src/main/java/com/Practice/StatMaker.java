/*
 * Abramenko Nikita, February, 2017
 */
package com.Practice;

import com.Practice.service.InfoService;
import org.apache.felix.scr.annotations.*;
import org.apache.felix.scr.annotations.Properties;
import java.util.*;

/**
 * This is a news statistics parser, which uses 2 implementations
 * of InfoService interface. It receives data from them and parses it
 * to make word statistics. It also provides a command for
 * easier use of the bundle.
 * This class is made with the use of OSGi DS.
 *
 * @version 1.0
 * @author Abramenko Nikita(Nikas)
 */
@Component(name = "StatMaker", immediate = true)
@Service(StatMaker.class)
@Properties({
        @Property(name = "osgi.command.scope", value = "news"),
        @Property(name = "osgi.command.function", value = {"stats"})
})
public class StatMaker
{
    /**
     * This a serice reference list, containing all the implementations of
     * the InfoService interface, placed in independent bundles
     *
     * @param impl
     */
    @Reference(cardinality= ReferenceCardinality.MANDATORY_MULTIPLE,
               bind="bind",
               unbind="unbind",
               referenceInterface = InfoService.class,
               policy = ReferencePolicy.DYNAMIC)
    List<InfoService> implList;

    /**
     * This is a service reference bind method implementation, which is needed to set up the reference,
     * like simple class set() method
     *
     * @param impl Method receives a service implementation object and sets
     *                    the class reference variable
     */
    protected void bind(InfoService impl){
        if(implList == null){
            implList = new ArrayList<InfoService>();
        }
        implList.add(impl);
    }

    /**
     * This is a service reference unbind method implementation, which is needed to unset the reference,
     * like simple class set() method
     *
     * @param impl Method receives a service implementation object and sets
     *                    the class reference variable
     */
    protected void unbind(InfoService impl){

        implList.remove(impl);
    }

    /**
     *  This is the service activation method, which tells my bundle
     *  what to do on it's activation. May be omitted due to the lack of
     *  actions
     */
    @Activate
    protected void activate() {}

    /**
     *  This is the service deactivation method, which tells my bundle
     *  what to do on it's deactivation. May be omitted due to the lack of
     *  actions
     */
    @Deactivate
    protected void deactivate() {}

    /**
     * This is the main bundle method, which will be provided as
     * a command for easier use of bundle
     * This method can have a String url parameter or have no parameters
     * if no parameters were set, a menu of options will occur
     */
    public void stats() {
        System.out.println("There are two sources available. Choose an option:");
        System.out.println("1: https://api.lenta.ru/lists/latest");
        System.out.println("2: http://www.aif.ru/rss/news.php");
        System.out.println("3: both");
        try {
            List<String> data = new ArrayList<String>();
            String url = "";
            int option;
            Scanner in = new Scanner(System.in);
            System.out.print("Enter value: ");
            option = in.nextInt();
            if (option == 1 ) {
                url = "https://api.lenta.ru/lists/latest";
                InfoService lentaService = implList.get(0);
                data = lentaService.getData(url);
                if (data == null) {
                    System.out.println("Network or service is not available. Try later.");
                    return;
                }
                data = parseStrings(data);
                countWords(data);
            }else
                if (option == 2) {
                    url = "http://www.aif.ru/rss/news.php";
                    InfoService aifService = implList.get(1);
                    data = aifService.getData(url);
                    if (data == null) {
                        System.out.println("Network or service is not available. Try later.");
                        return;
                    }
                    data = parseStrings(data);
                    countWords(data);
                }
                else
                    if (option == 3)
                    {
                        url = "https://api.lenta.ru/lists/latest";
                        InfoService lentaService = implList.get(0);
                        data = lentaService.getData(url);
                        data = parseStrings(data);
                        List<String> buf;
                        url = "http://www.aif.ru/rss/news.php";
                        InfoService aifService = implList.get(1);
                        buf = aifService.getData(url);
                        buf = parseStrings(buf);
                        data.addAll(buf);
                        if (data == null) {
                            System.out.println("Network or service is not available. Try later.");
                            return;
                        }
                        countWords(data);
                    }
                    else {
                        System.out.println("Incorrect value!");
                    }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the main bundle method, which will be provided as
     * a command for easier use of bundle
     * This method can have a String url parameter or have no parameters
     * if no parameters were set, a menu of options will occur
     *
     * @param url This method gets a String url to set up a connection
     */
    public void stats(String url) {
        List<String> data;
        if (url.equals("https://api.lenta.ru/lists/latest")) {
            InfoService lentaService = implList.get(0);
            data = lentaService.getData(url);
        }
        else if (url.equals("http://www.aif.ru/rss/news.php")){
            InfoService aifService = implList.get(1);
            data = aifService.getData(url);
        }
        else {
            System.out.println("Incorrect source!");
            return;
        }
        data = parseStrings(data);
        countWords(data);
    }

    /**
     * This is a private parser method, which separates String sentences
     * to an array of String words using regular expression pattern
     *
     * @param sentence This method receives a String sentence to parse
     * @return This method return a String array of words
     */
    private String[] getWordList(String sentence)
    {
        String lower = sentence.toLowerCase();
        String regexp = "\\s+";
        lower = lower.replaceAll("[!?,\".:;#@№\\u00ab\\u00bb\\p{Pi}\\p{Pf}]", "");
        String[] words = lower.split(regexp);
        return words;
    }

    /**
     * This is a private parser method, which separates String sentences
     * to an array of String words using regular expression pattern
     * the difference from getWorlList() method is the amount of sentences parsed
     * getWordList can parse 1 sentence per call, while parseString() works with
     * the Collection of Strings per call
     *
     * @param strings This method receives a String sentence List to parse
     * @return This method return a String array of words
     */
    private List<String> parseStrings(List<String> strings) {
        if (strings == null)return null; else  {
            List<String> result = new ArrayList<String>();
            for (String s: strings)
            {
                String[] word = getWordList(s);
                for (String w:word)
                {
                    result.add(w);
                }
            }
            return result;
        }
    }

    /**
     * This is a word counter and printer method. It receives a word List, counts
     * unique word occurence number in the list and adds a combination of unique word
     * and it's occurence number into TreeMap<String, Integer>. The Map is sorted by values
     * using special method and top 10 elements of the map is printed on the console
     *
     * @param data This method receives a List<String> of String words
     */
    private void countWords(List<String> data)
    {
        SortedMap<String, Integer> result = new TreeMap<String, Integer>();
        String current;
        Integer counter=0;
        while (!data.isEmpty())
        {
            current = data.get(0);
            counter=1;
            while(data.contains(current))
            {
                if (data.remove(current)) {
                    counter++;
                }
            }
            result.put(current,counter);
        }

        counter = 1;
        SortedSet<Map.Entry<String, Integer>> sortedSet = entriesSortedByValues(result);
        for (Map.Entry<String, Integer> entry: sortedSet)  {
            System.out.println(counter+": "+entry.getKey()+": "+entry.getValue());
            counter++;
            if (counter>10) break;
        }

    }

    /**
     * This is a Generic map sorting method, which sorts a map by it's
     * value (not the key!) and retuns a SortedSet, containing the Entries
     * of a map.
     *
     * @param map This method receives a Map to sort     *
     * @return This method returns a SortedSet Collection of map elements,
     * sorted by values
     */
    private static <K,V extends Comparable<? super V>>
    SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
                new Comparator<Map.Entry<K,V>>() {
                    @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                        int res = e2.getValue().compareTo(e1.getValue());
                        return res != 0 ? res : 1;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

}
