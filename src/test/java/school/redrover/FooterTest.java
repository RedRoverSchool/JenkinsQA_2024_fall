package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FooterTest extends BaseTest {

    @Test
    public void testFooter() {
        try {
            // Нажать кнопку
            getDriver().findElement(By.xpath("//button[@type='button']")).click();

            // Пауза на 5 секунд
            Thread.sleep(20000);

            // Выполнение дальнейших действий
            getDriver().findElement(By.xpath("//*[@id='tippy-1']/div/div/div/a[1]")).click();

            String hiStr = getDriver().findElement(By.xpath("//*[@id='main-panel']/p")).getText();

            Assert.assertEquals(hiStr, "The leading open source automation server which enables " +
                    "developers around the world to reliably build, test, and deploy their software.");
        } catch (InterruptedException e) {
            // Обработка исключения (например, можно вывести сообщение об ошибке)
            e.printStackTrace();
        }
    }
}