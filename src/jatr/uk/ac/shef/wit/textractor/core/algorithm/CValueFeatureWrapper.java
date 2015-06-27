package uk.ac.shef.wit.textractor.core.algorithm;

import uk.ac.shef.wit.textractor.core.feature.FeatureCorpusTermFrequency;
import uk.ac.shef.wit.textractor.core.feature.FeatureTermNest;

import java.util.Set;

import gnu.trove.TIntHashSet;

/**
 * CValueFeatureWrapper wraps an instance of FeatureCorpusTermFrequency, which tells a term's distribution over a corpus;
 * and an instance of FeatureTermNest, which tells term-nested-in-term relations
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

public class CValueFeatureWrapper extends AbstractFeatureWrapper {

	private FeatureCorpusTermFrequency _termFreq;
	private FeatureTermNest _termNest;

	/**
	 * Creates an instance. CValueFeatureWrapper wraps an instance of FeatureCorpusTermFrequency and an instance of FeatureTermNest
	 * @param termFreq
	 * @param termNest
	 */
	public CValueFeatureWrapper(FeatureCorpusTermFrequency termFreq, FeatureTermNest termNest){
		_termFreq=termFreq;
		_termNest=termNest;
	}

	/**
	 * @param term
	 * @return number of occurrences of a term in a corpus. If a term cannot be found it always returns 1.
	 */
	public int getTermFreq(String term){
		int freq= _termFreq.getTermFreq(term);
		return freq==0?1:freq;
	}

	/**
	 * @param id - the id of the term in question
	 * @return number of occurrences of a term in a corpus. If a term cannot be found it always returns 1.
	 */
	public int getTermFreq(int id){
		int freq= _termFreq.getTermFreq(id);
		return freq==0?1:freq;
	}

	/**
	 * @param nested
	 * @return ids of terms in which the provided term nests in
	 */
	public int[] getNestsOf(String nested){
		TIntHashSet res = _termNest.getNestIdsOf(nested);
		return res==null? new int[0]:res.toArray();
	}

	public Set<String> getTerms(){
		return _termFreq.getGlobalResourceIndex().getTermsCanonical();
	}

}
