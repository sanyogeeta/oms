package com.ge.predix.solsvc.boot.service.cxf;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



/**
 * An example of how to create a Rest service using standard javax.ws.rs annotations but registering with CXF
 * 
 * @author predix
 */

@RestController
@SpringBootApplication


@PropertySource("classpath:application.properties")

public class eOMAPIOrderDetails {

	static OAuth2Details oauthDetails = null;;
	String accessToken;

	public static void main(String[] args) throws Exception{		
		SpringApplication.run(eOMAPIOrderDetails.class, args);
	}

	public String getAccessToken(){

		Properties config = OAuthUtils.getClientConfigProps(OAuthConstants.CONFIG_FILE_PATH);
		//Generate the OAuthDetails bean from the config properties file
		oauthDetails = OAuthUtils.createOAuthDetails(config);

		//Validate Input
		if(!OAuthUtils.isValidInput(oauthDetails)){
			System.out.println("Please provide valid config properties to continue.");
			System.exit(0);
		}

		//Determine operation
		if(oauthDetails.isAccessTokenRequest()){
			//Generate new Access token
			accessToken = OAuthUtils.getAccessToken(oauthDetails);
			if(OAuthUtils.isValid(accessToken)){
				System.out.println("Successfully generated Access token for client_credentials grant_type: "+accessToken);
			}
			else{
				System.out.println("Could not generate Access token for client_credentials grant_type");
			}
		}

		else {
			System.out.println("Resource endpoint url: " + oauthDetails.getResourceServerUrl());
			OAuthUtils.getProtectedResource(oauthDetails);
		}

		return accessToken != null? accessToken : null;
	}



	@RequestMapping(value="/geteOMOrderDetails/{globalOrderNumber}", method = RequestMethod.GET)
	public  @ResponseBody  String getOrderDetails(@PathVariable long globalOrderNumber){

		StringBuffer response = new StringBuffer();
		JSONParser parser = new JSONParser();
		BufferedReader in = null;
		String inputLine;
		Properties config = OAuthUtils.getClientConfigProps(OAuthConstants.CONFIG_FILE_PATH);

		//Generate the OAuthDetails bean from the config properties file
		oauthDetails = OAuthUtils.createOAuthDetails(config);

		String authserverurl  = oauthDetails.getApiUrl() + globalOrderNumber;
		System.out.println("API URL--" + authserverurl);

		if(accessToken == null){
			System.out.println("********AccessToken is null *********" );
			getAccessToken();		
		}
		try{

			System.out.println("********accessToken is **********" + accessToken);
			URL obj = new URL(authserverurl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty("Authorization", "Bearer "+accessToken);

			int responseCode = con.getResponseCode();
						
			if(responseCode == con.HTTP_UNAUTHORIZED){	
				System.out.println("********AccessToken is UNAUTHORIZED *********" );
				con.disconnect();
				getAccessToken();
				con = (HttpURLConnection) obj.openConnection();				
				con.setRequestProperty("Authorization", "Bearer "+accessToken);
				responseCode = con.getResponseCode();
			}
			
			if(responseCode == con.HTTP_OK){			
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}
			
			if(responseCode == con.HTTP_NOT_FOUND ){
				in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return  response!=null ? response.toString():null;
	}
	
	
	@RequestMapping(value="/geteOMOrderDetails/{globalOrderNumber}/salesPeople", method = RequestMethod.GET)
	public  @ResponseBody  String getOrderSalesPeople(@PathVariable long globalOrderNumber){

		StringBuffer response = new StringBuffer();
		JSONParser parser = new JSONParser();
		BufferedReader in = null;
		String inputLine;
		Properties config = OAuthUtils.getClientConfigProps(OAuthConstants.CONFIG_FILE_PATH);

		//Generate the OAuthDetails bean from the config properties file
		oauthDetails = OAuthUtils.createOAuthDetails(config);

		String authserverurl  = oauthDetails.getApiUrl() + globalOrderNumber + "/salesPeople";
		System.out.println("API URL--" + authserverurl);

		if(accessToken == null){
			System.out.println("********AccessToken is null *********" );
			getAccessToken();		
		}
		try{

			System.out.println("********accessToken is **********" + accessToken);
			URL obj = new URL(authserverurl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty("Authorization", "Bearer "+accessToken);

			int responseCode = con.getResponseCode();
						
			if(responseCode == con.HTTP_UNAUTHORIZED){	
				System.out.println("********AccessToken is UNAUTHORIZED *********" );
				con.disconnect();
				getAccessToken();
				con = (HttpURLConnection) obj.openConnection();	
				con.setRequestProperty("Authorization", "Bearer "+accessToken);
				responseCode = con.getResponseCode();
			}
			
			if(responseCode == con.HTTP_OK){			
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}
			
			if(responseCode == con.HTTP_NOT_FOUND ){
				in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return  response!=null ? response.toString():null;
	}
	
	@RequestMapping(value="/geteOMOrderDetails/{globalOrderNumber}/orderChecklist", method = RequestMethod.GET)
	public  @ResponseBody  String getOrderChecklist(@PathVariable long globalOrderNumber){

		StringBuffer response = new StringBuffer();
		JSONParser parser = new JSONParser();
		BufferedReader in = null;
		String inputLine;
		Properties config = OAuthUtils.getClientConfigProps(OAuthConstants.CONFIG_FILE_PATH);

		//Generate the OAuthDetails bean from the config properties file
		oauthDetails = OAuthUtils.createOAuthDetails(config);

		String authserverurl  = oauthDetails.getApiUrl() + globalOrderNumber + "/orderChecklist";
		System.out.println("API URL--" + authserverurl);

		if(accessToken == null){
			System.out.println("********AccessToken is null *********" );
			getAccessToken();		
		}
		try{

			System.out.println("********accessToken is **********" + accessToken);
			URL obj = new URL(authserverurl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty("Authorization", "Bearer "+accessToken);

			int responseCode = con.getResponseCode();
						
			if(responseCode == con.HTTP_UNAUTHORIZED){	
				System.out.println("********AccessToken is UNAUTHORIZED *********" );
				con.disconnect();
				getAccessToken();
				con = (HttpURLConnection) obj.openConnection();	
				con.setRequestProperty("Authorization", "Bearer "+accessToken);
				responseCode = con.getResponseCode();
			}
			
			if(responseCode == con.HTTP_OK){			
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}
			
			if(responseCode == con.HTTP_NOT_FOUND ){
				in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return  response!=null ? response.toString():null;
	}
	
	@RequestMapping(value="/geteOMOrderDetails/{globalOrderNumber}/fulfillmentSets/{fulfillmentSetNumber}", method = RequestMethod.GET)
	public  @ResponseBody  String getfulfillmentSetsFset(@PathVariable long globalOrderNumber , @PathVariable long fulfillmentSetNumber){

		StringBuffer response = new StringBuffer();
		JSONParser parser = new JSONParser();
		BufferedReader in = null;
		String inputLine;
		Properties config = OAuthUtils.getClientConfigProps(OAuthConstants.CONFIG_FILE_PATH);

		//Generate the OAuthDetails bean from the config properties file
		oauthDetails = OAuthUtils.createOAuthDetails(config);

		String authserverurl  = oauthDetails.getApiUrl() + globalOrderNumber + "/fulfillmentSets/" + fulfillmentSetNumber ;
		System.out.println("API URL--" + authserverurl);

		if(accessToken == null){
			System.out.println("********AccessToken is null *********" );
			getAccessToken();		
		}
		try{

			System.out.println("********accessToken is **********" + accessToken);
			URL obj = new URL(authserverurl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty("Authorization", "Bearer "+accessToken);

			int responseCode = con.getResponseCode();
						
			if(responseCode == con.HTTP_UNAUTHORIZED){	
				System.out.println("********AccessToken is UNAUTHORIZED *********" );
				con.disconnect();
				getAccessToken();
				con = (HttpURLConnection) obj.openConnection();	
				con.setRequestProperty("Authorization", "Bearer "+accessToken);
				responseCode = con.getResponseCode();
			}
			
			if(responseCode == con.HTTP_OK){			
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}
			
			if(responseCode == con.HTTP_NOT_FOUND ){
				in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return  response!=null ? response.toString():null;
	}
	
	
	@RequestMapping(value="/geteOMOrderDetails/{globalOrderNumber}/fulfillmentSets/{fulfillmentSetNumber}/equipmentSets/{equipmentSetNumber}", method = RequestMethod.GET)
	public  @ResponseBody  String getfulfillmentSetsESETbyFSET(@PathVariable long globalOrderNumber , @PathVariable long fulfillmentSetNumber , @PathVariable long equipmentSetNumber){

		StringBuffer response = new StringBuffer();
		JSONParser parser = new JSONParser();
		BufferedReader in = null;
		String inputLine;
		Properties config = OAuthUtils.getClientConfigProps(OAuthConstants.CONFIG_FILE_PATH);

		//Generate the OAuthDetails bean from the config properties file
		oauthDetails = OAuthUtils.createOAuthDetails(config);

		String authserverurl  = oauthDetails.getApiUrl() + globalOrderNumber + "/fulfillmentSets/" + fulfillmentSetNumber + "/equipmentSets/" + equipmentSetNumber ;
		System.out.println("API URL--" + authserverurl);

		if(accessToken == null){
			System.out.println("********AccessToken is null *********" );
			getAccessToken();		
		}
		try{

			System.out.println("********accessToken is **********" + accessToken);
			URL obj = new URL(authserverurl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty("Authorization", "Bearer "+accessToken);

			int responseCode = con.getResponseCode();
						
			if(responseCode == con.HTTP_UNAUTHORIZED){	
				System.out.println("********AccessToken is UNAUTHORIZED *********" );
				con.disconnect();
				getAccessToken();
				con = (HttpURLConnection) obj.openConnection();	
				con.setRequestProperty("Authorization", "Bearer "+accessToken);
				responseCode = con.getResponseCode();
			}
			
			if(responseCode == con.HTTP_OK){			
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}
			
			if(responseCode == con.HTTP_NOT_FOUND ){
				in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return  response!=null ? response.toString():null;
	}
	
	@RequestMapping(value="/geteOMOrderDetails/{globalOrderNumber}/equipmentSets/{equipmentSetPath}", method = RequestMethod.GET)
	public  @ResponseBody  String getESETbyGON(@PathVariable long globalOrderNumber , @PathVariable long equipmentSetPath){

		StringBuffer response = new StringBuffer();
		JSONParser parser = new JSONParser();
		BufferedReader in = null;
		String inputLine;
		Properties config = OAuthUtils.getClientConfigProps(OAuthConstants.CONFIG_FILE_PATH);

		//Generate the OAuthDetails bean from the config properties file
		oauthDetails = OAuthUtils.createOAuthDetails(config);

		String authserverurl  = oauthDetails.getApiUrl() + globalOrderNumber + "/equipmentSets/" + equipmentSetPath ;
		System.out.println("API URL--" + authserverurl);

		if(accessToken == null){
			System.out.println("********AccessToken is null *********" );
			getAccessToken();		
		}
		try{

			System.out.println("********accessToken is **********" + accessToken);
			URL obj = new URL(authserverurl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty("Authorization", "Bearer "+accessToken);

			int responseCode = con.getResponseCode();
						
			if(responseCode == con.HTTP_UNAUTHORIZED){	
				System.out.println("********AccessToken is UNAUTHORIZED *********" );
				con.disconnect();
				getAccessToken();				
				con = (HttpURLConnection) obj.openConnection();	
				con.setRequestProperty("Authorization", "Bearer "+accessToken);
				responseCode = con.getResponseCode();
			}
			
			if(responseCode == con.HTTP_OK){			
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}
			
			if(responseCode == con.HTTP_NOT_FOUND ){
				in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return  response!=null ? response.toString():null;
	}
	
	@RequestMapping(value="/geteOMOrderDetails/{globalOrderNumber}/fulfillmentSets/{fulfillmentSetNumber}/equipmentSets/"
			+ "{equipmentSetNumber}/orderLines/{lineNumber}", method = RequestMethod.GET)
	public  @ResponseBody  String getOrderlines(@PathVariable long globalOrderNumber , @PathVariable long fulfillmentSetNumber ,
			@PathVariable long equipmentSetNumber, @PathVariable long lineNumber){

		StringBuffer response = new StringBuffer();
		JSONParser parser = new JSONParser();
		BufferedReader in = null;
		String inputLine;
		Properties config = OAuthUtils.getClientConfigProps(OAuthConstants.CONFIG_FILE_PATH);

		//Generate the OAuthDetails bean from the config properties file
		oauthDetails = OAuthUtils.createOAuthDetails(config);

		String authserverurl  = oauthDetails.getApiUrl() + globalOrderNumber + "/fulfillmentSets/" + fulfillmentSetNumber + "/equipmentSets/" + equipmentSetNumber + "/orderLines/" + lineNumber;
		System.out.println("API URL--" + authserverurl);

		if(accessToken == null){
			System.out.println("********AccessToken is null *********" );
			getAccessToken();		
		}
		try{

			System.out.println("********accessToken is **********" + accessToken);
			URL obj = new URL(authserverurl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty("Authorization", "Bearer "+accessToken);

			int responseCode = con.getResponseCode();
						
			if(responseCode == con.HTTP_UNAUTHORIZED){	
				System.out.println("********AccessToken is UNAUTHORIZED *********" );
				con.disconnect();
				getAccessToken();
				con = (HttpURLConnection) obj.openConnection();	
				con.setRequestProperty("Authorization", "Bearer "+accessToken);
				responseCode = con.getResponseCode();
			}
			
			if(responseCode == con.HTTP_OK){			
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}
			
			if(responseCode == con.HTTP_NOT_FOUND ){
				in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return  response!=null ? response.toString():null;
	}
	
	
	@RequestMapping(value="/sendEmail/{from}/{to}", method = RequestMethod.GET)
	public  @ResponseBody  void sendEmail(@PathVariable String from, @PathVariable String to){
		

		System.out.println("********In Email Functionality *********" );
	      // Recipient's email ID needs to be mentioned.
	      // to = "ganeshbabu.kvs@ge.com";
		
		System.out.println("********In Email Functionality ***to******"  + to);
		System.out.println("********In Email Functionality ***from******"  + from);
/*
	      // Sender's email ID needs to be mentioned
	       from = "ganeshbabu.kvs@ge.com";
*/
	      // Assuming you are sending email from localhost
	      String host = "10.38.9.235";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);

	   // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);
	      System.out.println("********In Email Functionality **** session*****"  + session);
	      try{
	         // Create a default MimeMessage object.
	         MimeMessage messagemail = new MimeMessage(session);

	         // Set From: header field of the header.
	         messagemail.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         messagemail.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         messagemail.setSubject("This is the Subject Line!");

	         // Now set the actual message
	         messagemail.setText("This is actual message");

	         // Send message
	         Transport.send(messagemail);
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	   }
		
	
}

	




