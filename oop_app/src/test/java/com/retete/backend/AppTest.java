package com.retete.backend;

import com.retete.backend.service.IngredienteServiceTest;
import com.retete.backend.service.RetetaServiceTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Unit test for simple App.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ RetetaServiceTest.class, IngredienteServiceTest.class })
public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite();
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {

    }
}
