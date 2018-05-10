package ua.com.rozetka.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Utils.WebDriverManager.getDriver;


public class ProductsFromCategoryPage extends BasePage {

    private static final String RES_PROD_PAGE_URL = "/";

    public ProductsFromCategoryPage() {
        super(RES_PROD_PAGE_URL);
    }

    public ProductsFromCategoryPage clickPage(int pageIndex) {
        final By pageSel = By.cssSelector("#page" + pageIndex);
        WebDriverWait w = new WebDriverWait(getDriver(), 20);
        scrollToElement(pageSel);
        getDriver().findElement(pageSel).click();
        //w.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#page" + pageIndex+" a")));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ProductsFromCategoryPage();
    }

    final By itemNameSel = By.cssSelector("#catalog_goods_block .g-i-tile-i-title");
    final By priceSel = By.cssSelector("#catalog_goods_block .g-price-uah");

    public HashMap<String, Integer> getProductsNameANDPrices() {
        HashMap<String, Integer> np = new HashMap<>();

        List<WebElement> names = getDriver().findElements(itemNameSel);
        List<WebElement> prices = getDriver().findElements(priceSel);

        for (int i = 0; i < names.size(); i++) {
            String n = names.get(i).getText();
            int p = toInteger(prices.get(i).getText());
            np.put(n, p);
        }
        return np;
    }

    public ArrayList<String> getProductsName() {
        return getElementsText(itemNameSel);
    }
}
