package winapp;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.net.URL;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.windows.WindowsDriver;

public class NotepadLaunch {

	WindowsDriver driver;
	@BeforeClass
	
	
	public void launchNotepad() throws IOException, InterruptedException {
		Desktop desk = Desktop.getDesktop();
		desk.open(new File ("C:\\Program Files (x86)\\Windows Application Driver\\WinAppDriver.exe"));
		Thread.sleep(1000);
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("app", "C:\\Windows\\System32\\notepad.exe");
		caps.setCapability("platformName", "Windows");
        caps.setCapability("deviceName", "WindowsPC");

		driver = new WindowsDriver(new URL("http://127.0.0.1:4723"),caps);
		System.out.println(driver.getSessionId());
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		captureScreenshot("Step1-Launching Notepad");
	}
		public void captureScreenshot(String fileName) {
		    File screenshot = driver.getScreenshotAs(OutputType.FILE);
		    File destination = new File(System.getProperty("user.home") + "\\Desktop\\Screenshots\\" + fileName + ".png");
		    screenshot.renameTo(destination);
		    System.out.println("Screenshot saved at: " + destination.getAbsolutePath());
		}
  
	@Test
	public void editFile() {
		driver.findElementByName("Text editor").sendKeys("Hello World");
		captureScreenshot("Step2-Typing text in Notepad");
		driver.findElementByName("File").click();
		captureScreenshot("Step3-Clicking File Menu");
		driver.findElementByName("Save").click();
		captureScreenshot("Step4-Saving Text");
		driver.findElementByAccessibilityId("1001").sendKeys("New File");
		captureScreenshot("Step5-Naming the File ");
		driver.findElementByAccessibilityId("1").click();
		captureScreenshot("Step6-Saving the File with Name");

		String userHome = System.getProperty("user.home");
	    File savedFile = new File(userHome + "\\Desktop\\New File.txt");

	    if (savedFile.exists()) {
	        System.out.println(" File successfully saved: " + savedFile.getAbsolutePath());
	    } else {
	        System.out.println(" File wasn't saved: " + savedFile.getAbsolutePath());
	    }
	}
		
	
	@AfterClass
	public void closeUp() throws IOException {
		driver.quit();
		Runtime.getRuntime().exec("taskkill /F /IM WinAppDriver.exe");
			
	}
}
