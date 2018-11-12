package Websites.eWork;

import Websites.Interfaces.Links;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class eWorkLinkReceiver implements Links {

    private static HashSet<String> set=new HashSet<>();

    private static final String FPGA="https://myework.eworkgroup.com/RequestList.cfm?page=1&competence_group_id=&competence_area_category_area_id=&country_city_id=&description=fpga";

    private static final String ASIC="https://myework.eworkgroup.com/RequestList.cfm?page=1&competence_group_id=&competence_area_category_area_id=&country_city_id=&description=ASIC";

    private static final String LOG_IN="https://myework.eworkgroup.com/Login.cfm";

    private static final String EMAIL="user_email";

    private static final String PASS="user_password";

    private static final String LOGIN="login";

    private static final String LOGIN_ANSWER="Login";

    private static final String ACTION="action";

    private static final String ACTION_ANSWER="login_nowin";

    private static final String KEY="abs:href";

    private static final String QUERY="a[href]";

    private static final String STRING_FORMAT="%s";

    private static final String PROJECT_ID="PROJECT_ID";

    @Override
    public void getLinks(String name, String password)throws IOException {

        Document doc1 =createConnection(name,password,FPGA);

        Elements elements1=doc1.select(QUERY);

        for (Element e : elements1)set.add(toString(e.attr(KEY)));

        Document doc2 =createConnection(name,password,ASIC);

        Elements elements2=doc2.select(QUERY);

        for (Element e : elements2) set.add(toString(e.attr(KEY)));

    }

    @Override
    public String toString(Object...args) {
        return String.format(STRING_FORMAT,args);
    }


    @Override
    public HashSet<String> getSet(String email, String password) throws IOException{

        getLinks(email,password);

        deleteWrong();

        return set;
    }

    @Override
    public void deleteWrong(){

        set.removeIf(s -> !s.contains(PROJECT_ID));

    }

    @Override
    public Document createConnection(String name, String password, String url) throws IOException {

        Connection.Response initial =
                Jsoup.connect(LOG_IN)
                        .method(Connection.Method.GET)
                        .execute();

        Connection.Response login =
                Jsoup.connect(LOG_IN)
                        .data(EMAIL,name)
                        .data(PASS,password)
                        .data(LOGIN,LOGIN_ANSWER)
                        .data(ACTION,ACTION_ANSWER)
                        .cookies(initial.cookies())
                        .method(Connection.Method.POST)
                        .execute();

        return  Jsoup.connect(url).cookies(login.cookies()).get();
    }

    @Override
    public void getLinks() {
        throw new UnsupportedOperationException();
    }

    @Override
    public HashSet<String> getSet(){
        throw new UnsupportedOperationException();
    }
}
