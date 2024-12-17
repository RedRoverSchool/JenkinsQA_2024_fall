package school.redrover.page.freestyle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

public class FreestyleBuildStatusPage extends BasePage {

    @FindBy(xpath = "//div[@id='main-panel']/table//ul//li[3]")
    private WebElement totalBuildTime;

    public FreestyleBuildStatusPage(WebDriver driver) {
        super(driver);
    }

    public int getLastBuildTotalTime(){
        String [] array = totalBuildTime.getText().split(" ");
        return Integer.parseInt(array[0]);
    }
}
