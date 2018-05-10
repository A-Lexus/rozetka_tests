package ua.com.rozetka.pages;

import org.openqa.selenium.By;

public class LeftSideMenu extends BasePO{


    public DropDownMenu hoverCoursorLeftSideMenuCategoryClick(String catName){
        final By mainCatSel = By.cssSelector("a[href*='"+catName+"'][name='fat_menu_link']");
        waitUntilElementIsClickable(mainCatSel).click();
        return new DropDownMenu();
    }

    public ProductsFromCategoryPage clickSEOFilter(String seoFilter){
        By seoFilter_sel = By.cssSelector("a[href*='preset="+seoFilter+"'].m-cat-l-i-title-link ");
        waitUntilElementIsClickable(seoFilter_sel).click();
        return new ProductsFromCategoryPage();

    }

    class DropDownMenu{}



}
