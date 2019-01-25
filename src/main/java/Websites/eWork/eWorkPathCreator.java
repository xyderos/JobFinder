package Websites.eWork;

import Websites.Interfaces.Files;
import Websites.Interfaces.Helpers;
import Websites.ProFinder.ProFinderPathCreator;

import java.io.*;
import java.util.HashSet;
import java.util.regex.Pattern;

public class eWorkPathCreator extends Helpers implements Files {

    private static HashSet<File> files=new HashSet<>();

    private eWorkLinkReceiver eWorkLinkReceiver =new eWorkLinkReceiver();

    private static final String DELIM ="/ProjectView_framed.cfm?";

    private static final String ENDL ="\0";

    private static final String EQUALS="=";

    private static final String UNDERSCORE="_";

    @Override
    public String extractName(String url) {

        String t=url.substring(url.indexOf(DELIM),url.indexOf(ENDL));

        return setDetails(t,EQUALS,UNDERSCORE);
    }

    @Override
    public String createAndWrite(String url) throws IOException {

        String name = extractName(url);

        String fileName = Websites.Interfaces.Files.PATH + name + TXT;

        return create(fileName,url);
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

        String fileName= ProFinderPathCreator.PATH+extractName(path)+TXT;

        BufferedReader br=new BufferedReader(new FileReader(fileName));

        fileName=fileName.replaceAll(Pattern.quote(OLD),EMPTY);

        writeFile(fileName,br);
    }

    @Override
    public void toFiles() {
        throw new UnsupportedOperationException();
    }
}

