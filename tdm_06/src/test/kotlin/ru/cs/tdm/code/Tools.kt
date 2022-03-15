package ru.cs.tdm.code

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.ExpectedConditions.*
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


class Tools(val driver: WebDriver) {
    private val webDriverWait = WebDriverWait(driver, Duration.ofSeconds(50))   // Явное ожидание
    //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))                 // Неявное ожидание
    private val fluentWait = FluentWait<WebDriver>(driver)                              // Беглое ожидание
                                        .withTimeout(Duration.ofSeconds(60))
                                        .pollingEvery(Duration.ofSeconds(1))
                                        .ignoreAll(listOf( NoSuchElementException::class.java ,
                                                ElementNotInteractableException::class.java,
                                                ElementClickInterceptedException::class.java,
                                                StaleElementReferenceException::class.java))


    fun xpathLast(xpath: String): WebElement? {
        val listElements = webDriverWait.until(presenceOfAllElementsLocatedBy(By.xpath(xpath)))?: return null
            println("найдено ${listElements.size} элементов $xpath")
        return listElements.lastOrNull()
    }

    fun qtipLast(qtip: String): WebElement? = fluentWait.until{ xpathLast("//*[contains(@data-qtip, '$qtip')]") }

    fun qtipLastClass(qtip: String): String? {
        repeat(600) {
            try {
               val element = fluentWait.until { qtipLast(qtip) }
                return element?.getAttribute("class")
            } catch (e: StaleElementReferenceException) {
                Thread.sleep(100)
            }
        }
        return null
    }

    fun qtipClickLast(qtip: String) = fluentWait.until{ qtipLast(qtip)?.click() }

    fun qtipPressedLast(qtip: String): Boolean = qtipLast(qtip)?.getAttribute("aria-pressed") =="true"

    fun closeXLast() = qtipClickLast("Закрыть диалог")

    fun yesClickLast() = fluentWait.until{ xpathLast("//span[text()='Да']/ancestor::a")?.click() }

    fun closeEsc(): Boolean {
        println("закрыть что-либо (окно, поле и.т.д) ESC драйвера ")
        Actions(driver).sendKeys(Keys.ESCAPE).perform()
        return true
    }
    fun xpathClickMenu(menu:String) {
        repeat(600) {
            try {
                fluentWait.until{
                    xpathLast("//span[starts-with(@id, 'menuitem-') and contains(@id, '-textEl') and  contains(text(),\'$menu\')]/parent::a")
                    }?.click()
                return
            } catch (e: ElementNotInteractableException) {
                Thread.sleep(100)
            }
             catch (e: StaleElementReferenceException) {
                Thread.sleep(100)
            }
        }
    }

    fun xpathGetText(xpath: String): String = fluentWait.until { xpathLast(xpath)?.text ?:"NULL" }
    fun xpathWaitTextTry(xpath: String, text: String): Boolean {
        repeat(600) {
           if (xpathGetText(xpath).contains(text)) return true
            Thread.sleep(100)
        }
        return false
    }
    fun xpathWaitText(xpath: String, text: String): Boolean =
        webDriverWait.until (textToBePresentInElement(xpathLast(xpath), text)) //{ xpathLast(xpath)?.text ?:"" }
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

    fun editDialogTitle(): String =
        xpathLast("//div[starts-with(@id, 'tdmsEditObjectDialog-') and contains(@id, '_header-title-textEl') and not(contains(@id, 'ghost'))]")?.text?:"NULL"
    fun editDialogTitleWait(title: String): Boolean =
        xpathWaitTextTry("//div[starts-with(@id, 'tdmsEditObjectDialog-') and contains(@id, '_header-title-textEl') and not(contains(@id, 'ghost'))]",title)

    fun selectedDialogTitle(): String =
        xpathLast("//div[starts-with(@id, 'tdmsSelectObjectDialog-') and contains(@id, '_header-title-textEl') and not(contains(@id, 'ghost'))]")?.text?:"NULL"
    fun selectedDialogTitleWait(title: String): Boolean =
        xpathWaitTextTry("//div[starts-with(@id, 'tdmsSelectObjectDialog-') and contains(@id, '_header-title-textEl') and not(contains(@id, 'ghost'))]",title)
}
