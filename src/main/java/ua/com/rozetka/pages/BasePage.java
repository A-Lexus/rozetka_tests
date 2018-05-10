package ua.com.rozetka.pages;

import static Utils.PropLoader.getProp;
import static Utils.WebDriverManager.getDriver;

public abstract class BasePage extends BasePO{


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
}
