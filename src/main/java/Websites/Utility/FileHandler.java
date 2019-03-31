package Websites.Utility;

import java.io.IOException;

public interface FileHandler {

    String TXT=".txt";

    String OLD="old";

    String PATH=System.getProperty("user.dir") + "/res/";

    String extractName(String url);

    String createAndWrite(String url)throws IOException, Exception;

    String createAndWrite(String url, String username, String pass) throws IOException, Exception;

    void toFiles(String query) throws IOException, Exception;

    void toFiles(String user, String password,String query) throws IOException;

}