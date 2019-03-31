package Websites.eWork;

import Websites.Utility.LinkHelpers;


import java.io.IOException;
import java.util.HashSet;

public class eWorkLinkReceiver extends LinkHelpers{

    private static HashSet<String> set=new HashSet<>();

    private static final String SEARCH="https://myework.eworkgroup.com/RequestList.cfm?page=1&competence_group_id=&competence_area_category_area_id=&country_city_id=&description=";

    private static final String PROJECT_ID="PROJECT_ID";

    @Override
    public void deleteWrongResults(HashSet<String> set){
        set.removeIf(s -> !s.contains(PROJECT_ID));
    }

    @Override
    public HashSet<String> getSetFromWebsite(String query, String email, String password) throws IOException {

        getLinksFromWebsite(email,password,SEARCH,query,set);

        deleteWrongResults(set);

        return set;
    }
}


