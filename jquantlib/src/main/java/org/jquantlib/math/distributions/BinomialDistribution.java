/*
 Copyright (C) 2008 Richard Gomes

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

package org.jquantlib.math.distributions;

import org.jquantlib.math.Factorial;
// import cern.jet.math.Functions; --> not yet implemented

/**
 * @author Richard Gomes
 * @author Dominik Holenstein
 */


//TODO: Translate C++ code (commented out) -> Dominik
//TODO: Write tests for the new functions -> Dominik
//TODO: Use Colt -> Dominik

public class BinomialDistribution {

	public BinomialDistribution(double p, int n) {

		n_=n;

		if (p==0.0) {
			logOneMinusP_ = 0.0;
		} else if (p==1.0) {
			logP_ = 0.0;
		} else {
			if (!(p>0)){
				throw new ArithmeticException("negative p not allowed");
			}
			if (!(p<1.0)){
				throw new ArithmeticException("p>1.0 not allowed");
			}

			logP_ = Math.log(p);
			logOneMinusP_ = Math.log(1.0-p);
			
		}

	}
	
	
	public double evaluate(int k) {

        if (k > n_) return 0.0;

        // p==1.0
        if (logP_==0.0) {
            return (k==n_ ? 1.0 : 0.0);
        }
        // p==0.0
        if (logOneMinusP_==0.0){
            return (k==0 ? 1.0 : 0.0);
        }
        
        return Math.exp(binomialCoefficientLn(n_, k) +
                            k * logP_ + (n_-k) * logOneMinusP_);

	}
	
	static private double binomialCoefficientLn(int n, int k) {

		if (!(n>=0)) throw new ArithmeticException("n<0 not allowed, " + n);

		if (!(k>=0)) throw new ArithmeticException("k<0 not allowed, " + k);

		if (!(n>=k)){
			throw new ArithmeticException("n<k not allowed");
		}

        return factorial_.ln(n)-factorial_.ln(k)-factorial_.ln(n-k);

    }
	
	/*
	 inline Real binomialCoefficient(BigNatural n, BigNatural k) {
        return std::floor(0.5+std::exp(binomialCoefficientLn(n, k)));
    }
	 */
	
	static private double binomialCoefficient(int n, int k) {
		return Math.floor(0.5 + Math.exp(binomialCoefficientLn(n,k)));
	}
	
	
	//TODO: Translate C++ code (commented out) -> Dominik
	//TODO: Write tests for the new functions -> Dominik
	//TODO: Use Colt -> Dominik
	
	//! Cumulative binomial distribution function
    /*! Given an integer k it provides the cumulative probability
        of observing kk<=k:
        formula here ...

    */
	
	/*
    class CumulativeBinomialDistribution
    : public std::unary_function<Real,Real> {
      public:
        CumulativeBinomialDistribution(Real p, BigNatural n);
        // function
        Real operator()(BigNatural k) const {
            if (k >= n_)
                return 1.0;
            else
                return 1.0 - incompleteBetaFunction(k+1, n_-k, p_);
        }
      private:
        BigNatural n_;
        Real p_;
    };
    */
	
	/*
	 * inline
    CumulativeBinomialDistribution::CumulativeBinomialDistribution(
                                                       Real p, BigNatural n)
    : n_(n), p_(p) {

        QL_REQUIRE(p>=0, "negative p not allowed");
        QL_REQUIRE(p<=1.0, "p>1.0 not allowed");

    } 
    */
	
	
	/*
	 * /*! Given an odd integer n and a real number z it returns p such that:
        1 - CumulativeBinomialDistribution((n-1)/2, n, p) =
                               CumulativeNormalDistribution(z)

        \pre n must be odd
    
    inline Real PeizerPrattMethod2Inversion(Real z, BigNatural n) {

        QL_REQUIRE(n%2==1,
                   "n must be an odd number: " << n << " not allowed");

        Real result = (z/(n+1.0/3.0+0.1/(n+1.0)));
        result *= result;
        result = std::exp(-result*(n+1.0/6.0));
        result = 0.5 + (z>0 ? 1 : -1) * std::sqrt((0.25 * (1.0-result)));
        return result;
    }
	 */
	
	
	private static final Factorial factorial_ = new Factorial();
    private int n_;
    private double logP_, logOneMinusP_;

}
