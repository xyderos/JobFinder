package Websites.AF;

import Websites.Utility.FileHelpers;

import java.io.File;
import java.util.HashSet;

public class AFPathCreator extends FileHelpers {

    private AFLinkReceiver r=new AFLinkReceiver();

    private static HashSet<File> files=new HashSet<>();

    private static final String DELIM="available-jobs/";

    @Override
    public void init(String query, String name, String password) throws Exception {
        toFiles(DELIM,query,r.getSetFromWebsite(name,password,query),files,name,password);
    }
}
