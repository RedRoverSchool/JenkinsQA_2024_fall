package school.redrover.runner;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import school.redrover.page.home.HomePage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;

public class TestUtils {

    public static ExpectedCondition<Boolean> isElementInViewPort(WebElement element) {
        return new ExpectedCondition<>() {
            @Override
            public Boolean apply(WebDriver driver) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                return (Boolean) js.executeScript(
                        "let rect = arguments[0].getBoundingClientRect();" +
                                "return (rect.top >= 0 && rect.left >= 0 && " +
                                "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                                "rect.right <= (window.innerWidth || document.documentElement.clientWidth));",
                        element);
            }
        };
    }

    public static class ExpectedConditions {
        public static ExpectedCondition<WebElement> elementIsNotMoving(final By locator) {
            return new ExpectedCondition<>() {
                private Point location = null;

                @Override
                public WebElement apply(WebDriver driver) {
                    WebElement element;
                    try {
                        element = driver.findElement(locator);
                    } catch (NoSuchElementException e) {
                        return null;
                    }

                    if (element.isDisplayed()) {
                        Point location = element.getLocation();
                        if (location.equals(this.location)) {
                            return element;
                        }
                        this.location = location;
                    }

                    return null;
                }
            };
        }

        public static ExpectedCondition<WebElement> elementIsNotMoving(final WebElement element) {
            return new ExpectedCondition<>() {
                private Point location = null;

                @Override
                public WebElement apply(WebDriver driver) {
                    if (element.isDisplayed()) {
                        Point location = element.getLocation();
                        if (location.equals(this.location)) {
                            return element;
                        }
                        this.location = location;
                    }

                    return null;
                }
            };
        }
    }

    public static void moveAndClickWithJS(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", element);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].dispatchEvent(new Event('click'));", element);
    }

    public static void scrollToBottomWithJS(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public static void scrollToElementWithJS(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void createPipelineProject(WebDriver driver, String name) {
        new HomePage(driver)
                .clickNewItem()
                .enterItemName(name)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage();
    }

    @Step("Paste '{text}' with JavaScript")
    public static void pasteTextWithJavaScript(WebDriver driver, WebElement element, String text) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].value = arguments[1];", element, text);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", element);
    }

    @Step("Create freestyle project '{name}'")
    public static void createFreestyleProject(WebDriver driver, String name) {
        new HomePage(driver)
                .clickNewItem()
                .enterItemName(name)
                .selectFreestyleProjectAndClickOk()
                .clickSaveButton()
                .gotoHomePage();
    }

    @Step("Create folder '{name}'")
    public static void createFolder(WebDriver driver, String name) {
        new HomePage(driver)
                .clickNewItem()
                .enterItemName(name)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .gotoHomePage();
    }

    @Step("Create multi-configuration project '{name}'")
    public static void createMultiConfigurationProject(WebDriver driver, String name) {
        new HomePage(driver)
                .clickNewItem()
                .enterItemName(name)
                .selectMultiConfigurationAndClickOk()
                .clickSaveButton()
                .gotoHomePage();
    }

    @Step("Create organization folder '{name}'")
    public static void createOrganizationFolder(WebDriver driver, String name) {
        new HomePage(driver)
                .clickNewItem()
                .enterItemName(name)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .gotoHomePage();
    }

    @Step("Create node '{name}'")
    public static void createNode(WebDriver driver, String name) {
        new HomePage(driver)
                .openManageJenkinsPage()
                .clickNodesButton()
                .clickButtonNewNode()
                .enterNodeName(name)
                .selectPermanentAgent()
                .clickButtonCreate()
                .clickButtonSave()
                .gotoHomePage();
    }

    @Step("Create multi-branch pipeline '{name}'")
    public static void createMultiBranchPipeline(WebDriver driver, String name) {
        new HomePage(driver)
                .clickNewItem()
                .enterItemName(name)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage();
    }

    @Step("Read file '{name}' from resources")
    public static String readFileFromResources(String name) {
        try(InputStream inputStream = TestUtils.class.getClassLoader().getResourceAsStream("jenkins/%s".formatted(name))) {
            if (inputStream == null) {
                throw new IOException("File not found in resources");
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encodeParam(String param) {
        return URLEncoder.encode(param, StandardCharsets.UTF_8);
    }
}
