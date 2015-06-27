package uk.ac.shef.wit.textractor.core.algorithm;

import uk.ac.shef.wit.textractor.JATRException;
import uk.ac.shef.wit.textractor.model.Term;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * An implementation of the GlossEx term recognition algorithm. See Kozakov, et. al 2004, <i>
 * Glossary extraction and utilization in the information search and delivery system for IBM Technical Support</i>
 *. This is the implementation of the scoring formula <b>only</b>, and does not include the filtering algorithm as mentioned
 * in the paper.
 * <p>
 * In the equation C(T) = a* TD(T) + B*TC(T), default a=0.2, B = 0.8.
 * </p>
 *
 * You might need to modify the value of B by increasing it substaintially when the reference corpus is relatively
 * much bigger than the target corpus, such as the BNC corpus. For details, please refer to the paper.
 *
 * //todo:test this class, see TC and TD values
 * @author <a href="mailto:z.zhang@dcs.shef.ac.uk">Ziqi Zhang</a>
 */

/*
(c) Copyright 2004 Natural Language Processing Group, The University of Sheffield
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
public class GlossExAlgorithm implements Algorithm {

    private final double _alpha;
    private final double _beta;
    private String name = "IBM_GlossEx_ATR_ALGORITHM";

    public GlossExAlgorithm() {
        this(0.2, 0.8);
    }

    public GlossExAlgorithm(double alpha, double beta) {
        _alpha = alpha;
        _beta = beta;
    }

    public Term[] execute(AbstractFeatureWrapper store) throws JATRException {
        if (!(store instanceof GlossExFeatureWrapper)) {
            throw new JATRException("" +
                    "Required: GlossExFeatureWrapper");
        }
        GlossExFeatureWrapper gFeatureStore = (GlossExFeatureWrapper) store;
        Set<Term> result = new HashSet<Term>();

        for (String s : gFeatureStore.getTerms()) {
            double score;
            String[] elements = s.split(" ");
            double T = (double) elements.length;
            double SUMwi = 0.0;
            double SUMfwi = 0.0;

            for (int i = 0; i < T; i++) {
                String wi = elements[i];
                SUMwi += Math.log((double) gFeatureStore.getWordFreq(wi) / (double) gFeatureStore.getTotalCorpusTermFreq() / gFeatureStore.getRefWordFreqNorm(wi));
                SUMfwi += (double) gFeatureStore.getWordFreq(wi);
            }

            double TD = SUMwi / T;
            double TC = (T * Math.log10(gFeatureStore.getTermFreq(s) + 1) * gFeatureStore.getTermFreq(s)) / SUMfwi;

            if (T == 1) {
                score = 0.9 * TD + 0.1 * TC;
            } else {
                score = _alpha * TD + _beta * TC;
            }
            result.add(new Term(s, score));
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
