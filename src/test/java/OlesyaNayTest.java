import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;

public class OlesyaNayTest {

    WebDriver driver;

    @BeforeMethod
    public void startDriver() {
        driver = new ChromeDriver(new ChromeOptions().addArguments("incognito", "start-maximized"));
    }

    @AfterMethod
    public void stopDriver() {
        driver.quit();
    }

    @Test
    public void testRatatype() {
        driver.get("https://www.ratatype.com/");

        driver.findElement(By.xpath("//*[text()='Test your speed']")).click();
        WebElement title = driver.findElement(By.xpath("//*[@class='h2']"));

        Assert.assertEquals(title.getText(), "Typing Certification Test");
    }

    @Test
    public void testRatatypeSelectSpanishLanguage() throws InterruptedException {
        driver.get("https://www.ratatype.com/");

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@data-bs-target='#footerLangModal']")).click();
        WebElement language = driver.findElement(By.xpath("//a[contains(@href,'/es/')]"));
        Thread.sleep(2000);
        language.click();

        WebElement title = driver.findElement(By.xpath("//h1"));
        Assert.assertEquals(title.getText(), "Prácticas de escritura a mecanografía");
    }

    // !!! !!! !!!
    // В тесте используется Robot для имитации нажатия клавиш на клавиатуре.
    // Если закрыть окно с тестом до окончания набора текста, то Robot продолжит нажимать клавиши
    // в ранее открытом окне (в IDE) пока не закончится текст или пока не будет остановлен тест.
    // !!! !!! !!!
    @Test
    public void testRatatypePrintSpeed() throws AWTException {
        driver.get("https://www.ratatype.com/typing-tutor/");

        Robot robot = new Robot();
        robot.delay(3000);      // Create a three seconds delay.

        // Фраза "type me to find out how many words per minute you can type", которую нужно напечатать,
        // закодирована числовыми кодами клавиш для раскладки клавиатуры QWERTY
        // ('t'=84; 'y'=89; ' '=32 и т.д.)
        Integer[] intArray = {
                84,89,80,69,32, 77,69,32, 84,79,32, 70,73,78,68,32, 79,85,84,32, 72,79,87,32, 77,65,78,89,32,
                87,79,82,68,83,32, 80,69,82,32, 77,73,78,85,84,69,32, 89,79,85,32, 67,65,78,32, 84,89,80,69
        };

        // Generating key press event for writing the QWERTY letters
        for(int i = 0; i < intArray.length; i++) {
            robot.delay(120);
            robot.keyPress(intArray[i]);
        }

        WebElement titleH2 = driver.findElement(By.xpath("//h2"));
        Assert.assertTrue(titleH2.getText().contains("words per minute"));
    }

}
