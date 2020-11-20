package simulation.lib.counter;

import java.util.Arrays;

/**
 * This class implements a discrete time confidence counter
 */
public class DiscreteConfidenceCounter extends DiscreteCounter {
    /*
     * TODO Problem 3.1.2 - implement this class according to the given class diagram!
     * Hint: see section 4.4 in course syllabus
     */
    private double alpha;

    /*	Row 1: degrees of freedom
     *  Row 2: alpha 0.01
     *  Row 3: alpha 0.05
     *  Row 4: alpha 0.10
     */
    private double tAlphaMatrix[][] = new double[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 1000000},
            {63.657, 9.925, 5.841, 4.604, 4.032, 3.707, 3.499, 3.355, 3.250, 3.169, 2.845, 2.750, 2.704, 2.678, 2.660, 2.648, 2.639, 2.632, 2.626, 2.576},
            {12.706, 4.303, 3.182, 2.776, 2.571, 2.447, 2.365, 2.306, 2.262, 2.228, 2.086, 2.042, 2.021, 2.009, 2.000, 1.994, 1.990, 1.987, 1.984, 1.960},
            {6.314, 2.920, 2.353, 2.132, 2.015, 1.943, 1.895, 1.860, 1.833, 1.812, 1.725, 1.697, 1.684, 1.676, 1.671, 1.667, 1.664, 1.662, 1.660, 1.645}};

    /**
     * Basic constructor
     * @param variable the variable to observe
     */
    public DiscreteConfidenceCounter(String variable) {
        super(variable, "counter type: discrete-time counter");
    }

    /**
     * Basic constructor
     * @param variable the variable to observe
     * @param alpha level of significance
     */
    public DiscreteConfidenceCounter(String variable, double alpha) {
        super(variable,"counter type: discrete-time counter");
        this.alpha = alpha;
    }

     /** Basic constructor
     * @param variable the variable to observe
     * @param alpha level of significance
     */
    public DiscreteConfidenceCounter(String variable, String type, double alpha) {
        super(variable, type);
        this.alpha = alpha;
    }

    /**
     * Returns the t_low/t_high of the observed variable
     * @param degsOfFreedom equals number of given samples PLUS ONE !
     * @return the t_low/t_high
     */
    public double getT(long degsOfFreedom) {
        int row = getRow() - 1; //Hilfsvariable (Alpha-Reihen-Index für tAlphaMatrix)

        if (degsOfFreedom > 0 && degsOfFreedom <= 1000000) {
            int index = Arrays.binarySearch(tAlphaMatrix[0], (double) degsOfFreedom); //gives index of degsOfFreedom in tAlphaMatrix[0]. negative value if not found

            if (index >= 0) {
                //degsOfFreedom is found in tAlphaMatrix[0]. return corresponding value in tAlphaMatrix[0]
                return tAlphaMatrix[row][index];
            }
            else {
                //degsOfFreedom is NOT found in tAlphaMatrix[0] and needs to be interpolated
                double dflow;   //Hilfsvariable (Index des nächsten linken Nachbarn von n in tAlphaMatrix[0])
                double dfhigh;  //Hilfsvariable (Index des nächsten rechten Nachbarn von n in tAlphaMatrix[0])
                for (int i = 0; i < tAlphaMatrix[0].length; i++) {
                    if (degsOfFreedom < tAlphaMatrix[0][i]){
                        dfhigh = i;
                        dflow = i-1;
                        return linearInterpol(dflow, dfhigh, tAlphaMatrix[row][(int) dflow],tAlphaMatrix[row][(int) dfhigh], degsOfFreedom);
                    }
                }
            }
        }
        //if degsOfFreedom > 1000000, we set degsOfFreedom to 1000000
        return tAlphaMatrix[row][1000000];
    }



    /**
     * Returns the linearly interpolated value if degsOfFreedom is between two entries in the corresponding tAlphaMatrix-Array
     * @return the inearly interpolated value for a degsOfFreedom not given in tAlphaMatrix
     */
        private double linearInterpol(double dflow, double dfhigh, double tlow, double thigh, long degsOfFreedom) {
            return (tlow + ((thigh - tlow)/(dfhigh - dflow)) * ((degsOfFreedom-1) - dflow));
        }


     /**
     * Returns the most suitable quantile row for a value of alpha
     * @return the most suitable quantile row
      * Row 2:  alpha 0.01      if alpha < 0.05,
      * Row 3:  alpha 0.05      if 0.05 ≤ alpha < 0.1,
      * Row 4:  alpha 0.1       otherwise
     */
    private int getRow(){
        if(alpha <=1 && alpha >= 0) {
            if(alpha < 0.05) return 2;
            else if(alpha < 0.1) return 3;
            else return 4;
        }
        return -1;
    }

    /**
     * Returns the lower bound of confidence interval
     * @return the lower bound
     */
    public double getLowerBound(){
        //Formula (-) 4.8, sec 4.4
        return (getMean() - getT(getNumSamples()) * Math.sqrt(getVariance()/getNumSamples()) );
    }

    /**
     * Returns the upper bound of confidence interval
     * @return the upper bound
     */
    public double getUpperBound(){
        //Formula (+) 4.8, sec 4.4
        return (getMean() + getT(getNumSamples()) * Math.sqrt(getVariance()/getNumSamples()) );
    }

    /**
     * Calculates the confidence interval bound (formula 4.8, sec 4.4)
     * @return the lower/upper bound
     */
    public double getBound(){
        //ToDo Keine Ahnung warum man das braucht, obwohl man schon getUpper- bzw. getLowerBound hat....
        return -1;
    }

    /**
     * @see Counter#report()
     * Uncomment this function when you have implemented this class for reporting.
     */
    @Override
    public String report() {
        String out = super.report();
        out += ("" + "\talpha = " + alpha + "\n" +
                "\tt(1-alpha/2) = " + getT(getNumSamples() - 1) + "\n" +
                "\tlower bound = " + getLowerBound() + "\n" +
                "\tupper bound = " + getUpperBound());
        return out;
    }

    /**
     * @see Counter#csvReport(String)
     * Uncomment this function when you have implemented this class for reporting.
     */
    @Override
    public void csvReport(String outputdir) {
        String content = observedVariable + ";" + getNumSamples() + ";" + getMean() + ";" + getVariance() + ";" + getStdDeviation() + ";" +
                getCvar() + ";" + getMin() + ";" + getMax() + ";" + alpha + ";" + getT(getNumSamples() - 1) + ";" + getLowerBound() + ";" +
                getUpperBound() + "\n";
        String labels = "#counter ; numSamples ; MEAN; VAR; STD; CVAR; MIN; MAX;alpha;t(1-alpha/2);lowerBound;upperBound\n";
        writeCsv(outputdir, content, labels);
    }

}


