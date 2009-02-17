/*
 Copyright (C) 2007 Srinivas Hasti

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

package org.jquantlib.util;

import org.jquantlib.time.IMM;

/**
 * Month names
 */
public enum Month {
	JANUARY   (1),
	FEBRUARY  (2),
    MARCH     (3),
    APRIL     (4),
    MAY       (5),
    JUNE      (6),
    JULY      (7),
    AUGUST    (8),
    SEPTEMBER (9),
    OCTOBER   (10),
    NOVEMBER  (11),
    DECEMBER  (12);

	private final int enumValue;
	
	private Month(final int month) {
		this.enumValue = month;
	}
	
	/**
	 * Returns the ordinal number of this Month
	 * 
	 * @return the ordinal number of this Month
	 */
	public int toInteger() {
		return enumValue;
	}
	
	/**
	 * Returns a new Month given it's ordinal number
	 * 
	 * @param month is the ordinal number
	 * @return a new Month given it's ordinal number
	 */
	static public Month valueOf(final int month) {
		final Month returnMonth;
		switch (month) {
		case 1:
			returnMonth = Month.JANUARY;
			break;
		case 2:
			returnMonth = Month.FEBRUARY;
			break;
		case 3:
			returnMonth = Month.MARCH;
			break;
		case 4:
			returnMonth = Month.APRIL;
			break;
		case 5:
			returnMonth = Month.MAY;
			break;
		case 6:
			returnMonth = Month.JUNE;
			break;
		case 7:
			returnMonth = Month.JULY;
			break;
		case 8:
			returnMonth = Month.AUGUST;
			break;
		case 9:
			returnMonth = Month.SEPTEMBER;
			break;
		case 10:
			returnMonth = Month.OCTOBER;
			break;
		case 11:
			returnMonth = Month.NOVEMBER;
			break;
		case 12:
			returnMonth =  Month.DECEMBER;
			break;
		default:
			throw new IllegalArgumentException();
		}
		return returnMonth;
	}
	
    /**
     * Returns the IMM char for this Month
     * 
     * @return the IMM char for this Month
     * @see IMM
     */
	public char getImmChar() {
		final char retunChar;
		switch (enumValue) {
		case 1:
			retunChar =  'F';
			break;
		case 2:
			retunChar =  'G';
			break;
		case 3:
			retunChar =  'H';
			break;
		case 4:
			retunChar =  'J';
			break;
		case 5:
			retunChar =  'K';
			break;
		case 6:
			retunChar =  'M';
			break;
		case 7:
			retunChar =  'N';
			break;
		case 8:
			retunChar =  'Q';
			break;
		case 9:
			retunChar =  'U';
			break;
		case 10:
			retunChar =  'V';
			break;
		case 11:
			retunChar =  'X';
			break;
		case 12:
			retunChar =  'Z';
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns a new month given it's IMM code
	 * 
	 * @param immCode is the IMM code 
	 * @return a new month given it's IMM code
	 */
	static public Month valueOf(final char immCode) {
		final Month returnMonth;
		switch (immCode) {
		case 'F': returnMonth = Month.JANUARY;break;
		case 'G': returnMonth = Month.FEBRUARY;break;
		case 'H': returnMonth = Month.MARCH;break;
		case 'J': returnMonth = Month.APRIL;break;
		case 'K': returnMonth = Month.MAY;break;
		case 'M': returnMonth = Month.JUNE;break;
		case 'N': returnMonth = Month.JULY;break;
		case 'Q': returnMonth = Month.AUGUST;break;
		case 'U': returnMonth = Month.SEPTEMBER;break;
		case 'V': returnMonth = Month.OCTOBER;break;
		case 'X': returnMonth = Month.NOVEMBER;break;
		case 'Z': returnMonth = Month.DECEMBER;break;
		default:
			throw new IllegalArgumentException();
		}
		return returnMonth;
	}
	
	public String toString() {
		final String returnString;
		switch (enumValue) {
		case 1:
			returnString = "January";
			break;
		case 2:
			returnString = "February";
			break;
		case 3:
			returnString = "March";
			break;
		case 4:
			returnString = "April";
			break;
		case 5:
			returnString = "May";
			break;
		case 6:
			returnString = "June";
			break;
		case 7:
			returnString = "July";
			break;
		case 8:
			returnString = "August";
			break;
		case 9:
			returnString = "September";
			break;
		case 10:
			returnString = "October";
			break;
		case 11:
			returnString = "November";
			break;
		case 12:
			returnString = "December";
			break;
		default:
			throw new IllegalArgumentException();
		}
		return returnString;
	}
}