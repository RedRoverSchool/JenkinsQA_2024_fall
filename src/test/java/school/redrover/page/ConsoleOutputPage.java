package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

import java.util.Arrays;

public class ConsoleOutputPage extends BasePage {
    public ConsoleOutputPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id='out']")
    private WebElement consoleOutput;

    public String getFinishResult() {
        String fullText = consoleOutput.getText();

        return Arrays.stream(fullText.split("\n"))
                .filter(line -> line.startsWith("Finished: "))
                .map(line -> line.replace("Finished: ", "").trim())
                .findFirst()
                .orElse("");
    }

}
