package uk.ac.shef.wit.textractor.core.feature;

import gnu.trove.THashSet;
import org.apache.log4j.Logger;
import uk.ac.shef.wit.textractor.JATRException;
import uk.ac.shef.wit.textractor.JATRProperties;
import uk.ac.shef.wit.textractor.model.Document;
import uk.ac.shef.wit.textractor.util.control.Normaliser;
import uk.ac.shef.wit.textractor.util.counter.TermFreqCounter;
import uk.ac.shef.wit.textractor.util.counter.WordCounter;

/**
 * A specific type of feature builder that builds an instance of FeatureDocumentTermFrequency from a GlobalIndexStore.
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

public class FeatureBuilderDocumentTermFrequency extends AbstractFeatureBuilder {

	private static Logger _logger = Logger.getLogger(FeatureBuilderDocumentTermFrequency.class);

	/**
	 * Creates an instance
	 * @param counter1 candidate term counter, counting distributions of candidate terms
	 * @param counter2 word counter, counting number of words in documents
	 * @param normaliser a normaliser for normalising terms to their canonical forms
	 * over the corpus and add up to the total frequencies of the lemma.
	 */
	public FeatureBuilderDocumentTermFrequency(TermFreqCounter counter1, WordCounter counter2, Normaliser normaliser) {
		super(counter1, counter2, normaliser);
	}

	/**
	 * Build an instance of FeatureCorpusTermFrequency
	 * @param index the global resource index
	 * @return
	 * @throws JATRException
	 */
	public FeatureDocumentTermFrequency build(GlobalResourceIndex index) throws JATRException {
		FeatureDocumentTermFrequency _feature = new FeatureDocumentTermFrequency(index);
		if (index.getTermsCanonical().size() == 0 || index.getDocuments().size() == 0) throw new
				JATRException("No resource indexed!");

		_logger.info("About to build FeatureDocumentTermFrequency...");

		int totalTermFreq = 0;
		for (Document d : index.getDocuments()) {
			_logger.info("For document " + d);
			totalTermFreq += _wordCounter.countWords(d);
			String context =d.getContent().replaceAll(JATRProperties.NP_FILTER_PATTERN, " ").replaceAll(
					"\\s+", " ");

			THashSet<String> candidates = index.retrieveTermsCanonicalInDoc(d);
			for (String np : candidates) {
				//term freq
				int tfreq = countFreq(context, index.retrieveVariantsOfTermCanonical(np).toArray(new String[0]));
				_feature.addToTermFreqInDoc(np, d, tfreq);
			}
		}
		_feature.setTotalCorpusTermFreq(totalTermFreq);
		return _feature;
	}
}
