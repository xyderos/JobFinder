package Websites.eWork;

import Websites.Interfaces.FileHandler;
import Websites.Interfaces.Helpers;
import Websites.ProFinder.ProFinderPathCreator;

import java.io.*;
import java.util.HashSet;
import java.util.regex.Pattern;

public class eWorkPathCreator extends Helpers implements FileHandler {

    private static HashSet<File> files=new HashSet<>();

    private eWorkLinkReceiver eWorkLinkReceiver =new eWorkLinkReceiver();

    private static final String DELIM ="ProjectView_framed.cfm?";

    private static final String EQUALS="=";

    private static final String UNDERSCORE="_";

    @Override
    public String extractName(String url) {

        String t=url.substring(url.indexOf(DELIM));

        return setDetails(t,EQUALS,UNDERSCORE);
    }

    @Override
    public String createAndWrite(String url, String n, String pass) throws IOException {

        String name = extractName(url);

        String fileName = FileHandler.PATH + name + TXT;

        return create(fileName,url, n,pass);
    }

    @Override
    public void toFiles(String username,String password) throws IOException {

        File file;

        for (String pf : eWorkLinkReceiver.getSetFromWebsite(username,password) ){

            file=new File(createAndWrite(pf,username,password));

            formatFile(pf);

            files.add(file);
        }
    }

    @Override
    public void formatFile(String path) throws IOException {

        String fileName= ProFinderPathCreator.PATH+extractName(path)+TXT;

        BufferedReader br=new BufferedReader(new FileReader(fileName));

        fileName=fileName.replaceAll(Pattern.quote(OLD),EMPTY);

        writeFile(fileName,br);
    }

    @Override
    public void toFiles() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String createAndWrite(String url){
        throw new UnsupportedOperationException();
    }
}

