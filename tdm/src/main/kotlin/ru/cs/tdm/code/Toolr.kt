package ru.cs.tdm.code

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions.*
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.WebDriverWait
import ru.cs.tdm.data.TestsProperties
import java.time.Duration

fun WebElement.Click() {
    for (repeate in 1..TestsProperties.repeateInNomber) {
        try {
            this.click()
            //js.executeScript("arguments[0].click();", this)
        return
        } catch (e: ElementClickInterceptedException) {
        if (TestsProperties.debugPrintNomber >5) println("####ElementClickInterceptedException - повтор $repeate $e")
        }
    }
    throw ElementClickInterceptedException("&&&&&&& Not Click &&&&&&&& за ${TestsProperties.repeateInNomber} повторов ")
}

fun WebElement.SendKeys(str:String, clear: Boolean = false) {
  //  this.click()
    if (clear) this.clear()
    this.sendKeys(str)
    //this.click()
}

class Toolr(val driver: WebDriver) {
    private val threadSleep = TestsProperties.threadSleepNomber     // задержки где они есть
    private val DT: Int =
        TestsProperties.debugPrintNomber          // глубина отладочной информации 0 - ничего не печатать, 9 - все

    private val fluentInDuration = TestsProperties.fluentInDurationNomber
    private val pollingInDuration = TestsProperties.pollingInDurationNomber
    private val fluentOutDuration = TestsProperties.fluentOutDurationNomber
    private val pollingOutDuration = TestsProperties.pollingOutDurationNomber
    private val implicitlyDuration = TestsProperties.implicitlyDurationNomber
    private val WebDriverDuration = TestsProperties.WebDriverDurationNomber
    private val repeateIn = TestsProperties.repeateInNomber
    private val repeateOut = TestsProperties.repeateOutNomber

     private val js = driver as JavascriptExecutor

    //private val webDriverWait = WebDriverWait(driver, Duration.ofMillis(WebDriverDuration))// Явное ожидание
    init {
        // Предупреждение: Не смешивайте неявные и явные ожидания.
       driver.manage().timeouts().implicitlyWait(Duration.ofMillis(implicitlyDuration))  // Неявное ожидание
    }

//    private val webDriverWait = WebDriverWait(driver, Duration.ofSeconds(WebDriverDuration/1000))

    private val fluentOutWait = FluentWait<WebDriver>(driver)                               // Беглое ожидание
        .withTimeout(Duration.ofMillis(fluentOutDuration))
        .pollingEvery(Duration.ofMillis(pollingOutDuration))
        .ignoreAll(
            listOf(
                NoSuchElementException::class.java,
                ElementNotInteractableException::class.java,
                ElementClickInterceptedException::class.java,
                StaleElementReferenceException::class.java,
                Exception::class.java
            )
        )
    private val fluentInWait = FluentWait<WebDriver>(driver)                               // Беглое ожидание
        .withTimeout(Duration.ofMillis(fluentInDuration))
        .pollingEvery(Duration.ofMillis(pollingInDuration))
        .ignoreAll(
            listOf(
                NoSuchElementException::class.java,
                ElementNotInteractableException::class.java,
                ElementClickInterceptedException::class.java,
                StaleElementReferenceException::class.java,
                NullPointerException::class.java,
                ElementClickInterceptedException::class.java,

            )
        )
    private val fluentClickWait = FluentWait<WebDriver>(driver)                               // Беглое ожидание
        .withTimeout(Duration.ofMillis(fluentInDuration))
        .pollingEvery(Duration.ofMillis(pollingInDuration))
        .ignoreAll(
            listOf(
                NoSuchElementException::class.java,
                ElementNotInteractableException::class.java,
                ElementClickInterceptedException::class.java,
                StaleElementReferenceException::class.java,
            )
        )

    fun xpath(xpath: String, prefix: String = "ROOT", suffix: String = ""): WebElement? {

        val xpathHtml: String = when (prefix) {
            "ROOT" -> "/html/body//div[@id='root']$xpath$suffix"
            "ROOT666" -> "/html/body//div[@id='root']//div[@data-tdms-view-content='current']$xpath$suffix"
            // "ROOT666"  -> "/html/body//div[@id='root']//div [starts-with(@class,'TdmsView_content_') and not(contains(@style,'none'))]$xpath$suffix"

            "Main-Tree" -> "/html/body//div[@id='root']//div[@data-tdms-view-content='current']//div[@data-reference='Main-Tree']$xpath$suffix"
            "Main-Grid" -> "/html/body//div[@id='root']//div[@data-tdms-view-content='current']//div[@data-reference='Main-Grid']$xpath$suffix"
            "Object-Preview" -> "/html/body//div[@id='root']//div[@data-tdms-view-content='current']//div[@data-reference='object-preview']$xpath$suffix"

            "MODAL" -> "/html/body//div[@id='modalRoot']//div[@data-modal-window='current']$xpath$suffix"
            else -> "/html/body$prefix$xpath$suffix".also { println("xpath пришел неизвестный prefix = $prefix") }
        }
        if (DT > 8) println("xpath будет произведен поиск элемента $xpathHtml")
        return try {  // fluentOutWait.until
            //Эксперимент
            val element =   fluentInWait.until ( presenceOfElementLocated(By.xpath(xpathHtml)) )
//            val element =   fluentInWait.until { driver.findElement(By.xpath(xpathHtml)) }
//            val element =  driver.findElement(By.xpath(xpathHtml))
            if (element == null) println("************xpath не найден элемент********* -> $xpathHtml")
            else if (DT > 7) println("xpath найден элемент $xpathHtml")
            element
        } catch (e: Exception) {
            println("&&&&&&&&&xpath catch TIME OUT за $repeateIn опросов по 1 сек xpathHtml=$xpathHtml \n ${e.stackTrace}&&&&&&&&&")
            null
        }
    }

    //Эксперимент
    fun byID(id: String): WebElement? =  fluentInWait.until ( presenceOfElementLocated(By.id(id)))
//    fun byID(id: String): WebElement? = driver.findElement(By.id(id))
    fun byIDs(id: String): Boolean = driver.findElements(By.id(id)).isNotEmpty()


    //Эксперимент
    fun byIDClick(id: String) =    // Click
        fluentClickWait.until { fluentInWait.until(elementToBeClickable(By.id(id))).click() }

//    fun byIDClick(id: String) = fluentClickWait.until(elementToBeClickable(byID(id))).click()
//    fun byIDClick(id: String) = fluentClickWait.until { byID(id)?.click() }
//    fun byIDClick(id: String) =  driver.findElement(By.id(id)).click()


    fun byIDPressed(id: String): Boolean = byID(id)?.getAttribute("class")?.contains("_pressed_") ?: false
    fun byXpath(xpath: String): WebElement? = driver.findElement(By.xpath(xpath))

    //Эксперимент
    fun xpathClick(xpath: String, prefix: String = "", suffix: String = "") =
        fluentClickWait.until { fluentOutWait.until(elementToBeClickable(xpath(xpath, prefix, suffix))).click() }
//    fun xpathClick(xpath: String, prefix: String = "", suffix: String = "") = fluentOutWait.until {
//        xpath(xpath, prefix, suffix)?.click() }

    fun reference(data_reference: String, prefix: String = "ROOT", suffix: String = ""): WebElement? =
        fluentOutWait.until { xpath("//*[@data-reference='$data_reference']", prefix, suffix) }

    //Эксперимент
    fun referenceClick(data_reference: String, prefix: String = "ROOT", suffix: String = ""): Boolean =
        fluentClickWait.until { reference(data_reference, prefix, suffix)?.click() != null }
//    fun referenceClick(data_reference: String, prefix: String = "ROOT", suffix: String = ""): Boolean =
//        reference(data_reference, prefix, suffix)?.click() != null
    fun referenceClickSend(data_reference: String, prefix: String = "ROOT", text:String = "", suffix: String = ""): Boolean =
        reference(data_reference, prefix, suffix)?.SendKeys(text) != null

    fun referencePressed(data_reference: String, prefix: String = "ROOT", suffix: String = ""): Boolean {
        val rezult1 = reference(data_reference, prefix, suffix)?.getAttribute("class")
        val rezult2 = rezult1?.contains("_pressed_") ?: false
        return rezult2
    }

    fun referenceWaitText(reference: String, text: String, prefix: String = "ROOT", suffix: String = "",cont:Boolean = false): Boolean {
        if (cont)  fluentInWait.until {
              reference("ATTR_USER_QUERY_NAME", "Object-Preview", "//descendant::input")?.getAttribute("value")
                        ?: "NONE".contains(text) // $localDateNow" )
            }.also { return true }
            else
        fluentInWait.until(textToBePresentInElement(xpath("//*[@data-reference= '$reference']", prefix, suffix), text)).also { return true }
    }
  /*   нигде не используется
    fun qtip(qtip: String, prefix: String = "ROOT", suffix: String = ""): WebElement? = xpath(
        "// *[contains(@title, '$qtip')]", prefix, suffix
    )
    fun qtipPressed(qtip: String, prefix: String = "ROOT", suffix: String = ""): Boolean {
        val rezult1 = qtip(qtip, prefix, suffix)?.getAttribute("class")
        val rezult2 = rezult1?.contains("_pressed_") ?: false
        return rezult2
    }
    fun qtipClick(qtip: String, prefix: String = "ROOT666", suffix: String = ""): Boolean =
        qtip(qtip, prefix, suffix)?.click() != null
*/
    fun headerWait(title: String): Boolean = fluentInWait.until(
        textToBePresentInElementLocated(
            By.xpath("/html/body//div[@id='modalRoot']//div[@data-modal-window='current']//span[starts-with(@class, 'Header_headerTitle_')]"),
            title
        )
    )

    fun closeX() = WebDriverWait(driver, Duration.ofSeconds(WebDriverDuration))
        .until(elementToBeClickable(xpath("//button[@data-reference='modal-window-close-button']", "MODAL")))
        .click()

    // ok-modal-window-btn  ok-modal-window-btn
    fun OK(OK: String = "ok-modal-window-btn") =  referenceClick(OK, "MODAL")
    //xpath("//div[@id='modalRoot']//div[@data-modal-window='current']//*[@data-reference='ok-modal-window-btn']]","")?.click()

    fun closeEsc(n: Int = 1) = repeat(n) { Actions(driver).sendKeys(Keys.ESCAPE).perform() }
        .also { if (DT > 7) println("ESC $n раз закрыть что-либо (окно, поле и.т.д) ESC драйвера ") }

    fun titleContain(title: String): Boolean = fluentOutWait.until(titleContains(title))

}