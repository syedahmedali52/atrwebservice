/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.shef.wit.textractor.core.algorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import uk.ac.shef.wit.textractor.JATRException;
import uk.ac.shef.wit.textractor.model.Term;

/**
 *
 * @author ischmidt
 */
public class WFAlgorithm implements Algorithm {
    private String name = "WF_ATR_ALGORITHM";
    
    public Term[] execute(AbstractFeatureWrapper store) throws JATRException {
        if (!(store instanceof MyWeirdnessFeatureWrapper)) {
            throw new JATRException("" +
                    "Required: MyWeirdnessFeatureWrapper");
        }
        MyWeirdnessFeatureWrapper gFeatureStore = (MyWeirdnessFeatureWrapper) store;
        Set<Term> result = new HashSet<Term>();

        for (String s : gFeatureStore.getTerms()) {
            String[] elements = s.split(" ");
            double T = (double) elements.length;
            double SUMwi = 0.0;

            for (int i = 0; i < T; i++) {
                String wi = elements[i];
                double v = (double) gFeatureStore.getWordFreq(wi);
                SUMwi += Math.log(v);
            }

            double TD = SUMwi / T;
            result.add(new Term(s, TD));
        }

        Term[] all = result.toArray(new Term[0]);
        Arrays.sort(all);
        return all;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
