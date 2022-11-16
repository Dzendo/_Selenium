package ru.cs.tdm.data

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

/**
 * ConfProperties читает записанные в файл «conf.properties» значения
 */
object ConfProperties {
    private lateinit var fileInputStream: FileInputStream
    private var properties: Properties = Properties()
    private var fileOpen: Boolean = false

    init {
        properties.setProperty("loginpageTDM","http://tdms-srv2a.csoft-msc.ru:444/client/?classic#objects")
        //properties.setProperty("loginpageTDM","http://tdms-srv2a:442/client/#objects/")
        properties.setProperty("loginTDM","SYSADMIN")
        properties.setProperty("passwordTDM","753951")
        try {
            //указание пути до файла с настройками
            fileInputStream = FileInputStream("conf.properties")
            properties.load(fileInputStream)
            fileOpen = true
            println("Файл конфигурации conf.properties присутствует")
        } catch (e: FileNotFoundException) {
            try{
                fileInputStream = FileInputStream("src/main/resources/conf.properties")
                properties.load(fileInputStream)
                fileOpen = true
                println("Файл конфигурации src/main/resources/conf.properties присутствует")
            } catch (e: FileNotFoundException) {
                println("Файл конфигурации отсутствует")
                fileOpen = false
            }
            //обработка возможного исключения (нет файла и т.п.)
        } finally {
            if (fileOpen) try {
                                fileInputStream.close()
                            } catch (e: IOException) {
                                println("ОШИБКА закрытия Файла конфигурации")
                            }
        }
    }
    /**
     * метод для возврата строки со значением из файла с настройками
     */
    fun getProperty(key: String?): String = properties.getProperty(key)
}