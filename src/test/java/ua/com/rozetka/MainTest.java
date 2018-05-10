package ua.com.rozetka;

import org.testng.annotations.*;
import ua.com.rozetka.pages.ProductsFromCategoryPage;

import java.io.File;
import java.util.*;

import static Utils.MyExcelWriter.sortHashMapByValues;
import static Utils.MyFileManager.*;
import static Utils.WebDriverManager.*;

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

    mainPage.clickLeftMainMenuCategory("telefony-tv-i-ehlektronika")
            .clickSecondCategory("telefony")
            .leftSideMenu.clickSEOFilter("smartfon");

        ArrayList<String> productsNames = new ArrayList<>();

        int pages = 3;
        int nextPage = 2;
        while (nextPage <= pages) {
            productsNames.addAll(productsFromCategory.getProductsName());
            productsFromCategory.clickPage(nextPage);
            nextPage++;
        }

        String fileName = "rozetkaTest";

        /**To add date of file created use
         * Calendar calendar = Calendar.getInstance();
         * SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
         * String dateOfCreation = "_" + df.format(calendar.getTime());
         * fileName = fileName + dateOfCreation;
         * */


        File outputFile = writeToTXT(productsNames, fileName);
        String recipients = "recipients.txt";
        String outputFilePAth = outputFile.getAbsolutePath();
        sendFileToStatedRecipients(recipients, outputFilePAth);

    }

    @Test
    public void getPowderWithPriceFrom100To300() {

        ProductsFromCategoryPage productsFromCategory
                = mainPage.open()
                .clickLeftMainMenuCategory("tovary-dlya-doma")
                .clickSecondCategory("bytovaya-himiya")
                .clickThirdCategory("sredstva-dlya-stirki");

        HashMap<String, Integer> namesAndPrices = new HashMap<>();
        String productsConstanceName = "Стиральный порошок";

        int pages = 5;
        int nextPage = 2;
        while (nextPage <= pages) {
            HashMap<String, Integer> np = productsFromCategory.getProductsNameANDPrices();
            namesAndPrices.putAll(np);
            productsFromCategory.clickPage(nextPage);
            nextPage++;
        }

        db.sendDataWhichConfirmedStatement(namesAndPrices, productsConstanceName,
                100, 300);

    }

    /*Сценарий 3:
зайти на сайт rozetka.com.ua
перейти в раздел "Телефоны, ТВ и электроника"
перейти в раздел "Телефоны"
перейти в раздел "Смартфоны"
выбрать из первых трех страниц поисковой выдачи название и цены всех девайсов, обозначенных как “Топ продаж”
выбрать из первых пяти страниц поисковой выдачи название и цены всех товаров в диапазоне от 3000 до 6000 гривен
записать полученные результаты в Excel файл в порядке убывания цены на двух разных листах
отправить данный файл по списку рассылки (e-mails из отдельного файла)*/

    @Test
    public void getSmartphoneInRangeOfPrices3000To6000SaveToXLS() {

        mainPage.open();

        mainPage.clickLeftMainMenuCategory("telefony-tv-i-ehlektronika")
                .clickSecondCategory("telefony")
                .leftSideMenu.clickSEOFilter("smartfon");

        HashMap<String, Integer> namesAndPrices = new HashMap<>();
        HashMap<String, HashMap<String, Integer>> xlsDataEntry = new HashMap<>();


        int pages = 3;
        int nextPage = 2;
        while (nextPage <= pages) {
            Map<String, Integer> np = productsFromCategory.getProductsNameANDPrices();
            namesAndPrices.putAll(np);
            productsFromCategory.clickPage(nextPage);
            nextPage++;
        }


        Map<String, Integer> sortedMap = sortHashMapByValues(namesAndPrices);
        Iterator iterator2 = sortedMap.keySet().iterator();
        while(iterator2.hasNext()) {
            String n = iterator2.next().toString();
            System.out.println(n+": "+sortedMap.get(n));
        }


        xlsDataEntry.put("PhonesInRangeFrom3000To6000UAH", namesAndPrices);
        File xlsFile = excelWriter.writeToExcelFile(xlsDataEntry, "FirstExcelFile");
        System.out.println("File is saved to: "+xlsFile.getAbsolutePath());

        sendFileToStatedRecipients("recipients.txt", xlsFile.getAbsolutePath());
    }

    @AfterTest
    public void postCondition() {
        if (getDriver() != null)
            getDriver().quit();
    }

}

