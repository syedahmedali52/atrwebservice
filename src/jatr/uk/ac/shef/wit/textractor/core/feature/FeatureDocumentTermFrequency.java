package uk.ac.shef.wit.textractor.core.feature;

import gnu.trove.TIntIntHashMap;
import gnu.trove.THashMap;
import gnu.trove.TIntHashSet;
import gnu.trove.TIntIterator;
import uk.ac.shef.wit.textractor.model.Document;

/**
 * A feature store that contains information of term distributions in each document. It contains following information:
 * <br>- total number of occurrences of all terms found in the corpus, which is the sum of occrrences of each term
 * <br>- number of occurrences of each term in each document
 * <br>- existence of terms in documents
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

public class FeatureDocumentTermFrequency extends AbstractFeature {

	private THashMap<Integer, TIntIntHashMap> _termInDocFreqMap = new THashMap<Integer, TIntIntHashMap>();
	private int _totalTermFreq=0;

	protected FeatureDocumentTermFrequency(GlobalResourceIndex index){
		_index=index;
	}

	/**
	 * Set total number of occurrences of all terms in the corpus
	 * @param i
	 */
	public void setTotalCorpusTermFreq(int i){
		_totalTermFreq=i;
	}
	public int getTotalCorpusTermFreq(){
		return _totalTermFreq;
	}

	/**
	 * Increment term t's number of occurrences in d by freq
	 * @param t
	 * @param d
	 * @param freq
	 */
	public void addToTermFreqInDoc(String t, Document d, int freq){
		addToTermFreqInDoc(_index.indexTermCanonical(t),_index.indexDocument(d),freq);
	}

	/**
	 * Increment term t (id) number of occurrences in d by freq
	 * @param t
	 * @param d
	 * @param freq
	 */
	public void addToTermFreqInDoc(int t, int d, int freq){
		TIntIntHashMap freqs = _termInDocFreqMap.get(t);
		if(freqs==null) freqs=new TIntIntHashMap();
		freqs.put(d,freq);
		_termInDocFreqMap.put(t,freqs);
	}

	/**
	 * @param term
	 * @param d
	 * @return number of occurrences of a term t in a document d
	 */
	public int getTermFreqInDoc(String term, Document d){
		return getTermFreqInDoc(_index.indexTermCanonical(term), _index.indexDocument(d));
	}

	/**
	 * @param t
	 * @param d
	 * @return number of occurrences of a term identified by id t in a document identified by id d
	 */
	public int getTermFreqInDoc(int t, int d){
		TIntIntHashMap freqInDocs=_termInDocFreqMap.get(t);
		if(freqInDocs==null) return 0;
		return freqInDocs.get(d);
	}

	/**
	 * @param t
	 * @param d
	 * @return number of occurrences of a term t in a document identified by id d
	 */
	public int getTermFreqInDoc(String t, int d){
		return getTermFreqInDoc(_index.indexTermCanonical(t), d);
	}

	/**
	 * @param t
	 * @return the id's of documents in which term t are found
	 */
	public int[] getTermAppear(String t){
		return _termInDocFreqMap.get(_index.indexTermCanonical(t)).keys();
	}

	/**
	 * @param term
	 * @return number of occurrences of a term in all documents
	 */
	public int getSumTermFreqInDocs(String term){
		return getSumTermFreqInDocs(_index.indexTermCanonical(term));
	}

	/**
	 * @param term
	 * @return number of occurrences of a term in all documents
	 */
	public int getSumTermFreqInDocs(int term){
		TIntHashSet docs = _index.retrieveDocIdsContainingTermCanonical(term);
		int sum=0;
		TIntIterator it = docs.iterator();
		while(it.hasNext())
			sum+=getTermFreqInDoc(term, it.next());

		return sum;
	}

}
