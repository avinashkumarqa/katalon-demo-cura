package KatalonDemoCura;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MakeAnAppointment {

    WebDriver driver;
    WebElement makeAppointmentButton;
    WebElement userName;
    WebElement password;
    WebElement loginButton;
    WebElement facilityDropdown;
    WebElement medicareRadioButton;
    WebElement medicaidRadiobutton;
    WebElement visitDate;
    WebElement bookAppointmentButton;
    WebElement goToHomeButton;

    @BeforeSuite
    public void navigateToThePage(){

            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;

            //go to the URL
            driver.get("https://katalon-demo-cura.herokuapp.com/");
            String currentURL = driver.getCurrentUrl();

            Assert.assertEquals(currentURL,"https://katalon-demo-cura.herokuapp.com/","URL does not matched");
    }

    @Test
    public void loginWithCredential() throws InterruptedException {

           makeAppointmentButton = driver.findElement(By.xpath("//a[@id='btn-make-appointment']"));

           makeAppointmentButton.click();
           Thread.sleep(3000);
           userName = driver.findElement(By.xpath("//input[@id='txt-username']"));
           password = driver.findElement(By.xpath("//input[@id='txt-password']"));
           loginButton = driver.findElement(By.xpath("//button[@id='btn-login']"));

           //Enter Username and password
           userName.sendKeys("John Doe");
           password.sendKeys("ThisIsNotAPassword");

           loginButton.click();
           Thread.sleep(3000);

    }

    @Test
    public void makeAnAppointment(){

        Date today = new Date();

        //select facility
        facilityDropdown = driver.findElement(By.xpath("//select[@id='combo_facility']"));
        medicaidRadiobutton = driver.findElement(By.xpath("//input[@value='Medicaid']"));
        visitDate = driver.findElement(By.xpath("//input[@id='txt_visit_date']"));


        bookAppointmentButton = driver.findElement(By.xpath("//button[@id='btn-book-appointment']"));
        Select fac = new Select(facilityDropdown);
        fac.selectByVisibleText("Seoul CURA Healthcare Center");

        //Healthcare program
        medicaidRadiobutton.click();

        //Select Visit Date
        visitDate.sendKeys(today.toString());

        //Click on Book Appointment
        bookAppointmentButton.click();

    }

    @Test(dependsOnMethods = "makeAnAppointment")
    public void verifyAppointmentConfirmation(){

         goToHomeButton = driver.findElement(By.xpath("//a[@class='btn btn-default']"));

         //Verify that Go to HomePage button is displayed
        Assert.assertEquals(true , goToHomeButton.isDisplayed() , "Go to Homepage button not present");

    }

    @AfterSuite
    public void closeTheBrowser(){
        driver.quit();
    }

}//class
