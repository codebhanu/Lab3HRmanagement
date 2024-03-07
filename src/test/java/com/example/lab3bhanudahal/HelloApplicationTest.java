package com.example.lab3bhanudahal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloApplicationTest {

    @Test
    void totalsalary() {
        HelloApplication instance=new HelloApplication();
        assertEquals(instance.totalsalary(4000),49000);
    }
}