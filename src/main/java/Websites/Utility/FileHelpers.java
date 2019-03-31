package Websites.Utility;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashSet;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.text.WordUtils.wrap;

public abstract class FileHelpers extends ConnectionHelpers {

    private static final String SLASH="/";

    private static final String DASH="-";

    private static final String UNDERSCORE="_";

    private static final String EQUALS="=";

    private static final String OLD="old";

    private static final String PARAGRAPH="p";

    private static final int CHAR_PER_LINE = 100;

    private String PATH=System.getProperty("user.dir") + "/res/";

    private String TXT = ".txt";

    public abstract void init(String user, String password, String query) throws Exception;

    private String replaceSymbols(String t){

        if (t.contains(SLASH)) t=t.replaceAll(Pattern.quote(SLASH),UNDERSCORE);

        if (t.contains(DASH)) t=t.replaceAll(Pattern.quote(DASH),UNDERSCORE);

        if (t.contains(EQUALS)) t=t.replaceAll(Pattern.quote(EQUALS),UNDERSCORE);

        return new StringBuilder(t).insert(t.length(),OLD).toString();
    }

    private String makeNameFromURL(String url, String delim) {

        String t=url.substring(url.indexOf(delim));

        return replaceSymbols(t);
    }

    private String createFileName(String url, String delim,String nm,String password) throws Exception {

        String name= makeNameFromURL(url,delim);

        String fileName= PATH+name+TXT;

        return create(fileName,url,nm,password);
    }

    private String create(String fileName, String url, String name, String password) throws IOException{

        Document doc;

        BufferedWriter out = createFileInDirectory(fileName);

        if (name==null && password==null) doc = createConnectionWitheWork(null,null, url);

        else doc = createConnectionWitheWork(name,password, url);

        Elements paragraphs = doc.select(PARAGRAPH);

        return getString(fileName, out, paragraphs);
    }

    private BufferedWriter createFileInDirectory(String fileName) throws IOException{

        File f=new File(fileName);

        if (f.exists()) return null;

        final boolean newFile= f.createNewFile();

        assert newFile;

        return new BufferedWriter(new FileWriter(fileName));
    }

    private String getString(String fileName, BufferedWriter out, Elements paragraphs) throws IOException {
        for (Element p : paragraphs) {

            String s = p.text();

            if (s==null || out==null) continue;

            out.write(s);
        }
        if (out != null) out.close();

        return fileName;
    }

    private void createFile(String path, String delim) throws IOException{

        String fileName= PATH+ makeNameFromURL(path,delim)+ TXT;

        BufferedReader br=new BufferedReader(new FileReader(fileName));

        String EMPTY = "";

        fileName=fileName.replaceAll(Pattern.quote(OLD), EMPTY);

        BufferedWriter out=new BufferedWriter(new FileWriter(fileName));

        String s=br.readLine();

        out.write(wrap(s, CHAR_PER_LINE, null, true));

        out.close();
    }

    protected void toFiles(String delim,String query, HashSet<String> linkSet, HashSet<File> fileSet,String name,String password) throws Exception {

        System.out.println("LOOKING FOR: " + query);

        File file;

        for(String pf:linkSet){

            if (name==null && password==null)  file=new File(createFileName(pf,delim,null,null));

            else file=new File(createFileName(pf,delim,name,password));

            createFile(pf,delim);

            System.out.println("CREATED FILE: "+ file.getName() );

            if (file.getName().contains(OLD)) System.out.println("FOUND OLD FILE " + file.getName() + " deleting is done: " + file.delete());

            fileSet.add(file);
        }
    }
}
