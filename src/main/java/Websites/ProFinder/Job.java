package Websites.ProFinder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashSet;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.text.WordUtils.*;

public class Job {

    private HashSet<File> files=new HashSet<>();

    private static final String EMPTY="";

    private static final String SPACE=" ";

    private static final String DASH="-";

    private static final String PARENTHESIS="(";

    private static final String TXT=".txt";

    private static final String PARAGRAPH="p";

    private static final String OLD="old";

    private static final String PATH="/Users/Konstantinos/Desktop/JobFinder/src/main/resources/Jobs/";

    private static final String DELIM="201";

    private static final String REF="refnr";

    private static final int CHAR_PER_LINE=120;

    private String extractName(String url){

        String t=url.substring(url.indexOf(DELIM)+11,url.indexOf(REF));

        if (t.contains(PARENTHESIS)) t=t.replaceAll(Pattern.quote(PARENTHESIS),SPACE);


        if (t.contains(DASH)) t=t.replaceAll(Pattern.quote(DASH),SPACE);

        t = new StringBuilder(t).insert(t.length(), OLD).toString();

        return t;

    }

    private String createAndWrite(String url)throws Exception{

        String name=extractName(url);

        String fileName=Job.PATH+name+TXT;

        File file=new File(fileName);

        boolean created = file.createNewFile();

        assert (created);

        BufferedWriter out=new BufferedWriter(new FileWriter(fileName));

        Document doc = Jsoup.connect(url).get();

        Elements paragraphs = doc.select(PARAGRAPH);

        for(Element p : paragraphs)  {

            String s=p.text();

            out.write(s);

        }

        out.close();

        return fileName;
    }

    public void toFiles() throws Exception{

        File file;

        for (String pf :ProFinder.getSet() ){

            file=new File(createAndWrite(pf));

            formatFile(pf);

            files.add(file);
        }
    }

    private void formatFile(String path) throws Exception{

        String fname=Job.PATH+extractName(path)+TXT;

        BufferedReader br=new BufferedReader(new FileReader(fname));

        fname=fname.replaceAll(Pattern.quote(OLD),EMPTY);

        BufferedWriter out=new BufferedWriter(new FileWriter(fname));

        String s=br.readLine();

        out.write(wrap(s, CHAR_PER_LINE, null, false));

        out.close();
    }
}





