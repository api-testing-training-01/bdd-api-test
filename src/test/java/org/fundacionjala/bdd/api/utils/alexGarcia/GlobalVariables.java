package org.fundacionjala.bdd.api.utils.alexGarcia;

import org.fundacionjala.bdd.api.EnvReader;

public class GlobalVariables {
    protected GlobalVariables() {

    }
    public static final String PROJECTS_ENDPOINT_URL = "https://api.trello.com/1";
    public static final int OK_STATUS_CODE = 200;
    public static final String API_KEY = EnvReader.getInstance().getApiKey();
    public static final String TOKEN = EnvReader.getInstance().getApiToken();
}
