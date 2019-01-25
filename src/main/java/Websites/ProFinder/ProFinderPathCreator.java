package Websites.ProFinder;

import Websites.Interfaces.Files;
import Websites.Interfaces.Helpers;

import java.io.*;
import java.util.HashSet;
import java.util.regex.Pattern;

public class ProFinderPathCreator extends Helpers implements Files {

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
    public String createAndWrite(String url) throws IOException{

        String name=extractName(url);

        String fileName= ProFinderPathCreator.PATH+name+TXT;

        return create(fileName,url);
    }

    @Override
    public void toFiles() throws IOException{

        File file;

        for (String pf : pf.getSet() ){

            file=new File(createAndWrite(pf));

            formatFile(pf);

            files.add(file);
        }
    }

    @Override
    public void formatFile(String path) throws IOException{

        BufferedReader br=new BufferedReader(new FileReader(ProFinderPathCreator.PATH+extractName(path)+TXT));

        String fileName=ProFinderPathCreator.PATH+extractName(path)+TXT.replaceAll(Pattern.quote(OLD),EMPTY);

        writeFile(fileName,br);
    }

    @Override
    public void toFiles(String user, String password) {
        throw new UnsupportedOperationException();
    }
}





