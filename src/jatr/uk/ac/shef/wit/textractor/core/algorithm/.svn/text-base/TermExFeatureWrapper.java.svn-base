package uk.ac.shef.wit.textractor.core.algorithm;

import uk.ac.shef.wit.textractor.core.feature.FeatureDocumentTermFrequency;
import uk.ac.shef.wit.textractor.core.feature.FeatureRefCorpusTermFrequency;
import uk.ac.shef.wit.textractor.core.feature.FeatureCorpusTermFrequency;

import java.util.Set;

/**
 * TermExFeatureWrapper wraps an instance of FeatureDocumentTermFrequency, which tells a candidate term's distribution over a corpus,
 * each document in the corpus, and existence in documents;
 * another instance of FeatureCorpusTermFrequency which tells individual words' distributions over corpus;
 * and an instance of FeatureRefCorpusTermFrequency, which tells individual words' distributions in a reference corpus.
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

public class TermExFeatureWrapper extends AbstractFeatureWrapper {

	private FeatureDocumentTermFrequency _termFreq;
	private FeatureRefCorpusTermFrequency _refWordFreq;
	private FeatureCorpusTermFrequency _wordFreq;

	/**
	 * Default constructor
	 * @param termfreq
	 * @param wordfreq
	 * @param ref
	 */
	public TermExFeatureWrapper(FeatureDocumentTermFrequency termfreq, FeatureCorpusTermFrequency wordfreq, FeatureRefCorpusTermFrequency ref) {
		_termFreq = termfreq;
		_wordFreq = wordfreq;
		_refWordFreq = ref;

	}

	/**
	 * @return total number of occurrences of terms in the corpus
	 */
	public int getTotalCorpusTermFreq() {
		return _termFreq.getTotalCorpusTermFreq();
	}

	/**
	 * @param term
	 * @return the number of occurrences of a candidate term in the corpus
	 */
	public int getTermFreq(String term) {
		int freq = _termFreq.getSumTermFreqInDocs(term);
		return freq == 0 ? 1 : freq;
	}

	/**
	 * @param term
	 * @param d
	 * @return the term's frequency in document with id=d
	 */
	public int getTermFreqInDoc(String term, int d) {
		return _termFreq.getTermFreqInDoc(term, d);
	}

	/**
	 * @param t
	 * @return the ids of documents in which term t is found
	 */
	public int[] getTermAppear(String t) {
		return _termFreq.getTermAppear(t);
	}

	/**
	 * @param term
	 * @return total number of occurrences of a term in the documents in which it is found
	 */
	public int getSumTermFreqInDocs(String term) {
		int[] docs = getTermAppear(term);
		int sum = 0;
		for (int i : docs) {
			sum += getTermFreqInDoc(term, i);
		}
		return sum == 0 ? 1 : sum;
	}

	/**
	 * @param t
	 * @param d
	 * @return normalised term frequency in a document with id=d. It is equal to freq of term t in d divided by
	 * total term frequency in d.
	 */
	public double getNormFreqInDoc(String t, int d) {
		return (getTermFreqInDoc(t, d) + 0.1) / (getSumTermFreqInDocs(t) + 1);
	}

	/**
	 * @param word
	 * @return the number of occurrences of a word in the corpus
	 */
	public int getWordFreq(String word) {
		int freq = _wordFreq.getTermFreq(word);
		return freq == 0 ? 1 : freq;
	}

	/**
	 * @param word
	 * @return the normalised frequency of a word in the reference corpus. It is equal to freq of word w divided by
	 * total frequencies
	 */
	public double getRefWordFreqNorm(String word) {
		return _refWordFreq.getNormalizedTermFreq(word);
	}

	public Set<String> getTerms() {
		return _termFreq.getGlobalResourceIndex().getTermsCanonical();
	}
}
