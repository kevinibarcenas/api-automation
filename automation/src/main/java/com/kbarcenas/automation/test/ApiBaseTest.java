package com.kbarcenas.automation.test;

import com.kbarcenas.automation.main.requests.HttpRequests;
import com.kbarcenas.automation.main.utils.report.ReportLoggerUtil;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import com.kbarcenas.automation.main.config.AppPropertiesConfig;
import com.kbarcenas.automation.main.utils.listeners.TestListeners;

@Listeners(TestListeners.class)
@SpringBootTest
public class ApiBaseTest extends AbstractTestNGSpringContextTests {
    protected @Autowired AppPropertiesConfig prop;
    protected @Autowired HttpRequests apiRequests;
    protected @Autowired ReportLoggerUtil htmlLog;

    @BeforeClass
    public void beforeMethod() {
        RestAssured.baseURI = prop.baseUri;
    }

    @AfterClass
    public void afterMethod(ITestContext results) {
    }

    @AfterSuite
    public void afterSuite() throws Exception {
    }
}
