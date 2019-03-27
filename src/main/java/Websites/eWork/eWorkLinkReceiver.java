package Websites.eWork;

import Websites.Utility.Helpers;
import Websites.Utility.Links;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class eWorkLinkReceiver extends Helpers implements Links {

    private static HashSet<String> set=new HashSet<>();

    private static final String FPGA="https://myework.eworkgroup.com/RequestList.cfm?page=1&competence_group_id=&competence_area_category_area_id=&country_city_id=&description=fpga";

    private static final String ASIC="https://myework.eworkgroup.com/RequestList.cfm?page=1&competence_group_id=&competence_area_category_area_id=&country_city_id=&description=ASIC";

    private static final String KEY="abs:href";

    private static final String QUERY="a[href]";

    private static final String STRING_FORMAT="%s";

    private static final String PROJECT_ID="PROJECT_ID";

    @Override
    public String toString(Object...args) {
        return String.format(STRING_FORMAT,args);
    }

    private void addLinkToTheSet(String name, String password, String subSite) throws IOException {

        Document doc = createConnectionWitheWork(name,password,subSite);

        Elements elements=doc.select(QUERY);
        
        for (Element e1 : elements) set.add(toString(e1.attr(KEY)));
    }

    private void addLinkToTheSet(String name, String password) throws IOException {

        addLinkToTheSet(name, password, ASIC);

        addLinkToTheSet(name, password, FPGA);
    }

    @Override
    public void getLinksFromWebsite(String name, String password)throws IOException {

        addLinkToTheSet(name, password);
    }

    @Override
    public void deleteWrongResults(){

        set.removeIf(s -> !s.contains(PROJECT_ID));
    }

    @Override
    public HashSet<String> getSetFromWebsite(String email, String password) throws IOException{

        getLinksFromWebsite(email,password);

        deleteWrongResults();

        return set;
    }

    @Override
    public void getLinksFromWebsite(){
        throw new UnsupportedOperationException();
    }

    @Override
    public HashSet<String> getSetFromWebsite(){
        throw new UnsupportedOperationException();
    }

    @Override
    public void formatFile(String file) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String extractName(String url) {
        throw new UnsupportedOperationException();
    }
}


