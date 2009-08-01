/*
 Copyright (C) 2009 Ueli Hofstetter

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
package org.jquantlib;

import java.io.PrintStream;

import org.jquantlib.math.Rounding;

public class Currency implements Cloneable {

    // Note: could also be implemented as non-static inner class
    protected Data data_;

    public static class Data {
        //  currency name, e.g, "U.S. Dollar"
        private String name,
        //  ISO 4217 three-letter code, e.g, "USD"
                code;
        // ! ISO 4217 numeric code, e.g, "840"
        private int numeric;
        //  symbol, e.g, "$"
        private String symbol,
        //  fraction symbol, e.g, "�"
                fractionSymbol;
        //  number of fractionary parts in a unit, e.g, 100
        private int fractionsPerUnit;
        //  rounding convention
        private Rounding rounding;
        /*
         *  The format will be fed three positional parameters, namely, value, code, and symbol, in this order.
         */
        private String formatString;
        //  currency used for triangulated exchange when required
        private Currency triangulated;

        public Data(final String name, final String code, int numericCode, final String symbol, final String fractionSymbol,
                int fractionsPerUnit, final Rounding rounding, final String formatString) {
            this(name, code, numericCode, symbol, fractionSymbol, fractionsPerUnit, rounding, formatString, new Currency());
        }

        public Data(final String name, final String code, int numericCode, final String symbol, final String fractionSymbol,
                int fractionsPerUnit, final Rounding rounding, final String formatString, final Currency triangulationCurrency) {
            this.name = (name);
            this.code = (code);
            this.numeric = (numericCode);
            this.symbol = (symbol);
            this.fractionSymbol = (fractionSymbol);
            this.fractionsPerUnit = (fractionsPerUnit);
            this.rounding = (rounding);
            this.triangulated = (triangulationCurrency);
            this.formatString = (formatString);
        }

        public Data clone() {
            Data data = new Data(name, code, numeric, symbol, fractionSymbol, fractionsPerUnit, rounding, formatString,
                    triangulated.clone());
            return data;
        }

    }

    /**
     * Instances built via this constructor have undefined behavior. Such instances can only act as placeholders and must be
     * reassigned to a valid currency before being used.
     */
    public Currency() {
    }

    // /accessors

    public final String name() {
        return data_.name;
    }

    public final String code() {
        return data_.code;
    }

    public final int numericCode() {
        return data_.numeric;
    }

    public final String symbol() {
        return data_.symbol;
    }

    public final String fractionSymbol() {
        return data_.fractionSymbol;
    }

    public final int fractionsPerUnit() {
        return data_.fractionsPerUnit;
    }

    public final Rounding rounding() {
        return data_.rounding;
    }

    public final String format() {
        return data_.formatString;
    }

    // ! is this a usable instance?
    public final boolean empty() {
        return data_ == null;
    }

    public final Currency triangulationCurrency() {
        return data_.triangulated;
    }

    // /OPERATORS

    // /class
    public final boolean equals(Currency currency) {
        return name().equals(currency.name());
    }

    public final boolean notEquals(final Currency currency) {
        // eating our own dogfood
        return !(equals(currency));
    }

    // /static
    public static final boolean operatorEquals(final Currency c1, final Currency c2) {
        return c1.name().equals(c2.name());
    }

    public static final boolean operatorNotEquals(final Currency c1, final Currency c2) {
        // eating our own dogfood
        return !(Currency.operatorEquals(c1, c2));
    }

    // / customized outputstream printing
    public static PrintStream print(PrintStream out, Currency c) {
        if (!c.empty()) {
            return out.append(c.code());
        } else {
            return out.append("null currency");
        }
    }

    protected Currency clone() {
        Currency currency = new Currency();
        if (data_ != null) {
            currency.data_ = data_.clone();
        }
        return currency;

    }

}