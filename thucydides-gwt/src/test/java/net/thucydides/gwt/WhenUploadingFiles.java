package net.thucydides.gwt;

import net.thucydides.gwt.pages.GwtShowcaseUploadPage;
import net.thucydides.gwt.widgets.FileToUpload;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class WhenUploadingFiles {

    private GwtShowcaseUploadPage uploadPage;

    @Test
    public void should_upload_a_file_from_the_resources_directory() {

        WebDriver driver = new FirefoxDriver();
        driver.get("http://gwt.google.com/samples1/Showcase/Showcase.html#!CwFileUpload");

        uploadPage = new GwtShowcaseUploadPage(driver);
        uploadPage.waitForRenderedElements(By.xpath("//button"));

        uploadPage.uploadFile("uploads/readme.txt");
        uploadPage.clickOkInAlertWindow();

        driver.quit();
    }

    @Test
    public void should_convert_a_windows_java_path_to_a_proper_windows_path() {
        String javaWindowsPath = "/C:/Users/MyUser/myproject/target/test-classes/documentUpload/somefile.pdf";
        String realWindowsPath= "C:\\Users\\MyUser\\myproject\\target\\test-classes\\documentUpload\\somefile.pdf";

        WebElement field = mock(WebElement.class);

        FileToUpload fileToUpload = new FileToUpload(javaWindowsPath);
        fileToUpload.to(field);

        verify(field).sendKeys(realWindowsPath);
    }


    @Test
    public void should_leave_a_unix_java_path_alone() {
        String unixPath = "/home/myuser/target/test-classes/documentUpload/somefile.pdf";

        WebElement field = mock(WebElement.class);

        FileToUpload fileToUpload = new FileToUpload(unixPath);
        fileToUpload.to(field);

        verify(field).sendKeys(unixPath);
    }

}
