/*
Copyright (C) 
2008 Praneet Tiwari
2009 Ueli Hofstetter

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
package org.jquantlib.model.shortrate.onefactormodels;

import static org.jquantlib.pricingengines.BlackFormula.blackFormula;

import org.jquantlib.instruments.Option;
import org.jquantlib.math.Array;
import org.jquantlib.math.Constants;
import org.jquantlib.methods.lattices.Lattice;
import org.jquantlib.methods.lattices.TrinomialTree;
import org.jquantlib.model.Parameter;
import org.jquantlib.model.shortrate.NullParameter;
import org.jquantlib.model.shortrate.TermStructureFittingParameter;
import org.jquantlib.processes.OrnsteinUhlenbeckProcess;
import org.jquantlib.quotes.Handle;
import org.jquantlib.termstructures.Compounding;
import org.jquantlib.termstructures.YieldTermStructure;
import org.jquantlib.time.Frequency;
import org.jquantlib.time.TimeGrid;

/**
 * 
 * @author Praneet Tiwari
 */
// ! Single-factor Hull-White (extended %Vasicek) model class.
/*
 * ! This class implements the standard single-factor Hull-White model defined by \f[ dr_t = (\theta(t) - \alpha r_t)dt + \sigma
 * dW_t \f] where \f$ \alpha \f$ and \f$ \sigma \f$ are constants.
 * 
 * \test calibration results are tested against cached values
 * 
 * \bug When the term structure is relinked, the r0 parameter of the underlying Vasicek model is not updated.
 * 
 * \ingroup shortrate
 */
public class HullWhite extends Vasicek implements TermStructureConsistentModel {

    // need permanent solution for this one
    TermStructureConsistentModelClass termStructureConsistentModelClass;
    Parameter phi_;
    
    public HullWhite(final Handle<YieldTermStructure> termStructure){
        this(termStructure, 0.1, 0.01);
    }

    public HullWhite(final Handle<YieldTermStructure>/* YieldTermStructure */termStructure, double /* @Real */a /* = 0.1 */,
            double /* @Real */sigma /* = 0.01 */) {
        super(termStructure.getLink().forwardRate(0.0, 0.0, Compounding.CONTINUOUS, Frequency.NO_FREQUENCY).rate(), a, 0.0, sigma,
                0.0);
        if (System.getProperty("EXPERIMENTAL") == null) {
            throw new UnsupportedOperationException("Work in progress");
        }
        termStructureConsistentModelClass = new TermStructureConsistentModelClass(termStructure);

        b_ = new NullParameter();
        lambda_ = new NullParameter();
        generateArguments();
        termStructure.getLink().addObserver(this);
    }

    public Lattice tree(final TimeGrid grid) {
        TermStructureFittingParameter phi = new TermStructureFittingParameter(termStructureConsistentModelClass.termStructure());
        // needed to activate the above constructor
        ShortRateDynamics numericDynamics = (new Dynamics(phi, a(), sigma()));
        TrinomialTree trinomial = new TrinomialTree(numericDynamics.process(), grid, true);
        ShortRateTree numericTree = null;// new ShortRateTree(trinomial, numericDynamics, grid);

        // typedef TermStructureFittingParameter::NumericalImpl NumericalImpl;
        TermStructureFittingParameter.NumericalImpl impl = (TermStructureFittingParameter.NumericalImpl) (phi.getImplementation());
        impl.reset();
        for (int /* @Size */i = 0; i < (grid.size() - 1); i++) {
            double /* @Real */discountBond = termStructureConsistentModelClass.termStructure().getLink().discount(grid.at(i + 1));
            Array statePrices = numericTree.statePrices(i);
            int /* @Size */size = numericTree.size(i);
            double /* @Time */dt = numericTree.timeGrid().dt(i);
            double /* @Real */dx = trinomial.dx(i);
            double /* @Real */x = trinomial.underlying(i, 0);
            double /* @Real */value = 0.0;
            for (int /* @Size */j = 0; j < size; j++) {
                value += statePrices.get(j) * Math.exp(-x * dt);
                x += dx;
            }
            value = Math.log(value / discountBond) / dt;
            // impl->set(grid[i], value);
            impl.set(grid.index(i), value); // ???????????????
        }
        return numericTree;
    }

    @Override
    public double /* @Real */A(double /* @Time */t, double /* @Time */T) {
        double /* @DiscountFactor */discount1 = termStructureConsistentModelClass.termStructure().getLink().discount(t);
        double /* @DiscountFactor */discount2 = termStructureConsistentModelClass.termStructure().getLink().discount(T);
        double /* @Rate */forward = termStructureConsistentModelClass.termStructure().getLink().forwardRate(t, t,
                Compounding.CONTINUOUS, Frequency.NO_FREQUENCY).rate();
        double /* @Real */temp = sigma() * B(t, T);
        double /* @Real */value = B(t, T) * forward - 0.25 * temp * temp * B(0.0, 2.0 * t);
        return Math.exp(value) * discount2 / discount1;
    }

    public void generateArguments() {
        phi_ = new FittingParameter(termStructureConsistentModelClass.termStructure(), a(), sigma());
    }

    public double /* @Real */discountBondOption(Option.Type type, double /* @Real */strike, double /* @Time */maturity,
            double /* @Time */bondMaturity) {

        double /* @Real */_a = a();
        double /* @Real */v;
        if (_a < Math.sqrt(Constants.QL_EPSILON)) {
            v = sigma() * B(maturity, bondMaturity) * Math.sqrt(maturity);
        } else {
            v = sigma() * B(maturity, bondMaturity) * Math.sqrt(0.5 * (1.0 - Math.exp(-2.0 * _a * maturity)) / _a);
        }
        double /* @Real */f = termStructureConsistentModelClass.termStructure().getLink().discount(bondMaturity);
        double /* @Real */k = termStructureConsistentModelClass.termStructure().getLink().discount(maturity) * strike;

        return blackFormula(type, k, f, v);
    }
    
    /*! Futures convexity bias (i.e., the difference between
    futures implied rate and forward rate) calculated as in
    G. Kirikos, D. Novak, "Convexity Conundrums", Risk
    Magazine, March 1997.

    \note t and T should be expressed in yearfraction using
          deposit day counter, F_quoted is futures' market price.
     */
    public static double /* @Rate */convexityBias(double /* @Real */futuresPrice, double /* @Time */t, double /* @Time */T,
            double /* @Real */sigma, double /* @Real */a) {
       
        if (futuresPrice < 0.0) {
            throw new IllegalArgumentException("negative futures price (" + futuresPrice + ") not allowed");
        }

        if (t < 0.0) {
            throw new IllegalArgumentException("negative t (" + t + ") not allowed");
        }

        if (T < t) {
            throw new IllegalArgumentException("T (" + T + ")  must not be less than t (" + t + ")");
        }

        if (a < 0.0) {
            throw new IllegalArgumentException("negative a (" + a + ") not allowed");
        }

        double /* @Time */deltaT = (T - t);
        double /* @Real */tempDeltaT = (1. - Math.exp(-a * deltaT)) / a;
        double /* @Real */halfSigmaSquare = sigma * sigma / 2.0;

        // lambda adjusts for the fact that the underlying is an interest rate
        double /* @Real */lambda = halfSigmaSquare * (1. - Math.exp(-2.0 * a * t)) / a * tempDeltaT * tempDeltaT;

        double /* @Real */tempT = (1.0 - Math.exp(-a * t)) / a;

        // phi is the MtM adjustment
        double /* @Real */phi = halfSigmaSquare * tempDeltaT * tempT * tempT;

        // the adjustment
        double /* @Real */z = lambda + phi;

        double /* @Rate */futureRate = (100.0 - futuresPrice) / 100.0;
        return (1.0 - Math.exp(-z)) * (futureRate + 1.0 / (T - t));
    }
    
  //! Analytical term-structure fitting parameter \f$ \varphi(t) \f$.
    /*! \f$ \varphi(t) \f$ is analytically defined by
        \f[
            \varphi(t) = f(t) + \frac{1}{2}[\frac{\sigma(1-e^{-at})}{a}]^2,
        \f]
        where \f$ f(t) \f$ is the instantaneous forward rate at \f$ t \f$.
    */
    public class FittingParameter extends TermStructureFittingParameter{
        private class Impl extends Parameter.Impl{
            public Impl(final Handle<YieldTermStructure> termStructure, double a, double sigma) {
                this.termStructure_ = termStructure;
                this.a_ = a;
                this.sigma_ = sigma;
            }
            
            private Handle<YieldTermStructure> termStructure_;
            private double a_, sigma_;
            
            @Override
            public double value(Array params, double t) {
                double forwardRate =
                    termStructure_.getLink().forwardRate(t, t, Compounding.CONTINUOUS, Frequency.NO_FREQUENCY).rate();
                double temp = sigma_*(1.0 - Math.exp(-a_*t))/a_;
                return (forwardRate + 0.5*temp*temp);
            }
        }
        public FittingParameter(Handle<YieldTermStructure> termStructure, double a, double sigma) {
            super(termStructure);
            throw new UnsupportedOperationException("work in progress");
            //FIXME: change class hierarchy -> use static inner classes ?
            //super(new Impl(termStructure, a, sigma));
           
        }
        
    }
    
    // ! Short-rate dynamics in the Hull-White model
    /*
     * ! The short-rate is here \f[ r_t = \varphi(t) + x_t \f] where \f$ \varphi(t) \f$ is the deterministic time-dependent
     * parameter used for term-structure fitting and \f$ x_t \f$ is the state variable following an Ornstein-Uhlenbeck process.
     */
    public class Dynamics extends ShortRateDynamics {

        private Parameter fitting_;

        public Dynamics(final Parameter fitting, double /* @Real */a, double /* @Real */sigma) {
            super(new OrnsteinUhlenbeckProcess(a, sigma, /* default */0.0, /* default */0.0));
            fitting_ = (fitting);
        }

        public double /* @Real */variable(double /* @Time */t, double /* @Rate */r) {
            return r - fitting_.getOperatorEq(t);
        }

        public double /* @Real */shortRate(double /* @Time */t, double /* @Real */x) {
            return x + fitting_.getOperatorEq(t);
        }
    }

    public ShortRateDynamics dynamics() {
        return (new Dynamics(phi_, a(), sigma()));
    }

    @Override
    public Handle<YieldTermStructure> termStructure() {
        // TODO Auto-generated method stub
        return null;
    }
}
