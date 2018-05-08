package ua.com.rozetka;

import Utils.DBManager;
import org.testng.annotations.AfterTest;
import pages.MainPage;

import static Utils.WebDriverManager.getDriver;

public class CommonTest {

    protected DBManager db = new DBManager();
    protected MainPage mainPage = new MainPage();

    @AfterTest
    public void postCondition() {
        if (getDriver() != null)
            getDriver().quit();
    }
}
