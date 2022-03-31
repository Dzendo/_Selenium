package ru.cs.tdm.code

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions.*
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.WebDriverWait
import ru.cs.tdm.cases.ToolsTest
import java.time.Duration

const val DT: Int = 9
class Tools(val driver: WebDriver) {
    private val webDriverWait = WebDriverWait(driver, Duration.ofSeconds(7))   // Явное ожидание
    //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7))                 // Неявное ожидание
    private val fluentWait = FluentWait<WebDriver>(driver)                              // Беглое ожидание
                                        .withTimeout(Duration.ofSeconds(7))
                                        .pollingEvery(Duration.ofSeconds(1))
                                        .ignoreAll(listOf( NoSuchElementException::class.java ,
                                                ElementNotInteractableException::class.java,
                                                ElementClickInterceptedException::class.java,
                                                StaleElementReferenceException::class.java))


    fun xpathLast(xpath: String): WebElement? {
        repeat(7) {
            try {
                val listElements = fluentWait.until(presenceOfAllElementsLocatedBy(By.xpath(xpath)))
                if ((listElements!=null) and (listElements.size > 0)) {
                    if (DT >7) println("N$it xpathLast найдено ${listElements.size} элементов $xpath")
                    return listElements.lastOrNull()
                }
                if (DT >6) println("**N$it НЕ найдено элементов $xpath")
               // return null
            } catch (_: TimeoutException) {}
            catch (_: StaleElementReferenceException) {}
            if (DT >5) println("###*##*N$it попытка ####xpathLast не нашлось  поле xpathLast=$xpath #######")
        }
        if (DT >4) println("&&&&&&&&&xpathLast catch TIME OUT за 7 опросов по 1 сек xpathLast=$xpath &&&&&&&&&")
        return null
    }

    fun qtipLast(qtip: String): WebElement? {
        repeat(7) {
            try {
                val element = fluentWait.until { xpathLast("//*[contains(@data-qtip, '$qtip')]") }
                if (element != null) return element
            } catch (_: TimeoutException) {}
            catch (_: StaleElementReferenceException) {}
            if (DT >5) println("###*##*N$it попытка #### qtipLast Не найден qtipLast=$qtip #######")
        }
        if (DT >4) println("&&&&&&&&& qtipLast за 7 опросов по 1 сек qtipLast=$qtip &&&&&&&&&")
        return null
    }
    fun qtipLastClass(qtip: String): String? {
        repeat(7) {
            try {
               val element = fluentWait.until { qtipLast(qtip) }
                if (element != null) return element.getAttribute("class")
            } catch (_: TimeoutException) {}
              catch (_: StaleElementReferenceException) {}
            if (DT >5) println("###*##*N$it попытка #### qtipLastClass не найден класс  опрос по 1 сек qtipLastClass=$qtip #######")
        }
        if (DT >4) println("&&&&&&&&& qtipLastClass за 7 опросов по 1 сек qtipLastClass=$qtip &&&&&&&&&")
        return null
    }

    fun qtipClickLast(qtip: String): Boolean {

        repeat(7) {
            try {
                val element = fluentWait.until { qtipLast(qtip)?.click() }
                if (element != null) return true
            } catch (_: TimeoutException) {}
            catch (_: StaleElementReferenceException) {}
            if (DT >5) println("####*##*N$it попытка ### qtipClickLast Не нажат  qtipClickLast=$qtip #######")
        }
        if (DT >4) println("&&&&&&&&& qtipClickLast за 7 опросов по 1 сек qtipClickLast=$qtip &&&&&&&&&")
        return false
    }
    fun qtipPressedLast(qtip: String): Boolean = qtipLast(qtip)?.getAttribute("aria-pressed") =="true"

    fun closeXLast() = qtipClickLast("Закрыть диалог")

    fun yesClickLast() = fluentWait.until{ xpathLast("//span[text()='Да']/ancestor::a")?.click() }

    fun closeEsc(): Boolean {
        //println("ESC закрыть что-либо (окно, поле и.т.д) ESC драйвера ")
        Actions(driver).sendKeys(Keys.ESCAPE).perform()
        return true
    }
    fun closeEsc5(): Boolean {
        if (DT >7) println("ESC закрыть что-либо (окно, поле и.т.д) ESC драйвера ")
        repeat(5) { closeEsc() }
        return true
    }
    fun xpathClickMenu(menu:String): Boolean {
        repeat(7) {
            try {
                fluentWait.until{
                    xpathLast("//span[starts-with(@id, 'menuitem-') and contains(@id, '-textEl') and  contains(text(),\'$menu\')]/parent::a")
                    }?.click()
                if (DT >7) println(" ClickMenu=$menu ")
                return true
            } catch (_: ElementNotInteractableException) {}
             catch (_: StaleElementReferenceException) {}
            catch (_: TimeoutException) {}
            if (DT >5) println("###N$it попытка #### xpathClickMenu NOT ClickMenu click по 1 сек xpathClickMenu=$menu #######")
        }
        if (DT >4) println("&&&&&&&&&  xpathClickMenu catch NOT ClickMenu за 7 опросов по 1 сек xpathClickMenu=$menu &&&&&&&&&")
        return false
    }

    fun xpathGetText(xpath: String): String = fluentWait.until { xpathLast(xpath)?.text ?:"NULL" }
    fun xpathWaitTextTry(xpath: String, text: String): Boolean {
        repeat(7) {
           if (xpathGetText(xpath).contains(text)) return true
            if (DT >5) println("###N$it попытка #### xpathGetText не взят- ждем $it sec  xpathGetText=$xpath #######")
          //  Thread.sleep(1000)  // необходимо для Comunda
            Thread.sleep(1000L*it)  // необходимо для Comunda
        }
        if (DT >4) println("&&&&&&&&& xpathWaitTextTry за 7 опросов через 1 сек xpathWaitTextTry=$xpath &&&&&&&&&")
        return false
    }
    fun xpathWaitText(xpath: String, text: String): Boolean =
        fluentWait.until (textToBePresentInElement(xpathLast(xpath), text)) //{ xpathLast(xpath)?.text ?:"" }
    fun xpathFluentWaitText(xpath: String, text: String): Boolean =
        fluentWait.until (textToBePresentInElement(xpathLast(xpath), text))
    fun qtipFluentWaitText(qtip: String, text: String): Boolean =
        fluentWait.until (textToBePresentInElement(qtipLast(qtip), text))

    fun titleContain(title: String): Boolean = fluentWait.until(titleContains(title))

    fun windowTitle(): String =
        xpathLast("//div[starts-with(@id, 'window-') and contains(@id, '_header-title-textEl') and not(contains(@id, 'ghost'))]")?.text?:"NULL"
    fun windowTitleWait(title: String): Boolean =
        xpathWaitTextTry("//div[starts-with(@id, 'window-') and contains(@id, '_header-title-textEl') and not(contains(@id, 'ghost'))]",title)

    fun messageTitle(): String =
        xpathLast("//div[starts-with(@id, 'messagebox-') and contains(@id, '_header-title-textEl') and not(contains(@id, 'ghost'))]")?.text?:"NULL"
    fun messageTitleWait(title: String): Boolean =
        xpathWaitTextTry("//div[starts-with(@id, 'messagebox-') and contains(@id, '_header-title-textEl') and not(contains(@id, 'ghost'))]", title)

    fun editDialogT9itle(): String =
        xpathLast("//div[starts-with(@id, 'tdmsEditObjectDialog-') and contains(@id, '_header-title-textEl') and not(contains(@id, 'ghost'))]")?.text?:"NULL"
    fun editDialogTitleWait(title: String): Boolean =
        xpathWaitTextTry("//div[starts-with(@id, 'tdmsEditObjectDialog-') and contains(@id, '_header-title-textEl') and not(contains(@id, 'ghost'))]",title)

    fun selectedDialogTitle(): String =
        xpathLast("//div[starts-with(@id, 'tdmsSelectObjectDialog-') and contains(@id, '_header-title-textEl') and not(contains(@id, 'ghost'))]")?.text?:"NULL"
    fun selectedDialogTitleWait(title: String): Boolean =
        xpathWaitTextTry("//div[starts-with(@id, 'tdmsSelectObjectDialog-') and contains(@id, '_header-title-textEl') and not(contains(@id, 'ghost'))]",title)
    fun selectedGridDialogTitleWait(title: String): Boolean =
        xpathWaitTextTry("//div[starts-with(@id, 'tdmsSelectObjectGridDialog-') and contains(@id, '_header-title-textEl') and not(contains(@id, 'ghost'))]",title)

    fun clickOK(): Boolean {
        repeat(7) {
            try {  // 41e 41a  41e 43a
                val element = fluentWait.until {
                    xpathLast("//span[text() = 'Ок' or text() = 'ОК' or text() = 'Да']/ancestor::a")
                        ?.click() }
                if (element != null) return true
            } catch (_: TimeoutException) {}
            catch (_: StaleElementReferenceException) {}
            if (DT >5) println("####*##*N$it попытка ### qtipClickLast Не нажат  OK #######")
        }
        if (DT >4) println("&&&&&&&&& qtipClickLast за 7 опросов по 1 сек OK &&&&&&&&&")
        return false
    }
}
