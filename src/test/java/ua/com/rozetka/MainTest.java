package ua.com.rozetka;

import Utils.DBManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Utils.MyFileManager.sendFileToStatedRecipients;
import static Utils.MyFileManager.writeToTXT;

public class MainTest {

/*Сценарий 1:
● зайти на сайт rozetka.com.ua
● перейти в раздел &quot;Телефоны, ТВ и электроника&quot;
● перейти в раздел &quot;Телефоны&quot;
● перейти в раздел &quot;Смартфоны&quot;
● выбрать из первых трех страниц поисковой выдачи название всех девайсов
● записать полученные результаты в текстовый файл
● отправить данный файл по списку рассылки (e-mails из отдельного файла)*/

    WebDriver driver;

    @Test
    public void openPageSaveDevicesNameToTxtFile() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "/home/alexuspc/documents/ideaProjects/rozetka-tests/src/main/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);

        driver.get("https://rozetka.com.ua/");

        By by = By.cssSelector("li[menu_id='3361']");
        WebElement firstSection = driver.findElement(by);
        Actions actions = new Actions(driver);
        actions.moveToElement(firstSection).build().perform();

        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("document.getElementById('3361').classList.add('hover');");

        By subMenuSel = By.cssSelector("li.f-menu-sub-l-i a[href*='/filter/preset=smartfon/']");
        WebDriverWait w = new WebDriverWait(driver, 2);
        w.until(ExpectedConditions.visibilityOfElementLocated(subMenuSel));
        WebElement subMenu = driver
                .findElement(subMenuSel);
        actions.moveToElement(subMenu).build().perform();
        subMenu.click();

        final By itemNamSel = By.cssSelector("div.g-i-tile-i-title.clearfix a");

        List<WebElement> items;
        ArrayList<String> itemsNames = new ArrayList<>();
        for (int i = 2; i < 4; i++) {
            w.until(ExpectedConditions.visibilityOfElementLocated(itemNamSel));
            items = driver.findElements(itemNamSel);
            for (WebElement item : items) {
                String s = item.getText();
                itemsNames.add(s);
                System.out.println(s);
            }

            By xpath = By.xpath("//ul[@name ='paginator']/li[" + i + "]");
            w.until(ExpectedConditions.elementToBeClickable(xpath));
            WebElement paginator = driver.findElement(xpath);
            System.out.println(xpath);
            paginator.click();
        }


        String fileName = "rozetkaTest";

        /**To add date of file created use
         * Calendar calendar = Calendar.getInstance();
         * SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
         * String dateOfCreation = "_" + df.format(calendar.getTime());
         * fileName = fileName + dateOfCreation;*/

        File outputFile = writeToTXT(itemsNames, fileName).getAbsoluteFile();
        String recipients = "recipients.txt";
        String outputFilePAth = outputFile.getAbsolutePath();
        sendFileToStatedRecipients(recipients, outputFilePAth);

    }


/*Сценарий 2:
развернуть локально БД, например MySQL
зайти на сайт rozetka.com.ua
перейти в раздел "Товары для дома"
перейти в раздел "Бытовая химия"
перейти в раздел "Для стирки"
перейти в раздел "Порошки для стирки"
выбрать из первых пяти страниц поисковой выдачи название и цены всех товаров в диапазоне от 100 до 300 гривен
записать полученные результаты в базу данных */

    @Test
    public void getPowderNamePriceInsertToDB() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "/home/alexuspc/documents/ideaProjects/rozetka-tests/src/main/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);


        driver.get("https://rozetka.com.ua/");
        String s = "стиральный порошок";

        final By firstMenuCat = By.cssSelector("a[name='fat_menu_link'][href*='tovary-dlya-doma']");
        waitUntilClicable(firstMenuCat).click();
        final By secondMenuCat =
                By.cssSelector("p.pab-h3 a[href*='bytovaya-himiya']");
        waitUntilClicable(secondMenuCat).click();
        final By thirdMenuCat =
                By.cssSelector("p.pab-h3 a[href*='sredstva-dlya-stirki']");
        waitUntilClicable(thirdMenuCat).click();

        HashMap<String, Integer> itemsWithPrice = new HashMap<>();

        for (int i = 2; i < 6; i++) {
            final By pageSel = By.cssSelector("#navigation_block #page" + i);
            final By itemNameSel = By.cssSelector("#catalog_goods_block .g-i-tile-i-title");
            final By priceSel = By.cssSelector("#catalog_goods_block .g-price-uah");

            waitUntilClicable(itemNameSel);
            List<WebElement> itemsName = driver.findElements(itemNameSel);
            waitUntilClicable(priceSel);
            List<WebElement> prices = driver.findElements(priceSel);

            for (int j = 0; j < itemsName.size(); j++) {

                String replace = prices.get(j).getText();
                int price = toInteger(replace);
                String name = itemsName.get(j).getText();
                if (name.toLowerCase().contains("cтиральный порошок") && (price >= 100 || price <= 300))
                    //itemsWithPrice.put(name, price);
                    db.saveItemAndPriceToDB(name, price);
            }
            waitUntilClicable(pageSel).click();
            Thread.sleep(5000);
        }
    }
    DBManager db = new DBManager();

    public Integer toInteger(String str) {
        Pattern p = Pattern.compile("(-?[0-9]+(?:[,.]{1}[0-9]+)?)");
        Matcher matcher = p.matcher(str.replaceAll(" ", ""));
        //DecimalFormat df = new DecimalFormat("#.##");
        int price = 0;
        if (matcher.find())
            price = Integer.parseInt(matcher.group(1));
        return price;
    }

    public WebElement waitUntilClicable(By by) {
        scrollToElement(by);
        WebDriverWait w =
                new WebDriverWait(driver, 20);
        return w.until(ExpectedConditions.elementToBeClickable(by));

    }

    public void scrollToElement(By by) {
        WebElement element = driver.findElement(by);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public Boolean waitForAttr(By by, String attr, String val) {
        WebDriverWait w =
                new WebDriverWait(driver, 20);
        return w.until(ExpectedConditions.attributeContains(by, attr, val));

    }

    @AfterTest
    public void postCondition() {
        if (driver != null)
            driver.quit();
    }
}

