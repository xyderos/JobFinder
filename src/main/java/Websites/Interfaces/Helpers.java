package Websites.Interfaces;

import java.io.IOException;

public abstract class Helpers {

    public abstract void formatFile(String file) throws IOException;

    public abstract String extractName(String url) throws IOException;


}
