package Websites.Utility;

import java.io.IOException;
import java.util.HashSet;

public interface Links {

    void getLinksFromWebsite(String query)throws IOException, Exception;

    void getLinksFromWebsite(String name, String password,String query)throws IOException;

    void deleteWrongResults();

    String toString(Object...args);

    HashSet<String> getSetFromWebsite(String query) throws IOException, Exception;

    HashSet<String> getSetFromWebsite(String email, String password,String query) throws IOException;
}
