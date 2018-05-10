package ua.com.rozetka;

import Utils.DBManager;
import Utils.MyExcelWriter;
import org.testng.annotations.AfterTest;
import ua.com.rozetka.pages.LeftSideMenu;
import ua.com.rozetka.pages.MainPage;
import ua.com.rozetka.pages.ProductsFromCategoryPage;

import static Utils.WebDriverManager.getDriver;

public class CommonTest {

    protected DBManager db = new DBManager();
    protected MainPage mainPage = new MainPage();
    protected ProductsFromCategoryPage productsFromCategory = new ProductsFromCategoryPage();
    protected MyExcelWriter excelWriter = new MyExcelWriter();


    @AfterTest
    public void postCondition() {
        if (getDriver() != null)
            getDriver().quit();
    }
}
