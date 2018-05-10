package ua.com.rozetka.pages;

import org.openqa.selenium.By;

public class MainPage extends BasePage{

    private static final String REL_URL = "/";

    public MainPage() {
        super(REL_URL);
    }

    public FirstCategoryPage clickLeftMainMenuCategory(String s) {
        By categorySel = By.cssSelector("a[name='fat_menu_link'][href*='"+s+"']");
        waitUntilElementIsClickable(categorySel).click();
        return new FirstCategoryPage();
    }

    public LeftSideMenu leftSideMenu = new LeftSideMenu();

    public MainPage open() {
        super.open();
        return new MainPage();
    }


}
