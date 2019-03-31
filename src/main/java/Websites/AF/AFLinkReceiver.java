package Websites.AF;

import Websites.Utility.Helpers;
import Websites.Utility.Links;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;

public class AFLinkReceiver extends Helpers implements Links {

    private static HashSet<String> set=new HashSet<>();

    private static final String SWEDEN="&Sco=c3a2cc3d-03a2-e411-9759-0050568f2054";

    private static final String SEARCH="http://www.afconsult.com/en/join-us/available-jobs/?SearchText=";

    private static final String KEY="abs:href";

    private static final String QUERY="a[href]";

    private static final String STRING_FORMAT="%s";

    private static final String JOIN_US="join-us";

    private static final String TWITTER="twitter";

    private static final String FACEBOOK="facebook";

    private static final String LINKEDIN="linkedin";

    private static final String SEARCH_TEXT ="?SearchText";

    private static final String SUBSCRIBE="subscribe-for-jobs";

    private static final String OPEN_APPLICATION ="openapplication";

    private String formatURL(String query){
        return getString(query, SEARCH) + "+" + SWEDEN;
    }

    @Override
    public void getLinksFromWebsite(String query) throws Exception{
        Document doc = Jsoup.connect(formatURL(query)).get();

        Elements elements = doc.select(QUERY);

        for (Element e : elements) set.add(toString(e.attr(KEY)));
    }

    @Override
    public void deleteWrongResults() {
        set.removeIf(s -> !s.contains(JOIN_US) || s.contains(TWITTER) || s.contains(FACEBOOK) || s.contains(LINKEDIN)
                || s.contains(SEARCH_TEXT) || s.contains(SUBSCRIBE) || s.contains(OPEN_APPLICATION));

        set.removeIf(s -> s.length()==51);

        set.removeIf(s -> s.length()==36);
    }

    @Override
    public String toString(Object... args) {
        return String.format(STRING_FORMAT,args);
    }

    @Override
    public HashSet<String> getSetFromWebsite(String query) throws Exception{

        getLinksFromWebsite(query);

        deleteWrongResults();

        return set;
    }

    @Override
    public HashSet<String> getSetFromWebsite(String email, String password, String query){
        throw new UnsupportedOperationException();
    }

    @Override
    public void getLinksFromWebsite(String name,String password,String query){
        throw new UnsupportedOperationException();
    }

    @Override
    public void formatFile(String file){
        throw new UnsupportedOperationException();
    }

    @Override
    public String extractName(String url){
        throw new UnsupportedOperationException();
    }
}
