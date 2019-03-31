package Websites.ProFinder;

import Websites.Utility.FileHelpers;

import java.io.*;
import java.util.HashSet;

public class ProFinderPathCreator extends FileHelpers {

    private static HashSet<File> files=new HashSet<>();

    private ProFinderLinkReceiver proFinderLinkReceiver =new ProFinderLinkReceiver();

    private static final String DELIM="201";

    @Override
    public void init(String query, String name, String password) throws Exception {
        toFiles(DELIM,query,proFinderLinkReceiver.getSetFromWebsite(name,password,query),files,name,password);
    }
}







