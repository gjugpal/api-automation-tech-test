package com.geek;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import lombok.Getter;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;

public class BaseTest {

    @Getter private static ExtentTest testReporter;
    private static ExtentTest testClassReporter;
    private static ExtentReports extentReports;

    @BeforeSuite(alwaysRun = true)
    public void beforeTestSuiteSetup() {

        ExtentSparkReporter spark = new ExtentSparkReporter("report.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(spark);
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetup(ITestContext context) {
        // Need to set this back to null as you can't get the name of the test class at this point
        testClassReporter = null;
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTestSetup(Method m) {

        if(testClassReporter == null) {
            String[] tmp = m.getDeclaringClass().getPackageName().split("[.]");
            String name = tmp[tmp.length -1] + "." + m.getDeclaringClass().getSimpleName();
            testClassReporter = extentReports.createTest(name);
        }

        testReporter = testClassReporter.createNode(m.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void afterTestTearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE)
            testReporter.fail(result.getThrowable());
        else if (result.getStatus() == ITestResult.SKIP)
            testReporter.skip(result.getThrowable());
        else
            testReporter.pass("Test passed");

        extentReports.flush();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws IOException {
        extentReports.flush();
    }
}
