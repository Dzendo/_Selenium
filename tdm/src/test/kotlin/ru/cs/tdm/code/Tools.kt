package ru.cs.tdm.code

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

class Tools(val driver: WebDriver) {

    fun xpathLast(xpath: String): WebElement?{

        val listElements = driver.findElements(By.xpath("$xpath")) ?: return null
        println ("найдено ${listElements.size} элементов $xpath")
        return listElements.last()
    }
    fun qtipLast(qtip: String): WebElement? = xpathLast("//*[contains(@data-qtip, '$qtip')]")


    fun qtipClickLast(qtip: String): Boolean = qtipLast(qtip).also { it?.click() } != null

    fun closeXLast(): Boolean =
        xpathLast("//div[contains(@data-qtip, 'Закрыть диалог')]").also { it?.click() } != null

    fun yesClickLast(): Boolean =
        xpathLast("//span[text()='Да']/ancestor::a").also { it?.click() } != null

    fun closeEsc(): Boolean {
        println("закрыть что-либо (окно, поле и.т.д) ESC драйвера ")
        Actions(driver).sendKeys(Keys.ESCAPE).perform()
        return true
    }
    fun xpathGetText(xpath: String): String = xpathLast(xpath)?.text ?:""


}