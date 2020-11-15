/**
 * 
 */
package simulation.lib.randVars.continous;

import simulation.lib.randVars.RandVar;
import simulation.lib.rng.RNG;

/*
 * TODO Problem 2.3.2 - implement this class (section 3.2.2 in course syllabus)
 * !!! If an abstract class method does not make sense to be implemented in a particular RandVar class,
 * an UnsupportedOperationException should be thrown !!!
 *
 * Expnential distributed random variable.
 */
public class Exponential extends RandVar {

	double lambda;

	public Exponential(RNG rng, double lambda) {
		super(rng);
		this.lambda = lambda;
	}

	@Override
	public double getRV() {
		return (-1) * Math.log(rng.rnd()) / lambda; // According to script
	}

	@Override
	public double getMean() {
		return 1 / (lambda);
	}

	@Override
	public double getVariance() {
		return 1 / (lambda * lambda);
	}

	@Override
	public void setMean(double m) {
		if (m > 0)
		{
			lambda = (1 / m);
		}
		else
		{
			throw new IllegalArgumentException("The mean is positive !");
		}
	}

	@Override
	public void setStdDeviation(double s) {
		if (s > 0){
			lambda = 1 / s;
		}
		else
		{
			throw new IllegalArgumentException("The Standard deviation is positive!");
		}

	}

	@Override
	public void setMeanAndStdDeviation(double m, double s) {
		setMean(m);
		setStdDeviation(s);
	}

	@Override
	public String getType() {
		return "Exponential";
	}

	@Override
	public String toString() {
		return super.toString() + "\n lambda: " + lambda + "\n";
	}
	
}
