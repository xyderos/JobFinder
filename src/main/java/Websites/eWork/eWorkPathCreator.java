package Websites.eWork;

import Websites.Utility.FileHelpers;

import java.io.*;
import java.util.HashSet;

public class eWorkPathCreator extends FileHelpers {

    private static HashSet<File> files=new HashSet<>();

    private Websites.eWork.eWorkLinkReceiver eWorkLinkReceiver =new eWorkLinkReceiver();

    private static final String DELIM ="ProjectView_framed.cfm?";

    @Override
    public void init(String query, String name, String password) throws Exception {
        toFiles(DELIM,query,eWorkLinkReceiver.getSetFromWebsite(query,name,password),files,name,password);
    }
}

