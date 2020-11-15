package simulation.lib.randVars.continous;

import simulation.lib.randVars.RandVar;
import simulation.lib.rng.RNG;

/*
 * TODO Problem 2.3.2 - implement this class (section 3.2.3 in course syllabus)
 * !!! If an abstract class method does not make sense to be implemented in a particular RandVar class,
 * an UnsupportedOperationException should be thrown !!!
 *
 * Erlang-k distributed random variable.
 */
public class ErlangK extends RandVar {

	double lambda;
	int k;

	public ErlangK(RNG rng, double lambda, int k) {
		super(rng);
		this.lambda = lambda;
		this.k = k;
	}

	@Override
	public double getRV() {
		double p = 1;
		for (int i = 0; i<k; i++)
		{
			p *= rng.rnd();
		}
		return (-1) / lambda * Math.log(p);
	}

	@Override
	public double getMean() {
		return k / lambda;
	}

	@Override
	public double getVariance() {
		return k / (lambda * lambda);
	}

	@Override
	public void setMean(double m) {
		if (m > 0)
		{
			lambda = k / m;
		}
		else
		{
			throw new IllegalArgumentException("Mean must be positive!");
		}
	}

	@Override
	public void setStdDeviation(double s) {
		double m = getMean();
		k = (int) Math.floor((m * m) / (s * s));
		lambda = k / m;

	}

	@Override
	public void setMeanAndStdDeviation(double m, double s) {
		setMean(m);
		setStdDeviation(s);
	}

	@Override
	public String getType() {
		return "Erlang-" + k;
	}

	@Override
	public String toString() {
		return super.toString() + "\nParameters:\n" +
				"lambda: " + lambda +
				"\nk: " + k + "\n";
	}		
}
