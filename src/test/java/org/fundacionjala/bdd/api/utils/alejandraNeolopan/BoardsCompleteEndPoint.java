package org.fundacionjala.bdd.api.utils.alejandraNeolopan;

import org.fundacionjala.bdd.api.EnvReader;

public final class BoardsCompleteEndPoint {
    private BoardsCompleteEndPoint() {

    }

    public static String build(final String processedEndPoint) {
        String url = EnvReader.getInstance().getApiUrl();
        String token = EnvReader.getInstance().getApiToken();
        String key = EnvReader.getInstance().getApiKey();
        return url + processedEndPoint + "?key=" + key + "&token=" + token;
    }
}
