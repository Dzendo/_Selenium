package ru.cs.tdm.code

import ru.cs.tdm.pages.LoginPage
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
    private val tools: Tools = Tools(driver)

    /**
     * метод для осуществления аутентификации
     */

    fun loginIn(login: String, password: String) {
        //получение доступа к методам класса LoginPage для взаимодействия с элементами страницы
        //вводим логин  ХАЛТУРА - только тема 1
        loginPage.inputLogin(login)
        //вводим пароль
        loginPage.inputPasswd(password)
        //нажимаем кнопку входа
        loginPage.clickLoginBtn()
    }

    fun loginUserName(): String {
        Thread.sleep(3000)
        val userName = tools.xpathGetText("//a[contains(@data-qtip, 'Настройки')]")
        println("получение первого имени пользователя из меню пользователя $userName")
        return if (userName.contains(" ")) userName.split(" ").toTypedArray()[0] else userName
    }
    fun loginUserNameWait(name: String): Boolean {
        Thread.sleep(3000)
        val bool = tools.xpathFluentWaitText("//a[contains(@data-qtip, 'Настройки')]",
            if (name.contains(" ")) name.split(" ").toTypedArray()[0] else name)
        println("ожидание $name пользователя из меню пользователя ")
        return bool
    }
    fun qtipLoginUserNameWait(name: String): Boolean {
        Thread.sleep(3000)
        val bool = tools.qtipFluentWaitText("Настройки",
            if (name.contains(" ")) name.split(" ").toTypedArray()[0] else name)
        println("ожидание $name пользователя из меню пользователя")
        return bool
    }

    fun loginOut() {
        tools.qtipClickLast("Настройки")
        tools.qtipClickLast("Выйти из приложения")
        //tools.closeXLast()
        tools.clickOK()
        //loginPage.ClickYesBtn()
    }
}