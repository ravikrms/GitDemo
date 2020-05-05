package stepDefinitions;



import static org.junit.Assert.assertEquals;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class StepDefinitions extends Utils {
	
	Response response;
	ResponseSpecification resspec;
	RequestSpecification res;
	static String place_id;
	
    TestDataBuild data = new TestDataBuild();
	
    @Given("Add Place Payload with {string}{string}{string}")
    public void add_Place_Payload_with(String name, String language, String address) throws IOException {
    	 
		 
		resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		res=given().spec(requestSpecification())
		.body(data.addPlacePayLoad(name,language,address));
		
	    
	}

    @When("user calls {string} using {string} http request")
    public void user_calls_using_http_request(String resource, String method){
	    // Write code here that turns the phrase above into concrete actions
		APIResources resources=APIResources.valueOf(resource);
		//System.out.println("Resources :"+resources.getResource());
		
		
		
		if(method.equalsIgnoreCase("POST"))
		{
			response =res.when().post(resources.getResource());
			
		}
		else if(method.equalsIgnoreCase("GET"))
		{
			response =res.when().get(resources.getResource());
		}
		else if(method.equalsIgnoreCase("DELETE"))
		{
			response =res.when().get(resources.getResource());
		}
		else
		{
			System.out.println(method+ ": Method is not recognised");
		}
 
		

	}

	@Then("the API call is success with status code {int}")
	public void the_API_call_is_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	 assertEquals(response.getStatusCode(),200);  
	 
	}

	@Then("{string} in ResponseBody is {string}")
	public void in_ResponseBody_is(String keyValue, String expectedValue) {
	    // Write code here that turns the phrase above into concrete actions
		//String respString = response.asString();
		//js = new JsonPath(respString);
		//String respValue =js.get(keyValue).toString();
		
		assertEquals(getJsonPath(response,keyValue),expectedValue);
		
		
	}
	@Then("Verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		place_id=getJsonPath(response,"place_id");
		 System.out.println("Place Id :"+place_id);
		
		res=given().spec(requestSpecification()).queryParams("place_id",place_id);
		user_calls_using_http_request(resource, "GET");
		String actualName = getJsonPath(response,"name");
		assertEquals(expectedName,actualName);		
	    
	}
	
	@Given("Delete Place payload")
	public void delete_Place_payload() throws IOException {
		
		res= given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
		
	    // Write code here that turns the phrase above into concrete actions
	    
	}
	
}
