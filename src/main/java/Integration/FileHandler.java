package Integration;

import Websites.ProFinder.ProFinderPathCreator;
import Websites.eWork.eWorkPathCreator;
import Websites.AF.AFPathCreator;

import java.io.File;
import java.io.IOException;

class FileHandler {

    private static ProFinderPathCreator pf=new ProFinderPathCreator();

    private static eWorkPathCreator ew=new eWorkPathCreator();

    private static AFPathCreator af=new AFPathCreator();

    static void getAds(String username, String password,String query) throws Exception {

        Thread t1 = new Thread(() -> {
            try {
                ew.toFiles(username,password,query);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                af.toFiles(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t2.start();

        Thread t3 = new Thread(() -> {
            try {
                pf.toFiles(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t3.start();

        t1.join();

        t2.join();

        t3.join();

    }

    static File[] files(){

        String f=System.getProperty("user.dir")+ "/res/";

        File folder = new File(f);

        return folder.listFiles();
    }
}
