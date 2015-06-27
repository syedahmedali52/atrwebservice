package uk.ac.shef.wit.textractor.core.feature;

import gnu.trove.TIntIntHashMap;

/**
 * A feature store that contains information of term distributions over a corpus. It contains following information:
 * <br>- total number of occurrences of all terms found in the corpus, which is the sum of occrrences of each term
 * <br>- number of occurrences of each term found in the corpus
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

public class FeatureCorpusTermFrequency extends AbstractFeature {

	private TIntIntHashMap _termFreqMap = new TIntIntHashMap();
	private int _totalCorpusTermFreq = 0;

	protected FeatureCorpusTermFrequency(GlobalResourceIndex index){
		_index=index;
	}

	public int getTotalCorpusTermFreq(){
		return _totalCorpusTermFreq;
	}
	public void setTotalCorpusTermFreq(int i){
		_totalCorpusTermFreq=i;
	}

	/**
	 * increment the number of occurrences of term by i
	 * @param term
	 * @param i
	 */
	public void addToTermFreq(String term, int i){
		addToTermFreq(_index.indexTermCanonical(term),i);
	}
	/**
	 * increment the number of occurrences of term with id t by i
	 * @param t
	 * @param i
	 */
	public void addToTermFreq(int t, int i){
		_termFreqMap.put(t,_termFreqMap.get(t)+i);
	}

	/**
	 * Set the number of occurrences of a term
	 * @param term
	 * @param freq
	 */
	public void setTermFreq(String term, int freq){
		setTermFreq(_index.indexTermCanonical(term),freq);
	}
	/**
	 * Set the number of occurrences of a term with id t
	 * @param t
	 * @param freq
	 */
	public void setTermFreq(int t, int freq){
		_termFreqMap.put(t,freq);
	}
	/**
	 * Get the number of occurrences of a term in the corpus
	 * @param term
	 * @return
	 */
	public int getTermFreq(String term){
		return getTermFreq(_index.indexTermCanonical(term));
	}
	/**
	 * Get the number of occurrences of a term in the corpus
	 * @param t the id of the term
	 * @return
	 */
	public int getTermFreq(int t){
		return _termFreqMap.get(t);
	}

	/**
	 * Get the normalised frequency of a term in the corpus, which is the number of occurrences of that term as a fraction
	 * of the total number of occurrences of all terms in the corpus.
	 * @param term
	 * @return
	 */
	public double getNormalizedTermFreq(String term){
		return getNormalizedTermFreq(_index.indexTermCanonical(term));
	}
	/**
	 * Get the normalised frequency of a term in the corpus, which is the number of occurrences of that term as a fraction
	 * of the total number of occurrences of all terms in the corpus.
	 * @param t the id of the term
	 * @return
	 */
	public double getNormalizedTermFreq(int t){
		return (double) getTermFreq(t) / ((double) getTotalCorpusTermFreq()+1);
	}
}
