package Websites.Interfaces;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.text.WordUtils.wrap;

public abstract class Helpers {

    private static final String SPACE=" ";

    private static final String OLD="old";

    public abstract void formatFile(String file) throws IOException;

    public abstract String extractName(String url) throws IOException;

    protected void writeFile(String fName, BufferedReader br)throws IOException{

        BufferedWriter out=new BufferedWriter(new FileWriter(fName));

        String s=br.readLine();

        int CHAR_PER_LINE = 120;

        out.write(wrap(s, CHAR_PER_LINE, null, false));

        out.close();
    }

    protected String create(String fileName,String url) throws IOException{

        String PARAGRAPH="p";

        File file = new File(fileName);

        boolean created = file.createNewFile();

        assert (created);

        BufferedWriter out = new BufferedWriter(new FileWriter(fileName));

        Document doc = Jsoup.connect(url).get();

        Elements paragraphs = doc.select(PARAGRAPH);

        for (Element p : paragraphs) {

            String s = p.text();

            out.write(s);

        }
        out.close();

        return fileName;
    }

    protected String setDetails(String t,String arg1,String arg2){

        if (t.contains(arg1)) t=t.replaceAll(Pattern.quote(arg1),SPACE);

        if (t.contains(arg2)) t=t.replaceAll(Pattern.quote(arg2),SPACE);

        t = new StringBuilder(t).insert(t.length(), OLD).toString();

        return t;
    }

}
