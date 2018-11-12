package Websites.eWork;

import Websites.Interfaces.Files;
import Websites.Interfaces.Helpers;
import Websites.ProFinder.ProFinderPathCreator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashSet;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.text.WordUtils.wrap;

public class eWorkPathCreator extends Helpers implements Files {

    private static HashSet<File> files=new HashSet<>();

    private eWorkLinkReceiver eWorkLinkReceiver =new eWorkLinkReceiver();

    @Override
    public String extractName(String url) {
//
//        String t=url.substring(url.indexOf(DELIM)+15,url.indexOf(REF));
//
//        if (t.contains(PARENTHESIS)) t=t.replaceAll(Pattern.quote(PARENTHESIS),SPACE);
//
//        if (t.contains(DASH)) t=t.replaceAll(Pattern.quote(DASH),SPACE);
//
//        t = new StringBuilder(t).insert(t.length(), OLD).toString();
//
//        return t;

        return null;
    }

    @Override
    public String createAndWrite(String url) throws IOException {

        String name = extractName(url);

        String fileName = Websites.Interfaces.Files.PATH + name + TXT;

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

    @Override
    public void toFiles(String username,String password) throws IOException {

        File file;

        for (String pf : eWorkLinkReceiver.getSet(username,password) ){

            file=new File(createAndWrite(pf));

            formatFile(pf);

            files.add(file);
        }
    }

    @Override
    public void formatFile(String path) throws IOException {

        String fname= ProFinderPathCreator.PATH+extractName(path)+TXT;

        BufferedReader br=new BufferedReader(new FileReader(fname));

        fname=fname.replaceAll(Pattern.quote(OLD),EMPTY);

        BufferedWriter out=new BufferedWriter(new FileWriter(fname));

        String s=br.readLine();

        out.write(wrap(s, CHAR_PER_LINE, null, false));

        out.close();
    }

    @Override
    public void toFiles() {
        throw new UnsupportedOperationException();
    }
}

