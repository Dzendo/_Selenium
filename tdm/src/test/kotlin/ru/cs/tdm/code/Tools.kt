package ru.cs.tdm.code

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


class Tools(val driver: WebDriver) {
    private val webDriverWait = WebDriverWait(driver, Duration.ofSeconds(60))
    private val fluentWait = FluentWait<WebDriver>(driver)
                                        .withTimeout(Duration.ofSeconds(20))
                                        .pollingEvery(Duration.ofSeconds(1))
                                        .ignoring(NoSuchElementException::class.java,)
                                        .ignoring(ElementNotInteractableException::class.java)
                                        .ignoring(ElementClickInterceptedException::class.java)
                                        .ignoring(StaleElementReferenceException::class.java)

    fun xpathLast(xpath: String): WebElement? {
        val listElements = webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)))?: return null
            println("найдено ${listElements.size} элементов $xpath")
        return listElements.last()
    }
    fun qtipLast(qtip: String): WebElement? = xpathLast("//*[contains(@data-qtip, '$qtip')]")

    fun qtipClickLast(qtip: String) //= fluentWait.until(qtipLast(qtip)).click()

        {
       for (i in 1..20) {
            try {
                val element = qtipLast(qtip)?:continue
                element  .click()
                break
            } catch (ex: Exception ){  //ElementNotInteractableException ){ //StaleElementReferenceException, ) {
                println ("** повтор $i ***** ${ex.toString().substringBefore("\n")}")
                Thread.sleep(1000L)
             continue
            }
        }
    }

    fun closeXLast(): Boolean {
         qtipClickLast("Закрыть диалог")
        return true
    }

    fun yesClickLast(): Boolean =
    webDriverWait.until(ExpectedConditions.elementToBeClickable(
        xpathLast("//span[text()='Да']/ancestor::a"))).also { it?.click() } != null

    fun closeEsc(): Boolean {
        println("закрыть что-либо (окно, поле и.т.д) ESC драйвера ")
        Actions(driver).sendKeys(Keys.ESCAPE).perform()
        return true
    }
    fun xpathGetText(xpath: String): String = xpathLast(xpath)?.text ?:""

    fun titleContain(title: String): Boolean = webDriverWait.until(ExpectedConditions.titleContains(title))

    fun windowTitle(): String {
    return xpathLast("//div[starts-with(@id, 'window') and contains(@id, 'title') and contains(@id, 'textEl') and not(contains(@id, 'ghost'))]")?.text?:""
    }


}