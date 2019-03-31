package Websites.ProFinder;

import Websites.Utility.FileHandler;
import Websites.Utility.Helpers;

import java.io.*;
import java.util.HashSet;

public class ProFinderPathCreator extends Helpers implements FileHandler {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static HashSet<File> files=new HashSet<>();

    private static final String DASH="-";

    private static final String PARENTHESIS="(";

    private static final String DELIM="201";

    private static final String REF="refnr";

    private ProFinderLinkReceiver pf=new ProFinderLinkReceiver();

    @Override
    public String extractName(String url){

        String t=url.substring(url.indexOf(DELIM)+11,url.indexOf(REF));

        return setDetails(t,PARENTHESIS,DASH);
    }

    @Override
    public String createAndWrite(String url) throws Exception{
        String name=extractName(url);

        String fileName= PATH+name+TXT;

        return create(fileName,url);
    }

    @Override
    public void toFiles(String query) throws Exception{
       toFiles(query,pf.getSetFromWebsite(query),files);
    }

    @Override
    public void toFiles(String user, String password,String query ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String createAndWrite(String url, String username, String pass){
        throw new UnsupportedOperationException();
    }
}







