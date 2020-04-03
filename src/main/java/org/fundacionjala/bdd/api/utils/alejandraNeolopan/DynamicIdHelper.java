package org.fundacionjala.bdd.api.utils.alejandraNeolopan;

import io.restassured.response.Response;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DynamicIdHelper {
    private DynamicIdHelper() {
    }
    public static String buildEndpoint(final Map<String, Response> context, final String endPoint) {
        String[] endPointSplit = endPoint.split("/");
        for (int i = 0; i < endPointSplit.length; i++) {
            Pattern pattern = Pattern.compile("(?<=\\{)(.*?)(?=\\})");
            Matcher matcher = pattern.matcher(endPointSplit[i]);
            if (matcher.find()) {
                endPointSplit[i] = getElementResponse(context, matcher.group(1));
            }
        }
        return String.join("/", endPointSplit);
    }

    private static String getElementResponse(final Map<String, Response> context, final String element) {
        String[] elementSplit = element.split("\\.");
        Response response = (Response) context.get(elementSplit[0]);
        return response.jsonPath().getString(elementSplit[1]);
    }

}
