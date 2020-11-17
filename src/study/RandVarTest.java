package study;

import simulation.lib.counter.DiscreteCounter;
import simulation.lib.randVars.continous.ErlangK;
import simulation.lib.randVars.continous.Exponential;
import simulation.lib.randVars.continous.HyperExponential;
import simulation.lib.randVars.continous.Uniform;
import simulation.lib.rng.RNG;
import simulation.lib.rng.StdRNG;

/*
 * TODO Problem 2.3.3 and 2.3.4[Bonus] - implement this class
 * You can call your test from the main-method in SimulationStudy
 */
public class RandVarTest {

    public static void testRandVars() {
        RNG rng = new StdRNG();
        ErlangK erlang = new ErlangK(rng, 2, 2);
        Uniform uniform = new Uniform(rng, 0, 1);
        Exponential exp = new Exponential(rng, 2);
        //HyperExponential h = new HyperExponential(rng,2,2, 2, 2);

        uniform.setMeanAndStdDeviation(1,.1);
        erlang.setMeanAndStdDeviation(1,.1);
        exp.setMeanAndStdDeviation(1,.1);

        //uniform.setMeanAndStdDeviation(1,.1);
        //erlang.setMeanAndStdDeviation(1,1);
        //exp.setMeanAndStdDeviation(1,1);

        //uniform.setMeanAndStdDeviation(1,.1);
        //erlang.setMeanAndStdDeviation(1,2);
        //exp.setMeanAndStdDeviation(1,2);

        System.out.println("Uniform distribution:");
        System.out.println(uniform.toString());
        System.out.println("erlang distribution:");
        System.out.println(erlang.toString());
        System.out.println("Exponential distribution:");
        System.out.println(exp.toString());

        DiscreteCounter dcUniform = new DiscreteCounter("Uniform counter");
        DiscreteCounter dcErlang = new DiscreteCounter("Erlang counter");
        DiscreteCounter dcExp = new DiscreteCounter("Exponential counter");

        for (int i = 0; i<1000000;i++){
            dcUniform.count(uniform.getRV());
            dcErlang.count(erlang.getRV());
            dcExp.count(exp.getRV());
        }

    }
}
