package org.fundacionjala.bdd.api.stepdefs.alejandraNeolopan;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.fundacionjala.bdd.api.utils.alejandraNeolopan.BoardsCompleteEndPoint;
import org.fundacionjala.bdd.api.utils.alejandraNeolopan.DynamicIdHelper;
import org.fundacionjala.bdd.api.utils.alejandraNeolopan.PicoContainer;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.testng.Assert.assertEquals;

public class BoardSteps {
    private PicoContainer helper;
    private Response response;
    private String idBoard = "";
    private final int limit = 1000;


    public BoardSteps(final PicoContainer helper) {
        this.helper = helper;
    }

    @Given("a board has been created as {string}")
    public void aBoardHasBeenCreatedAs(final String key,  final Map<String, String> parameters) {
        String finalEndPoint = BoardsCompleteEndPoint.build("/1/boards/");
        Response response = given()
                .header("Content-Type", "application/json")
                .queryParam("name", parameters.get("name"))
                .queryParam("defaultLabels", "true")
                .queryParam("defaultLists", "true")
                .queryParam("desc", parameters.get("desc"))
                .queryParam("keepFromSource", "none")
                .queryParam("prefs_permissionLevel", "private")
                .queryParam("prefs_voting", "disabled")
                .queryParam("prefs_comments", "members")
                .queryParam("prefs_invitations", "members")
                .queryParam("prefs_selfJoin", "true")
                .queryParam("prefs_cardCovers", "true")
                .queryParam("prefs_background", "blue")
                .queryParam("prefs_cardAging", "regular")
                .when()
                .post(finalEndPoint);
        idBoard = response.then()
                .assertThat()
                .extract()
                .path("id");
        helper.addNewId(idBoard);
        helper.addResponse(key, response);
    }

    @When("POST {string} is executed")
    public void postIsExecuted(final String endPoint, final Map<String, String> parameters) {
        String finalEndPoint = BoardsCompleteEndPoint.build(endPoint);
        response = given()
                .header("Content-Type", parameters.get("Content-Type"))
                .queryParam("name", parameters.get("name"))
                .queryParam("defaultLabels", parameters.get("defaultLabels"))
                .queryParam("defaultLists", parameters.get("defaultLists"))
                .queryParam("desc", parameters.get("desc"))
                .queryParam("keepFromSource", parameters.get("keepFromSource"))
                .queryParam("prefs_permissionLevel", parameters.get("prefs_permissionLevel"))
                .queryParam("prefs_voting", parameters.get("prefs_voting"))
                .queryParam("prefs_comments", parameters.get("prefs_comments"))
                .queryParam("prefs_invitations", parameters.get("prefs_invitations"))
                .queryParam("prefs_selfJoin", parameters.get("prefs_selfJoin"))
                .queryParam("prefs_cardCovers", parameters.get("prefs_cardCovers"))
                .queryParam("prefs_background", parameters.get("prefs_background"))
                .queryParam("prefs_cardAging", parameters.get("prefs_cardAging"))
                .when()
                .post(finalEndPoint);
        idBoard = response.then()
                .assertThat()
                .extract()
                .path("id");
        helper.addNewId(idBoard);
    }

    @When("GET {string} is executed")
    public void getIsExecuted(final String endPoint) {
        String processedEndPoint = DynamicIdHelper.buildEndpoint(helper.getResponses(), endPoint);
        String finalEndPoint = BoardsCompleteEndPoint.build(processedEndPoint);
        String value = "addAttachmentToCard%2CaddChecklistToCard%2CaddMemberToBoard%2CaddMemberToCard"
                + "%2CaddToOrganizationBoard%2CcommentCard%2CconvertToCardFromCheckItem%2CcopyBoard%2CcopyCard"
                + "%2CcopyCommentCard%2CcreateBoard%2CcreateCard%2CcreateList"
                + "%2CdeleteAttachmentFromCard%2CdeleteCard%2C"
                + "disablePlugin%2CdisablePowerUp%2CemailCard%2CenablePlugin%2CenablePowerUp%2CmakeAdminOfBoard%2C"
                + "makeNormalMemberOfBoard%2CmakeObserverOfBoard%2CmoveCardFromBoard"
                + "%2CmoveCardToBoard%2CmoveListFromBoard"
                + "%2CmoveListToBoard%2CremoveChecklistFromCard%2CremoveFromOrganizationBoard%2CremoveMemberFromCard%2C"
                + "unconfirmedBoardInvitation%2CunconfirmedOrganizationInvitation"
                + "%2CupdateBoard%2CupdateCard%3Aclosed%2C"
                + "updateCard%3Adue%2CupdateCard%3AidList%2CupdateCheckItemStateOnCard%2CupdateList%3Aclosed";
        String field = "closed%2CcreationMethod%2CdateLastActivity%2CdateLastView"
                + "%2CdatePluginDisable%2CenterpriseOwned%2C"
                + "id%2CidOrganization%2Cname%2Cprefs%2CshortLink%2CshortUrl%2Curl%2Cdesc"
                + "%2CdescData%2CidTags%2Cinvitations%2C"
                + "invited%2ClabelNames%2Climits%2Cmemberships%2CpowerUps%2Csubscribed";
        String cardField = "badges%2Cclosed%2Cdesc%2CidAttachmentCover%2CidBoard%2CidList%2CidMembers"
                + "%2CisTemplate%2Clabels%2Cname%2Cpos%2CshortLink%2Curl";
        response = given()
                .header("Content-Type", "application/json")
                .queryParam("lists", "open")
                .queryParam("actions", value)
                .queryParam("actions_display", "true")
                .queryParam("actions_limit", "50")
                .queryParam("cards", "visible")
                .queryParam("card_attachments", "true")
                .queryParam("card_fields", cardField)
                .queryParam("fields", field)
                .queryParam("labels", "all")
                .queryParam("labels_limit", limit)
                .queryParam("members", "all")
                .queryParam("memberships", "all")
                .queryParam("organization", "true")
                .queryParam("organization_memberships", "all")
                .when()
                .get(finalEndPoint);
    }

    @When("PUT {string} is executed with changes")
    public void putIsExecutedWithChanges(final String endPoint, final Map<String, String> parameters) {
        String processedEndPoint = DynamicIdHelper.buildEndpoint(helper.getResponses(), endPoint);
        String finalEndPoint = BoardsCompleteEndPoint.build(processedEndPoint);
        response = given()
                .header("Content-Type", "application/json")
                .queryParam("name", parameters.get("name"))
                .queryParam("desc", parameters.get("desc"))
                .when()
                .put(finalEndPoint);
    }

    @When("DELETE {string} is executed")
    public void deleteIsExecuted(final String endPoint) {
        String processedEndPoint = DynamicIdHelper.buildEndpoint(helper.getResponses(), endPoint);
        String finalEndPoint = BoardsCompleteEndPoint.build(processedEndPoint);
        response = given()
                .header("Content-Type", "application/json")
                .when()
                .delete(finalEndPoint);
    }

    @Then("board data is retrieved")
    public void boardDataIsRetrieved(final Map<String, String> expectedData) {
        for (String key : expectedData.keySet()) {
            assertEquals(response.jsonPath().getString(key), expectedData.get(key),
                    String.format("The '%s' field does not match with the expected value.", key));
        }
    }

    @Then("{int} status is retrieved")
    public void statusIsRetrieved(int status) {
        int actualStatusCode = response.getStatusCode();
        assertEquals(actualStatusCode, status);
    }

    @Then("schema should match with {string}")
    public void schemaShouldMatchWith(final String path) {
        response.then().assertThat().body(matchesJsonSchema(new File(path)));
    }
}


