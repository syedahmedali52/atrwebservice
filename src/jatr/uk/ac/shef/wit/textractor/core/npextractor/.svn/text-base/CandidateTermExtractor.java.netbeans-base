package uk.ac.shef.wit.textractor.core.npextractor;

import uk.ac.shef.wit.textractor.JATRException;
import uk.ac.shef.wit.textractor.model.Corpus;
import uk.ac.shef.wit.textractor.model.Document;
import uk.ac.shef.wit.textractor.util.control.Normaliser;
import uk.ac.shef.wit.textractor.util.control.StopList;

import java.util.Map;
import java.util.Set;

/**
 * Extract lexical units from texts.
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

public abstract class CandidateTermExtractor {
	protected StopList _stoplist;
	protected Normaliser _normaliser;

	/**
	 * @param c corpus
	 * @return a map containing mappings from term canonical form to its variants found in the corpus
	 * @throws JATRException
	 */
	public abstract Map<String, Set<String>> extract(Corpus c) throws JATRException;

	/**
	 * @param d document
	 * @return a map containing mappings from term canonical form to its variants found in the document
	 * @throws JATRException
	 */
	public abstract Map<String, Set<String>> extract(Document d) throws JATRException;

	/**
	 * @param content a string
	 * @return a map containing mappings from term canonical form to its variants found in the string
	 * @throws JATRException
	 */
	public abstract Map<String, Set<String>> extract(String content) throws JATRException;

	protected boolean containsLetter(String np) {
		char[] chars = np.toCharArray();
		for (char c : chars) {
			if (Character.isLetter(c)) return true;
		}
		return false;
	}

	protected boolean containsDigit(String word) {
		for (char c : word.toCharArray()) {
			if (Character.isDigit(c)) return true;
		}
		return false;
	}
}
