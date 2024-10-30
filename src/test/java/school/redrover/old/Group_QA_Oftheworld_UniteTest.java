package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class Group_QA_Oftheworld_UniteTest {

    @Ignore
    public class MainTest {

        @Test
        public void test() throws InterruptedException {
            WebDriver driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

            driver.get("https://text.ru/"); // юрл сайта
            driver.getTitle();

            // поиск поля для ввода текста
            WebElement textBox1 = driver.findElement(By.className("antiplagiat-block_text-container")); // в этой строке происходит поиск веб-элемента
            textBox1.sendKeys("Любопытно, что прошепчет Тутошкин кому-нибудь обо мне");

            // поиск кнопки Проверить уникальность  и нажатие на неё
            WebElement submitButton1 = driver.findElement(By.className("antiplagiat-block_submit t-btn t-btn--fill"));
            submitButton1.click();

            driver.quit();
        }
    }
}

