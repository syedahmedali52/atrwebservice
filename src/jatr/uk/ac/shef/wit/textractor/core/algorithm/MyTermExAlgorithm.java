package uk.ac.shef.wit.textractor.core.algorithm;

import uk.ac.shef.wit.textractor.model.Term;
import uk.ac.shef.wit.textractor.JATRException;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

/**
 * An implementation of the TermEx term recognition algorithm. See Sclano e. al 2007, <i>
 * TermExtractor: a Web application to learn the shared terminology of emergent web communities</i>
 * <p>
 * In the formula w(t,Di ) =a* DR + B* DC + Y* LC, default values of a, B, and Y are 0.33.
 * </p>
 *
 * This is the implementation of the scoring formula <b>only</b> and does not include the analysis of document structure
 * as discussed in the paper.
 *
 * @author <a href="mailto:z.zhang@dcs.shef.ac.uk">Ziqi Zhang</a>
 */

/*
(c) Copyright 2005 Natural Language Processing Group, The University of Sheffield
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.

3. The name of the author may not be used to endorse or promote products
derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class MyTermExAlgorithm implements Algorithm {

    private final double _alpha;
    private final double _beta;
    private final double _zeta;
    private String name = "MyTermEx_ATR_ALGORITHM";

    public MyTermExAlgorithm() {
        this(0.33, 0.33, 0.34);
    }

    public MyTermExAlgorithm(double alpha, double beta, double zeta) {
        _alpha = alpha;
        _beta = beta;
        _zeta = zeta;
    }

    public Term[] execute(AbstractFeatureWrapper store) throws JATRException {
        if (!(store instanceof MyTermExFeatureWrapper)) {
            throw new JATRException("" +
                    "Required: MyTermExFeatureWrapper");
        }
        MyTermExFeatureWrapper gFeatureStore = (MyTermExFeatureWrapper) store;
        Set<Term> result = new HashSet<Term>();

        double maxDC = Double.NEGATIVE_INFINITY;
        double minDC = Double.POSITIVE_INFINITY;

        double[] DCs = new double[gFeatureStore.getTerms().size()];
        double[] DRs = new double[gFeatureStore.getTerms().size()];
        double[] LCs = new double[gFeatureStore.getTerms().size()];

        int index = 0;
        for (String s : gFeatureStore.getTerms()) {
            double score;
            String[] elements = s.split(" ");
            double T = (double) elements.length;
            double SUMwi = 0.0;
            double SUMfwi = 0.0;

            for (int i = 0; i < T; i++) {
                String wi = elements[i];
                SUMwi += (double) gFeatureStore.getWordFreq(wi) / (double) gFeatureStore.getTotalCorpusTermFreq() /
                        (gFeatureStore.getRefWordFreqNorm(wi) + ((double) gFeatureStore.getWordFreq(wi) / (double) gFeatureStore.getTotalCorpusTermFreq()));
                SUMfwi += (double) gFeatureStore.getWordFreq(wi);
            }

            //calc DC
            int[] docs = gFeatureStore.getTermAppear(s);
            double sum = 0;
            for (int i : docs) {
                double norm = gFeatureStore.getNormFreqInDoc(s, i);
                if (norm == 0) {
                    sum += 0;
                } else {
                    //sum += norm * Math.log(norm + 0.1);
                    sum += norm * Math.log(norm);
                }
            }

            double DR = SUMwi;
            double DC = -sum;
            double LC = (T * Math.log(gFeatureStore.getTermFreq(s) + 1) * gFeatureStore.getTermFreq(s)) / SUMfwi;

            //System.err.println(DR+" "+DC+" "+LC);
			/*score = _alpha * DR + _beta * DC + _zeta * LC;
            result.add(new Term(s, score));*/

            DRs[index] = DR;
            DCs[index] = DC;
            LCs[index] = LC;

            if (DC < minDC) {
                minDC = DC;
            }
            if (DC > maxDC) {
                maxDC = DC;
            }
            index++;
        }

        index = 0;
        for (String s : gFeatureStore.getTerms()) {
            double DR = DRs[index];
            double DC = DCs[index];
            double LC = LCs[index];

            // Normalize DC
            DC = (DC - minDC) / (maxDC - minDC);

            //System.err.println(DR+" "+DC+" "+LC);
            double score = _alpha * DR + _beta * DC + _zeta * LC;

            // System.err.println("MyTermEx: " + s + " " + score);

            result.add(new Term(s, score));

            index++;
        }

        Term[] all = result.toArray(new Term[0]);
        Arrays.sort(all);
        return all;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
