package ru.cs.tdm;
// https://habr.com/ru/post/502292/
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
//import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Пользователь открывает страницу аутентификации;
 * Пользователь производит ввод валидных логина и пароля;
 * Пользователь удостоверяется в успешной аутентификации — об этом свидетельствует имя пользователя в верхнем правом углу окна;
 * Пользователь осуществляет выход из аккаунта путем нажатия на имя пользователя в верхнем правом углу окна с последующим нажатием на кнопку «Выйти…».
 *
 * Тест считается успешно пройденным в случае, когда пользователю удалось выполнить все вышеперечисленные пункты.
 */


public class LoginTest {
    // объявления статических переменных на созданные ранее классы-страницы
    public static LoginPage loginPage;
    public static ProfilePage profilePage;
    // Сюда же вынесем переменную для драйвера
    public static WebDriver driver;
    /**
     * осуществление первоначальной настройки
     */
    @BeforeClass
    public static void setup() {
        // определение пути до драйвера и его настройка (берется из файла)
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        //WebDriverManager.chromedriver().setup();
        //WebDriverManager.edgedriver().setup();
        //WebDriverManager.firefoxdriver().setup();
        // создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
        driver = new ChromeDriver();
        //driver = new EdgeDriver();
        //driver = new FirefoxDriver();
        // Создаем экземпляры классов созданных ранее страниц, и присвоим ссылки на них.
        // В качестве параметра указываем созданный перед этим объект driver,
        // который передается конструкторам класса, созданным ранее:
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        // задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //Deprecate 3.14
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  // New 4.1.2
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("loginpage")); }

    /**
     * тестовый метод для осуществления аутентификации
     */
    @Test
    public void loginTest() {
        //получение доступа к методам класса LoginPage для взаимодействия с элементами страницы
        //значение login/password берутся из файла настроек по аналогии с chromedriver
        //и loginpage
        //вводим логин
        loginPage.inputLogin(ConfProperties.getProperty("login"));
        //нажимаем кнопку входа
        loginPage.clickLoginBtn();
        //вводим пароль
        loginPage.inputPasswd(ConfProperties.getProperty("password"));
        //нажимаем кнопку входа
        loginPage.clickLoginBtn();
        //получаем отображаемый логин
        profilePage.entryMenu();
        String user = profilePage.getUserName();
        //и сравниваем его с логином из файла настроек
        Assert.assertEquals(ConfProperties.getProperty("login"), user);
        //profilePage.entryMenu();
    }
// В этом методе осуществляется вход в меню пользователя
// и нажатие кнопки «Выйти», чтобы разлогиниться.
    @AfterClass
    public static void tearDown() {
        //profilePage.entryMenu();
        profilePage.userLogout();
        driver.quit();   //  закрытия окна браузера
    }
}
/**  https://stackoverflow.com/questions/58993667/webdriverwait-is-deprecated-in-selenium-4
 *          driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
 *         driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
 *         driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
 */
