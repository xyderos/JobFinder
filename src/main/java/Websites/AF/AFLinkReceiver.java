package Websites.AF;

import Websites.Utility.LinkHelpers;

import java.io.IOException;
import java.util.HashSet;

public class AFLinkReceiver extends LinkHelpers {

    private static HashSet<String> set=new HashSet<>();

    private static final String SEARCH="http://www.afconsult.com/en/join-us/available-jobs/?SearchText=";

    private static final String JOIN_US="join-us";

    private static final String TWITTER="twitter";

    private static final String FACEBOOK="facebook";

    private static final String LINKEDIN="linkedin";

    private static final String SEARCH_TEXT ="?SearchText";

    private static final String SUBSCRIBE="subscribe-for-jobs";

    private static final String OPEN_APPLICATION ="openapplication";

    @Override
    public void deleteWrongResults(HashSet<String> set) {
        set.removeIf(s -> !s.contains(JOIN_US) || s.contains(TWITTER) || s.contains(FACEBOOK) || s.contains(LINKEDIN)
                || s.contains(SEARCH_TEXT) || s.contains(SUBSCRIBE) || s.contains(OPEN_APPLICATION) || s.length()==51 ||s.length()==36);
    }

    @Override
    public HashSet<String> getSetFromWebsite(String query, String email, String password) throws IOException {

        getLinksFromWebsite(email,password,SEARCH,query,set);

        deleteWrongResults(set);

        return set;
    }
}
