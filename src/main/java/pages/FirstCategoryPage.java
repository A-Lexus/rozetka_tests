package pages;

import org.openqa.selenium.By;

public class FirstCategoryPage extends BasePage {

    private static final String REL_URL = "/";

    public FirstCategoryPage() {
        super(REL_URL);
    }

    public SecondCategoryPage clickSecondCategory(String subMenuName){
        By subMenu = By.cssSelector("p.pab-h3 a[href*='"+subMenuName+"']");
        waitUntilElementIsClickable(subMenu).click();
        return new SecondCategoryPage();
    }


}
