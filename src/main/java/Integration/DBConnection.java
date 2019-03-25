package Integration;

import java.io.File;
import java.sql.*;
import java.util.Objects;

public class DBConnection {

    private static final String URL="jdbc:mysql://localhost:3306/jobs";

    private static final String INSERT_SQL = "INSERT INTO jobs( name, dt, ref) values( ?, ?, ?) ";

    private static final String SELECT_SQL ="SELECT * FROM jobs WHERE TIMESTAMPDIFF(?,interval 1 day)";

    private static Connection CONNECTION = null;

    private java.util.Date today = new java.util.Date();

    private static java.sql.Timestamp t;

    private ResultSet res;

    public ResultSet getRes() {
        return res;
    }

    public DBConnection(String name, String password, String eWName, String eWpass) throws Exception {

        CONNECTION=DriverManager.getConnection(URL, name, password);

        FileHandler.getAds(eWName,eWpass);

        FileHandler.cleanDub();

        insert();

        this.res=getRecentAdvertisements();
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
}
