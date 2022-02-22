package ru.cs.tdm.code

import ru.cs.tdm.pages.LoginPage
import ru.cs.tdm.pages.MainViewHeaderPage
import org.openqa.selenium.WebDriver

/**
 * при выходе на http://tdms-srv2a:444/client/#objects/ открывает страницу аутентификации;
 * Пользователь производит ввод валидных логина и пароля;
 * Пользователь удостоверяется в успешной аутентификации — об этом свидетельствует имя пользователя в верхнем правом углу окна;
 * Пользователь осуществляет выход из аккаунта путем нажатия на имя пользователя в верхнем правом углу окна с последующим нажатием на кнопку «Выйти…».
 *
 * Тест считается успешно пройденным в случае, когда пользователю удалось выполнить все вышеперечисленные пункты.
 */
class Login(val driver: WebDriver) {

    // объявления переменных на созданные ранее классы-страницы
    private val loginPage: LoginPage = LoginPage(driver)
    private val mainViewHeaderPage: MainViewHeaderPage = MainViewHeaderPage(driver)

    /**
     * метод для осуществления аутентификации
     */

    fun loginIn(login: String, password: String ) {
        //получение доступа к методам класса LoginPage для взаимодействия с элементами страницы
        //значение login/password берутся из файла настроек по аналогии с chromedriver и loginpage
        //вводим логин  ХАЛТУРА - только тема 1
        loginPage.inputLogin(login)
        //вводим пароль
        loginPage.inputPasswd(password)
        //нажимаем кнопку входа
        loginPage.clickLoginBtn()
    }
    fun loginUserName(): String = mainViewHeaderPage.firstUserName // Халтура - button

        fun loginOut() {
            mainViewHeaderPage.entryMenu() // Халтура - button
            mainViewHeaderPage.userLogout()
            mainViewHeaderPage.logoutOKBtn()
        }
}