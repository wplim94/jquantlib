/*
 Copyright (C) 2008 Renjith Nair

 This source code is release under the BSD License.

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the JQuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <jquant-devel@lists.sourceforge.net>. The license is also available online at
 <http://www.jquantlib.org/index.php/LICENSE.TXT>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.

 JQuantLib is based on QuantLib. http://quantlib.org/
 When applicable, the original copyright notice follows this notice.
 */

package org.jquantlib.testsuite.calendars;

import static org.jquantlib.util.Month.APRIL;
import static org.jquantlib.util.Month.FEBRUARY;
import static org.jquantlib.util.Month.JANUARY;
import static org.jquantlib.util.Month.JUNE;
import static org.jquantlib.util.Month.MAY;
import static org.jquantlib.util.Month.OCTOBER;
import static org.jquantlib.util.Month.SEPTEMBER;

import java.util.ArrayList;
import java.util.List;

import org.jquantlib.QL;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.calendars.Taiwan;
import org.jquantlib.util.Date;
import org.junit.Test;

/**
 * @author Renjith Nair
 *
 *
 */

public class TaiwanCalendarTest {

    private Calendar exchange = null;
	private final List<Date> expectedHol = null;

	public TaiwanCalendarTest() {
		QL.info("\n\n::::: "+this.getClass().getSimpleName()+" :::::");
		this.exchange = Taiwan.getCalendar(Taiwan.Market.TSE);
	}


    // 2002 - year in the past
	@Test
    public void testTaiwanTSEHolidaysYear2002()
    {
       	final int year = 2002;
        QL.info("Testing " + Taiwan.Market.TSE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,JANUARY,year));
    	expectedHol.add(new Date(11,FEBRUARY,year));
    	expectedHol.add(new Date(12,FEBRUARY,year));
    	expectedHol.add(new Date(13,FEBRUARY,year));
    	expectedHol.add(new Date(14,FEBRUARY,year));
    	expectedHol.add(new Date(15,FEBRUARY,year));
    	expectedHol.add(new Date(28,FEBRUARY,year));
    	expectedHol.add(new Date(5,APRIL,year));
    	expectedHol.add(new Date(1,MAY,year));
    	expectedHol.add(new Date(10,OCTOBER,year));

    	// Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2003 - year in the past
	@Test
    public void testTaiwanTSEHolidaysYear2003()
    {
       	final int year = 2003;
    	QL.info("Testing " + Taiwan.Market.TSE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,JANUARY,year));
    	expectedHol.add(new Date(31,JANUARY,year));
    	expectedHol.add(new Date(3,FEBRUARY,year));
    	expectedHol.add(new Date(4,FEBRUARY,year));
    	expectedHol.add(new Date(5,FEBRUARY,year));
    	expectedHol.add(new Date(28,FEBRUARY,year));
    	expectedHol.add(new Date(1,MAY,year));
    	expectedHol.add(new Date(4,JUNE,year));
    	expectedHol.add(new Date(11,SEPTEMBER,year));
    	expectedHol.add(new Date(10,OCTOBER,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }


	// 2004 - year in the past
	@Test
    public void testTaiwanTSEHolidaysYear2004()
    {
       	final int year = 2004;
    	QL.info("Testing " + Taiwan.Market.TSE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,JANUARY,year));
    	expectedHol.add(new Date(21,JANUARY,year));
    	expectedHol.add(new Date(22,JANUARY,year));
    	expectedHol.add(new Date(23,JANUARY,year));
    	expectedHol.add(new Date(26,JANUARY,year));
    	expectedHol.add(new Date(22,JUNE,year));
    	expectedHol.add(new Date(28,SEPTEMBER,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2005 - year in the past
	@Test
    public void testTaiwanTSEHolidaysYear2005()
    {
       	final int year = 2005;
    	QL.info("Testing " + Taiwan.Market.TSE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(4,FEBRUARY,year));
    	expectedHol.add(new Date(7,FEBRUARY,year));
    	expectedHol.add(new Date(8,FEBRUARY,year));
    	expectedHol.add(new Date(9,FEBRUARY,year));
    	expectedHol.add(new Date(10,FEBRUARY,year));
    	expectedHol.add(new Date(11,FEBRUARY,year));
    	expectedHol.add(new Date(28,FEBRUARY,year));
    	expectedHol.add(new Date(5,APRIL,year));
    	expectedHol.add(new Date(2,MAY,year));
    	expectedHol.add(new Date(10,OCTOBER,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2006 - year in the past
	@Test
    public void testTaiwanTSEHolidaysYear2006()
    {
       	final int year = 2006;
    	QL.info("Testing " + Taiwan.Market.TSE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(30,JANUARY,year));
    	expectedHol.add(new Date(31,JANUARY,year));
    	expectedHol.add(new Date(1,FEBRUARY,year));
    	expectedHol.add(new Date(2,FEBRUARY,year));
    	expectedHol.add(new Date(3,FEBRUARY,year));
    	expectedHol.add(new Date(28,FEBRUARY,year));
    	expectedHol.add(new Date(5,APRIL,year));
    	expectedHol.add(new Date(1,MAY,year));
    	expectedHol.add(new Date(31,MAY,year));
    	expectedHol.add(new Date(6,OCTOBER,year));
    	expectedHol.add(new Date(10,OCTOBER,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2007 - year in the past
	@Test
    public void testTaiwanTSEHolidaysYear2007()
    {
       	final int year = 2007;
    	QL.info("Testing " + Taiwan.Market.TSE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,JANUARY,year));
    	expectedHol.add(new Date(19,FEBRUARY,year));
    	expectedHol.add(new Date(20,FEBRUARY,year));
    	expectedHol.add(new Date(21,FEBRUARY,year));
    	expectedHol.add(new Date(22,FEBRUARY,year));
    	expectedHol.add(new Date(23,FEBRUARY,year));
    	expectedHol.add(new Date(28,FEBRUARY,year));
    	expectedHol.add(new Date(5,APRIL,year));
    	expectedHol.add(new Date(6,APRIL,year));
    	expectedHol.add(new Date(1,MAY,year));
    	expectedHol.add(new Date(18,JUNE,year));
    	expectedHol.add(new Date(19,JUNE,year));
    	expectedHol.add(new Date(24,SEPTEMBER,year));
    	expectedHol.add(new Date(25,SEPTEMBER,year));
    	expectedHol.add(new Date(10,OCTOBER,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2008 - Current Year
	@Test
    public void testTaiwanTSEHolidaysYear2008()
    {
       	final int year = 2008;
    	QL.info("Testing " + Taiwan.Market.TSE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,JANUARY,year));
    	expectedHol.add(new Date(4,FEBRUARY,year));
    	expectedHol.add(new Date(5,FEBRUARY,year));
    	expectedHol.add(new Date(6,FEBRUARY,year));
    	expectedHol.add(new Date(7,FEBRUARY,year));
    	expectedHol.add(new Date(8,FEBRUARY,year));
    	expectedHol.add(new Date(11,FEBRUARY,year));
    	expectedHol.add(new Date(28,FEBRUARY,year));
    	expectedHol.add(new Date(4,APRIL,year));
    	expectedHol.add(new Date(1,MAY,year));
    	expectedHol.add(new Date(10,OCTOBER,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2009 - Future Year
	@Test
    public void testTaiwanTSEHolidaysYear2009()
    {
       	final int year = 2009;
    	QL.info("Testing " + Taiwan.Market.TSE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,JANUARY,year));
    	expectedHol.add(new Date(1,MAY,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2010 - Future Year
	@Test
    public void testTaiwanTSEHolidaysYear2010()
    {
       	final int year = 2010;
    	QL.info("Testing " + Taiwan.Market.TSE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(1,JANUARY,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2011 - Future Year
	@Test
    public void testTaiwanTSEHolidaysYear2011()
    {
       	final int year = 2011;
    	QL.info("Testing " + Taiwan.Market.TSE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(28,FEBRUARY,year));
    	expectedHol.add(new Date(10,OCTOBER,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

	// 2012 - Future Year
	@Test
    public void testTaiwanTSEHolidaysYear2012()
    {
       	final int year = 2012;
    	QL.info("Testing " + Taiwan.Market.TSE + " holidays list for the year " + year + "...");
        
        final List<Date> expectedHol = new ArrayList<Date>();

    	expectedHol.add(new Date(28,FEBRUARY,year));
    	expectedHol.add(new Date(1,MAY,year));
    	expectedHol.add(new Date(10,OCTOBER,year));

        // Call the Holiday Check
        final CalendarUtil cbt = new CalendarUtil();
        cbt.checkHolidayList(expectedHol, exchange, year);
    }

}
