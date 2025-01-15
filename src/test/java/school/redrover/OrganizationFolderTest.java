package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class OrganizationFolderTest extends BaseTest {

    private static final String ORGANIZATION_FOLDER_NAME = "OrganizationFolderName";
    private static final String DISPLAY_NAME = "DisplayName";
    private static final String DESCRIPTION = "Description";
    private static final String NEW_DISPLAY_NAME = "NewNameOrganizationFolder";
    private static final String NEW_DESCRIPTION = "NewDescription";
    private static final String MIN_LENGTH_NAME = "a";

    @Test
    @Epic("00 New Item")
    @Story("US_00.007 | New item > Create Organization Folder")
    @Description("TC_00.007.01 Create 'New Item' of type 'Organization Folder'")
    public void testCreate() {
        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .gotoHomePage()
                .getItemList();

        Allure.step(String.format("Organization folder is created with name '%s'", ORGANIZATION_FOLDER_NAME));
        Assert.assertListContainsObject(projectList, ORGANIZATION_FOLDER_NAME,
                "Project is not created");
    }

    @Test(dependsOnMethods = "testCreate")
    @Epic("00 New Item")
    @Story("US_00.007 | New item > Create Organization Folder")
    @Description("TC_00.007.03 Add 'Display Name' to the created project of type 'Organization Folder'")
    public void testAddDisplayName() {
        String displayName = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickConfigure()
                .setDisplayName(DISPLAY_NAME)
                .clickSaveButton()
                .getItemName();

        Allure.step(String.format("'%s' is added to '%s'", DISPLAY_NAME, ORGANIZATION_FOLDER_NAME));
        Assert.assertEquals(displayName, DISPLAY_NAME);
    }

    @Test(dependsOnMethods = "testAddDisplayName")
    @Epic("00 New Item")
    @Story("US_00.007 | New item > Create Organization Folder")
    @Description("TC_00.007.02 Configure the project 'Organization Folder'")
    public void testEditDisplayName() {
        String newDisplayName = new HomePage(getDriver())
                .openOrganisationFolderProject(DISPLAY_NAME)
                .clickSidebarConfigButton()
                .editDisplayName(NEW_DISPLAY_NAME)
                .clickSaveButton()
                .getItemName();

        Allure.step(String.format("'%s' '%s' has been changed to '%s'", ORGANIZATION_FOLDER_NAME, DISPLAY_NAME, NEW_DISPLAY_NAME));
        Assert.assertEquals(newDisplayName, NEW_DISPLAY_NAME);
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.007 | New item > Create Organization Folder")
    @Description("(NO TC) 00.007.05 Add 'Description' to the created project of type 'Organization Folder'")
    public void testAddDescription() {
        TestUtils.createOrganizationFolder(getDriver(), ORGANIZATION_FOLDER_NAME);

        String description = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickSidebarConfigButton()
                .enterDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionWhenAddedViaConfigure();

        Allure.step(String.format("The following description: '%s' is added to '%s'", DESCRIPTION, ORGANIZATION_FOLDER_NAME));
        Assert.assertEquals(description, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testAddDescription")
    @Epic("00 New Item")
    @Story("US_00.007 | New item > Create Organization Folder")
    @Description("(NO TC) 06.004.02 Edit existing description")
    public void testEditDescription() {
        String description = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickSidebarConfigButton()
                .enterDescription(NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionWhenAddedViaConfigure();

        Allure.step(String.format("'%s' has been changed to '%s'", DESCRIPTION, NEW_DESCRIPTION));
        Assert.assertEquals(description, NEW_DESCRIPTION);
    }

    @Test
    @Epic("06 Organization folder")
    @Story("US_06.001 | Organization Folder > Configuration")
    @Description("TC_06.001.01 Check left sidebar menu consisting of 7 anchor links")
    public void testCheckLeftSidebarMenu() {
        TestUtils.createOrganizationFolder(getDriver(), ORGANIZATION_FOLDER_NAME);

        List<String> listOfItemsSidebar = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickConfigure()
                .getListOfItemsSidebar();

        Allure.step("Verification of all the 7 anchor links are presented");
        Assert.assertEquals(listOfItemsSidebar.size(), 7);
        Assert.assertEquals(
                listOfItemsSidebar,
                new ArrayList<>(List.of("General", "Projects", "Scan Organization Folder Triggers", "Orphaned Item Strategy", "Appearance", "Health metrics", "Properties")));
    }

    @Test(dependsOnMethods = "testCheckLeftSidebarMenu")
    @Epic("06 Organization folder")
    @Story("US_06.001 | Organization Folder > Configuration")
    @Description("(NO TC) 06.004.01 Delete organization folder using sidebar menu")
    public void testDelete() {
        HomePage homePage = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickDeleteButtonSidebarAndConfirm();

        Allure.step(String.format("Current '%s' has been deleted due to this action", ORGANIZATION_FOLDER_NAME));
        Assert.assertEquals(homePage.getWelcomeTitle(), "Welcome to Jenkins!");
        Assert.assertEquals(homePage.getItemList().size(), 0);
    }

    @Test
    @Epic("06 Organization folder")
    @Story("US_06.001 | Organization Folder > Configuration")
    @Description("(NO TC) 06.004.03 Verify config page title after Organization folder is created")
    public void testVerifyConfigPageTitle() {
        String title = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .getTitleOfConfigPage();

        Allure.step("Title of configuration page after organization folder creation is getting");
        Assert.assertEquals(
                title,
                "Configuration");
    }

    @Test
    @Epic("06 Organization folder")
    @Story("US_06.001 | Organization Folder > Configuration")
    @Description("(NO TC) 06.004.04 Verify Enable switcher tooltip once it's enable")
    public void testTooltipGeneralEnabled() {
        String tooltipText = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .getTooltipGeneralText();

        Allure.step("Tooltip for Enable switcher is getting");
        Assert.assertEquals(
                tooltipText,
                "(No new builds within this Organization Folder will be executed until it is re-enabled)");
    }

    @Test
    @Epic("06 Organization folder")
    @Story("US_06.001 | Organization Folder > Configuration")
    @Description("(NO TC) 06.004.05 Verify Description preview button enable state")
    public void testDescriptionPreview() {
        String descriptionPreview = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .enterDisplayName(DISPLAY_NAME)
                .enterDescription(DESCRIPTION)
                .changeDescriptionPreviewState()
                .getDescriptionPreviewText();

        Allure.step(String.format("Description preview option is enabled so '%s' is visible before saving", DESCRIPTION));
        Assert.assertEquals(
                descriptionPreview,
                DESCRIPTION);
    }

    @Test
    @Epic("06 Organization folder")
    @Story("US_06.001 | Organization Folder > Configuration")
    @Description("(NO TC) 06.004.06 Verify Description preview button disable state")
    public void testDescriptionPreviewHide() {
        String displayPreview = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .enterDisplayName(DISPLAY_NAME)
                .enterDescription(DESCRIPTION)
                .changeDescriptionPreviewState()
                .changeDescriptionPreviewState()
                .getPreviewStyleAttribute();

        Allure.step("Description preview option is disabled so there's no preview");
        Assert.assertEquals(displayPreview, "display: none;");
    }

    @Test
    @Epic("06 Organization folder")
    @Story("US_06.001 | Organization Folder > Configuration")
    @Description("(NO TC) 06.004.07 Verify Pipeline Jenkinsfile close button tooltip")
    public void testVerifyCloseButtonTooltip() {
        TestUtils.createOrganizationFolder(getDriver(), ORGANIZATION_FOLDER_NAME);

        String closeButtonTooltip = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickSidebarConfigButton()
                .getCloseButtonTooltip();

        Allure.step("Verify Pipeline Jenkinsfile close button tooltip on configuration page");
        Assert.assertEquals(
                closeButtonTooltip,
                "Delete");
    }

    @Test(dependsOnMethods = "testVerifyCloseButtonTooltip")
    @Epic("06 Organization folder")
    @Story("US_06.001 | Organization Folder > Configuration")
    @Description("(NO TC) 06.004.08 Verify Description components are added on the main view")
    public void testVerifyMainPanelInformationUponConfigure() {
        String mainPanelText = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickConfigure()
                .enterDisplayName(DISPLAY_NAME)
                .enterDescription(DESCRIPTION)
                .clickSaveButton()
                .getMainPanelText();

        Allure.step(String.format("Verify on main panel that '%s' is presented with name '%s' and description '%s'",
                ORGANIZATION_FOLDER_NAME, DISPLAY_NAME, DESCRIPTION));
        Assert.assertTrue(mainPanelText.contains(DISPLAY_NAME));
        Assert.assertTrue(mainPanelText.contains("Folder name: " + ORGANIZATION_FOLDER_NAME));
        Assert.assertTrue(mainPanelText.contains(DESCRIPTION));
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.007 | New item > Create Organization Folder")
    @Description("(NO TC) 00.007.06 Add 'Description' on Configure Page")
    public void testAddDescriptionOnConfigurePage() {
        String actualDescription = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .enterDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionWhenAddedViaConfigure();

        Allure.step("Verify possibility to add description on Configure Page");
        Assert.assertEquals(actualDescription, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testAddDescriptionOnConfigurePage")
    @Epic("06 Organization folder")
    @Story("US_06.001 | Organization Folder > Configuration")
    @Description("(NO TC) 06.004.09 Rename Organization folder with Rename sidebar button")
    public void testRenameSidebar() {
        final String newName = ORGANIZATION_FOLDER_NAME + "_New";

        String actualName = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName(newName)
                .clickRenameButton()
                .getItemName();

        Allure.step("Verify possibility to rename Organization folder with Rename sidebar button");
        Assert.assertEquals(actualName, newName);
    }

    @Test
    @Epic("06 Organization folder")
    @Story("US_06.001 | Organization Folder > Configuration")
    @Description("(NO TC) 06.004.10 Create Organization folder with Appearance - Default icon")
    public void testCreateOrganizationFolderWithDefaultIcon() {
        String iconName = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .selectDefaultIcon()
                .clickSaveButton()
                .getIconAttributeTitle();

        Allure.step("Verify possibility to choose and create Create Organization folder with Default icon");
        Assert.assertEquals(iconName, "Folder");
    }

    @Test(dependsOnMethods = "testCreateOrganizationFolderWithDefaultIcon")
    @Epic("06 Organization folder")
    @Story("US_06.001 | Organization Folder > Configuration")
    @Description("(NO TC) 06.004.11 Delete organization folder via dropdown menu")
    public void testFolderDeleteViaDropdownMenu() {
        List<String> projectsList = new HomePage(getDriver())
                .selectDeleteFromItemMenuAndClickYes(ORGANIZATION_FOLDER_NAME)
                .getItemList();

        Allure.step(String.format("'%s' has been deleted via Dropdown menu", ORGANIZATION_FOLDER_NAME));
        Assert.assertListNotContainsObject(projectsList, ORGANIZATION_FOLDER_NAME, "Project is not deleted");
    }

    @Test
    @Epic("06 Organization folder")
    @Story("US_06.001 | Organization Folder > Configuration")
    @Description("(NO TC) 06.004.12 Verify Display name field tooltip text")
    public void testGetDisplayNameTooltipDisplayedWhenHoverOverQuestionMark() {
        TestUtils.createOrganizationFolder(getDriver(), ORGANIZATION_FOLDER_NAME);

        String questionMarkTooltipText = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickConfigure()
                .hoverOverDisplayNameQuestionMark()
                .getQuestionMarkTooltipText();

        Allure.step("Verify tooltip for Display name field");
        Assert.assertEquals(questionMarkTooltipText, "Help for feature: Display Name");
    }

    @Test
    @Epic("06 Organization folder")
    @Story("US_06.001 | Organization Folder > Configuration")
    @Description("(NO TC) 06.004.13 Verify Display name field tooltip presence")
    public void testVisibleDisplayNameTooltipWhenHoverOverQuestionMark() {
        TestUtils.createOrganizationFolder(getDriver(), ORGANIZATION_FOLDER_NAME);

        boolean questionMarkTooltip = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickConfigure()
                .hoverOverDisplayNameQuestionMark()
                .toolTipQuestionMarkVisible();

        Allure.step("Verify tooltip for Display name field is presented");
        Assert.assertTrue(questionMarkTooltip);
    }

    @Test
    @Epic("00 New Item")
    @Story("US_00.007 | New item > Create Organization Folder")
    @Description("TC_00.007.04 Create Organization Folder with min name length")
    public void createWithMinLength() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MIN_LENGTH_NAME)
                .selectOrganizationFolderAndClickOk()
                .gotoHomePage();

        Allure.step("Verify possibility to create Organization folder with minimal name length one symbol");
        Assert.assertEquals(homePage.getItemNameByOrder(1), MIN_LENGTH_NAME);
        Assert.assertEquals(homePage.getItemList().size(), 1);
    }
}
