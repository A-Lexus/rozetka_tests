package pages;

import org.openqa.selenium.By;

public class SecondCategoryPage extends BasePage{

    private static final String REL_URL = "/";

    public SecondCategoryPage() {
        super(REL_URL);
    }


    public ThirdCategoryPage clickThirdCategory(String thirdCatName) {
        By subMenu = By.cssSelector("p.pab-h3 a[href*='"+thirdCatName+"']");
        waitUntilElementIsClickable(subMenu).click();
        return new ThirdCategoryPage();
    }
}
