package ru.cs.tdm.cases

import org.openqa.selenium.WebDriver
import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.*
import org.openqa.selenium.chrome.ChromeDriver
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.chrome.ChromeOptions
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.data.ConfProperties


/**
 * при выходе на http://tdms-srv2a:444/client/#objects/ открывает страницу аутентификации;
 * Пользователь производит ввод валидных логина и пароля;
 * Пользователь осуществляет выход из аккаунта путем нажатия на имя пользователя в верхнем правом углу окна с последующим нажатием на кнопку «Выйти…».
 * Тест считается успешно пройденным в случае, когда пользователю удалось выполнить все вышеперечисленные пункты.
 */
@DisplayName("Testing Tools Menu-Icons Test")
class HeadTest {
    // переменная для драйвера
    private lateinit var driver: WebDriver
    // объявления переменных на созданные ранее классы-страницы
    private lateinit var tools: Tools
    /**
     * осуществление первоначальной настройки
     * Предупреждение: Не смешивайте неявные и явные ожидания.
     * Это может привести к непредсказуемому времени ожидания.
     * Например, установка неявного ожидания 10 секунд и явного ожидания 15 секунд
     * может привести к таймауту через 20 секунд.
     */

    @BeforeEach
    fun setup() {
        // создание экземпляра драйвера (т.к. он объявлен в качестве переменной):
        WebDriverManager.chromedriver().setup()
        //окно разворачивается на полный второй экран
        driver = ChromeDriver(ChromeOptions().addArguments("--window-position=2000,0"))
        driver.manage().window().maximize()

        // Создаем экземпляры классов созданных ранее страниц, и присвоим ссылки на них.
        // В качестве параметра указываем созданный перед этим объект driver,
        tools = Tools(driver)

        val loginpageTDM = ConfProperties.getProperty("loginpageTDM")
        driver.get(loginpageTDM)

        val loginTDM = ConfProperties.getProperty("loginTDM")
        val passwordTDM = ConfProperties.getProperty("passwordTDM")
        Login(driver).loginIn(loginTDM, passwordTDM)
    }

    /**
     * тестовый метод нажатия на кнопки
     *
     */

    @RepeatedTest(300)
    @DisplayName("Testing each menu separately")
    fun headMenuTest()  {

        tools.qtipClickLast("Главное меню")
        assertTrue(tools.titleContain("TDM365"))
        assertTrue(tools.qtipPressedLast("Объекты"))

        tools.qtipClickLast("Рабочий стол")
        assertTrue(tools.titleContain("Рабочий"))
        assertTrue(tools.qtipPressedLast("Рабочий стол"))

        tools.qtipClickLast("Объекты")
        assertTrue(tools.titleContain("TDM365"))
        assertTrue(tools.qtipPressedLast("Объекты"))

        tools.qtipClickLast("Почта")
        assertTrue(tools.titleContain("Почта"))
        assertTrue(tools.qtipPressedLast("Почта"))

        tools.qtipClickLast("Совещания")
        assertTrue(tools.titleContain("Совещания"))
        assertTrue(tools.qtipPressedLast("Совещания"))

        tools.qtipClickLast("Справка")
        //assertFalse(tools.titleContain("Справка"))      // Исправить!!! NOT
        assertTrue(tools.qtipPressedLast("Справка"))

        tools.qtipClickLast("Рабочий стол")  // Ошибка TDMS - отжимается кнопка
        tools.qtipClickLast("Объекты")
        tools.qtipLast("Введите текст")?.sendKeys("Лебедев")
        tools.qtipClickLast("Искать")
        assertTrue(tools.titleContain("Результаты"))
        //val value = driver.findElement(By.xpath("//input[contains(@data-qtip, 'Введите текст')]")).getAttribute("value")
        assertEquals("Лебедев" ,tools.qtipLast("Введите текст")?.getAttribute("value"))
        tools.qtipClickLast("Очистить")
        assertEquals("" ,tools.qtipLast("Введите текст")?.getAttribute("value"))
        tools.qtipClickLast("Рабочий стол")     // Ошибка TDMS - отжимается кнопка
        tools.qtipClickLast("Объекты")
        assertTrue(tools.titleContain("TDM365"))
        assertTrue(tools.qtipPressedLast("Объекты"))  // Ошибка TDMS - отжимается кнопка

        tools.qtipClickLast("Уведомления")
        assertEquals("Окно сообщений", tools.windowTitle())
        tools.closeXLast()
    }
    @RepeatedTest(300)
    @DisplayName("Testing Tools Box")
    fun toolsTest()  {
        tools.qtipClickLast("Главное меню")
        assertTrue(tools.titleContain("TDM365"))
        assertTrue(tools.qtipPressedLast("Объекты"))

        assertTrue(tools.qtipPressedLast("Показать/скрыть дерево"))
        tools.qtipClickLast("Показать/скрыть дерево")   // Скрыть дерево
        assertFalse(tools.qtipPressedLast("Показать/скрыть дерево"))
        tools.qtipClickLast("Показать/скрыть дерево")   // Показать дерево
        assertTrue(tools.qtipPressedLast("Показать/скрыть дерево"))

        assertTrue(tools.qtipPressedLast("Показать/скрыть панель предварительного просмотра"))
        tools.qtipClickLast("Показать/скрыть панель предварительного просмотра")
        assertFalse(tools.qtipPressedLast("Показать/скрыть панель предварительного просмотра"))
        tools.qtipClickLast("Показать/скрыть панель предварительного просмотра")
        assertTrue(tools.qtipPressedLast("Показать/скрыть панель предварительного просмотра"))

        tools.qtipClickLast("Создать фильтр")
        assertTrue(tools.editDialogTitleWait("Редактирование объекта"))
        // tooltip-2106-innerCt
        // //div[start-whith(@id,"tooltip") and contains(text(),"Пользовательский")]
        tools.closeXLast()
        
        tools.qtipClickLast("Обновить")
        tools.qtipClickLast("Администрирование групп")
        assertTrue(tools.windowTitleWait("Редактирование групп"))
        tools.closeXLast()
        tools.qtipClickLast("Настройка Camunda")
        assertTrue(tools.editDialogTitleWait("Редактирование объекта"))
        tools.closeXLast()
        tools.qtipClickLast("Удалить структуру объектов")
        assertTrue(tools.selectedDialogTitleWait("Удаление структуры объектов"))
        tools.closeXLast()
        tools.qtipClickLast("Создать объект разработки")
        assertTrue(tools.editDialogTitleWait("Редактирование объекта"))
        tools.closeXLast()
        tools.qtipClickLast("Параметры системы")
        assertTrue(tools.windowTitleWait("Параметры системы"))
        tools.closeXLast()

        tools.qtipClickLast("Настройка шаблона уведомлений")
        assertTrue(tools.editDialogTitleWait("Редактирование объекта"))
        tools.closeXLast()

        fun openCETD(){
           // assertFalse(tools.qtipLastClass("СЭТД")?.contains("x-btn-menu-active") ?: false)
           // tools.qtipClickLast("СЭТД")
           // assertTrue(tools.qtipLastClass("СЭТД")?.contains("x-btn-menu-active") ?: false)
            repeat(7) {
                tools.qtipClickLast("СЭТД")
                if (tools.qtipLastClass("СЭТД")?.contains("x-btn-menu-active") ?: false) return
                println("####### СЭТД Повтор *##*$it  открытия через $it sec #######")
                repeat(3) { tools.closeEsc() }
                tools.qtipClickLast("Главное меню")
                Thread.sleep(1000L*it)
            }
            println("&&&&&&&&& Не открылось СЭТД за 7 опросов  &&&&&&&&&")
            assertTrue(tools.qtipLastClass("СЭТД")?.contains("x-btn-menu-active") ?: false)
        }
        fun clickMenu(menu:String, title:String ):Boolean {
            repeat(7) {
                openCETD()
                tools.xpathClickMenu(menu)
                if (tools.messageTitleWait(title)) return true
                println("####### пункт MENU за *##*$it не нажалось  $menu - нет  $title ждем $it sec #######")
                tools.closeEsc()
                tools.qtipClickLast("Главное меню")
                Thread.sleep(1000L*it)
            }
            println("&&&&&&&&& Не нажалось $menu за 7 нажатий $title  &&&&&&&&&")
            assertTrue(tools.messageTitleWait(title))
            return false
        }
        //openCETD()
        //tools.xpathClickMenu("Поток - Проверка статуса проекта")
        clickMenu("Поток - Проверка статуса проекта", "TDMS" )
        assertTrue(tools.messageTitleWait("TDMS"))
        val msgText = tools.xpathGetText("//div[starts-with(@id,'messagebox-') and  contains(@id,'-msg')]")
        assertTrue(msgText.contains("Да - Ввод GUID проекта вручную"))
        assertTrue(msgText.contains("Нет - Выбор проекта в системе"))
        tools.closeXLast()
        assertTrue(tools.messageTitleWait("Ввод значения"))
        tools.closeXLast()
        assertTrue(tools.messageTitleWait("TDMS"))
        tools.closeXLast()

        //openCETD()
        //tools.xpathClickMenu("Поток 0 - Отправка проекта")
        clickMenu("Поток 0 - Отправка проекта", "TDMS")
        assertTrue(tools.messageTitleWait("TDMS"))
        tools.closeXLast()

        //openCETD()
        //tools.xpathClickMenu("Поток 1 - Отправка передаточного документа")
        clickMenu("Поток 1 - Отправка передаточного документа", "Ввод пути к папке синхронизации")
        assertTrue(tools.messageTitleWait("Ввод пути к папке синхронизации"))
        tools.closeXLast()
        assertTrue(tools.messageTitleWait("TDMS"))
        tools.closeXLast()

        //openCETD()
        //tools.xpathClickMenu("Поток 2.1 - Ответ о результате передаче РЗ")
        clickMenu("Поток 2.1 - Ответ о результате передаче РЗ", "TDMS")
        assertTrue(tools.messageTitleWait("TDMS"))
        tools.closeXLast()
        assertTrue(tools.selectedDialogTitleWait("Выбор Реестра замечаний"))
        tools.closeXLast()
        assertTrue(tools.messageTitleWait("TDMS"))
        tools.closeXLast()

        //openCETD()
        //tools.xpathClickMenu("Поток 3 - Отправка ответов на замечания")
        clickMenu("Поток 3 - Отправка ответов на замечания", "TDMS")
        assertTrue(tools.messageTitleWait("TDMS"))
        tools.closeXLast()
        assertTrue(tools.selectedDialogTitleWait("Выбор Реестра замечаний"))
        tools.closeXLast()

    }

    @AfterEach
    fun tearDown() {
        repeat(3) {
            tools.closeEsc()
        }
        Login(driver).loginOut()
        driver.quit() //  закрытия окна браузера
    }
}