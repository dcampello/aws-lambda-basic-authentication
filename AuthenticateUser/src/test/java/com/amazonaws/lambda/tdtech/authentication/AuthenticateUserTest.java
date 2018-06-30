package com.amazonaws.lambda.tdtech.authentication;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AuthenticateUserTest {

    private static Object input;

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = new LinkedHashMap();
        ((HashMap) input).put("userName", "Dhruv");
        ((HashMap) input).put("passwordHash","8743b52063cd84097a65d1633f5c74f5");
    } 

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

/*    @Test
    public void testAuthenticateUser() {
        AuthenticateUser handler = new AuthenticateUser();
        Context ctx = createContext();

        AuthenticateUserResponse output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("Hello from Lambda!", output);
    }*/
    
    @Test
    public void testhandleRequest() {
        AuthenticateUser handler = new AuthenticateUser();
        Context ctx = createContext();

        AuthenticateUserResponse output = (AuthenticateUserResponse)handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        if (output.getStatus().equalsIgnoreCase("true")) {
            System.out.println("AuthenticateUser JUnit Test Passed");
        }else{
        	Assert.fail("AuthenticateUser JUnit Test Failed");
        }
    }

}
