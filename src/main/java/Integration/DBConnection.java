package Integration;

import Websites.ProFinder.ProFinderPathCreator;
import Websites.eWork.eWorkPathCreator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Timer;
import java.util.TimerTask;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/contactdb";

    private static final String admin = "test";

    private static final String password = "test";

    private static final int PERIOD=1000*60*60*24;

    private static final long DELAY= 0L;

    private static Timer timer = new Timer();

    private eWorkPathCreator e=new eWorkPathCreator();

    private ProFinderPathCreator p=new ProFinderPathCreator();

    private void updateLists(String username, String password) {

        TimerTask t = new TimerTask() {
            @Override
            public void run () {

                try {
                    e.toFiles(username,password);
                    p.toFiles();
                }
                catch (IOException e) { e.printStackTrace();}
            }
        };

        timer.schedule (t,DELAY , PERIOD);
    }

    public static void INSERT(String user,String pass, String path) throws Exception{

        if (!user.equals(admin) && !pass.equals(password)) return;

        //updateLists(user,pass);

        Connection conn= DriverManager.getConnection(URL, user, password);

        String query = "INSERT INTO jobs (path) values (LOAD_FILE(?))";

        PreparedStatement statement = conn.prepareStatement(query);

        statement.setString(1,path);

        int row=statement.executeUpdate();

        conn.close();
    }
}
