package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PipelineProject3Test extends BaseTest {

    private String randomWord(int lengthName) {
        String lowercaseAlphabet = IntStream.rangeClosed('a', 'z')
                .mapToObj(letter -> String.valueOf((char) letter))
                .collect(Collectors.joining());

        String uppercaseAlphabet = IntStream.rangeClosed('A', 'Z')
                .mapToObj(letter -> String.valueOf((char) letter))
                .collect(Collectors.joining());

        String alphabet = lowercaseAlphabet + uppercaseAlphabet;

        Random random = new Random();

        return IntStream.range(0, lengthName)
                .mapToObj(letter -> String.valueOf(alphabet.charAt(random.nextInt(alphabet.length()))))
                .collect(Collectors.joining());
    }

    @Test
    public void testCreatePipeline() {
        final String namePipeline = randomWord(new Random().nextInt(25));

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']"))
                .sendKeys(namePipeline);

        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertTrue(getDriver()
                        .findElement(By.className("job-index-headline"))
                        .getText().contains(namePipeline),
                "The names don't match");
    }
}
