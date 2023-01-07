import com.github.javafaker.Faker;
import net.bytebuddy.asm.Advice;
import org.bouncycastle.crypto.tls.ByteQueueOutputStream;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.seleniumhq.jetty9.server.Authentication;

import javax.imageio.plugins.tiff.ExifTIFFTagSet;
import java.text.BreakIterator;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SoftieProject {
    WebDriver driver;

    @BeforeEach
    void prepareBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://34.171.101.114/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

      @AfterEach
     void closeBrowser() {
         driver.quit();
      }

    @Test
    void NoLoginTest() {
        WebElement MyAccountButton = driver.findElement(By.id("menu-item-125"));
        MyAccountButton.click();
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("post-9")));
        WebElement Password = driver.findElement(By.id("password"));

        Faker faker = new Faker(new Locale("pl-PL"));
        String PssWrd = String.valueOf(faker.pokemon().name());
        Password.sendKeys(PssWrd);
        WebElement LoginButton = driver.findElement(By.cssSelector("button[name='login']"));
        LoginButton.click();
        WebElement LoginFailed = driver.findElement(By.className("woocommerce-error"));
        Assertions.assertEquals("Błąd: Nazwa użytkownika jest wymagana.", LoginFailed.getText());

    }

    @Test
    void LoginWithoutPassword() {
        WebElement MyAccountButton = driver.findElement(By.id("menu-item-125"));
        MyAccountButton.click();
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#post-9 > div > div > h2")));
        WebElement Login = driver.findElement(By.id("username"));
        Faker faker = new Faker(new Locale("pl-PL"));
        String LoginName = String.valueOf(faker.name().firstName() + "@mail.pl");
        Login.sendKeys(LoginName);
        WebElement LoginButton = driver.findElement(By.cssSelector("button[name='login']"));
        LoginButton.click();
        WebElement LoginFailed = driver.findElement(By.className("woocommerce-error"));
        Assertions.assertEquals("Błąd: pole hasła jest puste.", LoginFailed.getText());


    }


    @Test
    void RegisterNewUser() {

        WebElement MyAccountButton = driver.findElement(By.id("menu-item-146"));
        MyAccountButton.click();
        WebElement userName = driver.findElement(By.id("user_login"));
        Faker faker = new Faker(new Locale("pl-PL"));
        String newUser = faker.name().username() + "123";
        userName.sendKeys(newUser);
        WebElement userEmail = driver.findElement(By.id("user_email"));
        Faker faker2 = new Faker();
        String newEmail = faker2.name().username() + "@fake.mail";
        WebElement userPassword = driver.findElement(By.id("user_pass"));
        WebElement confirmPassword = driver.findElement(By.id("user_confirm_password"));
        String Password = "StrongFakePassword11@";
        confirmPassword.sendKeys(Password);
        userEmail.sendKeys(newEmail);
        userPassword.sendKeys(Password);
        Actions action = new Actions(driver);
        WebElement submitButton = driver.findElement(By.xpath("//div[@class='ur-button-container ']/button/span"));

        action.moveToElement(submitButton).click().perform();
     driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));





        Wait wait1 = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("ur-submit-message-node")));
        WebElement Registered = driver.findElement(By.id("ur-submit-message-node"));
        Assertions.assertEquals("User successfully registered.", Registered.getText());


        WebElement goToMyAcc = driver.findElement(By.linkText("Moje konto"));
        goToMyAcc.click();
        WebElement Login = driver.findElement(By.id("username"));
        Login.sendKeys(newUser);
        WebElement PasswordInput = driver.findElement(By.id("password"));
        PasswordInput.sendKeys(Password);
        WebElement LoginButton = driver.findElement(By.cssSelector("button[name='login']"));
        LoginButton.click();
        Assertions.assertTrue(driver.findElement(By.linkText("Wyloguj się")).isDisplayed());


    }

    @Test
    void checkLogo() {

        WebElement logo = driver.findElement(By.linkText("Metal Shop"));
        Assertions.assertEquals("Metal Shop", logo.getText());
        WebElement goToRegister = driver.findElement(By.id("menu-item-146"));
        goToRegister.click();
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("header>h1")));
        WebElement logo2 = driver.findElement(By.linkText("Metal Shop"));
        Assertions.assertEquals("Metal Shop", logo2.getText());


    }

    @Test
    void goToContactSite() {
        WebElement navigateToContact = driver.findElement(By.id("menu-item-132"));
        navigateToContact.click();
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//input[@type='submit']")));
        WebElement kontaktHeader = driver.findElement(By.xpath(".//header[@class='entry-header']"));
        Assertions.assertEquals("Kontakt", kontaktHeader.getText());

    }

    @Test
    void goFromLogintoMainSite() {

        WebElement navigateToMyAcc = driver.findElement(By.id("menu-item-125"));
        navigateToMyAcc.click();
        WebElement MyAccSite = driver.findElement(By.xpath(".//div[@class='woocommerce']/h2"));
        Assertions.assertEquals("Logowanie", MyAccSite.getText());
        WebElement navigateToMainPage = driver.findElement(By.linkText("Sklep"));
        navigateToMainPage.click();
        WebElement MainPage = driver.findElement(By.xpath(".//div[@class='content-area']/main/header/h1"));
        Assertions.assertEquals("Sklep", MainPage.getText());


    }


    @Test
    void sendMsgByContactPage() {

        WebElement navigateToContact = driver.findElement(By.id("menu-item-132"));
        navigateToContact.click();
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='submit']")));
        WebElement inputNameSurname = driver.findElement(By.xpath("//div[@id='wpcf7-f128-p129-o1']/form/p/label/span/input"));
        Faker faker = new Faker(new Locale("pl-PL"));
        String UserName = faker.name().name();
        inputNameSurname.sendKeys(UserName);
        WebElement inputEmail = driver.findElement(By.xpath("//div[@id='wpcf7-f128-p129-o1']/form/p[2]/label/span/input"));
        Faker faker2 = new Faker();
        String newEmail = faker2.name().username() + "123" + "@fake.com";
        inputEmail.sendKeys(newEmail);
        WebElement myMessage = driver.findElement(By.xpath("//div[@id='wpcf7-f128-p129-o1']/form/p[4]/label/span/textarea"));
        myMessage.sendKeys("Testowanie jest ok ;)");
        WebElement sendMessage = driver.findElement(By.xpath("//div[@id='wpcf7-f128-p129-o1']/form/p[5]/input"));
        sendMessage.click();
        Wait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='wpcf7-response-output']")));

        WebElement msgSentConfirmation = driver.findElement(By.xpath("//div[@class='wpcf7-response-output']"));
        Assertions.assertEquals("Twoja wiadomość została wysłana. Dziękujemy!", msgSentConfirmation.getText());


    }


    @Test
    void CheckIfCartEmpty() throws StaleElementReferenceException {
        WebElement navigateToCart = driver.findElement(By.id("menu-item-127"));
        navigateToCart.click();
        WebElement cartIsEmpty = driver.findElement(By.xpath("    //div[@class='woocommerce']/p[1]"));
        Assertions.assertEquals("Twój koszyk aktualnie jest pusty.", cartIsEmpty.getText());
        WebElement navigateToMainPage = driver.findElement(By.linkText("Sklep"));
        navigateToMainPage.click();
        WebElement addElementToCart = driver.findElement(By.xpath("//li[1]/a[2]"));
        addElementToCart.click();
        Wait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[1]/a[3]")));
        WebElement navigateToCartAgain = driver.findElement(By.id("menu-item-127"));
        navigateToCartAgain.click();
        WebElement checkCart = driver.findElement(By.xpath("//div[@class='cart_totals ']/h2"));
        Assertions.assertEquals("Podsumowanie koszyka", checkCart.getText());


    }


    @Test
    void removeItemFromCart() {
        WebElement addElementToCart = driver.findElement(By.xpath("//li[1]/a[2]"));
        addElementToCart.click();
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[1]/a[3]")));
        WebElement navigateToCartAgain = driver.findElement(By.id("menu-item-127"));
        navigateToCartAgain.click();
        WebElement deleteFromCart = driver.findElement(By.xpath("//div[@class='woocommerce']/form/table/tbody/tr[1]/td[1]/a"));
        deleteFromCart.click();
        Wait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='woocommerce-message']")));
        WebElement itemDeleted = driver.findElement(By.xpath("//div[@class='woocommerce-message']"));

        Assertions.assertEquals("Usunięto: „Srebrna moneta 5g - UK 1980”. Cofnij?", itemDeleted.getText());


    }


    public void login() {


        String username = "kierownik";
        String password = "!kinworeik!";
        WebElement Login = driver.findElement(By.id("username"));
        Login.sendKeys(username);
        WebElement Password = driver.findElement(By.id("password"));

        Password.sendKeys((password));


    }

    @Test
    void loginWithMethod() {

        WebElement MyAccountButton = driver.findElement(By.id("menu-item-125"));
        MyAccountButton.click();
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("post-9")));
        login();
        WebElement LoginButton = driver.findElement(By.cssSelector("button[name='login']"));
        LoginButton.click();
        Assertions.assertTrue(driver.findElement(By.linkText("Wyloguj się")).isDisplayed());


    }

    //  Zad 11.*(zadanie dodatkowe)
    //  Dodaj test dodający do koszyka tylko te produkty, które są aktualnie w promocji. Zakończ test
    //  sprawdzeniem poprawnej kwoty do zapłaty (podpowiedź możesz skorzystać z Listy, pętli lub z if i
    //       pętli).
    @Test
    void addPromoItemsToCart() {


        List<WebElement> promolist = driver.findElements(By.className("onsale"));

        for (int i = 0; i <promolist.size();i++ ) {

            promolist = driver.findElements(By.className("onsale"));
            promolist.get(i).click();

            WebElement addToBasket = driver.findElement(By.xpath("//div[2]/form/button"));
            addToBasket.click();
            WebElement goBacktoMain = driver.findElement(By.id("menu-item-124"));
            goBacktoMain.click();

        }


                WebElement navigateToCart = driver.findElement(By.id("menu-item-127"));
                navigateToCart.click();

                WebElement CheckPrice = driver.findElement(By.xpath("//div[@class='cart_totals ']/table/tbody/tr[1]/td"));
                Assertions.assertEquals("2430,00 zł", CheckPrice.getText());


            }
        }










