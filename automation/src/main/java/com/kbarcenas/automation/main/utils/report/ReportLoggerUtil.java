package com.kbarcenas.automation.main.utils.report;

import org.springframework.stereotype.Component;

import com.aventstack.extentreports.Status;
import io.restassured.response.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ReportLoggerUtil {

    public void info(String msg) {
        ExtentTestManager.getTest().log(Status.INFO, msg);
    }

    public void info(Response resp){
        ExtentTestManager.getTest().log(Status.INFO, "Status Code: "+resp.getStatusCode());
        ExtentTestManager.getTest().log(Status.INFO, "Body: "+resp.getBody().asPrettyString());
        ExtentTestManager.getTest().log(Status.INFO, "Headers: "+resp.getHeaders());
    }

}
