
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.shef.wit.textractor.core.algorithm;

import uk.ac.shef.wit.textractor.JATRException;
import uk.ac.shef.wit.textractor.model.Term;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
/**
 *
 * @author ischmidt
 */
public class TFAlgorithm implements Algorithm {

    private String name = "TF_ATR_ALGORITHM";
    
    public Term[] execute(AbstractFeatureWrapper store) throws JATRException {
        if (!(store instanceof LRFeatureWrapper)) {
            throw new JATRException("" +
                    "Required: LRFeatureWrapper");
        }
        LRFeatureWrapper gFeatureStore = (LRFeatureWrapper) store;
        Set<Term> result = new HashSet<Term>();

        double N = (double) gFeatureStore.getTotalCorpusWordFreq();
        double N_ = (double) gFeatureStore.getTotalRefCorpusWordFreq();
        for (String s : gFeatureStore.getTerms()) {
            double tf = gFeatureStore.getTermFreq(s);
            result.add(new Term(s, tf));
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
