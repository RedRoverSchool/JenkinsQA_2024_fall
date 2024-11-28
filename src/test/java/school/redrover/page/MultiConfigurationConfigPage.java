package school.redrover.page;

<<<<<<< HEAD
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseProjectPage;

public class MultiConfigurationConfigPage extends BaseProjectPage {
=======
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseConfigPage;
import school.redrover.page.base.BaseProjectPage;

public class MultiConfigurationConfigPage extends BaseConfigPage {

>>>>>>> e7357be367a870d0f61f746714286a6795ede335
    public MultiConfigurationConfigPage(WebDriver driver) {
        super(driver);
    }

<<<<<<< HEAD
    By getHomePageIcon = By.xpath("//a[@id='jenkins-home-link']");


    public HomePage goHome() {
        getDriver().findElement(getHomePageIcon).click();

        return new HomePage(getDriver());
    }


=======
    @Override
    protected MultiConfigurationProjectPage createProjectPage() {

        return new MultiConfigurationProjectPage(getDriver());
    }
>>>>>>> e7357be367a870d0f61f746714286a6795ede335
}
