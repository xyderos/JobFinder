package Websites.ProFinder;

import java.io.IOException;
import java.util.HashSet;

import Websites.Utility.Links;

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

    @Override
    public void getLinksFromWebsite(String query)throws IOException {

        Document doc = Jsoup.connect(URL).get();

        Elements elements = doc.select(QUERY);

        for (Element e : elements) set.add(toString(e.attr(KEY)));
    }

    @Override
    public String toString(Object...args) {
        return String.format(STRING_FORMAT,args);
    }

    @Override
    public void deleteWrongResults(){
        set.removeIf(s -> s.contains(LINKEDIN) || s.contains(TWITTER) || !s.contains(REFNR));
    }

    @Override
    public HashSet<String> getSetFromWebsite(String query) throws IOException{

        getLinksFromWebsite(query);

        deleteWrongResults();

        return set;
    }

    @Override
    public HashSet<String> getSetFromWebsite(String email, String password,String query) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getLinksFromWebsite(String name, String password,String query) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) throws Exception {
        ProFinderLinkReceiver r=new ProFinderLinkReceiver();

        r.getLinksFromWebsite(null);
    }
}
