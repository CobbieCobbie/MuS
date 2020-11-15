package simulation.lib.randVars.continous;

import simulation.lib.randVars.RandVar;
import simulation.lib.rng.RNG;

/*
 * TODO Problem 2.3.2 - implement this class (section 3.2.1 in course syllabus)
 * !!! If an abstract class method does not make sense to be implemented in a particular RandVar class,
 * an UnsupportedOperationException should be thrown !!!
 *
 * Uniform distributed random variable.
 */
public class Uniform extends RandVar {

	double leftBound, rightBound;

	public Uniform(RNG rng, double a, double b) {
		super(rng);
		leftBound = a;
		rightBound = b;

	}

	@Override
	public double getRV() {
		return rightBound - (rightBound-leftBound)*rng.rnd();
	}

	@Override
	public double getMean() {
		return (rightBound + leftBound) / 2;
	}

	@Override
	public double getVariance() {
		return (rightBound - leftBound) * (rightBound - leftBound) / 12;
	}

	@Override
	public void setMean(double m) {
		//setting a new mean without any further parameters will shift a and b, with its respective distance
		double distance = Math.abs(rightBound - leftBound);
		leftBound = m - (distance / 2);
		rightBound = leftBound + distance;
	}

	@Override
	public void setStdDeviation(double s) {
		//setting a new stdDeviation will shift a and b according to the mean
		double variance = s * s;
		double newDistance = Math.sqrt(variance * 12);
		double m = getMean();
		leftBound = m - newDistance / 2;
		rightBound = leftBound + newDistance;
	}

	@Override
	public void setMeanAndStdDeviation(double m, double s) {
		setMean(m);
		setStdDeviation(s);
	}

	@Override
	public String getType() {
		return "Uniform";
	}

	@Override
	public String toString() {
		return super.toString() + "Parameters: \n" +
				"Left Bound: " + leftBound +
				"\nRight Bound" + rightBound + "\n";
	}
	
}
