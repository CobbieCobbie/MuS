package study;

import simulation.lib.counter.DiscreteAutocorrelationCounter;

public class AutocorrelationTest {

    public static void testAutocorrelation() {
        double[] sampleSeriesOne = {2,2,2,2,2,2,2,2,2,2};
        double[] sampleSeriesTwo = {-2,-2,-2,-2,-2,-2,-2,-2,-2,-2};
        double[] sampleSeriesThree = {2,-2,2,-2,2,-2,2,-2,2,-2};
        double[] sampleSeriesFour = {-2,2,-2,2,-2,2,-2,2,-2,2};
        double[] sampleSeriesFive = {2,2,2,2,2,2,-2,2,2,-2};
        double[] sampleSeriesSix = {-2,-2,-2,-2,-2,2,2,2,2,2};

        DiscreteAutocorrelationCounter autocorrOne = new DiscreteAutocorrelationCounter("SampleSeriesOne",10);
        DiscreteAutocorrelationCounter autocorrTwo = new DiscreteAutocorrelationCounter("SampleSeriesTwo",10);
        DiscreteAutocorrelationCounter autocorrThree = new DiscreteAutocorrelationCounter("SampleSeriesThree",10);
        DiscreteAutocorrelationCounter autocorrFour = new DiscreteAutocorrelationCounter("SampleSeriesFour",10);
        DiscreteAutocorrelationCounter autocorrFive = new DiscreteAutocorrelationCounter("SampleSeriesFive",10);
        DiscreteAutocorrelationCounter autocorrSix = new DiscreteAutocorrelationCounter("SampleSeriesSix",10);

        for (int i = 0;i<10;i++)
        {
            autocorrOne.count(sampleSeriesOne[i]);
            autocorrTwo.count(sampleSeriesTwo[i]);
            autocorrThree.count(sampleSeriesThree[i]);
            autocorrFour.count(sampleSeriesFour[i]);
            autocorrFive.count(sampleSeriesFive[i]);
            autocorrSix.count(sampleSeriesSix[i]);
        }

        System.out.println(autocorrOne.report());
        System.out.println(autocorrTwo.report());
        System.out.println(autocorrThree.report());
        System.out.println(autocorrFour.report());
        System.out.println(autocorrFive.report());
        System.out.println(autocorrSix.report());
    }
}
