package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Utils.PropLoader.getProp;
import static Utils.WebDriverManager.getDriver;

public abstract class BasePage {


    private final String REL_URL;
    private String BASE_URL;
    private final String LANG_URL;

    public BasePage(String url) {
        REL_URL = url;
        BASE_URL = getProp("baseUrl") == null ? "Base URL is not stetted!" : getProp("baseUrl");
        LANG_URL = (getProp("language").equalsIgnoreCase("UA")) ?
                "/" + getProp("language").toLowerCase() : "";
    }

    private BasePage open(String url) {
        getDriver().get(url);
        return this;
    }



    public BasePage open() {
        String s = BASE_URL + LANG_URL + REL_URL;
        open(s);
        return this;
    }


    public static WebElement waitUntilElementIsClickable(By by) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        WebElement el = getDriver().findElement(by);
        return wait.until(ExpectedConditions.elementToBeClickable(el));
    }

    public static Boolean waitUntilAttribute(By by, String attr, String val){
        WebDriverWait wait = new WebDriverWait(getDriver(), 20);
        return wait.until(ExpectedConditions.attributeContains(by, "class", "hidden"));
    }


    public static void addAttr(String selector, String attrName, String value) {
        ((JavascriptExecutor) getDriver()).
                executeScript("$(" + selector + ").attr('" + attrName + "', '" + value + "');");
    }

    public static void scrollToElement(By by) {
        WebElement element = getDriver().findElement(by);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView();"
                , element);
    }

    public static Integer toInteger(String str) {
        Pattern p = Pattern.compile("(-?[0-9]+(?:[,.]{1}[0-9]+)?)");
        Matcher matcher = p.matcher(str.replaceAll(" ", ""));
        int price = 0;
        if (matcher.find())
            price = Integer.parseInt(matcher.group(1));
        return price;
    }

}
