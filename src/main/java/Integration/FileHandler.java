package Integration;

import Websites.ProFinder.ProFinderPathCreator;
import Websites.eWork.eWorkPathCreator;
import Websites.AF.AFPathCreator;

import java.io.File;

class FileHandler {

    private static ProFinderPathCreator pf=new ProFinderPathCreator();

    private static eWorkPathCreator ew=new eWorkPathCreator();

    private static AFPathCreator af=new AFPathCreator();

    private static final String DIR=System.getProperty("user.dir")+ "/res/";

    static void getAds(String username, String password,String query) throws Exception{

        Thread t1 = new Thread(() -> {
            try {

                System.out.println(query);
                ew.init(query,username,password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                af.init(query,null,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t2.start();

        Thread t3 = new Thread(() -> {
            try {
                pf.init(null,null,null);
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

        File folder = new File(DIR);

        return folder.listFiles();
    }
}
