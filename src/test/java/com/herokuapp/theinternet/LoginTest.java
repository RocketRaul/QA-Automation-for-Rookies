package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTest {

    private WebDriver driver;
    public String url = "https://the-internet.herokuapp.com/login";

    @BeforeMethod(alwaysRun = true)
    private void setUp(){
//        Create Driver
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();

//        Open Test Page
        driver.get(url);
        System.out.println("Home Page Opened");

//        Maximize Browser Window
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    private void closeBrowser(){
        //      Close Browser
        driver.quit();
    }

    @Test(priority = 1, groups = {"positive","smokeTest"})
    public void positiveLoginTest() {

        System.out.println("Starting login Test");


//      Sleep 3 seconds
        sleep(2000);

//      Enter Username
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("tomsmith");

//      Sleep 3 seconds
        sleep(2000);


//      Enter Password
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("SuperSecretPassword!");

//      Sleep 3 seconds
        sleep(2000);


//      Click Login Button
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();

//      Sleep 3 seconds
        sleep(2000);


//      Verifications;
//      New Url
        String expectedURL = "https://the-internet.herokuapp.com/secure";
        String actualURL = driver.getCurrentUrl();

        Assert.assertEquals(actualURL, expectedURL, "Actual URL different to Expected");

//      Logout Button is Visible
        WebElement logOutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
        Assert.assertTrue(logOutButton.isDisplayed(), "LogOut Button is not Displayed");

//      Successful login message
        //WebElement success_message = driver.findElement(By.cssSelector("#flash"));
        WebElement success_message = driver.findElement(By.xpath("//div[@id='flash']"));
        String expectedMessage = "You logged into a secure area!";
        String actualMessage = success_message.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Actual Message does not Contain Expected Message.\n"
                + "Actual Message is " + actualMessage + " and Expected Message is " + expectedMessage);

    }

    @Parameters({ "username","password","expectedMessage" })
    @Test(priority = 2, groups = {"negative","smokeTest"})
    public void negativeLoginTest(String username, String password, String expectedMessage){

        System.out.println("Negative Test Started");

//        Enter Wrong Username
        WebElement wrongUsername = driver.findElement(By.id("username"));
        wrongUsername.sendKeys(username);

//        Enter Correct Password
        WebElement wrongPassword = driver.findElement(By.id("password"));
        wrongPassword.sendKeys(password);

//        Click Login Button
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();


//        Validations

//        Same URL
        String currentURL = driver.getCurrentUrl();

        Assert.assertEquals(currentURL,url, "Expected URL different From Actual URL");

//        Invalid Username
        WebElement invalidUsername = driver.findElement(By.xpath("//div[@id='flash']"));
        String actualMessage = invalidUsername.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage),"Actual Message different from Expected\n" +
                "Expected Message " + expectedMessage + " and Actual Message " + actualMessage);
    }

    private void sleep(long m) {
        try{
            Thread.sleep(m);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
