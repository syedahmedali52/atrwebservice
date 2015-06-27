package uk.ac.shef.wit.textractor.core.npextractor;

import gnu.trove.THashMap;
import gnu.trove.THashSet;
import uk.ac.shef.wit.textractor.JATRException;
import uk.ac.shef.wit.textractor.JATRProperties;
import uk.ac.shef.wit.textractor.model.Corpus;
import uk.ac.shef.wit.textractor.model.Document;
import uk.ac.shef.wit.textractor.util.control.Normaliser;
import uk.ac.shef.wit.textractor.util.control.StopList;

import java.util.Map;
import java.util.Set;

/**
 * Extracts words from texts. Words will be lemmatised to reduce deviations. Characters which are not one of the followings
 * are replaced by whitespaces:
 * <br>letter, digit, -
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

public class WordExtractor extends CandidateTermExtractor {

	/**
	 * Creates an instance with specified stopwords list and normaliser
	 * @param stop a list of words which are unlikely to occur in a domain specific candidate term
	 * @param normaliser an instance of a Normaliser which returns candidate term to canonical form
	 */
	public WordExtractor(StopList stop, Normaliser normaliser) {
		_stoplist = stop;
		_normaliser = normaliser;
	}

	public Map<String,Set<String>> extract(Corpus c) throws JATRException {
		Map<String, Set<String>> res = new THashMap<String, Set<String>>();
		for (Document d : c) {
			for(Map.Entry<String, Set<String>> e: extract(d).entrySet()){
				Set<String> variants = res.get(e.getKey());
				variants=variants==null?new THashSet<String>():variants;
				variants.addAll(e.getValue());
				res.put(e.getKey(),variants);
			}
		}

		return res;
	}

	public Map<String,Set<String>> extract(Document d) throws JATRException {
		return extract(d.getContent());
	}

	public Map<String,Set<String>> extract(String content) throws JATRException {
		String[] words = content.replaceAll(JATRProperties.NP_FILTER_PATTERN, " ").replaceAll("\\s+", " ").trim().split(" ");
		Map<String, Set<String>> result = new THashMap<String, Set<String>>();
		for (String w : words) {
			w=w.trim();
			//
			w=w.toLowerCase();
			if(_stoplist.isStopWord(w)) continue;
			w= _normaliser.normalise(w);
			//
			if(!containsLetter(w)&&!containsDigit(w)) continue;
			//String lemma = _normaliser.normalise(w.trim());
			//word should be treated separately to NP, as different forms of a word should be treated separately in counting
			if (w.length()>0) {
				Set<String> variants = result.get(w);
				variants=variants==null?new THashSet<String>():variants;
				variants.add(w);
				result.put(w,variants);
			}
/*			String lemma = _normaliser.normalise(w.trim());
			if (lemma.length()>0) {
				result.add(lemma);
			}*/
		}
		return result;
	}
}
