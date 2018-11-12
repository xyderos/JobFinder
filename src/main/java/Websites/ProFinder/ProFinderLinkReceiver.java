package Websites.ProFinder;

import java.io.IOException;
import java.util.HashSet;

import Websites.Interfaces.Links;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class ProFinderLinkReceiver implements Links {

    private static HashSet<String> set=new HashSet<>();

    private static final String URL="http://www.profinder.se/uppdrag-19993191";

    private static final String KEY="abs:href";

    private static final String QUERY="a[href]";

    private static final String STRING_FORMAT="%s";

    private static final String TWITTER="twitter";

    private static final String LINKEDIN="linkedin";

    private static final String REFNR="refnr";

    public void getLinks()throws IOException {

        Document doc = Jsoup.connect(URL).get();

        Elements elements = doc.select(QUERY);

        for (Element e : elements) set.add(toString(e.attr(KEY)));

    }

    public void deleteWrong(){
        set.removeIf(s -> s.contains(LINKEDIN) || s.contains(TWITTER) || !s.contains(REFNR));
    }

    public String toString(Object...args) {
        return String.format(STRING_FORMAT,args);
    }

    public HashSet<String> getSet() throws IOException{

        getLinks();

        deleteWrong();

        return set;
    }

    @Override
    public HashSet<String> getSet(String email, String password) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Document createConnection(String name, String password, String url) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getLinks(String name, String password) {
        throw new UnsupportedOperationException();
    }
}
