package uk.ac.shef.wit.textractor.core.feature;

import gnu.trove.THashMap;

/**
 * A feature store that contains information of term distributions over a reference corpus. It contains following
 * information:
 * <br>- total number of occurrences of all terms found in the reference corpus, which is the sum of occrrences of each term
 * <br>- number of occurrences of each term found in the reference corpus
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

public class FeatureRefCorpusTermFrequency extends AbstractFeature {

	private THashMap<String, Integer> _refTermFreqMap = new THashMap<String, Integer>();
	private long _totalCorpusTermFreq=0;

	protected FeatureRefCorpusTermFrequency(){
		_index=null;
	}


	public long getTotalRefCorpusTermFreq(){
		return _totalCorpusTermFreq;
	}
	public void setTotalRefCorpusTermFreq(int i){
		_totalCorpusTermFreq=i;
	}

		/**
	 * increment the number of occurrences of term t by i
	 * @param t
	 * @param i
	 */
	public void addToTermFreq(String t, int i){
		Integer freq = _refTermFreqMap.get(t);
		if(freq==null) _refTermFreqMap.put(t,i);
		else _refTermFreqMap.put(t,freq+i);
		_totalCorpusTermFreq+=i;
	}

	/**
	 * Get the number of occurrences of a term in the corpus
	 * @param t
	 * @return
	 */
	public int getTermFreq(String t){
		Integer freq = _refTermFreqMap.get(t);
		return freq==null?0:freq;
	}

	/**
	 * Get the normalised frequency of a term in the corpus, which is the number of occurrences of that term as a fraction
	 * of the total number of occurrences of all terms in the corpus.
	 * @param w the id of the term
	 * @return
	 */
	public double getNormalizedTermFreq(String w){
		return (double) getTermFreq(w) / ((double) getTotalRefCorpusTermFreq()+1);
	}

}
