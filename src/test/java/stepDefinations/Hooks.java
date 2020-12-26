package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		StepDefination m = new StepDefination();
		if(StepDefination.place_id == null)
		{
		m.add_place_payload( "Debasis" , "Spanish", "Africa");
		m.user_calls_with_http_request("addPlaceAPI", "POST");
		m.verify_place_id_created_maps_to_using("Debasis", "getPlaceAPI");
		}
		}
}
