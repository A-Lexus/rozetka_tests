package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PageFactoryMainPage {

    WebDriver driver;

    @FindBy(css = "li[menu_id='3361']")
    WebElement smartPhonesANDElectronics;

    @FindBy(css = "li.f-menu-sub-l-i a[href*='/filter/preset=smartfon/']")
    WebElement subMenuSel;

    @FindBy(css = "div.g-i-tile-i-title.clearfix a")
    WebElement itemName;


    public PageFactoryMainPage(WebDriver driver){
        this.driver=driver;
    }



}
