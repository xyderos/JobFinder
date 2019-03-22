package Websites.Interfaces;

import java.io.IOException;
import java.util.HashSet;

public interface Links {

    void getLinksFromWebsite()throws IOException;

    void getLinksFromWebsite(String name, String password)throws IOException;

    void deleteWrongResults();

    String toString(Object...args);

    HashSet<String> getSetFromWebsite() throws IOException;

    HashSet<String> getSetFromWebsite(String email, String password) throws IOException;

}
