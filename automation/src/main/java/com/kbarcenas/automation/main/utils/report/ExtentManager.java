package com.kbarcenas.automation.main.utils.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;

@UtilityClass
public class ExtentManager{

	public static final ExtentReports extentReports = new ExtentReports();
	private static final String REPORT_PATH = "./report/report.html";
	private static final String POST_VALIDATION_REPORT = "Automation Report";
	private static final String AUTHOR = "Author";
	private static final String NAME = "Kevin Barcenas";
	
	public static synchronized ExtentReports createExtentReports() {
		ExtentSparkReporter reporter = new ExtentSparkReporter(REPORT_PATH);
		reporter.config().setTheme(Theme.DARK);
		reporter.config().setReportName(POST_VALIDATION_REPORT);
		reporter.config().setDocumentTitle(POST_VALIDATION_REPORT);
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo(AUTHOR, NAME);
		return extentReports;
	}
}