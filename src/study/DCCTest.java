package study;

import simulation.lib.counter.DiscreteConfidenceCounter;
import simulation.lib.randVars.continous.ErlangK;
import simulation.lib.randVars.continous.Exponential;
import simulation.lib.randVars.continous.Normal;
import simulation.lib.rng.RNG;
import simulation.lib.rng.StdRNG;

/*
 * TODO Problem 3.1.3 and 3.1.4 - implement this class
 */
public class DCCTest {

    public static void main(String[] args) {
        testDCC();
    }

    public static void testDCC() {
        StdRNG rng = new StdRNG();
        // c_var = sqrt(var)/mean <-> var = (c_var * mean) ^2
        double firstVar = Math.pow((0.25 * 10.0), 2);
        double secondVar = Math.pow((0.5 * 10.0), 2);
        double thirdVar = Math.pow((1 * 10.0), 2);
        double fourthVar = Math.pow((2 * 10.0), 2);
        double fifthVar = Math.pow((4 * 10.0), 2);

        Normal firstDist = new Normal(rng, 10, firstVar);
        Normal secondDist = new Normal(rng, 10, secondVar);
        Normal thirdDist = new Normal(rng, 10, thirdVar);
        Normal fourthDist = new Normal(rng, 10, fourthVar);
        Normal fifthDist = new Normal(rng, 10, fifthVar);


        //for 1.4, the following was used:

        /*ErlangK firstDist = new ErlangK(rng, 10, firstVar);
        ErlangK secondDist = new ErlangK(rng, 10, secondVar);
        ErlangK thirdDist = new ErlangK(rng, 10, thirdVar);
        ErlangK fourthDist = new ErlangK(rng, 10, fourthVar);
        ErlangK fifthDist = new ErlangK(rng, 10, fifthVar);

        Exponential firstDist = new Exponential(rng, 10, 10);
        //Mean must equal standard deviation!

*/


        int first90FractionCount  = 0,
                first95FractionCount  = 0,
                second90FractionCount = 0,
                second95FractionCount = 0,
                third90FractionCount = 0,
                third95FractionCount  = 0,
                fourth90FractionCount = 0,
                fourth95FractionCount = 0,
                fifth90FractionCount = 0, fifth95FractionCount = 0;

        int numberOfExperiments = 500;
        int sampleSizes[] = {5,10,50,100};

        for (int j = 0; j<numberOfExperiments;j++)
        {
            for (int n : sampleSizes)
            {
                System.out.println("n:" + n);
                DiscreteConfidenceCounter firstNormalCounter95 = new DiscreteConfidenceCounter("normal distribution with mean 10 and c_var 0.25, significance of 0.05", "discrete counter", 0.05);
                DiscreteConfidenceCounter firstNormalCounter90 = new DiscreteConfidenceCounter("normal distribution with mean 10 and c_var 0.25, significance of 0.1", "discrete counter", 0.10);
                DiscreteConfidenceCounter secondNormalCounter90 = new DiscreteConfidenceCounter("normal distribution with mean 10 and c_var 0.5, significance of 0.1", "discrete counter", 0.1);
                DiscreteConfidenceCounter secondNormalCounter95 = new DiscreteConfidenceCounter("normal distribution with mean 10 and c_var 0.5, significance of 0.05","discrete counter", 0.05);
                DiscreteConfidenceCounter thirdNormalCounter90 = new DiscreteConfidenceCounter("normal distribution with mean 10 and c_var 1, significance of 0.1","discrete counter", 0.1);
                DiscreteConfidenceCounter thirdNormalCounter95 = new DiscreteConfidenceCounter("normal distribution with mean 10 and c_var 1, significance of 0.05","discrete counter", 0.05);
                DiscreteConfidenceCounter fourthNormalCounter90 = new DiscreteConfidenceCounter("normal distribution with mean 10 and c_var 2, significance of 0.1","discrete counter", 0.1);
                DiscreteConfidenceCounter fourthNormalCounter95 = new DiscreteConfidenceCounter("normal distribution with mean 10 and c_var 2, significance of 0.05","discrete counter", 0.05);
                DiscreteConfidenceCounter fifthNormalCounter90 = new DiscreteConfidenceCounter("normal distribution with mean 10 and c_var 4, significance of 0.1","discrete counter", 0.1);
                DiscreteConfidenceCounter fifthNormalCounter95 = new DiscreteConfidenceCounter("normal distribution with mean 10 and c_var 4, significance of 0.05","discrete counter", 0.05);
                for (int i = 0; i<n;i++)
                {
                    firstNormalCounter90.count(firstDist.getRV());
                    firstNormalCounter95.count(firstDist.getRV());
                    secondNormalCounter90.count(secondDist.getRV());
                    secondNormalCounter95.count(secondDist.getRV());
                    thirdNormalCounter90.count(thirdDist.getRV());
                    thirdNormalCounter95.count(thirdDist.getRV());
                    fourthNormalCounter90.count(fourthDist.getRV());
                    fourthNormalCounter95.count(fourthDist.getRV());
                    fifthNormalCounter90.count(fifthDist.getRV());
                    fifthNormalCounter95.count(fifthDist.getRV());
                }
                // print reports
                /*System.out.println(firstNormalCounter90.report());
                System.out.println(firstNormalCounter95.report());
                System.out.println(secondNormalCounter90.report());
                System.out.println(secondNormalCounter95.report());
                System.out.println(thirdNormalCounter90.report());
                System.out.println(thirdNormalCounter95.report());
                System.out.println(fourthNormalCounter90.report());
                System.out.println(fourthNormalCounter95.report());
                System.out.println(fifthNormalCounter90.report());
                System.out.println(fifthNormalCounter95.report());
                */
                if ((firstNormalCounter90.getLowerBound() <= 10) && (firstNormalCounter90.getUpperBound() >= 10))
                {
                    first90FractionCount++;
                }
                if ((firstNormalCounter95.getLowerBound() <= 10) && (firstNormalCounter95.getUpperBound() >= 10))
                {
                    first95FractionCount++;
                }
                if ((secondNormalCounter90.getLowerBound() <= 10) && (secondNormalCounter90.getUpperBound() >= 10))
                {
                    second90FractionCount++;
                }
                if ((secondNormalCounter95.getLowerBound() <= 10) && (secondNormalCounter95.getUpperBound() >= 10))
                {
                    second95FractionCount++;
                }
                if ((thirdNormalCounter90.getLowerBound() <= 10) && (thirdNormalCounter90.getUpperBound() >= 10))
                {
                    third90FractionCount++;
                }
                if ((thirdNormalCounter95.getLowerBound() <= 10) && (thirdNormalCounter95.getUpperBound() >= 10))
                {
                    third95FractionCount++;
                }
                if ((fourthNormalCounter90.getLowerBound() <= 10) && (fourthNormalCounter90.getUpperBound() >= 10))
                {
                    fourth90FractionCount++;
                }
                if ((fourthNormalCounter95.getLowerBound() <= 10) && (fourthNormalCounter95.getUpperBound() >= 10))
                {
                    fourth95FractionCount++;
                }
                if ((fifthNormalCounter90.getLowerBound() <= 10) && (fifthNormalCounter90.getUpperBound() >= 10))
                {
                    fifth90FractionCount++;
                }
                if ((fifthNormalCounter95.getLowerBound() <= 10) && (fifthNormalCounter95.getUpperBound() >= 10))
                {
                    fifth95FractionCount++;
                }
            }
        }
        System.out.println("first 90: " + first90FractionCount);
        System.out.println("first 95: " + first95FractionCount);
        System.out.println("second 90: " + second90FractionCount);
        System.out.println("second 95: " + second95FractionCount);
        System.out.println("third 90: " + third90FractionCount);
        System.out.println("third 95: " + third95FractionCount);
        System.out.println("fourth 90: " + fourth90FractionCount);
        System.out.println("fourth 95: " + fourth95FractionCount);
        System.out.println("fifth 90: " + fifth90FractionCount);
        System.out.println("fifth 95: " + fifth95FractionCount);


    }
}
