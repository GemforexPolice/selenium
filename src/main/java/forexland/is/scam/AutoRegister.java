package forexland.is.scam;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Optional;

public class AutoRegister {

  private WebDriver driver;
  JavascriptExecutor js;

  @Before
  public void setUp() {
    WebDriverManager.chromedriver().setup();
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");
    ChromeDriverService driverService = ChromeDriverService.createDefaultService();
    driver = new ChromeDriver(driverService, options);
    js = (JavascriptExecutor) driver;

  }

  @After
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void doRegister() {
    String mailDomain =
      Optional.ofNullable(System.getenv("MAIL_DOMAIN")).orElse("@concept3.co.jp");
    int times =
      Integer.parseInt(Optional.ofNullable(System.getenv("TIMES")).orElse("1000"));

    String tel = "12340110";
    String moji = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    // 3つの引数を受け取るTriConsumerを別途作成(作成してくれた方に脱帽!!)
    TriConsumer<WebDriver, String, String> doInput = (driver, key, value) -> {
      driver.findElement(By.id(key)).click();
      driver.findElement(By.id(key)).sendKeys(value);
    };

    // 無限に作成
    for (int i=0; i<times; i++) {
      String mailAddress
        = RandomStringUtils.random(100 - mailDomain.length(), moji) + mailDomain;
      try {
        driver.get("https://memberarea.forexland-fx.com/ja/live_signup");
        driver.manage().window().setSize(new Dimension(794, 704));
        doInput.accept(driver, "fname", "GEMFOREX");
        doInput.accept(driver, "lname", "POLICE");
        doInput.accept(driver, "userEmail", mailAddress);
        doInput.accept(driver, "userPassword", "Root5is22360679");
        doInput.accept(driver, "confirmUserPassword", "Root5is22360679");
        doInput.accept(driver, "tel", tel);
        driver.findElement(By.id("currency")).click();
        {
          WebElement dropdown = driver.findElement(By.id("currency"));
          dropdown.findElement(By.xpath("//option[. = 'JPY']")).click();
        }
        driver.findElement(By.id("register_new")).click();
        driver.findElement(By.cssSelector("strong:nth-child(6)")).click();
        driver.findElement(By.cssSelector("strong:nth-child(6)")).click();
        {
          WebElement element = driver.findElement(By.cssSelector("strong:nth-child(6)"));
          Actions builder = new Actions(driver);
          builder.doubleClick(element).perform();
        }
      } catch (Exception e) {
//        System.out.println("Exception!!" + e.getMessage());
      } finally {
        System.out.println("Finish!!: " + mailAddress);
      }
    }
  }
}
