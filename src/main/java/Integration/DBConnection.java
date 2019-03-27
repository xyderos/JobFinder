package Integration;

import java.io.File;
import java.sql.*;
import java.util.Objects;

public class DBConnection {

    private static final String URL="jdbc:mysql://localhost:3306/test";

    private static final String DRIVER="com.mysql.cj.jdbc.Driver";

    private static final String INSERT_SQL = "INSERT INTO test.users( name, dt, ref) values( ?, ?, ?) ";

    private static final String SELECT_SQL ="SELECT * FROM test.users WHERE dt > now() - INTERVAL 20 MINUTE";

    private static Connection CONNECTION = null;

    private java.util.Date today = new java.util.Date();

    private ResultSet res;

    public ResultSet getRes() {
        return res;
    }

    public DBConnection(String name, String password, String eWName, String eWpass) throws Exception {

        Class.forName(DRIVER);

        CONNECTION=DriverManager.getConnection(URL, name, password);

        FileHandler.getAds(eWName,eWpass);

        insert();

        this.res=getRecentAdvertisements();
    }

    private void insert() throws SQLException{

        PreparedStatement ps = CONNECTION.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);

        for (File file : Objects.requireNonNull(FileHandler.files())){

            System.out.println("INSERTING INTO THE DATABASE FILE: " + file.getName());

            ps.setString(1, file.getName());
            ps.setTimestamp(2,new java.sql.Timestamp(today.getTime()));
            ps.setString(3, file.getAbsolutePath());
            ps.executeUpdate();
        }
    }

    private ResultSet getRecentAdvertisements() throws SQLException{

        PreparedStatement ps = CONNECTION.prepareStatement(SELECT_SQL, Statement.RETURN_GENERATED_KEYS);

        return ps.executeQuery(SELECT_SQL);
    }
}
