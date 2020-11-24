package com.uOttawaSEG.servicenovigrad;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class projectboringteamTESTS {
    @Test
    public void BranchTest() {
        Branch tester = new Branch();

        tester.setAddress("123 Bank Street");
        tester.setId("a1b2c3d4");
        tester.setName("Southern Novigrad");
        tester.addService("1A2B3C4D","Sword License");
        tester.setWorkingHoursFriday("1:00 - 2:00");
        tester.setWorkingHoursMonday("2:00 - 3:00");
        tester.setWorkingHoursSaturday("3:00 - 4:00");
        tester.setWorkingHoursSunday("4:00 - 6:00");
        tester.setWorkingHoursThursday("5:30 - 9:40");
        tester.setWorkingHoursTuesday("6:30 - 19:50");
        tester.setWorkingHoursWednesday("7:45 - 21:12");

        String expectedTesterAddress="123 Bank Street";
        String expectedTesterId="a1b2c3d4";
        String expectedTesterName="Southern Novigrad";
        String expectedTesterServiceName="Sword License";
        String expectedTesterServiceId="1A2B3C4D";
        String expectedTesterFridayHours="1:00 - 2:00";
        String expectedTesterMondayHours="2:00 - 3:00";
        String expectedTesterSaturdayHours="3:00 - 4:00";
        String expectedTesterSundayHours="4:00 - 6:00";
        String expectedTesterThursday="5:30 - 9:40";
        String expectedTesterTuesday="6:30 - 19:50";
        String expectedTesterWednesday="7:45 - 21:12";

        HashMap testerServices= tester.getServices();
        HashMap testerWorkingHours = tester.getWorkingHours();

        assertEquals(expectedTesterAddress,tester.getAddress());
        assertEquals(expectedTesterId,tester.getId());
        assertEquals(expectedTesterName,tester.getName());
        assertEquals(expectedTesterServiceName,testerServices.get(expectedTesterServiceId));
        assertEquals(expectedTesterFridayHours,testerWorkingHours.get("Friday"));
        assertEquals(expectedTesterMondayHours,testerWorkingHours.get("Monday"));
        assertEquals(expectedTesterSaturdayHours,testerWorkingHours.get("Saturday"));
        assertEquals(expectedTesterSundayHours,testerWorkingHours.get("Sunday"));
        assertEquals(expectedTesterThursday,testerWorkingHours.get("Thursday"));
        assertEquals(expectedTesterTuesday,testerWorkingHours.get("Tuesday"));
        assertEquals(expectedTesterWednesday,testerWorkingHours.get("Wednesday"));


    }

    @Test
    public void Test(){

    }
}