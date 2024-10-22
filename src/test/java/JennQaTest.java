import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class JennQaTest {

    @Test
    public void testImdbCheckFilteredRangeForListOfMovies() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.imdb.com/");
        driver.manage().window().maximize();

        WebElement burgerMenu = driver.findElement(By.id("imdbHeader-navDrawerOpen"));
        burgerMenu.click();

        Thread.sleep(2000);

        WebElement topMoviesButton = driver.findElement(By.xpath("//a[@href='/chart/top/?ref_=nv_mv_250']"));
        topMoviesButton.click();

        WebElement filterButton = driver.findElement(By.xpath("//button[@data-testid='filter-menu-button']"));
        filterButton.click();

        WebElement releaseYearFromField = driver.findElement(By.xpath("//input[@aria-label='Enter release year above']"));
        releaseYearFromField.sendKeys("1970");
        WebElement releaseYearToField = driver.findElement(By.xpath("//input[@aria-label='Enter release year below']"));
        releaseYearToField.sendKeys("1973");
        WebElement closePrompt = driver.findElement(By.xpath("//button[@title='Close Prompt']"));
        closePrompt.click();

        Thread.sleep(2000);

        List<WebElement> resultList = driver.findElements(
                By.xpath("//ul[contains(@class, 'ipc-metadata-list--dividers-between')]//li"));

        boolean allReleaseYearsInRange = true;
        for(WebElement years : resultList) {
            String text = years.getText();
            String[] words = text.split("\\s+");
            for (String word : words) {
                try {
                    int year = Integer.parseInt(word);
                    if ((year < 1970 || year > 1973) && word.length() == 4) {
                        allReleaseYearsInRange = false;
                    }
                } catch (NumberFormatException e) {
                }
            }
        }

        Assert.assertTrue(allReleaseYearsInRange, "There's a movie in the list with release date out of range");

        driver.quit();
    }

    @Test
    public void testImdbTryToRateMovieWhenUnauthorized() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.imdb.com./");
        driver.manage().window().maximize();

        WebElement searchField = driver.findElement(By.xpath("//input[@placeholder]"));
        searchField.sendKeys("Хороший, плохой, злой" + Keys.RETURN);

        List<WebElement> searchResult = driver.findElements(
                By.xpath("//ul[contains(@class, 'ipc-metadata-list ipc-metadata-list--dividers-after')]//li"));
        for (WebElement movie : searchResult) {
            String movieInfo = movie.getText();
            if (movieInfo.contains("1966")) {
                movie.click();
                break;
            }
        }

        WebElement yourRatingRateButton = driver.findElement(
                By.xpath("//button[@aria-label='Rate Хороший, плохой, злой']"));
        yourRatingRateButton.click();

        Thread.sleep(1000);

        WebElement tenStars = driver.findElement(By.xpath("//div[@class='ipc-starbar']//div[2]//button[10]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",tenStars);

        WebElement confirmStarsRateButton = driver.findElement(
                By.xpath("//button[contains(@class, 'ipc-rating-prompt__rate-button')]"));
        confirmStarsRateButton.click();

        String signInPageHeader = driver.findElement(By.tagName("h1")).getText();

        Assert.assertEquals(signInPageHeader, "Sign in");

        driver.quit();
    }
}
