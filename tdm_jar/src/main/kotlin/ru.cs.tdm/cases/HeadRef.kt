package ru.cs.tdm.cases

import org.openqa.selenium.WebDriver
import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.*
import org.openqa.selenium.chrome.ChromeDriver
import org.junit.jupiter.api.Assertions.*
import org.openqa.selenium.By
import org.openqa.selenium.Point
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated
import ru.cs.tdm.code.Login
import ru.cs.tdm.code.Tools
import ru.cs.tdm.data.ConfProperties

/**
 *
 * data-reference="SUB_SYSADMIN"
 */
// ������� ��� ������ ��� ������, �� ����� �� ������������
@DisplayName("Testing Tools Menu-Icons Test")
// ��������� Junit ��������� ����� �� ��������. ����� �� �����������, �������� ��� ��������� �������
@TestMethodOrder(MethodOrderer.MethodName::class)
// ����� �������� � ������� ����� ��� ��������� - ���������� ������
class HeadRef {
    // ����������� ���� - �������������� ��� ������ ���������, � �� ��� �������� ����������
    // BeforeAll � AfterAll ������ ���� ������������ ���������� Junit
    // � ����������� ��� ����������, �������� ��� ����������
    companion object {
        // �������� ����������, ��� �� ������ �� ������ �����, � ��� ���������� ��� ����� � ������
        const val threadSleep = 1000L // �������� ��� ��� ���� 1���
        const val DT: Int = 9  // ������� ���������� ���������� 0 - ������ �� ��������, 9 - ���
        const val NN: Int = 3 // ���������� ���������� ������

        // ���������� ��� ��������
        lateinit var driver: WebDriver

        // ���������� ���������� �� ��������� ����� ������-��������
        lateinit var tools: Tools
        // ������� ��� ��������� ����� ���������� ����� ������
        lateinit var loginIN: String
        lateinit var passwordIN: String

        /**
         * ������������� �������������� ���������
         * ��������������: �� ���������� ������� � ����� ��������.
         * ��� ����� �������� � ���������������� ������� ��������.
         * ��������, ��������� �������� �������� 10 ������ � ������ �������� 15 ������
         * ����� �������� � �������� ����� 20 ������.
         */
        // ���������� ������, ������� �������, ��� ��� ������� ����� ��������� � Java �����������
        @JvmStatic
        // ��������� Junit ��������� ���� ��� ����� ����� �������
        @BeforeAll
        fun beforeAll() {
            if (DT > 7) println("����� BeforeAll")
            // �������� ���������� �������� (�.�. �� �������� � �������� ����������):
            WebDriverManager.chromedriver().setup()
            driver = ChromeDriver()
            //���� ��������������� �� ������ ������ �����-1500 1500 3000 2000,0
            driver.manage().window().position = Point(2000, -1000)
            driver.manage().window().maximize()

            // ������� ���������� ������� � �������� ������ �� ���.
            // � �������� ��������� ��������� ��������� ����� ���� ������ driver,
            tools = Tools(driver)

            val loginpage = ConfProperties.getProperty("loginpageTDM")
            if (DT > 8) println("�������� �������� $loginpage")
            driver.get(loginpage)

            val login = ConfProperties.getProperty("loginTDM")
            val password = ConfProperties.getProperty("passwordTDM")
            if (DT > 8) println("login= $login   password= $password")
            Login(driver).loginIn(login, password)
            // ��������� ����� � ������ ��� ��������� ����� - �������.
            loginIN = login
            passwordIN = password
        }

        @JvmStatic
        // ��������� Junit ��������� ���� ��� ����� ���� ������
        @AfterAll
        fun afterAll() {
            //tools.idList()
            if (DT > 7) println("����� BeforeAll")
            tools.closeEsc5()
            Login(driver).loginOut()
            driver.quit() //  �������� ���� ��������
        }
    }  //����� companion object

    // ������� ����������� �� ��� �����

    // ��� ������, ����������� ����� ������ ������, ������ �� ������
    @BeforeEach
    fun beforeEach() {
        if (DT > 7) println("����� BeforeEach �������")
        //driver.navigate().refresh()
    }

    // ������� ����������� �� ��� �����

    // ��� ������, ����������� ����� ������� �����, ������ �� ������
    @AfterEach
    fun afterEach() {
        if (DT > 7) println("����� AfterEach �������")
        tools.closeEsc5()
        //driver.navigate().refresh()
    }

    /**
     * �������� ����� ������� �� ������ ���� � ������� ������
     */

    // ��������� Junit5 ��������� ������� ������ - ������ �� ���� ����� ������� � ������
    // HeadMenuTest ��� ����� ���������� �� ������� ���� - ����� ��������� ��������
    @Nested
    @DisplayName("Testing each menu separately")
    inner class HeadMenuTest {

        @BeforeEach
        fun beforeEach() {
            if (DT > 7) println("����� inner Head BeforeEach")
            tools.qtipClickLast("�������")
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast("�������"))
        }

        @AfterEach
        fun afterEach() {
            if (DT > 7) println("����� inner Head AfterEach 5 ��� closeEsc")
            tools.closeEsc5()
        }

        @RepeatedTest(NN)
        @DisplayName("�������")
        fun mainMenuTest() {
            val mainMenu = "�������"
            if (DT > 8) println("Test ������� �� $mainMenu TDMS Web")
            // ���� �� ������� � data-qtip="�������"
            tools.qtipClickLast(mainMenu)
            // ��������� ��������� �������� Junit
            assertTrue(tools.titleContain("TDM365"))
            // ���������, ��� �������� ������ �������
            assertTrue(tools.qtipPressedLast("�������"))
        }

        @RepeatedTest(NN)
        @DisplayName("������� ����")
        fun workTableTest() {
            val workTable = "������� ����"
            if (DT > 8) println("Test ������� �� $workTable")
            tools.qtipClickLast(workTable)
            assertTrue(tools.titleContain(workTable))
            assertTrue(tools.qtipPressedLast(workTable))
        }

        @RepeatedTest(NN)
        @DisplayName("�������")
        fun objectsTest() {
            val objects = "�������"
            if (DT > 8) println("Test ������� �� $objects")
            tools.qtipClickLast(objects)
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast(objects))
        }

        @RepeatedTest(NN)
        @DisplayName("�����")
        fun mailTest() {
            val mail = "�����"
            if (DT > 8) println("Test ������� �� $mail")
            tools.qtipClickLast(mail)
            assertTrue(tools.titleContain(mail))
            assertTrue(tools.qtipPressedLast(mail))
        }

        @RepeatedTest(NN)
        @DisplayName("���������")
        fun meetingTest() {
            var meeting = "���������"
            var title = meeting
            if (driver.findElements(By.xpath("//*[contains(@data-qtip, '���')]")).size > 0) {
                meeting = "���"
                title = "������"
            }
            if (DT > 8) println("Test ������� �� $meeting")
            tools.qtipClickLast(meeting)
            assertTrue(tools.titleContain(title))
            assertTrue(tools.qtipPressedLast(meeting))
        }

        @RepeatedTest(NN)
        @DisplayName("��������� �����")
        fun ganttchartTest() {  //(repetitionInfo: RepetitionInfo) {

            //if (repetitionInfo.currentRepetition % 10 == 1) driver.navigate().refresh()
            val ganttchart = "��������� �����"
            if (DT > 8) println("Test ������� �� $ganttchart")
            tools.qtipClickLast(ganttchart)
            //assertTrue(tools.titleContain(ganttchart))  // ��� ���������
            assertTrue(tools.qtipPressedLast(ganttchart))
            Thread.sleep(threadSleep)
            driver.navigate().refresh()
            Thread.sleep(threadSleep)
            if (driver.title == "Tdms") Login(driver).loginIn(loginIN, passwordIN) // �������
        }

        @RepeatedTest(NN)
        @DisplayName("�������")
        fun helpTest() {
            val help = "�������"
            if (DT > 8) println("Test ������� �� $help")
            tools.qtipClickLast(help)
            //assertFalse(tools.titleContain(help))      // ���������!!! NOT
            assertTrue(tools.qtipPressedLast(help))
        }

        @RepeatedTest(NN)
        @DisplayName("������")
        fun searchTest() {
            val search = "������"
            if (DT > 8) println("Test ������� �� $search")
            tools.qtipClickLast("������� ����")  // ������ TDMS - ���������� ������ !! ������
            tools.qtipClickLast("�������")
            tools.qtipLast("������� �����")?.sendKeys("�������")
            tools.qtipClickLast(search)
            assertTrue(tools.titleContain("����������"))
            // �� ���� � data-qtip="������� �����" �������� �������� ����� ���� � �������� � �������,
            // ���� ��, �� ���� ������, ���� ���, �� ����
            assertEquals("�������", tools.qtipLast("������� �����")?.getAttribute("value"))
            tools.qtipClickLast("��������")
            // �����������, ��� ���� ������ ������
            assertEquals("", tools.qtipLast("������� �����")?.getAttribute("value"))
        }

        @RepeatedTest(NN)
        @DisplayName("�����������")
        fun notificationTest() {
            val notification = "�����������"
            if (DT > 8) println("Test ������� �� $notification")
            tools.qtipClickLast(notification)
            // ����������� ��������� ������������ ���� � ���������, ��� �� ���� ���������
            assertEquals("���� ���������", tools.windowTitle())
            tools.closeXLast()
        }
    }
    /**
     * �������� ����� ������� �� ������ ������������
     * �� ������ ������ ��� SYSADMIN ������ �������
     * ���������� ������� ����������� ���� � ��������� ������
     */
    @Nested
    @DisplayName("Testing Tools Box")
    @TestMethodOrder(MethodOrderer.MethodName::class)
    inner class ToolTest {
        @BeforeEach
        fun beforeEach() {
            if (DT > 7) println("����� inner Tools BeforeEach")
            tools.qtipClickLast("�������")
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast("�������"))
        }

        @AfterEach
        fun afterEach() {
            if (DT > 7) println("����� inner Tools AfterEach ���� ��� closeEsc")
            tools.closeEsc5()
        }

        @RepeatedTest(NN)
        @DisplayName("��������/������ ������")
        // repetitionInfo (Junit) ������, ������� �������� � �.�. ����� ������� ����� repetitionInfo.currentRepetition
        fun open_showTreeTest(){     //(repetitionInfo: RepetitionInfo) {
            // ���� ����� ������� ����� 1,11,21 (������� �� ������� �� 10 ����� 1) � ��, �� �������� �����
            // � TDM ���������� ����� ���� ����� ������ ��������-������
            //if (repetitionInfo.currentRepetition % 10 == 1) driver.navigate().refresh() // ������� ��������� ������
            driver.navigate().refresh()
            val open_showTree = "��������/������ ������"
            if (DT > 8) println("Test ������� �� $open_showTree")
            assertTrue(tools.qtipPressedLast(open_showTree)) // ��������� �� ��������
            tools.referenceClickLast("TDMS_COMMAND_COMMON_SHOWTREE")  // ������ ������
            //tools.qtipClickLast(open_showTree)   // ������ ������
            assertFalse(tools.qtipPressedLast(open_showTree))
            tools.referenceClickLast("TDMS_COMMAND_COMMON_SHOWTREE")  // �������� ������
            //tools.qtipClickLast(open_showTree)   // �������� ������
            assertTrue(tools.qtipPressedLast(open_showTree))
        }

        @RepeatedTest(NN)  // �� ������ reference
        @DisplayName("��������/������ ������ ���������������� ���������")
        fun open_showPreviewPanelTest() {   //(repetitionInfo: RepetitionInfo) {
            // if (repetitionInfo.currentRepetition % 10 == 1) driver.navigate().refresh()
            driver.navigate().refresh()
            val open_showPreviewPanel = "��������/������ ������ ���������������� ���������"
            if (DT > 8) println("Test ������� �� $open_showPreviewPanel")
            assertTrue(tools.qtipPressedLast(open_showPreviewPanel))
            tools.qtipClickLast(open_showPreviewPanel)
            assertFalse(tools.qtipPressedLast(open_showPreviewPanel))
            tools.qtipClickLast(open_showPreviewPanel)
            assertTrue(tools.qtipPressedLast(open_showPreviewPanel))

        }

        @RepeatedTest(NN)
        @DisplayName("������� ������")
        fun filterTest(){ //(repetitionInfo: RepetitionInfo) {
            // if (repetitionInfo.currentRepetition % 10 == 1) driver.navigate().refresh()
            driver.navigate().refresh()
            val filter = "������� ������"
            if (DT > 8) println("Test ������� �� $filter")
            tools.referenceClickLast("CMD_CREATE_USER_QUERY")
            //tools.qtipClickLast(filter)
            assertTrue(tools.titleWait("tdmsEditObjectDialog", "�������������� �������"))
            assertTrue(tools.referenceWaitText("T_ATTR_USER_QUERY_NAME", "������������ �������"))
            tools.closeXLast()
        }

        @RepeatedTest(NN)
        @DisplayName("��������")
        fun renewTest() {  //(repetitionInfo: RepetitionInfo) {
            // if (repetitionInfo.currentRepetition % 10 == 1) driver.navigate().refresh()
            val renew = "��������"
            if (DT > 8) println("Test ������� �� $renew")
            tools.referenceClickLast("TDMS_COMMAND_UPDATE")
            //tools.qtipClickLast(renew)
        }

        @RepeatedTest(NN)
        @DisplayName("����������������� �����")
        fun adminUserTest() {
            val adminUser = "����������������� �����"
            if (DT > 8) println("Test ������� �� $adminUser")
            tools.referenceClickLast("CMD_GROUP_CHANGE")
            //tools.qtipClickLast(adminUser)
            assertTrue(tools.titleWait("window", "�������������� �����"))
            assertTrue(tools.referenceWaitText("STATIC1", "������ �������������"))
            tools.closeXLast()
        }

        @RepeatedTest(NN)
        @Disabled
        @DisplayName("������ �������������")
        fun importUserTest() {
            val importUser = "������ �������������"
            if (DT > 8) println("Test ������� �� $importUser")
            tools.referenceClickLast("CMD_IMPORT_USERS")
            //tools.qtipClickLast(importUser)
            // �������� - ����������� ���� Windows �� ������� ��������� � HTML
            //assertTrue(tools.titleWait("window", "�������������� �����"))
            //assertTrue(tools.referenceWaitText("STATIC1", "������ �������������"))
            //tools.closeXLast()
            tools.closeEsc()
        }

        @RepeatedTest(NN)
        @DisplayName("������� ������ ����������")
        fun createObjectTest() {
            val createObject = "������� ������ ����������"
            if (DT > 8) println("Test ������� �� $createObject")
            tools.referenceClickLast("CMD_OBJECT_STRUCTURE_CREATE")
            //tools.qtipClickLast(createObject)
            assertTrue(tools.titleWait("tdmsEditObjectDialog","�������������� �������"))
            assertTrue(tools.referenceWaitText("T_ATTR_OCC_CODE", "��� �������"))
            tools.closeXLast()
        }

        @RepeatedTest(NN)
        @DisplayName("��������� ������� �����������")
        fun configuringNotificationTest() { //repetitionInfo: RepetitionInfo) {
            val configuringNotification = "��������� ������� �����������"
            //val nomerTesta: Int = repetitionInfo.currentRepetition
            //if ((nomerTesta % 10 == 1)) driver.navigate().refresh()
            //if (DT > 8) println("Test $nomerTesta ������� �� $configuringNotification")
            Thread.sleep(threadSleep *3)
            tools.referenceClickLast("CMD_NOTIFICATIONS_SETTINGS")
            //tools.qtipClickLast(configuringNotification)
            Thread.sleep(threadSleep *2)
            assertTrue(tools.titleWait("tdmsEditObjectDialog", "�������������� �������"))
            assertTrue(tools.referenceWaitText("T_ATTR_NAME", "������������"))
            assertTrue(tools.referenceWaitText("T_ATTR_REGULATION_START_TIME", "����� ������� �������� ����������"))
            tools.closeXLast()
            Thread.sleep(threadSleep *2)
        }
    }  // ����� inner - nested Testing Tools Box

    /**
     * �������� ����� ������� �� ������ ���������� �������
     * �� ������ ������ ��� SYSADMIN �� ������ �������
     * ����� ��������� ����� ������������ ����
     */
    @Nested
    @DisplayName("Testing SubSysadmin")
    @TestMethodOrder(MethodOrderer.MethodName::class)
    inner class SubSysadminTest {
        @BeforeEach
        fun beforeEach() {
            if (DT > 7) println("����� inner SubSysadmin BeforeEach")
            tools.qtipClickLast("�������")
            assertTrue(tools.titleContain("TDM365"))
            assertTrue(tools.qtipPressedLast("�������"))
        }

        @AfterEach
        fun afterEach() {
            if (DT > 7) println("����� inner SubSysadmin AfterEach 5 ��� closeEsc")
            tools.closeEsc5()
        }

        // ��������������� ��������� �������� ���������� ���� SubSysadmin � ��� ����
        private fun openSubSysadmin() {
            repeat(7) {
                ///Thread.sleep(threadSleep)
                tools.referenceClickLast("SUB_SYSADMIN")
                ///Thread.sleep(threadSleep)
                if (tools.qtipLastClass("���� ������������")?.contains("x-btn-menu-active") ?: false) return
                if (DT > 6) println("####### SUB_SYSADMIN ������ *##*$it  �������� ����� $it sec #######")
                repeat(3) { tools.closeEsc() }
                tools.qtipClickLast("�������")
                Thread.sleep(threadSleep * it)
            }
            if (DT > 5) println("&&&&&&&&& �� ��������� SUB_SYSADMIN �� 7 �������  &&&&&&&&&")
            assertTrue(tools.qtipLastClass("���� ������������")?.contains("x-btn-menu-active") ?: false)
        }

        /**
         * ��������������� ��������� ������� �� ������ ���������� ���� SubSysadmin � ����
         * ������� �������� ����� ���� � �������� ����������
         * ������ ������� ��� ������ ���� - ������� � Tools.kt
         */
        private fun clickMenu(menu: String, window: String, title: String): Boolean {
            repeat(7) {
                //openSubSysadmin()
                ///Thread.sleep(threadSleep)
                tools.xpathClickMenu(menu)
                ///Thread.sleep(threadSleep)
                if (tools.titleWait(window, title)) return true
                if (DT > 6) println("####### ����� MENU �� *##*$it �� ��������  $menu - ���  $title ���� $it sec #######")
                tools.closeEsc()
                tools.qtipClickLast("�������")
                Thread.sleep(threadSleep * it)
            }
            if (DT > 5) println("&&&&&&&&& �� �������� $menu �� 7 ������� $title  &&&&&&&&&")
            assertTrue(tools.titleWait(window, title))
            return false
        }

        // data-reference="FORM_SYSTEM_SETTINGS"
        @RepeatedTest(NN)
        @DisplayName("���������� � �������")
        fun systemParametersTest() {
            val systemParameters = "���������� � �������"
            if (DT > 8) println("Test ������� �� $systemParameters")
            openSubSysadmin()
            clickMenu(systemParameters, "window", systemParameters)
            //tools.referenceClickLast("CMD_SYSTEM_SETTINGS")
            //tools.qtipClickLast(systemParameters)
            //println("FORM_SYSTEM_SETTING = ${tools.referenceLast("FORM_SYSTEM_SETTINGS")?.text}")
            assertTrue(tools.referenceWaitText("FORM_SYSTEM_SETTINGS", systemParameters))
            assertTrue(tools.titleWait("window", systemParameters))
            assertTrue(tools.referenceWaitText("T_VER_SERVER", "������ �������"))
            assertTrue(tools.referenceWaitText("T_VER_TDM365", "������ TDM365"))
            tools.closeXLast()
        }

        // data-reference="FORM_ATTRS_LIST"
        @RepeatedTest(NN)
        @DisplayName("��������� ��������")
        fun sysAttributesTest() {
            val sysAttributes = "��������� ��������"
            if (DT > 8) println("Test ������� �� $sysAttributes")
            openSubSysadmin()
            clickMenu(sysAttributes, "window", "��������")
            //println("FORM_ATTRS_LIST = ${tools.referenceLast("FORM_ATTRS_LIST")?.text}")
            // ����� Selenium ������ ����������� �������� � DOM ��������
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='FORM_ATTRS_LIST']")) != null)
            assertTrue(tools.referenceWaitText("FORM_ATTRS_LIST", "��������"))
            assertTrue(tools.titleWait("window", "��������"))
            tools.closeXLast()
        }

        // data-reference="FORM_TREE_OBJS"
        // data-reference="TREE"
        @RepeatedTest(NN)
        @DisplayName("����� ������")
        fun dataTreeTest() {
            val dataTree = "����� ������"
            if (DT > 8) println("Test ������� �� $dataTree")
            openSubSysadmin()
            clickMenu(dataTree, "window", dataTree)
            //println("FORM_TREE_OBJS = ${tools.referenceLast("FORM_TREE_OBJS")?.text}")
            //println("TREE = ${tools.referenceLast("TREE")?.text}")
            // ����� ��������� ������������ �� ���� ������� � DOM presenceOfElementLocated(By locator)
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='FORM_TREE_OBJS']")) != null)
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='TREE']")) != null)
            assertTrue(tools.referenceWaitText("FORM_TREE_OBJS", dataTree))
            assertTrue(tools.referenceWaitText("TREE", "���� ��������"))
            assertTrue(tools.titleWait("window", dataTree))
            tools.closeXLast()
        }

        // data-reference="FORM_EVENTS_LOG"
        // data-reference="GRID"
        @RepeatedTest(NN)
        //@Disabled
        @DisplayName("������ �������")
        fun eventsLogTest() {
            val eventsLog = "������ �������"
            if (DT > 8) println("Test ������� �� $eventsLog")
            openSubSysadmin()
            clickMenu(eventsLog, "window", eventsLog)
            //println("FORM_EVENTS_LOG = ${tools.referenceLast("FORM_EVENTS_LOG")?.text}")
            //println("GRID = ${tools.referenceLast("GRID")?.text}")
            // ����� ��������� ������������ �� ���� ������� � DOM presenceOfElementLocated(By locator)
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='FORM_EVENTS_LOG']")) != null)
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='GRID']")) != null)
            assertTrue(tools.referenceWaitText("FORM_EVENTS_LOG", eventsLog))
            assertTrue(tools.referenceWaitText("GRID", ""))
            assertTrue(tools.titleWait("window", eventsLog))
            tools.closeXLast()
        }

        // data-reference="FORM_SERVER_LOG"
        // data-reference="QUERY_SERVER_LOG"
        @RepeatedTest(NN)
        //@Disabled
        @DisplayName("������ �������")
        fun serverLogTest() {
            val serverLog = "������ �������"
            if (DT > 8) println("Test ������� �� $serverLog")
            openSubSysadmin()
            clickMenu(serverLog, "window", serverLog)
            //println("FORM_SERVER_LOG = ${tools.referenceLast("FORM_SERVER_LOG")?.text}")
            //println("QUERY_SERVER_LOG = ${tools.referenceLast("QUERY_SERVER_LOG")?.text}")
            // ����� ��������� ������������ �� ���� ������� � DOM presenceOfElementLocated(By locator)
            Thread.sleep(threadSleep)
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='FORM_SERVER_LOG']")) != null)
            assertTrue(presenceOfElementLocated(By.xpath("//*[data-reference='QUERY_SERVER_LOG']")) != null)
            assertTrue(tools.referenceWaitText("FORM_SERVER_LOG", serverLog))
            assertTrue(tools.referenceWaitText("QUERY_SERVER_LOG", "Timestamp"))
            assertTrue(tools.titleWait("window", serverLog))
            tools.closeXLast()
        }

        @RepeatedTest(NN)
        @DisplayName("������� ��������� ��������")
        fun delObjectsTest() {
            val delObjects = "������� ��������� ��������"
            if (DT > 8) println("Test ������� �� $delObjects")
            openSubSysadmin()
            clickMenu(delObjects, "tdmsSelectObjectDialog", "�������� ��������� ��������")
            assertTrue(tools.titleWait("tdmsSelectObjectDialog", "�������� ��������� ��������"))
            tools.closeXLast()
        }

        /**
         * �������� ����� ������� �� ������ ������� ����
         * �� ������ ������ ��� SYSADMIN � �������� ��� ����
         * ������ � ������� ����� ������������ ���� ����������
         * ��������� ����� �� ��������� ����� ������ � ��������� ��� ��� � ����
         * ����� ��� ��������� ��������, �.�. �� �������� ��������� �������
         */
        @Nested
        @DisplayName("Testing CETD")
        @TestMethodOrder(MethodOrderer.MethodName::class)
        inner class CETDTest {
            @BeforeEach
            fun beforeEach() {
                if (DT > 7) println("����� inner Tools BeforeEach")
                tools.qtipClickLast("�������")
                assertTrue(tools.titleContain("TDM365"))
                assertTrue(tools.qtipPressedLast("�������"))
            }

            @AfterEach
            fun afterEach() {
                if (DT > 7) println("����� inner Tools AfterEach 5 ��� closeEsc")
                tools.closeEsc5()
            }

            // �������� ��� ����
            private fun openCETD() {
                repeat(7) {
                    openSubSysadmin()
                    Thread.sleep(threadSleep)
                    val click = tools.xpathClickMenu("�������� ��� ����")
                    Thread.sleep(threadSleep)
                    if (click) return
                    if (DT > 6) println("####### ��� ���� ������ *##*$it  �������� ����� $it sec #######")
                    repeat(3) { tools.closeEsc() }
                    tools.qtipClickLast("�������")
                    Thread.sleep(threadSleep * it)
                }
                if (DT > 5) println("&&&&&&&&& �� ��������� ��� ���� �� 7 �������  &&&&&&&&&")
                assertTrue(tools.qtipLastClass("����")?.contains("x-btn-menu-active") ?: false)
            }

            @RepeatedTest(NN)
            @DisplayName("����� - �������� ����� ��� �����������")
            fun flow_2Test() {
                val flow0 = "����� - �������� ����� ��� �����������"
                if (DT > 8) println("Test ������� �� $flow0")
                openCETD()
                clickMenu(flow0, "messagebox", "TDMS")
                assertTrue(tools.titleWait("messagebox","TDMS"))
                tools.closeXLast()
                tools.closeEsc()
            }
            // �������� ������� �� ������ - �� ����
            // ? ���������� ����������� (� ������������)
            @RepeatedTest(NN)
            @DisplayName("����� - �������� ����� � ������������")
            fun flow_1Test() {
                val flow0 = "����� - �������� ����� � ������������"
                if (DT > 8) println("Test ������� �� $flow0")
                openCETD()
                clickMenu(flow0, "messagebox", "TDMS")
                assertTrue(tools.titleWait("messagebox","TDMS"))
                tools.closeXLast()
                tools.closeEsc()
            }

            @RepeatedTest(NN)
            @DisplayName("����� - �������� ������� �������")
            fun flowTest() {
                val flow = "����� - �������� ������� �������"
                if (DT > 8) println("Test ������� �� $flow")
                openCETD()
                //tools.xpathClickMenu(flow)
                clickMenu(flow, "messagebox", "TDMS")
                assertTrue(tools.titleWait("messagebox","TDMS"))
                val msgText = tools.xpathGetText("//div[starts-with(@id,'messagebox-') and  contains(@id,'-msg')]")
                //assertTrue(msgText.contains("��")) // - ���� GUID ������� �������"))
                //assertTrue(msgText.contains("���")) // - ����� ������� � �������"))
                tools.closeXLast()
                assertTrue(tools.titleWait("messagebox","���� ��������"))
                tools.closeXLast()
                Thread.sleep(threadSleep)
                assertTrue(tools.titleWait("messagebox","TDMS"))
                tools.closeXLast()
            }

            @RepeatedTest(NN)
            @DisplayName("����� 0 - �������� �������")
            fun flow0Test() {
                val flow0 = "����� 0 - �������� �������"
                if (DT > 8) println("Test ������� �� $flow0")
                openCETD()
                clickMenu(flow0, "messagebox", "TDMS")
                assertTrue(tools.titleWait("messagebox","TDMS"))
                tools.closeXLast()
            }

            //@Disabled
            @RepeatedTest(NN)
            @DisplayName("����� 1 - �������� ������������� ���������")
            fun flow1Test() {
                val flow1 = "����� 1 - �������� ������������� ���������"
                if (DT > 8) println("Test ������� �� $flow1")
                openCETD()
                clickMenu(flow1, "window", "����� ��������")
                //tools.closeXLast()
                assertTrue(tools.titleWait("window", "����� ��������"))
               // tools.idList()
                tools.closeXLast()
                //assertTrue(tools.titleWait("messagebox","TDMS"))
                //tools.closeXLast()
            }

            @RepeatedTest(NN)
            @DisplayName("����� 2.1 - ����� � ���������� �������� ��")
            fun flow2Test() {
                val flow2 = "����� 2.1 - ����� � ���������� �������� ��"
                if (DT > 8) println("Test ������� �� $flow2")
                openCETD()
                clickMenu(flow2, "messagebox", "TDMS")
                assertTrue(tools.titleWait("messagebox","TDMS"))
                tools.closeXLast()
                assertTrue(tools.titleWait("tdmsSelectObjectDialog", "����� ������� ���������"))
                tools.closeXLast()
                //Thread.sleep(threadSleep)
                //assertTrue(tools.titleWait("messagebox","TDMS")) // ��� � 1.2.18
                //tools.closeXLast()
            }

            @RepeatedTest(NN)
            @DisplayName("����� 3 - �������� ������� �� ���������")
            fun flow3Test() {
                val flow3 = "����� 3 - �������� ������� �� ���������"
                if (DT > 8) println("Test ������� �� $flow3")
                openCETD()
                clickMenu(flow3, "messagebox", "TDMS")
                assertTrue(tools.titleWait("messagebox","TDMS"))
                tools.closeXLast()
                assertTrue(tools.titleWait("tdmsSelectObjectDialog", "����� ������� ���������"))
                tools.closeXLast()
            }
        }   // ����� �������� ��� ����
    }  // ����� Testing SubSysadmin
}