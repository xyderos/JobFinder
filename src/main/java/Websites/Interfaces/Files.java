package Websites.Interfaces;

import java.io.IOException;

public interface Files {

    String SPACE=" ";

    String DASH="-";

    String EMPTY="";

    int CHAR_PER_LINE=120;

    String OLD="old";

    String PATH="/Users/Konstantinos/Desktop/JobFinder/src/main/resources/Jobs/";

    String TXT=".txt";

    String PARAGRAPH="p";

    String extractName(String url);

    String createAndWrite(String url)throws IOException;

    void toFiles() throws IOException;

    void toFiles(String user, String password) throws IOException;

}
