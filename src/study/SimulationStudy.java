package study;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import simulation.lib.Simulator;
import simulation.lib.counter.ContinuousCounter;
import simulation.lib.counter.DiscreteAutocorrelationCounter;
import simulation.lib.counter.DiscreteCounter;
import simulation.lib.histogram.ContinuousHistogram;
import simulation.lib.histogram.DiscreteHistogram;
import simulation.lib.randVars.RandVar;
import simulation.lib.randVars.continous.Exponential;
import simulation.lib.rng.StdRNG;
import simulation.lib.statistic.IStatisticObject;

/**
 * Represents a simulation study. Contains diverse counters for statistics and
 * program/simulator parameters. Starts the simulation.
 */
public class SimulationStudy {

	protected long cSimulationTime = 10000;

	/**
	 * Main method
	 */

	public static void main(String[] args) {
		//AutocorrelationTest.testAutocorrelation();
		/*
		 * create simulation object
		 */
		Simulator sim = new Simulator();
		/*
		 * run simulation
		 */
		sim.start();
		/*
		 * print out report
		 */
		sim.report();
	}

	// PARAMETERS
	/**
	 * Turn on/off debug report in console.
	 */
	protected boolean isDebugReport = true;

	/**
	 * Turn on/off report in csv-files.
	 */
	protected boolean isCsvReport = true;

	/**
	 * Simulation time.
	 */
	public long simulationTime;

	/**
	 * Random number generator for inter arrival times.
	 */
	public RandVar randVarInterArrivalTime;

	/**
	 * random number generator for service times
	 */
	public RandVar randVarServiceTime;

	// STATISTICS
	/**
	 * Map that contains all statistical relevant object such as counters and
	 * histograms.
	 */
	public HashMap<String, IStatisticObject> statisticObjects;

	/**
	 * Maximum queue size.
	 */
	public long maxQS;

	/**
	 * Minimum queue size.
	 */
	public long minQS;

	// Strings used for receiving statisticobjects later in the dictionary.
	public String dtcWaitingTime = "discreteTimeCounterWaitingTime";
	public String dthWaitingTime = "discreteTimeHistogramWaitingTime";
	public String dtcServiceTime = "discreteTimeCounterServiceTime";
	public String dthServiceTime = "discreteTimeHistogramServiceTime";

	public String ctcQueueOccupancy = "continuousTimeCounterQueueOccupancy";
	public String cthQueueOccupancy = "continuousTimeHistogramQueueOccupancy";
	public String ctcServerUtilization = "continuousTimeCounterServerUtilization";
	public String cthServerUtilization = "continuousTimeHistogramServerUtilization";

	public String dtaWaitingTime = "discreteTimeAutocorrelationCounterWaitingTime";
	public String dtaServiceTime = "discreteTimeAutocorrelationCounterServiceTime";

	private Simulator simulator;

	/**
	 * Constructor
	 * @param sim Simulator instance.
	 */
	public SimulationStudy(Simulator sim) {
		simulator = sim;
		simulator.setSimTimeInRealTime(1000);
		setSimulationParameters();
		initStatistics();
	}

	/**
	 * Sets simulation parameters, converts real time to simulation time if
	 * needed.
	 */
	private void setSimulationParameters() {
		simulationTime = simulator.realTimeToSimTime(cSimulationTime);

		StdRNG rng = new StdRNG();
		StdRNG rng2 = new StdRNG();
		this.randVarInterArrivalTime = new Exponential(rng, 1,1);
		this.randVarServiceTime = new Exponential(rng2, .95,.95);
	}

	/**
	 * Initializes statistic objects. Adds them into Hashmap.
	 */
	private void initStatistics() {
		maxQS = Long.MIN_VALUE;
		minQS = Long.MAX_VALUE;

		statisticObjects = new HashMap<>();

		statisticObjects.put(dtcWaitingTime, new DiscreteCounter("waiting time/customer"));
		/*
		 * Problem 4.2.4
		 * The statistic object for counting the waiting times in a histogram is already created here
		 */
		statisticObjects.put(dthWaitingTime, new DiscreteHistogram("waiting_time_per_customer", 80, 0, 80));

		statisticObjects.put(dtcServiceTime, new DiscreteCounter("service time/customer"));
		statisticObjects.put(dthServiceTime, new DiscreteHistogram("service_time_per_customer", 80, 0, 80));

		statisticObjects.put(ctcQueueOccupancy, new ContinuousCounter("queue occupancy/time", simulator));
		statisticObjects.put(cthQueueOccupancy,
				new ContinuousHistogram("queue_occupancy_over_time", 80, 0, 80, simulator));

		statisticObjects.put(ctcServerUtilization, new ContinuousCounter("server utilization/time", simulator));
		statisticObjects.put(cthServerUtilization,
				new ContinuousHistogram("server_utilization_over_time", 80, 0, 80, simulator));

		statisticObjects.put(dtaServiceTime, new DiscreteAutocorrelationCounter("service time per customer", 20));
		statisticObjects.put(dtaWaitingTime, new DiscreteAutocorrelationCounter("waiting time per customer", 20));
	}


	/**
	 * Report results. Print to console if isDebugReport = true. Print to csv
	 * files if isCsvReport = true. Note: Histogramms are only printed to csv
	 * files.
	 */
	public void report() {
		String sd = new SimpleDateFormat("yyyyMMdd_HHmmss_").format(new Date(System.currentTimeMillis()));
		String destination = sd + this.getClass().getSimpleName();

		if (isCsvReport) {
			File file = new File(destination);
			file.mkdir();
			for (IStatisticObject so : statisticObjects.values()) {
				so.csvReport(destination);
			}
		}
		if (isDebugReport) {
			for (IStatisticObject so : statisticObjects.values()) {
				System.out.println(so.report());
			}

			System.out.println("minimum queue size: " + minQS + "\n" + "maximum queue size: " + maxQS);
		}
	}
}
