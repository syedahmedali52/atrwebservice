package uk.ac.shef.wit.textractor.core.algorithm;

import uk.ac.shef.wit.textractor.JATRException;
import uk.ac.shef.wit.textractor.model.Term;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * An implementation of the word weirdness algorithm applied to term recognition algorithm. See
 * Ahmad et al 1999, <i>Surrey Participation in TREC8: Weirdness Indexing for Logical Document Extrapolation
 * and Retrieval</i>
 *
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
public class MyWeirdnessAlgorithm implements Algorithm {

    private String name = "MyWeirdness_ATR_ALGORITHM";

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
                double v = (double) gFeatureStore.getWordFreq(wi) / (double) gFeatureStore.getTotalCorpusWordFreq() / gFeatureStore.getRefWordFreqNorm(wi);
                //SUMwi += Math.log((double) gFeatureStore.getWordFreq(wi) / (double) gFeatureStore.getTotalCorpusWordFreq() / gFeatureStore.getRefWordFreqNorm(wi));
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

    public String toString() {
        return name;
    }
}
