package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class CreateFreestyleProjectFromMyViewsTest extends BaseTest {

    private static final String NEW_PROJECT = "My new project";

    @Test
        public void testCreate() {

        String projectName = new HomePage(getDriver())
                .clickMyViewsButton()
                .clickCreateJob()
                .enterItemName(NEW_PROJECT)
                .selectFreestyleProject()
                .clickOkToSubmit()
                .clickSaveButton()
                .getProjectName();

       Assert.assertEquals(projectName, NEW_PROJECT);
    }
}

