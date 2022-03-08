package ru.cs.tdm.code

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


class Tools(val driver: WebDriver) {
    private val webDriverWait = WebDriverWait(driver, Duration.ofSeconds(60))

    fun xpathLast(xpath: String): WebElement? {
        val listElements = webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)))?: return null
            println("найдено ${listElements.size} элементов $xpath")
        return listElements.last()
        /*
         val listElements = driver.findElements(By.xpath("$xpath")) ?: return null
        println ("найдено ${listElements.size} элементов $xpath")
        return listElements.last()
         */
    }
    fun qtipLast(qtip: String): WebElement? = xpathLast("//*[contains(@data-qtip, '$qtip')]")

    fun qtipClickLast(qtip: String) {
        Thread.sleep(5000)
       // webDriverWait.until(ExpectedConditions.stalenessOf(qtipLast(qtip)))
        try {
            qtipLast(qtip)?.click()
            //webDriverWait.until(ExpectedConditions.elementToBeClickable(qtipLast(qtip))).click()
            //(driver as JavascriptExecutor).executeScript("arguments[0].click()", qtipLast(qtip))
        }
        catch(ex: StaleElementReferenceException) {
            qtipLast(qtip)?.click()
            //webDriverWait.until(ExpectedConditions.elementToBeClickable(qtipLast(qtip))).click()
        }

    }

    fun qtipClickLast0(qtip: String) {

      webDriverWait.until(ExpectedConditions.elementToBeClickable(qtipLast(qtip))).click()

    }
    fun closeXLast(): Boolean {
         qtipClickLast("Закрыть диалог")
        return true
      //return xpathLast("//div[contains(@data-qtip, 'Закрыть диалог')]").also { it?.click() } != null
       //return xpathLast("//div[contains(@data-qtip, 'Закрыть диалог') and contains(@class, 'x-tool-pressed')]/div").also { it?.click() } != null
    }
        /*webDriverWait.until(ExpectedConditions.elementToBeClickable(
                xpathLast("//div[contains(@data-qtip, 'Закрыть диалог')]/div"))
            ).also { it?.click() }  != null
        */
    /*
    <div class="x-tool x-box-item x-tool-default x-tool-after-title x-tool-pressed" role="presentation" id="tool-1372" data-qtip="Закрыть диалог" style="left: 696px; top: 0px; margin: 0px;"><div id="tool-1372-toolEl" data-ref="toolEl" class="x-tool-tool-el x-tool-img x-tool-close " role="presentation"></div></div>
    <div class="x-tool x-box-item x-tool-default x-tool-after-title" role="presentation" id="tool-1228" data-qtip="Закрыть диалог" style="left: 1509px; top: 0px; margin: 0px;"><div id="tool-1228-toolEl" data-ref="toolEl" class="x-tool-tool-el x-tool-img x-tool-close " role="presentation"></div></div>
     */
    fun yesClickLast(): Boolean =
    webDriverWait.until(ExpectedConditions.elementToBeClickable(
        xpathLast("//span[text()='Да']/ancestor::a"))).also { it?.click() } != null

    fun closeEsc(): Boolean {
        println("закрыть что-либо (окно, поле и.т.д) ESC драйвера ")
        Actions(driver).sendKeys(Keys.ESCAPE).perform()
        return true
    }
    fun xpathGetText(xpath: String): String = xpathLast(xpath)?.text ?:""

    fun windowTitle(): String {
    return xpathLast("//div[starts-with(@id, 'window') and contains(@id, 'title') and contains(@id, 'textEl') and not(contains(@id, 'ghost'))]")?.text?:""
    }


}