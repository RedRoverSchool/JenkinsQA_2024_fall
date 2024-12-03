package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class PipelineRenameTest extends BaseTest {

    private final static String PROJECT_NAME1 = "FirstPipelineProject";
    private final static String PROJECT_NAME2 = "SecondPipelineProject";

    @Test
    public void testErrorRenameWithExistingName() {
        TestUtils.createPipeline(this, PROJECT_NAME1);
        TestUtils.createPipeline(this, PROJECT_NAME2);

       String actualErrorMessage = new HomePage(getDriver())
               .goToPipelineRenamePageViaDropdown(PROJECT_NAME1)
               .cleanInputFieldAndTypeName(PROJECT_NAME2)
               .clickRenameButtonAndRedirectToErrorPage()
               .getErrorMessage();

       Assert.assertEquals(actualErrorMessage, "The name “" + PROJECT_NAME2 + "” is already in use.");
    }
}
