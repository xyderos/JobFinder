package Websites.AF;

import Websites.Utility.FileHandler;
import Websites.Utility.Helpers;

import java.io.File;
import java.util.HashSet;

public class AFPathCreator extends Helpers implements FileHandler {

    private AFLinkReceiver r=new AFLinkReceiver();

    private static HashSet<File> files=new HashSet<>();

    private static final String DELIM="available-jobs/";

    private static final String DASH="-";


    @Override
    public String extractName(String url) {

        String t=url.substring(url.indexOf(DELIM)+11);

        return setDetails(t,DASH,null);
    }

    @Override
    public void toFiles(String query) throws Exception {
        toFiles(query,r.getSetFromWebsite(query),files);
    }

    @Override
    public String createAndWrite(String url, String username, String pass){
        throw new UnsupportedOperationException();
    }

    @Override
    public void toFiles(String user, String password, String query){
        throw new UnsupportedOperationException();
    }

    @Override
    public String createAndWrite(String url) throws Exception{

        String name=extractName(url);

        String fileName= PATH+name+TXT;

        return create(fileName,url);
    }
}
