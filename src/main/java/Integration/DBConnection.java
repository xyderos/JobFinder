package Integration;

import Websites.ProFinder.ProFinderPathCreator;
import Websites.eWork.eWorkPathCreator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class DBConnection {

    private static Connection CONNECTION = null;

    private static ProFinderPathCreator pf=new ProFinderPathCreator();

    private static eWorkPathCreator ew=new eWorkPathCreator();

    private static Timer timer = new Timer();

    private static final int PERIOD=1000*60*60*24;

    private static final long DELAY= 0L;

    DBConnection(String url, String name, String password) throws SQLException {

        CONNECTION=DriverManager.getConnection(url, name, password);
    }



    private void getLists(String username, String password) {

        Set<String> set=new HashSet<>();

        TimerTask t = new TimerTask() {
            @Override
            public void run () {

                try {
                    ew.toFiles(username,password);
                    pf.toFiles();
                }
                catch (IOException e) { e.printStackTrace();}
            }
        };

        timer.schedule (t,DELAY , PERIOD);
    }

}
