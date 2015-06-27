package uk.ac.shef.wit.textractor.core.algorithm;

import uk.ac.shef.wit.textractor.core.feature.FeatureCorpusTermFrequency;
import uk.ac.shef.wit.textractor.core.feature.FeatureRefCorpusTermFrequency;

import java.util.Set;

/**
 * WeirdnessFeatureWrapper wraps an instance of FeatureCorpusTermFrequency, which tells a candidate term's distribution over a corpus;
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

public class MyWeirdnessFeatureWrapper extends AbstractFeatureWrapper {
	private FeatureCorpusTermFrequency _wordFreq;
	private FeatureCorpusTermFrequency _termFreq;
	private FeatureRefCorpusTermFrequency _refWordFreq;

	public MyWeirdnessFeatureWrapper(FeatureCorpusTermFrequency wordFreq,
	                               FeatureCorpusTermFrequency termFreq,
	                               FeatureRefCorpusTermFrequency refWordFreq){
		_wordFreq=wordFreq;
		_termFreq=termFreq;
		_refWordFreq=refWordFreq;
	}

	/**
	 * @return total number of words in the corpus
	 */
	public long getTotalCorpusWordFreq(){
		return _wordFreq.getTotalCorpusTermFreq();
	}

	/**
	 * @param word
	 * @return the number of occurrences of a word in the corpus
	 */
	public int getWordFreq(String word){
		Integer freq = _wordFreq.getTermFreq(word);
		return freq==null||freq==0?1:freq;
	}

	/**
	 * @param word
	 * @return the normalised frequency of a word in the reference corpus. It is equal to freq of word w divided by
	 * total frequencies
	 */
	public double getRefWordFreqNorm(String word){
		Double value = _refWordFreq.getNormalizedTermFreq(word);
		return value==0? (1.0 / _refWordFreq.getTotalRefCorpusTermFreq()) :value;
	}


	public Set<String> getTerms(){
		return _termFreq.getGlobalResourceIndex().getTermsCanonical();
	}
}
