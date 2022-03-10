package ru.cs.tdm.code

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


class Tools(val driver: WebDriver) {
    private val webDriverWait = WebDriverWait(driver, Duration.ofSeconds(20))  // Явное ожидание
    //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))  // Неявное ожидание
    private val fluentWait = FluentWait<WebDriver>(driver)    // Беглое ожидание
                                        .withTimeout(Duration.ofSeconds(30))
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

    fun qtipClickLast(qtip: String) = fluentWait.until{ qtipLast(qtip)?.click() }

    fun closeXLast() = qtipClickLast("Закрыть диалог")

    fun yesClickLast() = fluentWait.until{ xpathLast("//span[text()='Да']/ancestor::a")?.click() }

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