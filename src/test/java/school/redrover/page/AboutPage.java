package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

import java.util.List;

public class AboutPage extends BasePage {

    @FindBy(xpath = "//*[@id='main-panel']/p")
    private WebElement aboutDescription;

    @FindBy(xpath = "//*[@id='main-panel']/div[4]/table/tbody/tr")
    private List<WebElement> mavenDependencies;

    public AboutPage(WebDriver driver) {
        super(driver);
    }

    public String getAboutDescription() {

        return aboutDescription.getText();
    }

    public int getNumberOfMavenDependencies() {

        return mavenDependencies.size();
    }
}
