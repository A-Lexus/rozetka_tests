/*
package Utils;

import io.github.bonigarcia.wdm.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.DriverManager;
import java.util.concurrent.TimeUnit;

public class WebDriverManager {


    private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    public static WebDriver getDriver() {
        if (driver.get() == null) setDriver();
        return driver.get();
    }

    private static void setDriver() {
        try {
            DriverManager..set(configureDriver(init()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Remote URL is not set");
        }
    }

    private static String browserName;
    private static String browserType;


    private static WebDriver init() throws MalformedURLException {
        browserType = PropLoader.getProp("driverType");
        if (browserType.equalsIgnoreCase("remote"))
            return initRemoteDriver();
        else if (browserType.equalsIgnoreCase("local"))
            return initLocalDriver();
        else
            return initRemoteDriver();
    }

    private static final String REMOTE_URL = PropLoader.getProp("remoteServerURL");

    private static WebDriver initRemoteDriver() throws MalformedURLException {

        browserName = PropLoader.getProp("browser");
        if (browserName != null) {
            System.out.println("Current browser is: " +
                    browserName.toUpperCase());
        } else {
            browserName = "chrome";
            System.out.println("Browser is not stated. Default stated browser is: " +
                    browserName.toUpperCase());
        }

        switch (browserName) {
            case "chrome":
                ChromeDriverManager.getInstance().setup();
                //System.setProperty("webdriver.chrome.driver", getBrowserDriverPath());
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("start-maximized");
                return new RemoteWebDriver(new URL(REMOTE_URL),
                        DesiredCapabilities.chrome());
            case "firefox":
                FirefoxDriverManager.getInstance().setup();
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                capabilities.setCapability("marionette", true);
                return new RemoteWebDriver(new URL(REMOTE_URL),
                        DesiredCapabilities.firefox());
            case "opera":
                OperaDriverManager.getInstance().setup();
                OperaOptions options = new OperaOptions();
                options.setBinary(new File("C:\\Program Files\\Opera\\47.0.2631.80\\opera.exe"));
                return new RemoteWebDriver(new URL(REMOTE_URL),
                        DesiredCapabilities.operaBlink());
        }
        return new RemoteWebDriver(new URL(REMOTE_URL),
                DesiredCapabilities.chrome());
    }


    private static WebDriver initLocalDriver() {

        browserName = PropLoader.getProp("browser");
        if (browserName != null) {
            System.out.println("Current browser is: " +
                    browserName.toUpperCase());
        } else {
            browserName = "chrome";
            System.out.println("Browser is not stated. Default stated browser is: " +
                    browserName.toUpperCase());
        }


        switch (browserName) {
            case "chrome":
                ChromeDriverManager.getInstance().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("start-maximized");
                return new ChromeDriver(chromeOptions);
            case "firefox":
                FirefoxDriverManager.getInstance().setup();
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                capabilities.setCapability("marionette", true);
                return new FirefoxDriver(capabilities);
            case "opera":
                OperaDriverManager.getInstance().setup();
                OperaOptions options = new OperaOptions();
                options.setBinary(new File("C:\\Program Files\\Opera\\47.0.2631.80\\opera.exe"));
                return new OperaDriver(options);
            case "phantom":
                PhantomJsDriverManager.getInstance().setup();
                return new PhantomJSDriver();
            case "edge":
                EdgeDriverManager.getInstance().setup();
                return new EdgeDriver();
            case "ie":
                InternetExplorerDriverManager.getInstance().setup();
                return new InternetExplorerDriver();
        }
        return new ChromeDriver();
    }

    private static String getBrowserDriverPath() {
        if (browserName.equalsIgnoreCase("firefox"))
            browserName = "gecko";

        ClassLoader classLoader = DriverManager.class.getClassLoader();
        String browserDriverFilePath = new File(classLoader.getResource("./drivers/" + browserName
                + "driver.exe").getFile()).getAbsolutePath();
        return browserDriverFilePath;
    }

    private static WebDriver configureDriver(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(new Long(PropLoader.getProp("implicitWait")),
                TimeUnit.SECONDS);
        return driver;
    }

}


*/
