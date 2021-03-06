package stepDefinations;



import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuilder;
import resources.Utils;
import static io.restassured.RestAssured.given;


public class StepDefination extends Utils {

	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuilder data = new TestDataBuilder();
	JsonPath js;
	static String place_id;
	@Given("Add Place Payload {string} {string} {string}")
	public void add_place_payload(String name, String language, String address) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		res = given().spec(requestSpecification()).body(data.addPlacePayload(name, language, address));
	}
	

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(method.equalsIgnoreCase("POST"))
			response =res.when().post(resourceAPI.getResource());
		else if(method.equalsIgnoreCase("GET"))
			response =res.when().get(resourceAPI.getResource());
			
			//then().spec(resspec).extract().response();
		  
		//response = res.when().post("/maps/api/place/add/json").then().spec(resspec).extract().response();
	}

	@Then("the API call is success with status code {int}")
	public void the_api_call_is_success_with_status_code(Integer int1) {
    assertEquals(response.getStatusCode(),200);
	}
	
	@Then("verify place_Id created maps to {string} using getPlaceAPI")
	public void verify_place_id_created_maps_to_using_get_place_api(String string) {    // Write code here that turns the phrase above into concrete actions
		assertEquals(response.getStatusCode(),200);
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
	    assertEquals(getJsonPath(response, keyValue),expectedValue);
	}
	
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	  place_id = getJsonPath(response, "place_id");
	    res= given().spec(requestSpecification()).queryParam("place_id", place_id);
	    user_calls_with_http_request(resource, "GET");
	    String name = getJsonPath(response, "name");
	    assertEquals(expectedName, name);
	}
	

	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
    // Write code here that turns the phrase above into concrete actions
    res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
}
	
}
