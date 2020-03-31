package org.fundacionjala.bdd.api.utils.jorge;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.restassured.response.Response;

public final class EndPointBuilder {

    private static final String DATA_MATCHER_REGEX = "(?<=\\{)(.*?)(?=\\})";
    private static final String CAP_REGEX = "[{}]";
    private static final String EMPTY_VAL = "";

    private EndPointBuilder() {

    }

    public static String build(final Map<String, Response> responseMap, final String endPoint) {
        return build(responseMap, new HashMap<>(), endPoint);
    }

    public static String build(final Map<String, Response> responseMap,
                               final Map<String, String> paramValue,
                               final String endPoint) {

        Pattern pattern = Pattern.compile(DATA_MATCHER_REGEX);
        Matcher matcher = pattern.matcher(endPoint);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String valueMapped = getElementResponse(responseMap,
                                                    paramValue,
                                                    matcher.group().replaceAll(CAP_REGEX, EMPTY_VAL));
            if (Objects.isNull(valueMapped)) {
                continue;
            }
            matcher.appendReplacement(result, valueMapped);
        }
        matcher.appendTail(result);
        return result.toString().replaceAll(CAP_REGEX, EMPTY_VAL);
    }

    private static String getElementResponse(final Map<String, Response> responseMap,
                                             final Map<String, String> paramValue,
                                             final String element) {

        String[] elementSplit = element.split("\\.");
        if (paramValue.isEmpty() || elementSplit[1].equalsIgnoreCase("id")) {
            Response response = responseMap.get(elementSplit[0]);
            return response.jsonPath().getString(elementSplit[1]);
        } else {
            return paramValue.get(elementSplit[1]);
        }

    }
}
