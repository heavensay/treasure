package com.helix.demo.testtool.junit;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class JunitTest {

    /**
     * number equals test
     */
    @Test
    public void numberEqualTest() {
        assertEquals("error", 1, 1);
        assertThat(1, equalTo(new Integer(1)));
    }


    @Test
    public void test2() {
        Process process = new Process();
        process.throwNestedException();

    }
}
