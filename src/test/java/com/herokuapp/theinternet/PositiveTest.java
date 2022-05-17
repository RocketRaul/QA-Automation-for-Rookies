package com.herokuapp.theinternet;

import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer11_OmitComments;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositiveTest {
    @Test
    public void loginTest(){

        System.out.println("Starting login Test");

//      Create Driver
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

//      Sleep 3 seconds
        sleep(3000);

//      Open Test page
        String url = "https://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("Page is opened");


//      Maximize Browser Window
        driver.manage().window().maximize();

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
        String actualURL =driver.getCurrentUrl();

        Assert.assertEquals(actualURL,expectedURL,"Actual URL different to Expected");

//      Logout Button is Visible
        WebElement logOutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
        Assert.assertTrue(logOutButton.isDisplayed(),"LogOut Button is not Displayed");

//      Successful login message
        //WebElement success_message = driver.findElement(By.cssSelector("#flash"));
        WebElement success_message = driver.findElement(By.xpath("//div[@id='flash']"));
        String expectedMessage = "You logged into a secure area!";
        String actualMessage = success_message.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage),"Actual Message does not Contain Expected Message.\n"
        +"Actual Message is "+ actualMessage+ " and Expected Message is " + expectedMessage);

//      Close Browser
        driver.quit();

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
