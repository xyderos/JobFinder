package Websites.Interfaces;

import java.io.IOException;

public interface FileHandler {

    String EMPTY="";

    String OLD="old";

    String PATH=System.getProperty("user.dir") + "/res/";

    String TXT=".txt";

    String extractName(String url);

    String createAndWrite(String url)throws IOException;

    String createAndWrite(String url, String username, String pass) throws IOException;

    void toFiles() throws IOException;

    void toFiles(String user, String password) throws IOException;

}
