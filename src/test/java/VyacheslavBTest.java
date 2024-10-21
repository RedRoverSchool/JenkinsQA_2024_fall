import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;


    public class VyacheslavBTest {
        @Test
        public void testMCode() {

            WebDriver driver = new ChromeDriver();
            driver.get("https://thecode.media/");

            WebElement search_button = driver.findElement(By.className("heading-search__open"));
            search_button.click();

            WebElement search_text = driver.findElement(By.className("heading-search__input"));
            search_text.sendKeys("Api");

            search_button.click();

            WebElement found_text = driver.findElement(By.className("search__title"));
            String result_Search = found_text.getText();

            Assert.assertEquals("api", result_Search);

            driver.quit();

        }

        @Test
        public void testTasksArea() {
            WebDriver driver = new ChromeDriver();
            driver.get("https://thecode.media/");

            WebElement search_area = driver.findElement(By.className("tab-questions"));

            Action myAction = new Actions(driver).doubleClick(search_area).build();
            myAction.perform();

            WebElement found_text = driver.findElement(By.xpath("(//h1[@class='search__title'])"));
            String found_search_title = found_text.getText();

            Assert.assertEquals("Как решить", found_search_title);

            driver.quit();

        }


}
