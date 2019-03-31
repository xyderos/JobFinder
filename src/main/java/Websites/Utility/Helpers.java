package Websites.Utility;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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

    private static final int CHAR_PER_LINE = 100;

    private static final String SLASH="/";

    private static final String PORTAL="portal";

    private String PATH=System.getProperty("user.dir") + "/res/";

    private static final String FMT="\\W+";

    private String TXT = ".txt";

    private static final String KEY="abs:href";

    private static final String QUERY="a[href]";

    public abstract String extractName(String url) throws IOException;

    protected String getString(String query, String search) {
        String[] words = query.split(FMT);

        StringBuilder q= new StringBuilder(search);

        for (String e: words) q.append("+").append(e);

        return q.toString();
    }

    private void writeFile(String fName, BufferedReader br)throws IOException{

        BufferedWriter out=new BufferedWriter(new FileWriter(fName));

        String s=br.readLine();

        out.write(wrap(s, CHAR_PER_LINE, null, true));

        out.close();
    }

    protected String create(String fileName, String url, String name, String password) throws IOException{

        BufferedWriter out = writer(fileName);

        Document doc = createConnectionWitheWork(name,password, url);

        Elements paragraphs = doc.select(PARAGRAPH);

        return getString(fileName, out, paragraphs, null);

    }

    private BufferedWriter writer(String fileName) throws IOException{

        File f=new File(fileName);

        if (f.exists()) return null;

        final boolean newFile= f.createNewFile();

        assert newFile;

        return new BufferedWriter(new FileWriter(fileName));
    }

    private String getString(String fileName, BufferedWriter out, Elements paragraphs,String link) throws IOException {
        for (Element p : paragraphs) {

            String s = p.text();

            if (s==null || out==null) continue;

            out.write(s);
        }

        if (link!=null){

            Objects.requireNonNull(out).write(link);
            out.close();

            return fileName;
        }

        if (out != null) out.close();

        return fileName;
    }

    public String toString(Object... args) {
        return String.format("%s",args);
    }

    private String extractLinkForApplication(Document doc){

        Set<String> s=new HashSet<>();

        Elements elements = doc.select(QUERY);

        for (Element e : elements) s.add(toString(e.attr(KEY)));

        s.removeIf(str -> !str.contains(PORTAL));

        for (String f : s)  if (f.contains(PORTAL)) return f;

        return null;
    }

    protected String create(String fileName,String url) throws IOException{

        BufferedWriter out =writer(fileName);

        Document doc = Jsoup.connect(url).get();

        Elements paragraphs = doc.select(PARAGRAPH);

        String link=extractLinkForApplication(doc);

        return getString(fileName, out, paragraphs,link);
    }

    protected String setDetails(String t,String arg1,String arg2){

        if (t.contains(SLASH)) t=t.replaceAll(Pattern.quote(SLASH),UNDERSCORE);

        if (t.contains(arg1)) t=t.replaceAll(Pattern.quote(arg1),UNDERSCORE);

        if (arg2==null) arg2="";

        if (t.contains(arg2)) t=t.replaceAll(Pattern.quote(arg2),UNDERSCORE);

        t = new StringBuilder(t).insert(t.length(),OLD).toString();

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

    protected void formatFile(String path) throws IOException{


        String fileName= PATH+extractName(path)+ TXT;

        BufferedReader br=new BufferedReader(new FileReader(fileName));

        String EMPTY = "";
        fileName=fileName.replaceAll(Pattern.quote(OLD), EMPTY);

        writeFile(fileName,br);
    }

    protected void toFiles(String query, HashSet<String> s, HashSet<File> ns) throws Exception {

        System.out.println("LOOKING FOR: " + query);

        File file;

        for(String pf:s){

            file=new File(createAndWrite(pf));

            formatFile(pf);

            System.out.println("CREATED FILE: "+ file.getName() );

            if (file.getName().contains(OLD)) System.out.println("FOUND OLD FILE " + file.getName() + " deleting is done: " + file.delete());

            ns.add(file);
        }
    }

    protected String createAndWrite(String url) throws Exception {

        String name=extractName(url);

        String fileName= PATH+name+TXT;

        return create(fileName,url);
    }
}
