package school.redrover;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.page.systemConfiguration.PluginsPage;
import school.redrover.runner.BaseTest;

@Epic("09 Manage Jenkins")
public class PluginTest extends BaseTest {

    @Test
    @Story("US_09.006 Plugin")
    @Description("TC_09.006.01 Check number of update plugin")
    public void testNumberOfUpdatePlugin() {

        int indicatorCount = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openPluginsPage()
                .getUpdateCountFromIndicator();

        int countPluginsFromUpdateTable = new PluginsPage(getDriver())
                .getPluginsCountFromUpdateTable();

        Allure.step("Expected result: Check number of update plugin");
        Assert.assertEquals(indicatorCount, countPluginsFromUpdateTable);

    }

    @Test(dependsOnMethods = "testNumberOfUpdatePlugin")
    @Story("US_09.006 Plugin")
    @Description("TC_09.006.02 Check number of all update plugin")
    public void testNumberOfAllUpdatePlugin() {

        int countAvailablePlugins = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openPluginsPage()
                .getCountAvailablePlugins();

        Allure.step("Expected result: Check number of all update plugin");
        Assert.assertEquals(countAvailablePlugins, 50);

    }

    @Test (dependsOnMethods = "testNumberOfAllUpdatePlugin")
    @Story("US_09.006 Plugin")
    @Description("TC_09.006.03 Search plugin via tag")
    public void testSearchPluginViaTag() {

        int countOfPluginsFound = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openPluginsPage()
                .searchInstalledPlugin("Theme Manager")
                .getCountOfPluginsFound();

        Allure.step("Expected result: Search plugin via tag");
        Assert.assertEquals(countOfPluginsFound, 1);

    }
}
