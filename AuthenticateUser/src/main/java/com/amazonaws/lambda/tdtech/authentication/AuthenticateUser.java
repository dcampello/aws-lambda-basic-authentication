package com.amazonaws.lambda.tdtech.authentication;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClient;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenForDeveloperIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenForDeveloperIdentityResult;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class AuthenticateUser implements RequestHandler<Object, AuthenticateUserResponse> {

	@SuppressWarnings("rawtypes")
	@Override
	public AuthenticateUserResponse handleRequest(Object input, Context context) {
	      
	    AuthenticateUserResponse authenticateUserResponse = new AuthenticateUserResponse();
	    LinkedHashMap inputHashMap = (LinkedHashMap)input;
	    User user = authenticateUser(inputHashMap);
	    if(user!=null){
	        authenticateUserResponse.setUserId(user.getUserId());
	        authenticateUserResponse.setStatus("true");
	        authenticateUserResponse.setOpenIdToken(user.getOpenIdToken());
	    }else{
	        authenticateUserResponse.setUserId(null);
	        authenticateUserResponse.setStatus("false");
	        authenticateUserResponse.setOpenIdToken(null);
	    }
	        
	    return authenticateUserResponse;
	}
    
    @SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	private String getOpenIdToken(Integer userId){
    	

    	
    	AmazonCognitoIdentityClient client = new AmazonCognitoIdentityClient();
        client.setRegion(Region.getRegion(Regions.US_EAST_2));
        GetOpenIdTokenForDeveloperIdentityRequest tokenRequest = new GetOpenIdTokenForDeveloperIdentityRequest();
        tokenRequest.setIdentityPoolId("us-east-2:d13031ce-4a99-4483-aae1-fa1a1451b79c");
        	
        HashMap map = new HashMap();
        map.put("login.agenda.services", userId.toString());
        	
        tokenRequest.setLogins(map);
        tokenRequest.setTokenDuration(new Long(10001));
        	
        GetOpenIdTokenForDeveloperIdentityResult result = client.getOpenIdTokenForDeveloperIdentity(tokenRequest);
        	
        String token = result.getToken();
        	
        return token;
    }
    
    @SuppressWarnings({ "deprecation", "rawtypes" })
	public User authenticateUser(LinkedHashMap input){
        User user=null;
        	
        String userName = (String) input.get("userName");
        String passwordHash = (String) input.get("passwordHash");
        	
        try{
            AmazonDynamoDBClient client = new AmazonDynamoDBClient();
            client.setRegion(Region.getRegion(Regions.US_EAST_2));
            DynamoDBMapper mapper = new DynamoDBMapper(client);
    	    	
            user = mapper.load(User.class, userName);
    	    	
            if(user!=null){
                if(user.getPasswordHash().equalsIgnoreCase(passwordHash)){
                    String openIdToken = getOpenIdToken(user.getUserId());
                    user.setOpenIdToken(openIdToken);
                    return user;
                }
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return user;
    }

}
