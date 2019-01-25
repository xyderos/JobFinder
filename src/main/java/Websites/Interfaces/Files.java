package Websites.Interfaces;

import java.io.IOException;

public interface Files {

    String EMPTY="";

    String OLD="old";

    String PATH=System.getProperty("user.dir");

    String TXT=".txt";

    String extractName(String url);

    String createAndWrite(String url)throws IOException;

    void toFiles() throws IOException;

    void toFiles(String user, String password) throws IOException;

}
