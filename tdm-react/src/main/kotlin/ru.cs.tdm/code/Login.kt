package ru.cs.tdm.code

import org.junit.jupiter.api.Assertions.*
import ru.cs.tdm.pages.LoginPage
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import ru.cs.tdm.data.Tdms
import ru.cs.tdm.data.TestsProperties

/**
 * при выходе на http://tdms-srv2b.csoft-msc.ru:443/tdms/?classic=&mobile=false#objects открывает страницу аутентификации;
 * Пользователь производит ввод валидных логина и пароля;
 * Пользователь удостоверяется в успешной аутентификации — об этом свидетельствует имя пользователя в верхнем правом углу окна;
 * Пользователь осуществляет выход из аккаунта путем нажатия на имя пользователя в верхнем правом углу окна с последующим нажатием на кнопку «Выйти…».
 *
 * Тест считается успешно пройденным в случае, когда пользователю удалось выполнить все вышеперечисленные пункты.
 */
class Login(val driver: WebDriver) {
    private val threadSleep = TestsProperties.threadSleepNomber     // задержки где они есть
    private val DT: Int = TestsProperties.debugPrintNomber          // глубина отладочной информации 0 - ничего не печатать, 9 - все
    //val threadSleep = 1000L
    // объявления переменных на созданные ранее классы-страницы
    private val loginPage: LoginPage = LoginPage(driver)

    /**
     * метод для осуществления аутентификации
     */

    fun loginIn(login: String, password: String) {
        //driver.navigate().refresh()  // Костыль из-за заголовка браузера
        assertTrue(loginPage.titleContain(Tdms),"Браузер не имеет вкладку с заголовком $Tdms для ввода пароля")
        assertEquals(loginPage.authorizationHeaderName(),"Войти в TDMS","Нет окна с заголовком Войти в TDMS")
        //получение доступа к методам класса LoginPage для взаимодействия с элементами страницы
        //вводим логин
        loginPage.inputLogin(login)
        //вводим пароль
        loginPage.inputPasswd(password)
        //нажимаем кнопку входа
        loginPage.clickAuthorizationButton()
        //получаем отображаемый логин и сравниваем его с логином из файла настроек
        assertTrue(loginPage.titleLoginUserNameWait() == login,
            "@@@@ Не вошли под пользователем $login @@")
        if (DT >6) println(" Проверили под login= $login   password= $password")
    }

    fun loginOut() {
        loginPage.clickCurrentUser()
        loginPage.clickUserLogout()
        loginPage.clickYesBtn()
        //driver.navigate().refresh()  // Костыль из-за заголовка браузера
        assertTrue(loginPage.titleContain(Tdms),"Браузер не имеет вкладку с заголовком Tdms для ввода пароля")
        assertEquals(loginPage.authorizationHeaderName(),"Войти в TDMS","Нет окна с заголовком Войти в TDMS")
    }

    fun checkBrowser(url:String): Boolean {
        loginPage.getBrowserUrl()
        assertTrue(loginPage.getBrowserUrl() == url,"В Браузере не имеет открыт $url")
        return true
    }
}