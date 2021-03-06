package uk.ac.shef.wit.textractor.core.feature;

import org.apache.log4j.Logger;
import uk.ac.shef.wit.textractor.JATRException;
import uk.ac.shef.wit.textractor.core.npextractor.CandidateTermExtractor;
import uk.ac.shef.wit.textractor.model.Corpus;
import uk.ac.shef.wit.textractor.model.Document;

import java.util.Set;
import java.util.Map;

/**
 * Builds a GlobalResourceIndex from a corpus
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

public class GlobalResourceIndexBuilder {

	private static Logger _logger = Logger.getLogger(GlobalResourceIndexBuilder.class);

	private boolean BUILD_TERM_TO_DOC_MAP = true;
	private boolean BUILD_DOC_TO_TERM_MAP = true;



	public GlobalResourceIndexBuilder() {
	}

	/**
	 * creates an instance
	 * @param textunit2DocMap true if should create information of textunit-document binary relation
	 * @param doc2TextunitMap true if should create information of document-textunit binary relation
	 */
	public GlobalResourceIndexBuilder(boolean textunit2DocMap, boolean doc2TextunitMap) {
		BUILD_TERM_TO_DOC_MAP = textunit2DocMap;
		BUILD_DOC_TO_TERM_MAP = doc2TextunitMap;
	}

	/**
	 * Build a GlobalResourceIndex from a corpus, using the specified text unit extractor
	 * @param c
	 * @param extractor
	 * @return
	 * @throws JATRException
	 */
	public GlobalResourceIndex build(Corpus c, CandidateTermExtractor extractor) throws JATRException {
		GlobalResourceIndex _index = new GlobalResourceIndex();
		 for (Document d : c) {
			_logger.info("For Document " + d);
			Map<String,Set<String>> nps = extractor.extract(d);
			_index.indexTermWithVariant(nps);
			if (BUILD_DOC_TO_TERM_MAP) _index.indexDocWithTermsCanonical(d, nps.keySet());
			if (BUILD_TERM_TO_DOC_MAP) {
				for (String t : nps.keySet()) _index.indexTermCanonicalInDoc(t, d);
			}
		}
		return _index;
	}
//        public GlobalResourceIndex build(String input,CandidateTermExtractor extractor) throws JATRException {
//            GlobalResourceIndex _index = new GlobalResourceIndex();
//            _logger.info("For Document " + input);
//			Map<String,Set<String>> nps = extractor.extract(input);
//			_index.indexTermWithVariant(nps);
//			if (BUILD_DOC_TO_TERM_MAP) _index.indexDocWithTermsCanonical(d, nps.keySet());
//			if (BUILD_TERM_TO_DOC_MAP) {
//				for (String t : nps.keySet()) _index.indexTermCanonicalInDoc(t, d);
//			}
//            return _index;
//        }
}

