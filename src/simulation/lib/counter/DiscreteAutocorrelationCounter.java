package simulation.lib.counter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * This class implements a discrete time autocorrelation counter
 */
public class DiscreteAutocorrelationCounter extends DiscreteCounter{

	private int maxLag;
	private double[] sumSquares;
	private double[] firstSamples;
	LinkedList<Double> lastSamples;

	public DiscreteAutocorrelationCounter(String variable, int maxLag)
	{
		super(variable, "discrete auto-correlation counter");
		this.maxLag = maxLag;
	}

	public DiscreteAutocorrelationCounter(String variable, String type, int maxLag)
	{
		super(variable, type);
		this.maxLag = maxLag;
	}

	public int getMaxLag()
	{
		return this.maxLag;
	}

	public void setMaxLag(int maxLag)
	{
		this.maxLag = maxLag;
	}

	public void count(double x)
	{
		super.count(x);
		int i = (int) getNumSamples() - 1;
		if (i < maxLag +1)
		{
			firstSamples[i] = x;
		}
		lastSamples.push(x);
		if (lastSamples.size() > maxLag  + 1)
		{
			lastSamples.pollLast();
		}
		int min = Math.min(maxLag, i);
		for (int j = 0; j<=min;j++)
		{
			sumSquares[j] += x * lastSamples.get(j);
		}
	}

	public double getAutoCovariance(int lag)
	{
		if (lag <= maxLag){
			double firstSum = 0;
			double lastSum = 0;
			for (int i = 0; i<lag; i++)
			{
				firstSum += firstSamples[i];
				lastSum += lastSamples.get(i);
			}
			return (sumSquares[lag] - getMean() * (2 * getSumPowerOne() - firstSum - lastSum)) / (double)(getNumSamples() - lag)
					+ getMean()*getMean();
		}
		else
		{
			throw new IllegalArgumentException("lag must be <= " + maxLag);
		}
	}

	public double getAutoCorrelation(int lag){
		if (lag <= maxLag)
		{
			if (getVariance() != 0)
			{
				return (getAutoCovariance(lag) / getVariance());
			}
			else{
				return 1;
			}
		}
		else
		{
			throw new IllegalArgumentException("lag must be <= " + maxLag);
		}
	}

	@Override
	public String report() {
		String out  = super.report();
		out += ("\n\tCorrelation/Covariance:\n");
		for(int i = 0; i <= (getNumSamples() < maxLag ? getNumSamples() : maxLag); i++){
			out += ("\t\tlag = " + i + "   " +
					"covariance = " + getAutoCovariance(i) + "   " +
					"correlation = " + getAutoCorrelation(i)+"\n");
		}
		return out;
	}

	@Override
	public void reset()
	{
		super.reset();
		this.firstSamples = new double[maxLag + 1];
		this.lastSamples = new LinkedList<Double>();
		this.sumSquares = new double[maxLag + 1];
	}

	@Override
	public void csvReport(String outputdir){
	    String content = "";
        for(int i = 0; i <= (getNumSamples() < maxLag ? getNumSamples() : maxLag); i++) {
            content += observedVariable + " (lag=" + i + ")" + ";" + getNumSamples() + ";" + getMean() + ";" +
                    getVariance() + ";" + getStdDeviation() + ";" + getCvar() + ";" + getMin() + ";" + getMax() + ";" +
                    getAutoCovariance(i) + ";" + getAutoCorrelation(i) + "\n";
        }
        String labels = "#counter ; numSamples ; MEAN; VAR; STD; CVAR; MIN; MAX; COV; CORR\n";
        writeCsv(outputdir, content, labels);
	}
}
