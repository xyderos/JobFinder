package Driver;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    public static void main(String[] args) throws Exception {

        Connection.Response initial = Jsoup
                .connect("https://myework.eworkgroup.com/Login.cfm")
                .method(Connection.Method.GET)
                .execute();

        Connection.Response login = Jsoup
                .connect("https://myework.eworkgroup.com/Login.cfm")
                .data("user_email", "put_email")
                .data("user_password", "put password")
                .cookies(initial.cookies())
                .method(Connection.Method.POST)
                .execute();

        Document document = Jsoup.connect("https://myework.eworkgroup.com/ProjectView_framed.cfm?PROJECT_ID=100292342").cookies(initial.cookies()).get();

        Elements elements = document.select("p");

        for (Element element : elements) System.out.println(element.text());
    }

}



