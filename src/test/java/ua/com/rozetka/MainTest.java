package ua.com.rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.MainPage;
import pages.PageFactoryMainPage;
import pages.ThirdCategoryPage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Utils.MyFileManager.sendFileToStatedRecipients;
import static Utils.MyFileManager.writeToTXT;
import static Utils.WebDriverManager.getDriver;

public class MainTest extends CommonTest {



/*Сценарий 1:
● зайти на сайт rozetka.com.ua
● перейти в раздел &quot;Телефоны, ТВ и электроника&quot;
● перейти в раздел &quot;Телефоны&quot;
● перейти в раздел &quot;Смартфоны&quot;
● выбрать из первых трех страниц поисковой выдачи название всех девайсов
● записать полученные результаты в текстовый файл
● отправить данный файл по списку рассылки (e-mails из отдельного файла)*/

    @Test
    public void openPageSaveDevicesNameToTxtFile() throws InterruptedException {

    mainPage.open();

        By by = By.cssSelector("li[menu_id='3361']");
        WebElement firstSection = getDriver().findElement(by);
        Actions actions = new Actions(getDriver());
        actions.moveToElement(firstSection).build().perform();

        JavascriptExecutor je = (JavascriptExecutor) getDriver();
        je.executeScript("document.getElementById('3361').classList.add('hover');");

        By subMenuSel = By.cssSelector("li.f-menu-sub-l-i a[href*='/filter/preset=smartfon/']");
        WebDriverWait w = new WebDriverWait(getDriver(), 2);
        w.until(ExpectedConditions.visibilityOfElementLocated(subMenuSel));
        WebElement subMenu = getDriver()
                .findElement(subMenuSel);
        actions.moveToElement(subMenu).build().perform();
        subMenu.click();

        final By itemNamSel = By.cssSelector("div.g-i-tile-i-title.clearfix a");

        List<WebElement> items;
        ArrayList<String> itemsNames = new ArrayList<>();
        for (int i = 2; i < 4; i++) {
            w.until(ExpectedConditions.visibilityOfElementLocated(itemNamSel));
            items = getDriver().findElements(itemNamSel);
            for (WebElement item : items) {
                String s = item.getText();
                itemsNames.add(s);
                System.out.println(s);
            }

            By xpath = By.xpath("//ul[@name ='paginator']/li[" + i + "]");
            w.until(ExpectedConditions.elementToBeClickable(xpath));
            WebElement paginator = getDriver().findElement(xpath);
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

    @Test
    public void getPowderWithPriceFrom100To300() {

        ThirdCategoryPage pageWithGoods
                = mainPage.open()
                .clickLeftMainMenuCategory("tovary-dlya-doma")
                .clickSecondCategory("bytovaya-himiya")
                .clickThirdCategory("sredstva-dlya-stirki");

        HashMap<String, Integer> namesAndPrices = new HashMap<>();
        String s = "Стиральный порошок";

        int pages = 5;
        int nextPage = 2;
        while (nextPage <= pages) {
            HashMap<String, Integer> np = pageWithGoods.getProductsNameANDPrices();
            pageWithGoods.clickPage(nextPage);
            nextPage++;
        }

        db.sendDataWhichConfirmedStatement(namesAndPrices, "Стиральный порошок",
                100, 300);

    }

    @AfterTest
    public void postCondition() {
        if (getDriver() != null)
            getDriver().quit();
    }

}

