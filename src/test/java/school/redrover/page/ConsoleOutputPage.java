package school.redrover.page;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

import java.util.Arrays;

public class ConsoleOutputPage extends BasePage<ConsoleOutputPage> {
    public ConsoleOutputPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id='out']")
    private WebElement consoleOutput;

    @Step("Get finish result")
    public String getFinishResult() {
        String fullText = consoleOutput.getText();

        return Arrays.stream(fullText.split("\n"))
                .filter(line -> line.startsWith("Finished: "))
                .map(line -> line.replace("Finished: ", "").trim())
                .findFirst()
                .orElse("");
    }
}
