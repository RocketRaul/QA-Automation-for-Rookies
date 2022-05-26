package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ExceptionTest {

    private WebDriver driver;

    @BeforeTest()
    private void setUp(){
//      Create Driver
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();

//        Maximize Window
        driver.manage().window().maximize();

//        Wait
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

    }

    @AfterTest
    private void closeBrowser(){
        driver.quit();
    }

    @Test
    private void notVisibleTest(){
        System.out.println("Test Started");

//        Open Browser
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");

//        Click on Start button
        WebElement startButton = driver.findElement(By.cssSelector("button"));
        startButton.click();

//        Find Message
        WebElement afterClickMessage = driver.findElement(By.xpath("//div[@id='finish']"));

//        Wait until Message Visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try{
            wait.until(ExpectedConditions.visibilityOf(afterClickMessage));
        }catch (TimeoutException e) {
            System.out.println("Timeout Exception Caught: " + e.getMessage());
        }

//        Compare Text
        String expectedMessage = "Hello World!";
        String actualMessage = afterClickMessage.getText();
        Assert.assertEquals(actualMessage,expectedMessage, "The expected Message differs on the actual message");

//        Message Not Visible Yet
//        Assert.assertTrue(afterClickMessage.isDisplayed(),"Message is not Visible as Yet");
    }

    @Test
    private void noSuchElementTest(){
        System.out.println("Test Started");

//        Open Browser
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");

//        Click on Start button
        WebElement startButton = driver.findElement(By.cssSelector("button"));
        startButton.click();

//        Wait until Message Visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='finish']/h4")));
        }catch (Exception e){
            System.out.println("No such Element " + e);
            driver.quit();
        }

//        Find Message
        WebElement afterClickMessage = driver.findElement(By.xpath("//div[@id='finish']/h4"));


//        Compare Text
        String expectedMessage = "Hello World!";
        String actualMessage = afterClickMessage.getText();
        Assert.assertEquals(actualMessage,expectedMessage, "The expected Message differs on the actual message");

//        Message Not Visible Yet
//        Assert.assertTrue(afterClickMessage.isDisplayed(),"Message is not Visible as Yet");
    }

    @Test
    public void staleElementTest(){

//        Go to URL
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");

//        Locate the checkbox
        WebElement checkbox = driver.findElement(By.xpath("//input[@type='checkbox']"));

//        Click Remove Checkbox
        WebElement removeButton = driver.findElement(By.xpath("//button[contains(text(),'Remove')]"));
        removeButton.click();

//        Wait for Checkbox to Disappear
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
//        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOf(checkbox)));
        Assert.assertTrue(wait.until(ExpectedConditions.stalenessOf(checkbox)));

//        Verification Checkbox is not Displayed
//        Assert.assertFalse(checkbox.isDisplayed());

//        Click to Add Checkbox
        WebElement addButton = driver.findElement(By.xpath("//button[contains(text(),'Add')]"));
        addButton.click();

//        Verification that Checkbox Exists.
        checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='checkbox']")));
        Assert.assertTrue(checkbox.isDisplayed());

    }

    @Test
    private void disableElementTest(){

//        Go to URL
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");

//        Click to enable input field
        WebElement button = driver.findElement(By.xpath("//button[contains(text(),'Enable')]"));
        button.click();

//        Create WebElement for Input
        WebElement inputField;

//        Wait for the input to Enable
        WebDriverWait waitForInput = new WebDriverWait(driver,Duration.ofSeconds(10));
        inputField= waitForInput.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='input-example']/input")));
        Assert.assertTrue(inputField.isEnabled(),"Input Field is not Enable");

//        Click and Write Text on the Input field
        inputField.click();
        inputField.sendKeys("Example Text");
        Assert.assertEquals(inputField.getAttribute("value"),"Example Text", "Text different from expected");

    }
}
