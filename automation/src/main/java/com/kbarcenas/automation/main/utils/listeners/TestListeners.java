package com.kbarcenas.automation.main.utils.listeners;

import com.aventstack.extentreports.Status;
import com.kbarcenas.automation.main.utils.report.ExtentManager;
import com.kbarcenas.automation.main.utils.report.ExtentTestManager;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

@Slf4j
public class TestListeners implements ITestListener {

	private String packageName;

	@Override
	public void onTestStart(ITestResult result) {
		log.info("*** onTestStart ***");
		log.info(result.getTestClass().getName() + " -- " + result.getMethod().getMethodName());
		packageName = (result.getTestClass().getName() + " -- " + result.getMethod().getMethodName());
		ExtentTestManager.startTest(packageName.substring(packageName.indexOf("test.") + 5, packageName.length()), "");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		log.info("*** onTestSuccess ***");
		ExtentTestManager.getTest().log(Status.PASS, "Test passed.");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		log.error("*** onTestFailure ***");
		String trace;
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		Throwable cause = result.getThrowable();
		if (!Objects.isNull(cause)) {
			if (cause.getClass().getName().equalsIgnoreCase("java.lang.AssertionError")) {
				trace = cause.getMessage();
			} else {
				cause.printStackTrace(pw);
				trace = sw.getBuffer().toString();
			}
		} else {
			trace = "Test failed.";
		}
		log.error("trace: " + trace);
		ExtentTestManager.getTest().log(Status.FAIL, trace.replace("\n", "<br>"));
		if (!packageName.toLowerCase().contains("request") || !packageName.contains("api")) {
			try {
				ExtentTestManager.getTest().log(Status.FAIL, "Test Case Failed!");
			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
			}
		} else {
			ExtentTestManager.getTest().log(Status.FAIL, "API test Failed");
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		log.warn("*** onTestSkipped ***");
		String trace = null;
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		Throwable cause = result.getThrowable();
		if (!Objects.isNull(cause)) {
			cause.printStackTrace(pw);
			trace = sw.getBuffer().toString();
			log.error("trace: " + trace);
			ExtentTestManager.getTest().log(Status.SKIP, trace.replace("\n", "<br>"));
		}
		if (!packageName.toLowerCase().contains("request") || !packageName.contains("api")) {
			try {
				ExtentTestManager.getTest().log(Status.SKIP, "Test Case Skipped!");
			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
			}
		}
		ExtentTestManager.getTest().log(Status.SKIP, result.getMethod().getMethodName());
		ExtentManager.extentReports.flush();
	}

	@Override
	public void onFinish(ITestContext context) {
		log.info("*** onFinish ***");
		ExtentTestManager.endTest();
		ExtentManager.extentReports.flush();
	}
}