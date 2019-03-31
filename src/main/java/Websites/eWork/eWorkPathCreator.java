package Websites.eWork;

import Websites.Utility.FileHandler;
import Websites.Utility.Helpers;
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

        return create(fileName,url,n,pass);
    }

    @Override
    public void toFiles(String username,String password,String query) throws IOException {

        File file;

        for (String pf : eWorkLinkReceiver.getSetFromWebsite(username,password,query) ){

            file=new File(createAndWrite(pf,username,password));

            System.out.println("CREATED FILE: "+ file.getName() );

            formatFile(pf);

            if (file.getName().contains(OLD)) System.out.println("FOUND OLD FILE " + file.getName() + "deleting is done: " + file.delete());

            files.add(file);
        }
    }

    @Override
    public void toFiles(String query) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String createAndWrite(String url){
        throw new UnsupportedOperationException();
    }
}

