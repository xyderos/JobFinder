package Integration;

import Websites.ProFinder.ProFinderPathCreator;
import Websites.eWork.eWorkPathCreator;

import java.io.File;
import java.io.IOException;

class FileHandler {

    private static ProFinderPathCreator pf=new ProFinderPathCreator();

    private static eWorkPathCreator ew=new eWorkPathCreator();

    static void getAds(String username, String password) throws IOException {

        ew.toFiles(username,password);
        pf.toFiles();
    }

    static File[] files(){

        String f=System.getProperty("user.dir")+ "/res/";

        File folder = new File(f);

        return folder.listFiles();
    }

    static void cleanDub(){

        for (File file : files())  if(file.getName().contains("old")) System.out.println(file.delete());
    }
}
