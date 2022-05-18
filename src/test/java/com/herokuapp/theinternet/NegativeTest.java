package com.herokuapp.theinternet;

import org.checkerframework.common.reflection.qual.NewInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NegativeTest {

    public String url = "https://the-internet.herokuapp.com/login";

    @Parameters({ "username","password","expectedMessage" })
    @Test(priority = 2, groups = {"negative","smokeTest"})
    public void negativeLoginTest(String username, String password, String expectedMessage){

        System.out.println("Negative Test Started");

//        Create Driver
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

//        Open Test Page
        driver.get(url);
        System.out.println("Home Page Opened");

//        Maximize Browser Window
        driver.manage().window().maximize();

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

//        Close Explorer
        driver.quit();

    }
/*    @Test(priority = 1)
    public void incorrectUsername(){

        System.out.println("Negative Test Started");

//        Create Driver
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

//        Open Test Page
        driver.get(url);
        System.out.println("Home Page Opened");

//        Maximize Browser Window
        driver.manage().window().maximize();

//        Enter Wrong Username
        WebElement wrongUsername = driver.findElement(By.id("username"));
        wrongUsername.sendKeys("incorrectUsername");

//        Enter Correct Password
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("SuperSecretPassword!");

//        Click Login Button
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();


//        Validations

//        Same URL
        String currentURL = driver.getCurrentUrl();

        Assert.assertEquals(currentURL,url, "Expected URL different From Actual URL");

//        Invalid Username
        WebElement invalidUsername = driver.findElement(By.xpath("//div[@id='flash']"));
        String expectedMessage = "Your username is invalid!";
        String actualMessage = invalidUsername.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage),"Actual Message different from Expected\n" +
                "Expected Message " + expectedMessage + " and Actual Message " + actualMessage);

//        Close Explorer
        driver.quit();

    }

    @Test(priority = 2)
    public void incorrectPassword(){
        System.out.println("Invalid Password Test Started");

//        Create Driver
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        WebDriver driver2 = new ChromeDriver();

//        Open Home Page
        driver2.get(url);
        System.out.println("Home Page Opened");

//        Maximize Window
        driver2.manage().window().maximize();

//        Enter Valid Username
        WebElement validUsername = driver2.findElement(By.id("username"));
        validUsername.sendKeys("tomsmith");

//        Enter Invalid Password
        WebElement invalidPassword = driver2.findElement(By.id("password"));
        invalidPassword.sendKeys("invalidPassword");

//        Click Login Button
        WebElement loginButton = driver2.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();

//        Validations

//        Invalid Password Message
        String expectedMessage ="Your password is invalid!\n×";
        WebElement invalidPasswordMessage = driver2.findElement(By.xpath("//div[@id='flash']"));
        String actualMessage = invalidPasswordMessage.getText();
        Assert.assertEquals(actualMessage,expectedMessage,"The actual Message and the expected Message are Different");

        sleep(4000);


//        Close Browser
        driver2.quit();

    }
*/
    private void sleep(long m) {
        try{
            Thread.sleep(m);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

