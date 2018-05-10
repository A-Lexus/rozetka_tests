package ua.com.rozetka.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Utils.WebDriverManager.getDriver;

public abstract class BasePO {

    public static WebElement waitUntilElementIsClickable(By by) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        WebElement el = getDriver().findElement(by);
        return wait.until(ExpectedConditions.elementToBeClickable(el));
    }

    public static List<WebElement> waitUntilElementsIsVisible(By by) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 30);
        WebElement el = getDriver().findElement(by);
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
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


    public static ArrayList<String> getElementsText(By by){
        List<WebElement> els = waitUntilElementsIsVisible(by);
        ArrayList<String> t = new ArrayList<>();
        for(WebElement el:els){
            t.add(el.getText());
        }
        return t;
    }


}
