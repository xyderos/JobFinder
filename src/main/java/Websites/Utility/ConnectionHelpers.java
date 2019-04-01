package Websites.Utility;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;

import java.io.IOException;

class ConnectionHelpers {

    private static final String EMAIL="[name$=user_email]";

    private static final String PASS="[name$=user_password]";

    private static final String ACTION="[name$=action]";

    private static final String ACTION_ANSWER="login_nowin";

    private static final String MOZILLA="Mozilla";

    private static final String FORM="form[name=login]";

    private static final String LOG_IN="https://myework.eworkgroup.com/Login.cfm";

    private static final String BUTTON="[class$=btn btn-primary btn-login]";

    private static final String B_ANSWER="Logga in";

    private Document createConnectionWitheWork(String name, String password, String url) throws IOException {

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

    Document decide(String name,String password,String url) throws Exception{

        if (name==null && password==null) return Jsoup.connect(url).get();

        else return createConnectionWitheWork(name,password,url);
    }
}
