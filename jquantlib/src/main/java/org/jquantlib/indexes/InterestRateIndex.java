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

package org.jquantlib.indexes;

import org.jquantlib.Configuration;
import org.jquantlib.daycounters.DayCounter;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Calendar;
import org.jquantlib.time.Period;
import org.jquantlib.time.TimeUnit;
import org.jquantlib.util.Date;
import org.jquantlib.util.Observable;
import org.jquantlib.util.Observer;

/**
 * 
 * @author Srinivas Hasti
 *
 */
// TODO: code review :: please verify against original QL/C++ code
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
public abstract class InterestRateIndex extends Index implements Observer {

    
    
    // TODO: code review :: Please review this class! :S
    // This is a time being implementation
    protected static abstract class Currency {
        
        protected Currency(String currency) {
            //TODO: Code review :: incomplete code
            if (true)
                throw new UnsupportedOperationException("Work in progress");
        }
        
    }

    protected static class EURCurrency extends Currency {
        public EURCurrency() {
            super("EUR");
        }
    }
    

    
    
    private String familyName;
	private Period tenor;
	private int fixingDays;
	private Calendar fixingCalendar;
	private Currency currency;
	
	// TODO: code review :: please verify against original QL/C++ code
	protected DayCounter dayCounter;

	
	public InterestRateIndex(
	            final String familyName, 
	            final Period tenor, 
	            final /*@Natural*/ int fixingDays,
	            final Calendar fixingCalendar, 
	            final Currency currency, 
	            final DayCounter dayCounter) {
        
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
        
		this.familyName = familyName;
		this.tenor = tenor;
		this.fixingDays = fixingDays;
		this.fixingCalendar = fixingCalendar;
		this.currency = currency;
		this.dayCounter = dayCounter;

		if (fixingDays > 2) throw new IllegalArgumentException("wrong number of fixing days"); // TODO: message
		
		// tenor.normalize(); //TODO :: code review
		Configuration.getSystemConfiguration(null).getGlobalSettings().getEvaluationDate().addObserver(this);
		IndexManager.getInstance().notifier(name()).addObserver(this);		
	}

	
	//adoption for 0.9.7 switched parameters...
	public InterestRateIndex(
	            final String familyName, 
	            final Period tenor, 
	            /*@Natural*/ int settlementDays, 
	            final Currency currency, 
	            final Calendar calendar,
	            final DayCounter fixedLegDayCounter) {
        this(familyName, tenor, settlementDays, calendar, currency, fixedLegDayCounter);
    }


    //
    // protected abstract methods
    //
    
    protected abstract double forecastFixing(Date fixingDate);

    
	//
	// public abstract methods
	//
	
	public abstract Handle<YieldTermStructure> termStructure();
    public abstract Date maturityDate(Date valueDate);


	//
	// public methods
	//
	
	@Override
	//FIXME: a detailed code review is needed here!
	public double fixing(Date fixingDate, boolean forecastTodaysFixing) {
		if (isValidFixingDate(fixingDate))
			throw new IllegalStateException("Fixing date " + fixingDate
					+ " is not valid");
		Date today = org.jquantlib.Configuration.getSystemConfiguration(null).getGlobalSettings().getEvaluationDate();
		boolean enforceTodaysHistoricFixings = org.jquantlib.Configuration
				.getSystemConfiguration(null).isEnforcesTodaysHistoricFixings();
		if (fixingDate.le(today)
				|| (fixingDate.equals(today) && enforceTodaysHistoricFixings && !forecastTodaysFixing)) {
			// must have been fixed
			Double pastFixing = IndexManager.getInstance().get(name()).find(
					fixingDate);
			if (pastFixing == null)
				throw new IllegalArgumentException("Missing " + name() + " fixing for " + fixingDate);
			return pastFixing;
		}
		if ((fixingDate.equals(today)) && !forecastTodaysFixing) {
			// might have been fixed
			try {
				Double pastFixing = IndexManager.getInstance().get(name())
						.find(fixingDate);
				if (pastFixing != null)
					return pastFixing;
				else
					; // fall through and forecast
			} catch (Exception e) {
				; // fall through and forecast
			}
		}
		// forecast
		return forecastFixing(fixingDate);
	}
	
	   public double fixing(Date fixingDate) {
	       return fixing(fixingDate, false);
	    }

	@Override
	public String name() {
		StringBuilder builder = new StringBuilder(familyName);
		if (tenor.units() == TimeUnit.DAYS) {
			if (fixingDays == 0)
				builder.append("ON");
			else if (fixingDays == 2)
				builder.append("SN");
			else
				builder.append("TN");
		} else
			builder.append(tenor.getShortFormat());
		builder.append(dayCounter.name());
		return builder.toString();
	}

	public Date fixingDate(Date valueDate) {
		Date fixingDate = fixingCalendar().advance(valueDate, (fixingDays),
				TimeUnit.DAYS);
		if (!(isValidFixingDate(fixingDate)))
			throw new IllegalArgumentException("fixing date " + fixingDate + " is not valid");
		return fixingDate;
	}

	@Override
	public boolean isValidFixingDate(Date fixingDate) {
		return fixingCalendar.isBusinessDay(fixingDate);
	}

	public String familyName() {
		return familyName;
	}

	public Period tenor() {
		return tenor;
	}

	public int fixingDays() {
		return fixingDays;
	}

    @Override
	public Calendar fixingCalendar() {
		return fixingCalendar;
	}

	public Currency currency() {
		return currency;
	}

	public DayCounter dayCounter() {
		return dayCounter;
	}

	public Date valueDate(Date fixingDate) {
		if (!isValidFixingDate(fixingDate))
			throw new IllegalArgumentException("Fixing date " + fixingDate
					+ " is not valid");
		return fixingCalendar().advance(fixingDate, fixingDays,
				TimeUnit.DAYS);
	}

	@Override
	public void update(Observable o, Object arg) {
	    notifyObservers(arg);	
	}
	

}
