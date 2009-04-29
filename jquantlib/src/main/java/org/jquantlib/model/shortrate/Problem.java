/*
Copyright (C) 2008 Praneet Tiwari

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
package org.jquantlib.model.shortrate;

import org.jquantlib.math.Array;
import org.jquantlib.math.optimization.Constraint;

/**
 * 
 * @author Praneet Tiwari
 */
public class Problem {

    // ! Unconstrained cost function
    protected CostFunction costFunction_;
    // ! Constraint
    protected Constraint constraint_;
    // ! current value of the local minimum
    protected Array currentValue_;
    // ! function and gradient norm values at the curentValue_ (i.e. the last step)
    protected Double /* @Real */functionValue_, squaredNorm_;
    // ! number of evaluation of cost function and its gradient
    protected Integer functionEvaluation_, gradientEvaluation_;

    // ! default constructor

    public Problem(CostFunction costFunction, Constraint constraint, final Array initialValue /* = Array() */) {
        costFunction_ = costFunction;
        constraint_ = constraint;
        currentValue_ = initialValue;
        if (System.getProperty("EXPERIMENTAL") == null) {
            throw new UnsupportedOperationException("Work in progress");
        }
    }

    /*
     * ! \warning it does not reset the current minumum to any initial value
     */
    public void reset() {
        functionEvaluation_ = gradientEvaluation_ = 0;
        functionValue_ = squaredNorm_ = null/* Null<Real>() */;
    }

    // ! call cost function computation and increment evaluation counter
    public Double /* @Real */value(final Array x) {
        ++functionEvaluation_;
        return costFunction_.value(x);
    }

    // ! call cost values computation and increment evaluation counter
    public/* Disposable<Array> */Array values(final Array x) {
        ++functionEvaluation_;
        return costFunction_.values(x);
    }

    // ! call cost function gradient computation and increment
    // evaluation counter
    public void gradient(Array grad_f, final Array x) {
        ++gradientEvaluation_;
        costFunction_.gradient(grad_f, x);
    }

    // ! call cost function computation and it gradient
    public Double /* @Real */valueAndGradient(Array grad_f, final Array x) {
        ++functionEvaluation_;
        ++gradientEvaluation_;
        return costFunction_.valueAndGradient(grad_f, x);
    }

    // ! Constraint
    public Constraint constraint() {
        return constraint_;
    }

    // ! Cost function
    public CostFunction costFunction() {
        return costFunction_;
    }

    public void setCurrentValue(final Array currentValue) {
        currentValue_ = currentValue;
    }

    // ! current value of the local minimum
    public Array currentValue() {
        return currentValue_;
    }

    public void setFunctionValue(Double /* @Real */functionValue) {
        functionValue_ = functionValue;
    }

    // ! value of cost function
    public Double /* @Real */functionValue() {
        return functionValue_;
    }

    public void setGradientNormValue(Double /* @Real */squaredNorm) {
        squaredNorm_ = squaredNorm;
    }

    // ! value of cost function gradient norm

    public Double /* @Real */gradientNormValue() {
        return squaredNorm_;
    }

    // ! number of evaluation of cost function
    public Integer functionEvaluation() {
        return functionEvaluation_;
    }

    // ! number of evaluation of cost function gradient
    public Integer gradientEvaluation() {
        return gradientEvaluation_;
    }
}