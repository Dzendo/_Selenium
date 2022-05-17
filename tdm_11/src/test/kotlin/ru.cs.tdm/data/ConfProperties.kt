package ru.cs.tdm.data

import java.io.FileInputStream
import java.io.IOException
import java.util.*

/**
 * ConfProperties читает записанные в файл «conf.properties» значения
 */
object ConfProperties {
    private lateinit var fileInputStream: FileInputStream
    private lateinit var PROPERTIES: Properties

    init {
        try {
            //указание пути до файла с настройками
            fileInputStream = FileInputStream("src/test/resources/conf.properties")
            PROPERTIES = Properties()
            PROPERTIES.load(fileInputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            //обработка возможного исключения (нет файла и т.п.)
        } finally {
           // if (fileInputStream != null)
                try {
                fileInputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * метод для возврата строки со значением из файла с настройками
     */
    fun getProperty(key: String?): String {
        return PROPERTIES.getProperty(key)
    }
}