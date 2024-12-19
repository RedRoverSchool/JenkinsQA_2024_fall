package school.redrover;

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
    public void testCreate() {
        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .gotoHomePage()
                .getItemList();

        Assert.assertListContainsObject(projectList, ORGANIZATION_FOLDER_NAME,
                "Project is not created");
    }

    @Test(dependsOnMethods = "testCreate")
    public void testAddDisplayName() {
        String displayName = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickConfigure()
                .setDisplayName(DISPLAY_NAME)
                .clickSaveButton()
                .getItemName();

        Assert.assertEquals(displayName, DISPLAY_NAME);
    }

    @Test(dependsOnMethods = {"testAddDisplayName"})
    public void testEditDisplayName() {
        String newDisplayName = new HomePage(getDriver())
                .openOrganisationFolderProject(DISPLAY_NAME)
                .clickSidebarConfigButton()
                .editDisplayName(NEW_DISPLAY_NAME)
                .clickSaveButton()
                .getItemName();

        Assert.assertEquals(newDisplayName, NEW_DISPLAY_NAME);
    }

    @Test
    public void testAddDescription() {
        TestUtils.createOrganizationFolder(getDriver(), ORGANIZATION_FOLDER_NAME);

        String description = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickSidebarConfigButton()
                .enterDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionWhenAddedViaConfigure();

        Assert.assertEquals(description, DESCRIPTION);
    }

    @Test(dependsOnMethods = {"testAddDescription"})
    public void testEditDescription() {
        String description = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickSidebarConfigButton()
                .enterDescription(NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescriptionWhenAddedViaConfigure();

        Assert.assertEquals(description, NEW_DESCRIPTION);
    }

    @Test
    public void testCheckLeftSidebarMenu() {
        TestUtils.createOrganizationFolder(getDriver(), ORGANIZATION_FOLDER_NAME);

        List<String> listOfItemsSidebar = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickConfigure()
                .getListOfItemsSidebar();

        Assert.assertEquals(listOfItemsSidebar.size(), 7);
        Assert.assertEquals(
                listOfItemsSidebar,
                new ArrayList<>(List.of("General", "Projects", "Scan Organization Folder Triggers", "Orphaned Item Strategy", "Appearance", "Health metrics", "Properties")));
    }

    @Test(dependsOnMethods = {"testCheckLeftSidebarMenu"})
    public void testDelete() {
        HomePage homePage = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickDeleteButtonSidebarAndConfirm();

        Assert.assertEquals(homePage.getWelcomeTitle(), "Welcome to Jenkins!");
        Assert.assertEquals(homePage.getItemList().size(), 0);
    }

    @Test
    public void testVerifyConfigPageTitle() {
        String title = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .getTitleOfConfigPage();

        Assert.assertEquals(
                title,
                "Configuration");
    }

    @Test
    public void testTooltipGeneralEnabled() {
        String tooltipText = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .getTooltipGeneralText();

        Assert.assertEquals(
                tooltipText,
                "(No new builds within this Organization Folder will be executed until it is re-enabled)");
    }

    @Test
    public void testDescriptionPreview() {
        String descriptionPreview = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .enterDisplayName(DISPLAY_NAME)
                .enterDescription(DESCRIPTION)
                .changeDescriptionPreviewState()
                .getDescriptionPreviewText();

        Assert.assertEquals(
                descriptionPreview,
                DESCRIPTION);
    }

    @Test
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

        Assert.assertEquals(displayPreview, "display: none;");
    }

    @Test
    public void testVerifyCloseButtonTooltip() {
        TestUtils.createOrganizationFolder(getDriver(), ORGANIZATION_FOLDER_NAME);

        String closeButtonTooltip = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickSidebarConfigButton()
                .getCloseButtonTooltip();

        Assert.assertEquals(
                closeButtonTooltip,
                "Delete");
    }

    @Test(dependsOnMethods = "testVerifyCloseButtonTooltip")
    public void testVerifyMainPanelInformationUponConfigure() {
        String mainPanelText = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickConfigure()
                .enterDisplayName(DISPLAY_NAME)
                .enterDescription(DESCRIPTION)
                .clickSaveButton()
                .getMainPanelText();

        Assert.assertTrue(mainPanelText.contains(DISPLAY_NAME));
        Assert.assertTrue(mainPanelText.contains("Folder name: " + ORGANIZATION_FOLDER_NAME));
        Assert.assertTrue(mainPanelText.contains(DESCRIPTION));
    }

    @Test
    public void testAddDescriptionOnConfigurePage() {
        String actualDescription = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .enterDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescriptionWhenAddedViaConfigure();

        Assert.assertEquals(actualDescription, DESCRIPTION);
    }

    @Test(dependsOnMethods = "testAddDescriptionOnConfigurePage")
    public void testRenameSidebar() {
        final String newName = ORGANIZATION_FOLDER_NAME + "_New";

        String actualName = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName(newName)
                .clickRenameButton()
                .getItemName();

        Assert.assertEquals(actualName, newName);
    }

    @Test
    public void testCreateOrganizationFolderWithDefaultIcon() {
        String iconName = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .selectDefaultIcon()
                .clickSaveButton()
                .getIconAttributeTitle();

        Assert.assertEquals(iconName, "Folder");
    }

    @Test(dependsOnMethods = "testCreateOrganizationFolderWithDefaultIcon")
    public void testFolderDeleteViaDropdownMenu() {
        List<String> projectsList = new HomePage(getDriver())
                .selectDeleteFromItemMenuAndClickYes(ORGANIZATION_FOLDER_NAME)
                .getItemList();

        Assert.assertListNotContainsObject(projectsList, ORGANIZATION_FOLDER_NAME, "Project is not deleted");
    }

    @Test
    public void testGetDisplayNameTooltipDisplayedWhenHoverOverQuestionMark() {
        TestUtils.createOrganizationFolder(getDriver(), ORGANIZATION_FOLDER_NAME);

        String questionMarkTooltipText = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickConfigure()
                .hoverOverDisplayNameQuestionMark()
                .getQuestionMarkTooltipText();

        Assert.assertEquals(questionMarkTooltipText, "Help for feature: Display Name");
    }

    @Test
    public void testVisibleDisplayNameTooltipWhenHoverOverQuestionMark() {
        TestUtils.createOrganizationFolder(getDriver(), ORGANIZATION_FOLDER_NAME);

        boolean questionMarkTooltip = new HomePage(getDriver())
                .openOrganisationFolderProject(ORGANIZATION_FOLDER_NAME)
                .clickConfigure()
                .hoverOverDisplayNameQuestionMark()
                .toolTipQuestionMarkVisible();

        Assert.assertTrue(questionMarkTooltip);
    }

    @Test
    public void createWithMinLength() {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MIN_LENGTH_NAME)
                .selectOrganizationFolderAndClickOk()
                .gotoHomePage();

        Assert.assertEquals(homePage.getItemNameByOrder(1), MIN_LENGTH_NAME);
        Assert.assertEquals(homePage.getItemList().size(), 1);
    }
}
