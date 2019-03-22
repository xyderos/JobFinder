package Websites.Interfaces;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.text.WordUtils.wrap;

public abstract class Helpers {

    private static final String LOG_IN="https://myework.eworkgroup.com/Login.cfm";

    private static final String BUTTON="[class$=btn btn-primary btn-login]";

    private static final String B_ANSWER="Logga in";

    private static final String EMAIL="[name$=user_email]";

    private static final String PASS="[name$=user_password]";

    private static final String ACTION="[name$=action]";

    private static final String ACTION_ANSWER="login_nowin";

    private static final String MOZILLA="Mozilla";

    private static final String FORM="form[name=login]";

    private static final String UNDERSCORE="_";

    private static final String OLD="old";

    private static final String PARAGRAPH="p";

    public abstract void formatFile(String file) throws IOException;

    public abstract String extractName(String url) throws IOException;

    protected void writeFile(String fName, BufferedReader br)throws IOException{

        BufferedWriter out=new BufferedWriter(new FileWriter(fName));

        String s=br.readLine();

        System.out.println();

        System.out.println(s);

        int CHAR_PER_LINE = 60;

        System.out.println(wrap(s, CHAR_PER_LINE, null, true)==null);

        out.write(wrap(s, CHAR_PER_LINE, null, true));

        out.close();
    }

    protected String create(String fileName, String url, String name, String password) throws IOException{

        BufferedWriter out = writer(fileName);

        Document doc = createConnectionWitheWork(name,password, url);

        Elements paragraphs = doc.select(PARAGRAPH);

        return getString(fileName, out, paragraphs);

    }

    private BufferedWriter writer(String fileName) throws IOException{

        File f=new File(fileName);

        if (f.exists()) return null;

        final boolean newFile= f.createNewFile();

        assert newFile;

        return new BufferedWriter(new FileWriter(fileName));

    }

    private String getString(String fileName, BufferedWriter out, Elements paragraphs) throws IOException {
        for (Element p : paragraphs) {

            String s = p.text();

            out.write(s);

        }
        out.close();

        return fileName;
    }

    protected String create(String fileName,String url) throws IOException{

        BufferedWriter out =writer(fileName);

        Document doc = Jsoup.connect(url).get();

        Elements paragraphs = doc.select(PARAGRAPH);

        return getString(fileName, out, paragraphs);
    }

    protected String setDetails(String t,String arg1,String arg2){

        if (t.contains(arg1)) t=t.replaceAll(Pattern.quote(arg1),UNDERSCORE);

        if (t.contains(arg2)) t=t.replaceAll(Pattern.quote(arg2),UNDERSCORE);

        t = new StringBuilder(t).insert(t.length(), OLD).toString();

        return t;
    }

    protected Document createConnectionWitheWork(String name, String password, String url) throws IOException {

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
}
