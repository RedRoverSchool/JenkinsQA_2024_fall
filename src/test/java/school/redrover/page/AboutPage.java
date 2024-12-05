package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BasePage;

public class AboutPage extends BasePage {

    public AboutPage(WebDriver driver) {
        super(driver);
    }

    public String getAboutDescription() {

        return getDriver().findElement(By.xpath("//*[@id='main-panel']/p")).getText();
    }

    public int getNumberOfMavenDependencies() {

        return getDriver().findElements(By.xpath("//*[@id='main-panel']/div[4]/table/tbody/tr")).size();
    }
}
