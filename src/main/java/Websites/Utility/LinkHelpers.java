package Websites.Utility;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;

public abstract class LinkHelpers extends ConnectionHelpers {

    private static final String KEY="abs:href";

    private static final String QUERY="a[href]";

    private static final String STRING_FORMAT="%s";

    private static final String FMT="\\W+";

    private static final String PLUS="+";

    public abstract HashSet<String> getSetFromWebsite(String query,String email, String password) throws Exception;

    protected abstract void deleteWrongResults(HashSet<String> set);

    private String toString(Object... args) {
        return String.format(STRING_FORMAT,args);
    }

    private String getString(String search, String query) {

        if (query==null) return search;

        String[] words = query.split(FMT);

        StringBuilder q= new StringBuilder(search);

        for (String e: words) q.append(PLUS).append(e);

        return q.toString();
    }

    protected void getLinksFromWebsite(String email,String password, String url,String query, HashSet<String> set) throws Exception {

        String formattedQuery=getString(url,query);

        Document doc=decide(email,password,formattedQuery);

        Elements elements=doc.select(QUERY);

        for (Element e1 : elements) set.add(toString(e1.attr(KEY)));
    }
}
