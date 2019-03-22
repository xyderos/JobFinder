package Integration;

import java.io.File;
import java.sql.*;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class DBConnection {

    private static final String URL="jdbc:mysql://localhost:3306/jobs";

    private static final String INSERT_SQL = "INSERT INTO jobs( name, dt, ref) values( ?, ?, ?) ";

    private static final String SELECT_SQL ="SELECT * FROM jobs WHERE DATE_SUB(?,interval 1 day)";

    private static Connection CONNECTION = null;

    private java.util.Date today = new java.util.Date();

    private static java.sql.Timestamp t;

    private static Timer timer = new Timer();

    private static final int PERIOD=1000*60*60*24;

    private static final long DELAY= 0L;

    private DBConnection(String name, String password,String eWName, String eWpass) throws Exception {

        CONNECTION=DriverManager.getConnection(URL, name, password);


        TimerTask t = new TimerTask()  {
            @Override
            public void run (){

                try{
                    FileHandler.getAds(eWName,eWpass);

                    FileHandler.cleanDub();

                    insert();

                    System.out.println(getRecentAdvertisements());

                }catch (final Exception e){
                    e.printStackTrace();
                }

            }
        };

        timer.schedule (t,DELAY , PERIOD);
    }

    private void insert() throws SQLException{

        PreparedStatement ps = CONNECTION.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);

        for (File file : Objects.requireNonNull(FileHandler.files())){

                ps.setString(1, file.getName());
                ps.setTimestamp(2,new java.sql.Timestamp(today.getTime()));
                ps.setString(3, file.getAbsolutePath());
                int numRowsAffected = ps.executeUpdate();


            System.out.println(numRowsAffected);
        }

        t=new java.sql.Timestamp(today.getTime());

        FileHandler.cleanDub();
    }

    private ResultSet getRecentAdvertisements() throws SQLException{

        PreparedStatement ps = CONNECTION.prepareStatement(SELECT_SQL, Statement.RETURN_GENERATED_KEYS);

        ps.setTimestamp(1,t);

        return ps.executeQuery(SELECT_SQL);
    }

    public static void main(String[] args) throws Exception {
        DBConnection c=new DBConnection("root", "samplepass", "sampleuser", "samplepass");

        System.out.println("logged in and it will probably never reach this line lulululu "+ c.today );
    }
}
