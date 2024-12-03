package school.redrover;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.page.PluginsPage;
import school.redrover.runner.BaseTest;

public class PluginTest extends BaseTest {

    @Test
    public void testNumberOfUpdatePlugin() {

        int indicatorCount = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openPluginsPage()
                .getUpdateCountFromIndicator();

        int countPluginsFromUpdateTable = new PluginsPage(getDriver())
                .getPluginsCountFromUpdateTable();

        Assert.assertEquals(indicatorCount, countPluginsFromUpdateTable);

    }

    @Test(dependsOnMethods = "testNumberOfUpdatePlugin")
    public void testNubmerOfAllUpdatePlugin() {

        int countAvailablePlugins = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openPluginsPage()
                .getCountAvailablePlugins();

        Assert.assertEquals(countAvailablePlugins, 50);

    }

    @Test (dependsOnMethods = "testNubmerOfAllUpdatePlugin")
    public void testSearchPluginViaTag() {

        int countOfPluginsFound = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openPluginsPage()
                .searchInstalledPlugin("Theme Manager")
                .getCountOfPluginsFound();

        Assert.assertEquals(countOfPluginsFound, 1);

    }

}
