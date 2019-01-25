package Websites.eWork;

import Websites.Interfaces.Links;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class eWorkLinkReceiver implements Links {

    private static HashSet<String> set=new HashSet<>();

    private static final String FPGA="https://myework.eworkgroup.com/RequestList.cfm?page=1&competence_group_id=&competence_area_category_area_id=&country_city_id=&description=fpga";

    private static final String ASIC="https://myework.eworkgroup.com/RequestList.cfm?page=1&competence_group_id=&competence_area_category_area_id=&country_city_id=&description=ASIC";

    private static final String LOG_IN="https://myework.eworkgroup.com/Login.cfm";

    private static final String BUTTON="[class$=btn btn-primary btn-login]";

    private static final String B_ANSWER="Logga in";

    private static final String EMAIL="[name$=user_email]";

    private static final String PASS="[name$=user_password]";

    private static final String ACTION="[name$=action]";

    private static final String ACTION_ANSWER="login_nowin";

    private static final String KEY="abs:href";

    private static final String QUERY="a[href]";

    private static final String STRING_FORMAT="%s";

    private static final String PROJECT_ID="PROJECT_ID";

    private static final String MOZILLA="Mozilla";

    private static final String FORM="form[name=login]";

    @Override
    public void getLinks(String name, String password)throws IOException {

        retrieveLinks(name, password);
    }

    private void retrieveLinks(String name, String password) throws IOException {

        jobs(name, password, ASIC);

        jobs(name, password, FPGA);
    }

    private void jobs(String name, String password, String subSite) throws IOException {

        Document doc =createConnection(name,password, subSite);

        Elements elements=doc.select(QUERY);

        for (Element e1 : elements) set.add(toString(e1.attr(KEY)));
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

        Connection.Response response =
                Jsoup.connect(LOG_IN)
                        .userAgent(MOZILLA)
                        .method(Connection.Method.GET)
                        .execute();

        Document responseDocument = response.parse();

        Element potentialForm = responseDocument.select(FORM).first();

        FormElement form = (FormElement) potentialForm;

        fillForm(form, EMAIL, name, PASS, password);

        fillForm(form, ACTION, ACTION_ANSWER, BUTTON, B_ANSWER);

        Document searchResults = form.submit().cookies(response.cookies()).post();

        assert (searchResults!=null);

        return Jsoup.connect(url).cookies(response.cookies()).get();
    }

    private void fillForm(FormElement form, String action2, String actionAnswer, String button, String bAnswer) {
        Element action=form.select(action2).first();

        action.val(actionAnswer);

        Element btn = form.select(button).first();

        btn.val(bAnswer);
    }

    @Override
    public void getLinks() {
        throw new UnsupportedOperationException();
    }

    @Override
    public HashSet<String> getSet(){
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) throws Exception {

        eWorkLinkReceiver e= new eWorkLinkReceiver();

        System.out.println(e.getSet("forslundaren@hotmail.com","1Testing2_"));

    }
}


