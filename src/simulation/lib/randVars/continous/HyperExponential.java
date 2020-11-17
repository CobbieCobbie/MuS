package simulation.lib.randVars.continous;

import simulation.lib.randVars.RandVar;
import simulation.lib.rng.RNG;
import static java.lang.Math.*;

/*
 * TODO Problem 2.3.2 - implement this class (section 3.2.4 in course syllabus)
 * !!! If an abstract class method does not make sense to be implemented in a particular RandVar class,
 * an UnsupportedOperationException should be thrown !!!
 *
 * Hyperexponential distributed random variable.
 */
public class HyperExponential extends RandVar {

	double mean, lambda1, lambda2, p1, p2;

	public HyperExponential(RNG rng, double lambda1, double lambda2, double p1, double p2) {
		super(rng);
		this.lambda1 = lambda1;
		this.lambda2 = lambda2;
		this.p1 = p1;
		this.p2 = p2;
	}

	@Override
	public double getRV() {
		double p = rng.rnd();
		if (p1 >= p)
		{
			return -Math.log(rng.rnd())/lambda1;
		}
		else
		{
			return -Math.log(rng.rnd())/lambda2;
		}
	}

	@Override
	public double getMean() {
		return (p1 / lambda1) + (p2 / lambda2);
	}

	@Override
	public double getVariance() {
		double m2 = 2 * (p1 / (lambda1 * lambda1) + p2 / (lambda2 * lambda2));
		return m2 - getMean() * getMean();
	}

	@Override
	public void setMean(double m) {
		mean = m;
	}

	@Override
	public void setStdDeviation(double s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setMeanAndStdDeviation(double m, double s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getType() {
		return "Hyperexponential";
	}

	@Override
	public String toString() {
		return super.toString() + "\nParameters:\n"
				+ "lambda1: " + lambda1 + ", p1: " + p1 + "\n"
				+ "lambda2: " + lambda2 + ", p2: " + p2;
	}
}
