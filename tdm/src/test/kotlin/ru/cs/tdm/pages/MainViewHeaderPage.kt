package ru.cs.tdm.pages

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.PageFactory
import java.time.Duration

/** Создадим класс MainViewHeaderPage, в котором определим локаторы для меню пользователя
 * TDMSWeb РАБОЧИЙ СТОЛ  ОБЪЕКТЫ ПОЧТА СПРАВКА Найти Сообщения Пользователь (Сменить пароль настройки выход).
 * Помимо этого, напишем методы, которые будут получать имя пользователя и нажимать на кнопку выхода.
 */
class MainViewHeaderPage(private val driver: WebDriver) {
    /**
     * конструктор класса, занимающийся инициализацией полей класса
     */
    init {
        PageFactory.initElements(driver, this)
    }

    /**
     *
     * В body вложена панель меню сверху  //div[@id="mainView_header"]
     * Потом через два вложения  //div[@id="mainView_header-innerCt"]//div[@id="mainView_header-targetEl"]
     * и третье вложение 8 сестринских
     * //div[@id="mainHeaderText"] -  внутри TDMSWeb // *[@id="mainHeaderText"]/span/b/a
     * //div[@id="container-1061"] - Остальные пункты меню
     * вглубь два div и там:
     * //а[@id="button-1104"] - раб стол
     * //a[@id="button-1105"] - объекты
     * //a[@id="button-1106"] - почта
     *
     * //a[@id="button-1107"] - справка
     * //div[@id="tdmsClipboardText"] - рамка вокруг меню
     * //div[@id="tdmsClipboardText"] - Найти
     * //input[@id="tdmsSearch-1064-inputEl"] - найти
     * //a[@id="button-1096"] - найти
     * здесь еще лупа
     * здесь еще стрелочка для вызлва подменю ::after flex
     *
     * Здесь еще есть скрытая кнопка //a[@id="button-1087"] невидимая
     * в нее вложено куча span и она пристроена под имя пользователя
     * &nbsp что это за поле для меня загадка - может погашенное может костыль
     * //a[@id="button-1097"] - колокольчик
     * и внутри него лежит // *[@id="ext-element-8"] - цифра сообщений
     * //а[@id="button-1098"] - имя пользователя и раскрывающееся меню с выходом
     * //span[@id="button-1098-btnInnerEl"]
     *
     * Сильно ниже div для подменю и в нем
     * // *[@id="menuitem-1103-textEl"] - выход
     */
    /**
     * метод получения заголовка страницы
     */
    //@FindBy(xpath = "//title") //[contains(text(),'TDMS Web')]") //*[@id="ext-element-4"]/head/title
    @FindBy(xpath = "//*[@id='ext-element-4']/head/title") //[contains(text(),'TDMS Web')]") //*[@id="ext-element-4"]/head/title
    private lateinit var title: WebElement
    fun title(): String {
        Thread.sleep(100)   // Костыль  --  поменять на ожидание Selenium
        var titleStr = driver.title
        titleStr = if (titleStr.contains(" ")) titleStr.split(" ").toTypedArray()[0] else titleStr
        println("Ответ title = $titleStr")
        return titleStr
    }
    fun titleTDM(): String {
        Thread.sleep(3000)
        println("Запрос title = ${title.size}")
        return title.text
    }


    @FindBy(xpath = "//a[contains(text(),'TDMS Web')]")
    lateinit var tdmsWebBtn: WebElement
    fun ClickTDMSWeb(): Boolean {
        println("нажатия кнопки TDMSWeb ${objectsBtn.getAttribute("aria-pressed") =="true"}")
        tdmsWebBtn.click()
        //println("tdmsWebBtn.text = ${tdmsWebBtn.text}")
        //println(" tdmsWebBtn.ariaRole = ${tdmsWebBtn.ariaRole}")

        return objectsBtn.getAttribute("aria-pressed") =="true"
    }

    /**
     * метод для нажатия кнопки РАБОЧИЙ СТОЛ
     * <a class="x-btn header-button x-unselectable x-btn-default-small x-btn-pressed"
     * hidefocus="on" unselectable="on" role="button" aria-hidden="false" aria-disabled="false"
     * id="button-1095" tabindex="0" href="#desktop" aria-pressed="true">
     * <b>РАБОЧИЙ СТОЛ</b></a>
     */
    @FindBy(xpath = "//a[contains(@href,'#desktop')]") //@FindBy(xpath = "//b[contains(text(),'РАБОЧИЙ СТОЛ')]/ancestor::a")
    //@FindBy(xpath = "//b[contains(text(),'РАБОЧИЙ СТОЛ')]/../../../..")
    //@FindBy(xpath = "//a[contains(@href,'#desktop')] and //b[contains(text(),'РАБОЧИЙ СТОЛ')]/ancestor::a") NO
    private lateinit var desktopBtn: WebElement
    fun ClickDesktop(): Boolean {
        println("нажатия кнопки РАБОЧИЙ СТОЛ")
        desktopBtn.click()
      //  println(" desktopBtn text = ${desktopBtn.text}")  // РАБОЧИЙ СТОЛ
      //  println(" desktopBtn ariaRole = ${desktopBtn.ariaRole}")  // button
      //  println(" desktopBtn size = ${desktopBtn.size}")  // (120, 22)
      //  println(" desktopBtn aria-pressed = ${desktopBtn.getAttribute("aria-pressed")}") // true  String
      //  println(" desktopBtn aria-pressed = ${desktopBtn.getAttribute("aria-pressed")}") // true
      //  println(" desktopBtn class = ${desktopBtn.getAttribute("class")}")  // выдал весь класс
      //  println(" desktopBtn.accessibleName = ${desktopBtn.accessibleName}")  // Доступное имя = РАБОЧИЙ СТОЛ
      //  println(" desktopBtn.tagName = ${desktopBtn.tagName}")  // a
      //  println(" desktopBtn.getDomAttribute(class) = ${desktopBtn.getDomAttribute("class")}") // выдал весь класс
      //  println(" desktopBtn.getDomProperty(class) = ${desktopBtn.getDomProperty("class")}")  // null
      //  println(" desktopBtn.isDisplayed = ${desktopBtn.isDisplayed}")
      //  println(" desktopBtn.isSelected = ${desktopBtn.isSelected}")

        return desktopBtn.getAttribute("aria-pressed") =="true"
    }

    /**
     * метод для нажатия кнопки ОБЪЕКТЫ
     */
    //@FindBy(xpath = "//a[contains(@href,'#objects')]") NO 27
    @FindBy(xpath = "//b[contains(text(),'ОБЪЕКТЫ')]/ancestor::a") //@FindBy(xpath = "//b[contains(text(),'ОБЪЕКТЫ')]/../../../..")
    //@FindBy(xpath = "//a[contains(@href,'#objects')] and //b[contains(text(),'ОБЪЕКТЫ')]/ancestor::a") NO
    private lateinit var objectsBtn: WebElement
    fun ClickObjects(): Boolean {
        println("нажатия кнопки ОБЪЕКТЫ")
        objectsBtn.click()
        return objectsBtn.getAttribute("aria-pressed") =="true"
    }

    /**
     * метод для нажатия кнопки ПОЧТА
     */
    @FindBy(xpath = "//a[contains(@href,'#mail')]") //@FindBy(xpath = "//b[contains(text(),'ПОЧТА')]/ancestor::a")
    //@FindBy(xpath = "//b[contains(text(),'ПОЧТА')]/../../../..")
    //@FindBy(xpath = "//a[contains(@href,'#mail')] and //b[contains(text(),'ПОЧТА')]/ancestor::a") NO
    private lateinit var mailBtn: WebElement
    fun ClickMail(): Boolean {
        println("нажатия кнопки ПОЧТА")
        mailBtn.click()
        return mailBtn.getAttribute("aria-pressed") =="true"
    }

    /**
     * метод для нажатия кнопки СОВЕЩАНИЯ
     */
    @FindBy(xpath = "//a[contains(@href,'#chat')]") //@FindBy(xpath = "//b[contains(text(),'СОВЕЩАНИЯ')]/ancestor::a")
    //@FindBy(xpath = "//b[contains(text(),'СОВЕЩАНИЯ')]/../../../..")
    //@FindBy(xpath = "//a[contains(@href,'#mail')] and //b[contains(text(),'ПОЧТА')]/ancestor::a") NO
    private lateinit var chatBtn: WebElement
    fun ClickChat(): Boolean {
        println("нажатия кнопки СОВЕЩАНИЯ")
        chatBtn.click()
        return chatBtn.getAttribute("aria-pressed") =="true"
    }

    /**
     * метод для нажатия кнопки СПРАВКА
     */
    @FindBy(xpath = "//a[contains(@href,'#help')]") //@FindBy(xpath = "//b[contains(text(),'ПОЧТА')]/ancestor::a")
    //@FindBy(xpath = "//b[contains(text(),'ПОЧТА')]/../../../..")
    //@FindBy(xpath = "//a[contains(@href,'#mail')] and //b[contains(text(),'ПОЧТА')]/ancestor::a") NO
    private lateinit var helpBtn: WebElement
    fun ClickHelp(): Boolean {
        println("нажатия кнопки СПРАВКА")
        helpBtn.click()
        return helpBtn.getAttribute("aria-pressed") =="true"
    }

    /**
     * метод для нажатия кнопки Найти
     */
    @FindBy(xpath = "//input[contains(@placeholder,'Найти')]")
    private lateinit var SearchInput: WebElement
    fun ClickSearchEnter(): Boolean {
        println("нажатия кнопки Найти")
        val action = Actions(driver)
        action.sendKeys(Keys.ENTER).perform()
        return true
    }

    fun InputSearch(search: String): Boolean {
        println("Ввод в InputSearch $search")
        SearchInput.sendKeys(search)
        return true
    }
    /**
     * Крестик, Лупа и подменю Расширенный поиск
     * #tdmsSearch-1055-trigger-search document.querySelector("#tdmsSearch-1055-trigger-search")
     * // *[@id="tdmsSearch-1055-trigger-search"]
     * /html/body/div[1]/div/div/div[6]/div/div/div[3] - лупа
     */
    /**
     * метод для нажатия кнопки Лупа
     */
    @FindBy(xpath = "//input[contains(@placeholder,'Найти')]")
    private lateinit var magnifieBtn: WebElement
    fun ClickMagnifier(): Boolean {
        println("нажать на лупу - поиск")
        magnifieBtn.click()
        return true
    }

    /**
     * метод для нажатия кнопки Сообщения
     */
    @FindBy(xpath = "//*[@id='button-1097']")
    private lateinit var мessagesBtn: WebElement
    fun ClickMessages(): Boolean {
        println("нажатия кнопки Сообщения")
        мessagesBtn.click()
        return true
    }

    /**
     * метод закрыть Окно сообщений
     * org.openqa.selenium.ElementClickInterceptedException: element click intercepted:
     * Element <a class="x-btn header-button x-unselectable x-box-item x-btn-default-small" hidefocus="on" unselectable="on" role="button" aria-hidden="false" aria-disabled="false" aria-haspopup="true" aria-owns="menu-1099" id="button-1098" tabindex="-1" style="left: 1790px; margin: 0px; top: 0px;" data-tabindex-value="0" data-tabindex-counter="1">...</a>
     * is not clickable at point (1854, 15).
     * Other element would receive the click:
     * <div role="presentation" class="x-mask x-border-box" style="height: 924px; width: 1920px; z-index: 18996;
    left: 0px; top: 0px;" id="ext-element-18"></div>
     */
    @FindBy(xpath = "//*[contains(text(),'Окно сообщений')]") // не будет работать
    private lateinit var мessagesClose: WebElement
    fun CloseMessages(): Boolean {
        println("закрыть Окно сообщений")
        val action = Actions(driver)
        action.sendKeys(Keys.ESCAPE).perform()
        return true
    }

    /**
     * определение локатора и метод для нажатия кнопки меню пользователя
     */
    //@FindBy(xpath = "//*[@id='button-1089']")  // кандинский - Справка
    @FindBy(xpath = "//a[@id='button-1098']") // SYSADMIN
    private lateinit var userMenu: WebElement
    fun entryMenu() {
       // Actions(driver).moveToElement(userMenu).perform()
        println("нажатия кнопки меню пользователя")
        userMenu.click()
    }

    /**
     * метод для получения имени пользователя из меню пользователя
     * // *[@id="root"]/div/div[2]/div[1]/header/div[2]/div[2]/div/div/div/ul/div[1]/div/span/text()
     * //span[@id="button-1098-btnInnerEl"]
     */
   // @FindBy(xpath = "//span[@id='button-1098-btnInnerEl']") //@FindBy(xpath = "//a[@id='button-1098']/span//span//span[2]")
    @FindBy(xpath = "//a[contains(@data-qtip, 'Настройки')]/span/span/span[2]")
    private lateinit var userAccountName: WebElement

    /**
     * метод для получения имени пользователя из меню пользователя
     */
    val userName: String
        get() = userAccountName.text

    /**
     * метод для получения первого имени пользователя из меню пользователя
     */
    val firstUserName: String
        get() {
            var userName = userAccountName.text
            userName = if (userName.contains(" ")) userName.split(" ").toTypedArray()[0] else userName
            println("получения первого имени пользователя из меню пользователя $userName")
            return userName
        }// New 4.1.2

    //wait.until(ExpectedConditions.visibilityOfElementLocated(By
    // .xpath("//*[contains(@class, 'account__name_hasAccentLetter')]")));
    // wait.until(ExpectedConditions.visibilityOfElementLocated(By
    //         .xpath("//*[contains(@class, 'personal-info-login__text personal-info-login__text_decorated')]")));
    //WebDriverWait wait = new WebDriverWait(driver, 10);  //Deprecate 3.14
    /**
     * Интересный момент: в метод getUserName() пришлось добавить еще одно ожидание,
     * т.к. страница «тяжелая» и загружалась довольно медленно.
     * В итоге тест падал, потому что метод не мог получить имя пользователя.
     * Метод getUserName() с ожиданием:
     */
    val userName_Wait: String
        get() {

            //WebDriverWait wait = new WebDriverWait(driver, 10);  //Deprecate 3.14
            //val wait = WebDriverWait(driver, Duration.ofSeconds(10)) // New 4.1.2

            //wait.until(ExpectedConditions.visibilityOfElementLocated(By
            // .xpath("//*[contains(@class, 'account__name_hasAccentLetter')]")));
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By
            //         .xpath("//*[contains(@class, 'personal-info-login__text personal-info-login__text_decorated')]")));
            return userAccountName.text
        }

    /**
     * определение локатора и метод для нажатия кнопки (пункта меню) выхода из аккаунта
     */
    //@FindBy(xpath = "//*[@id='menuitem-1103-textEl']")
    @FindBy(xpath = "//span[text()='Выход']//parent::a")
    private lateinit var logoutBtn: WebElement
    fun userLogout() {
        println("нажатия кнопки (пункта меню) выхода из аккаунта")
      /*  Actions(driver)
            .moveToElement(logoutBtn)
            .perform()
       */
        logoutBtn.click()
    }

    /**
     * определение кнопки ДА - подтрерждение выхода из аккаунта
     */
    //@FindBy(xpath = "//*[@id='button-1274']")   // //*[@id="button-1274"] //*[@id="button-1161"]
    @FindBy(xpath = "//span[text()='Да']/ancestor::a")
    private lateinit var logoutOKBtn: WebElement

    fun logoutOKBtn() {
        println("нажатия кнопки кнопки ДА - подтрерждение выхода из аккаунта")
       /* Actions(driver)
            .moveToElement(logoutOKBtn)
            .perform()

        */
        //logoutOKBtn.click()
        val listElements =  driver.findElements(By.xpath("//span[text()='Да']/ancestor::a"))
        if (listElements == null) return
        println ("найдено ${listElements.size} элементов Да")
        listElements.last().click()

    }
    fun logout() {
       // entryMenu()
         Actions(driver)
            .moveToElement(userMenu)
            .perform()

        println("нажатия кнопки меню пользователя")
        userMenu.click()

       // userLogout()
        println("нажатия кнопки (пункта меню) выхода из аккаунта")
          Actions(driver)
              .moveToElement(logoutBtn)
              .perform()

        logoutBtn.click()

       // logoutOKBtn()
        println("нажатия кнопки кнопки ДА - подтрерждение выхода из аккаунта")
         Actions(driver)
             .moveToElement(logoutOKBtn)
             .perform()

        logoutOKBtn.click()
    }
    fun CloseWindowLast(): Boolean {
        val listElements =  driver.findElements(By.xpath("//div[contains(@data-qtip, 'Закрыть диалог')]"))
        if (listElements == null) return false
        println ("найдено ${listElements.size} элементов Х")
        listElements.last().click()
        return true
    }
}