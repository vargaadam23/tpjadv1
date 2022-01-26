package org.tpjad.integration;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.testng.annotations.Test;

public class BookIntegrationTest extends  SeleniumTestCase{

    private String username;
    private String password;
    private String name;
    private String author;
    private String genre;
    private String price;

    public BookIntegrationTest(){
        username = "test";
        password = "integration";
        name = "NameTest";
        author = "AuthorTest";
        genre = "Genre";
        price = "20";
    }

    private void addBookMethod(){
        open("/login");
        waitForPageToLoad();

        type("id=email", username);
        type("id=password", password);

        click("//form[@id='login']//input[@type='submit']");
        waitForPageToLoad();

        open("/bookadmin");

        type("id=name", name);
        type("id=author", author);
        type("id=genre", genre);
        type("id=price", price);
        type("id=image", "toms.jpg");

        click("//form[@id='book']//input[@type='submit']");
        waitForPageToLoad();

        open("/booklist");
    }


    @Test(priority = 1, groups="book")
    public void addBook()
    {
        addBookMethod();

        assertTrue(isElementPresent("//h4[text()='"+author+" - "+name+"']"));
    }

//    @Test(priority = 2, groups="book")
//    public void logout()
//    {
//        addBookMethod();
//
//        assertTrue(isElementPresent("//a[text()='Register']"));
//    }

}