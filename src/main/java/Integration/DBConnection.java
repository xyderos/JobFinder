package Integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/contactdb";

    private String user = "JOHAN";

    private String password = "OK";

    private Connection conn=null;

    public void INSERT(String user,String pass, String path) throws Exception{

        if (!user.equals(this.user) && !password.equals(this.password)) return;

        this.conn= DriverManager.getConnection(URL, user, password);

        String query = "INSERT INTO jobs (path) values (LOAD_FILE(?))";

        PreparedStatement statement = conn.prepareStatement(query);

        statement.setString(1,path);

        int row=statement.executeUpdate();

        conn.close();
    }
}
