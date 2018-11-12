package Websites.Interfaces;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashSet;

public interface Links {

    void getLinks()throws IOException;

    void getLinks(String name, String password)throws IOException;

    void deleteWrong();

    String toString(Object...args);

    HashSet<String> getSet() throws IOException;

    HashSet<String> getSet(String email, String password) throws IOException;

    Document createConnection(String name, String password, String url) throws IOException;

}
