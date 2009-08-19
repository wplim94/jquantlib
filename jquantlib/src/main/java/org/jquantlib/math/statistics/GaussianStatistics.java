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

package org.jquantlib.math.statistics;

import org.jquantlib.QL;
import org.jquantlib.math.distributions.CumulativeNormalDistribution;
import org.jquantlib.math.distributions.InverseCumulativeNormal;
import org.jquantlib.math.distributions.NormalDistribution;
// TODO: code review :: license, class comments, comments for access modifiers, comments for @Override
// TODO: code review :: please verify against QL/C++ code
public class GaussianStatistics /*implements IStatistics*/ /*aka genericgaussianStatistics*/ {

    private final IStatistics statistics;

    public GaussianStatistics(final IStatistics statistics) {
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
        this.statistics = statistics;
    }

    // ! \name Gaussian risk measures
    // @{
    /*
     * ! returns the downside variance, defined as \f[ \frac{N}{N-1} \times \frac{ \sum_{i=1}^{N} \theta \times x_i^{2}}{
     * \sum_{i=1}^{N} w_i} \f], where \f$ \theta \f$ = 0 if x > 0 and \f$ \theta \f$ =1 if x <0
     */
    public double gaussianDownsideVariance() {
        return gaussianRegret(0.0);
    }

    /*
     * ! returns the downside deviation, defined as the square root of the downside variance.
     */
    public double gaussianDownsideDeviation() {
        return Math.sqrt(gaussianDownsideVariance());
    }

    // inline definitions
    /*
     * ! returns the variance of observations below target \f[ \frac{\sum w_i (min(0, x_i-target))^2 }{\sum w_i}. \f]
     *
     * See Dembo, Freeman "The Rules Of Risk", Wiley (2001)
     */

    public double gaussianRegret(final double target) {
        final double m = statistics.mean();
        final double std = statistics.standardDeviation();
        final double variance = std * std;
        final CumulativeNormalDistribution gIntegral = new CumulativeNormalDistribution(m, std);
        final NormalDistribution g = new NormalDistribution(m, std);
        final double firstTerm = variance + m * m - 2.0 * target * m + target * target;
        final double alfa = gIntegral.op(target);
        final double secondTerm = m - target;
        final double beta = variance * g.op(target);
        final double result = alfa * firstTerm - beta * secondTerm;
        return result / alfa;
    }

    /*
     * ! gaussian-assumption y-th percentile, defined as the value x such that \f[ y = \frac{1}{\sqrt{2 \pi}} \int_{-\infty}^{x}
     * \exp (-u^2/2) du \f]
     */
    public double gaussianPercentile(final double percentile) {
        QL.require(percentile > 0.0 , "percentile must be > 0.0"); // QA:[RG]::verified // TODO: message
        QL.require(percentile < 1.0 , "percentile must be < 1.0"); // QA:[RG]::verified // TODO: message
        final InverseCumulativeNormal gInverse = new InverseCumulativeNormal(statistics.mean(), statistics.standardDeviation());
        return gInverse.op(percentile);
    }

    /* ! \pre percentile must be in range (0%-100%) extremes excluded */
    public double gaussianTopPercentile(final double percentile) {
        return gaussianPercentile(1.0 - percentile);
    }

    // ! gaussian-assumption Potential-Upside at a given percentile
    public double gaussianPotentialUpside(final double percentile) {
        QL.require(percentile >= 0.9 && percentile < 1.0 , "percentile out of range [0.9, 1)"); // QA:[RG]::verified // TODO: message
        final double result = gaussianPercentile(percentile);
        // potential upside must be a gain, i.e., floored at 0.0
        return Math.max(result, 0.0);
    }

    /* ! \pre percentile must be in range [90%-100%) */
    public double gaussianValueAtRisk(final double percentile) {

        if (percentile >= 1.0 || percentile < 0.9)
            throw new IllegalArgumentException("percentile (" + percentile + ") out of range [0.9, 1)");
        final double result = gaussianPercentile(1.0 - percentile);
        // VAR must be a loss
        // this means that it has to be MIN(dist(1.0-percentile), 0.0)
        // VAR must also be a positive quantity, so -MIN(*)
        return -Math.min(result, 0.0);
    }

    // ! gaussian-assumption Expected Shortfall at a given percentile
    /*
     * ! Assuming a gaussian distribution it returns the expected loss in case that the loss exceeded a VaR threshold,
     *
     * \f[ \mathrm{E}\left[ x \;|\; x < \mathrm{VaR}(p) \right], \f]
     *
     * that is the average of observations below the given percentile \f$ p \f$. Also know as conditional value-at-risk.
     *
     * See Artzner, Delbaen, Eber and Heath, "Coherent measures of risk", Mathematical Finance 9 (1999)
     */
    /* ! \pre percentile must be in range [90%-100%) */
    public double gaussianExpectedShortfall(final double percentile) {
        if (percentile >= 1.0 || percentile < 0.9)
            throw new IllegalArgumentException("percentile (" + percentile + ") out of range [0.9, 1)");
        final double m = statistics.mean();
        final double std = statistics.standardDeviation();
        final InverseCumulativeNormal gInverse = new InverseCumulativeNormal(m, std);
        final double var = gInverse.op(1.0 - percentile);
        final NormalDistribution g = new NormalDistribution(m, std);
        final double result = m - std * std * g.op(var) / (1.0 - percentile);
        // expectedShortfall must be a loss
        // this means that it has to be MIN(result, 0.0)
        // expectedShortfall must also be a positive quantity, so -MIN(*)
        return -Math.min(result, 0.0);
    }

    public double gaussianShortfall(final double target) {
        final CumulativeNormalDistribution gIntegral = new CumulativeNormalDistribution(statistics.mean(), statistics.standardDeviation());
        return gIntegral.op(target);
    }

    public double gaussianAverageShortfall(final double target) {
        final double m = statistics.mean();
        final double std = statistics.standardDeviation();
        final CumulativeNormalDistribution gIntegral = new CumulativeNormalDistribution(m, std);
        final NormalDistribution g = new NormalDistribution(m, std);
        return ((target - m) + std * std * g.op(target) / gIntegral.op(target));
    }

    /*
     * TODO: where do we need this one???????????????????????????????? //! Helper class for precomputed distributions class
     * StatsHolder { public: typedef Real value_type; StatsHolder(Real mean, Real standardDeviation) : mean_(mean),
     * standardDeviation_(standardDeviation) {} ~StatsHolder() {} Real mean() const { return mean_; } Real standardDeviation() const
     * { return standardDeviation_; } private: Real mean_, standardDeviation_; };
     */

}
