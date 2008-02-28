/*
 Copyright (C) 2007 Richard Gomes

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <jquantlib-dev@lists.sf.net>. The license is also available online at
 <http://jquantlib.org/license.shtml>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
 
 JQuantLib is based on QuantLib. http://quantlib.org/
 When applicable, the originating copyright notice follows below.
 */

/*
 Copyright (C) 2003 Ferdinando Ametrano
 Copyright (C) 2001, 2002, 2003 Sadruddin Rejeb
 Copyright (C) 2006 StatPro Italia srl

 This file is part of QuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://quantlib.org/

 QuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <quantlib-dev@lists.sf.net>. The license is also available online at
 <http://quantlib.org/license.shtml>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
*/

package org.jquantlib.exercise;

import org.jquantlib.util.Date;

/**
 * An American option can be exercised at any time between two
 * predefined dates. In case the first date is omitted, the 
 * option can be exercised at any time before the expiry date.
 *
 * @author Richard Gomes
 */
public class AmericanExercise extends EarlyExercise {

	/**
	 * Constructs an AmericanExercise with two limiting dates define and a default payoff 
	 * equals <code>false</code>, which means there's no payoff at exercise Date.
	 * 
	 * @param earliestDate
	 * @param latestDate
	 */
	public AmericanExercise(final Date earliestDate, final Date latestDate) {
		this(earliestDate, latestDate, false);
	}

	/**
	 * Constructs an AmericanExercise with two limiting dates define and a defined payoff.
	 *  
	 * @param earliestDate is the earliest Date of exercise
	 * @param latestDate is the latest Date of exercise
	 * @param payoffAtExpiry is <code>true</code> if a payoff is expected to happen on exercise date
	 */
	public AmericanExercise(final Date earliestDate, final Date latestDate, boolean payoffAtExpiry) {
		super(Exercise.Type.American, payoffAtExpiry);
		if (! (earliestDate.le(latestDate)) ) throw new IllegalArgumentException("earliest > latest exercise date");
		super.addDate(earliestDate);
		super.addDate(latestDate);
    }

// TODO: check that everywhere the American condition is applied from earliestDate and not earlier

//	public AmericanExercise(final Date latestDate) {
//		this(latestDate, false);
//	}
//
//	public AmericanExercise(final Date latestDate, boolean payoffAtExpiry) {
//		super(Exercise.Type.American, payoffAtExpiry);
//		add(latestDate);
//    }

}
