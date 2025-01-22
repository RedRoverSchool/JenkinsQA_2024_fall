package school.redrover.page;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

import java.util.List;

public class AboutPage extends BasePage<AboutPage> {

    @FindBy(xpath = "//*[@id='main-panel']/p")
    private WebElement aboutDescription;

    @FindBy(xpath = "//*[@id='main-panel']/div[4]/table/tbody/tr")
    private List<WebElement> mavenDependencies;

    public AboutPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get description from About page")
    public String getAboutDescription() {

        return aboutDescription.getText();
    }

    @Step("Count number of Maven dependencies")
    public int getNumberOfMavenDependencies() {

        return mavenDependencies.size();
    }
}
