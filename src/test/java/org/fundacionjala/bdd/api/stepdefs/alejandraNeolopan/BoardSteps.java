package org.fundacionjala.bdd.api.stepdefs.alejandraNeolopan;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.alejandraNeolopan.PicoContainer;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.testng.Assert.assertEquals;

public class BoardSteps {
    private PicoContainer helper;
    private String url = EnvReader.getInstance().getApiUrl();
    private String token = EnvReader.getInstance().getApiToken();
    private String key = EnvReader.getInstance().getApiKey();
    private Response response;
    private final int oKStatus = 200;
    private String idBoard = "";
    private final int limit = 1000;


    public BoardSteps(final PicoContainer helper) {
        this.helper = helper;
    }

    @When("POST boards is executed")
    public void postBoardsIsExecuted(final Map<String, String> parameters) {
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
                .post(url + "/1/boards/?key={key}&token={token}", key, token);


    }

    @Then("a new board data is retrieved")
    public void aNewBoardDataIsRetrieved() {
        int actualStatusCode = response.getStatusCode();
        assertEquals(actualStatusCode, oKStatus);
        idBoard = response.jsonPath().getString("id");
        helper.addNewId(idBoard);
        String path = "src/test/resources/schemas/alejandraNeolopan/postSchema.json";
        response.then().assertThat().body(matchesJsonSchema(new File(path)));
    }

    @Given("a board has been created")
    public void aBoardHasBeenCreated() {
        Response response = given()
                .header("Content-Type", "application/json")
                .queryParam("name", "New%2CBoard")
                .queryParam("defaultLabels", "true")
                .queryParam("defaultLists", "true")
                .queryParam("desc", "Created%20by%20API")
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
                .post(url + "/1/boards/?key={key}&token={token}", key, token);
        idBoard = response.then()
                .assertThat()
                .extract()
                .path("id");
        helper.addNewId(idBoard);
    }

    @Then("board data is retrieved")
    public void boardDataIsRetrieved() {
        int actualStatusCode = response.getStatusCode();
        assertEquals(actualStatusCode, oKStatus);
        String path = "src/test/resources/schemas/alejandraNeolopan/getSchema.json";
        response.then().assertThat().body(matchesJsonSchema(new File(path)));
    }

    @Then("board data is retrieved with updated data")
    public void boardDataIsRetrievedWithUpdatedData() {
        int actualStatusCode = response.getStatusCode();
        assertEquals(actualStatusCode, oKStatus);
        String path = "src/test/resources/schemas/alejandraNeolopan/putSchema.json";
        response.then().assertThat().body(matchesJsonSchema(new File(path)));
    }

    @Then("{int} status is retrieved")
    public void statusIsRetrieved(int status) {
        int actualStatusCode = response.getStatusCode();
        assertEquals(actualStatusCode, status);
        String path = "src/test/resources/schemas/alejandraNeolopan/deleteSchema.json";
        response.then().assertThat().body(matchesJsonSchema(new File(path)));
    }

    @When("GET boards is executed")
    public void getBoardsIsExecuted() {
        String value = "addAttachmentToCard%2CaddChecklistToCard%2CaddMemberToBoard%2CaddMemberToCard"
                + "%2CaddToOrganizationBoard%2CcommentCard%2CconvertToCardFromCheckItem%2CcopyBoard%2CcopyCard"
                + "%2CcopyCommentCard%2CcreateBoard%2CcreateCard%2CcreateList"
                +  "%2CdeleteAttachmentFromCard%2CdeleteCard%2C"
                + "disablePlugin%2CdisablePowerUp%2CemailCard%2CenablePlugin%2CenablePowerUp%2CmakeAdminOfBoard%2C"
                + "makeNormalMemberOfBoard%2CmakeObserverOfBoard%2CmoveCardFromBoard"
                + "%2CmoveCardToBoard%2CmoveListFromBoard"
                + "%2CmoveListToBoard%2CremoveChecklistFromCard%2CremoveFromOrganizationBoard%2CremoveMemberFromCard%2C"
                + "unconfirmedBoardInvitation%2CunconfirmedOrganizationInvitation"
                +  "%2CupdateBoard%2CupdateCard%3Aclosed%2C"
                +  "updateCard%3Adue%2CupdateCard%3AidList%2CupdateCheckItemStateOnCard%2CupdateList%3Aclosed";
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
                .get(url + "/1/boards/" + idBoard + "?key={key}&token={token}", key, token);
    }

    @When("PUT boards is executed with changes")
    public void putBoardsIsExecutedWithChanges(final Map<String, String> parameters) {
        response = given()
                .header("Content-Type", "application/json")
                .queryParam("name", parameters.get("name"))
                .queryParam("desc", parameters.get("desc"))
                .when()
                .put(url + "/1/boards/" + idBoard + "?key={key}&token={token}", key, token);
    }

    @When("DELETE boards is executed")
    public void deleteBoardsIsExecuted() {
        response = given()
                .header("Content-Type", "application/json")
                .when()
                .delete(url + "/1/boards/" + idBoard + "?key={key}&token={token}", key, token);
    }
}
