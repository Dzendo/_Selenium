package ru.cs.tdm.code

import org.apache.commons.io.FileUtils
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * screenShot
 */
fun screenShot(name: String = "image", driver:WebDriver) {
    val scrFile = (driver as TakesScreenshot).getScreenshotAs<File>(OutputType.FILE)
    val sdf = SimpleDateFormat("ddMMyyyyhhmmss")
    val pathName = "./$name${sdf.format(Date())}.png"
    FileUtils.copyFile(scrFile, File(pathName))
    if (6 >5) println("Скрин $name сохранен в $pathName")
}
