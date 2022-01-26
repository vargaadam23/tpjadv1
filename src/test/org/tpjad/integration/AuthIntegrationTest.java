package org.tpjad.integration;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

public class AuthIntegrationTest extends  SeleniumTestCase{

    private String username;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;

    public AuthIntegrationTest(){
        username = "test";
        password = "integration";
        email = "test@test.com";
        phone = "070000000000";
        firstName = "TestFirstName";
        lastName = "TestLastName";
    }

    @Test(priority = 1, groups="auth")
    public void signin()
    {
        open("/login");
        waitForPageToLoad();

        type("id=email", username);
        type("id=password", password);

        click("//form[@id='login']//input[@type='submit']");
        waitForPageToLoad();

        assertTrue(isTextPresent("Log out"));
    }

    @Test(priority = 2, groups="auth")
    public void logout()
    {
        open("/login");
        waitForPageToLoad();

        type("id=email", username);
        type("id=password", password);

        click("//form[@id='login']//input[@type='submit']");
        waitForPageToLoad();


        click("//a[text()='Log out']");
        waitForPageToLoad();

        assertTrue(isElementPresent("//a[text()='Register']"));
    }

}