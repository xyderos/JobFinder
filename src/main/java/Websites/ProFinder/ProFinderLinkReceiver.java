package Websites.ProFinder;

import java.util.HashSet;

import Websites.Utility.LinkHelpers;

class ProFinderLinkReceiver extends LinkHelpers {

    private static HashSet<String> set=new HashSet<>();

    private static final String SEARCH="http://www.profinder.se/uppdrag-19993191";

    private static final String TWITTER="twitter";

    private static final String LINKEDIN="linkedin";

    private static final String REFNR="refnr";

    @Override
    public void deleteWrongResults(HashSet<String> set){
        set.removeIf(s -> s.contains(LINKEDIN) || s.contains(TWITTER) || !s.contains(REFNR));
    }

    @Override
    public HashSet<String> getSetFromWebsite(String email, String password,String query) throws Exception {

        System.out.println(email);

        System.out.println(password);

        System.out.println(query);

        getLinksFromWebsite(email,password,SEARCH,query,set);

        deleteWrongResults(set);

        return set;
    }
}
