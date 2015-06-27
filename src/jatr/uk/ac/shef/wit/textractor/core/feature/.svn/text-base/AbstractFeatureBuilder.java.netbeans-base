package uk.ac.shef.wit.textractor.core.feature;

import gnu.trove.TIntHashSet;
import uk.ac.shef.wit.textractor.JATRException;
import uk.ac.shef.wit.textractor.util.control.Normaliser;
import uk.ac.shef.wit.textractor.util.counter.TermFreqCounter;
import uk.ac.shef.wit.textractor.util.counter.WordCounter;

/**
 * Feature builder that builds a feature from a GlobalResourceIndex
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

public abstract class AbstractFeatureBuilder {

	protected TermFreqCounter _termFreqCounter;// = new NPFreqCounter();
	protected Normaliser _normaliser; //= new Lemmatizer();
	protected WordCounter _wordCounter;// = new WordCounter();

	/**
	 * Creates an instance
	 *
	 * @param counter1   an instance of term frequency counter
	 * @param counter2   an instance of word counter
	 * @param normaliser an instance of Normaliser, which returns a candidate term to its canonical form
	 */
	public AbstractFeatureBuilder(TermFreqCounter counter1, WordCounter counter2, Normaliser normaliser) {
		_termFreqCounter = counter1;
		_wordCounter = counter2;
		_normaliser = normaliser;
	}

	public abstract AbstractFeature build(GlobalResourceIndex index) throws JATRException;

	/**
	 * Count distributions of a candidate term in a text by counting every variations of that term found in the corpus
	 * and adding them up
	 *
	 * @param termVariants the variant forms of a term
	 * @param context
	 * @return
	 */
	protected int countFreq(String context, String... termVariants) {
		TIntHashSet offsets = new TIntHashSet();

		if (termVariants != null) {
			for (String form : termVariants) {
				offsets.addAll(_termFreqCounter.countOffsets(form.trim().toLowerCase(), context.toLowerCase()));
			}

		}
		return offsets.size();
	}

}
