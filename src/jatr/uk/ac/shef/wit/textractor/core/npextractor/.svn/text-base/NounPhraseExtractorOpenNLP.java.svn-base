package uk.ac.shef.wit.textractor.core.npextractor;

import gnu.trove.THashMap;
import gnu.trove.THashSet;
import org.apache.log4j.Logger;
import uk.ac.shef.wit.text.WitTextException;
import uk.ac.shef.wit.textractor.JATRException;
import uk.ac.shef.wit.textractor.JATRProperties;
import uk.ac.shef.wit.textractor.core.nlptools.NLPToolsControllerOpenNLP;
import uk.ac.shef.wit.textractor.model.Corpus;
import uk.ac.shef.wit.textractor.model.Document;
import uk.ac.shef.wit.textractor.util.control.Normaliser;
import uk.ac.shef.wit.textractor.util.control.StopList;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Nounphrase extractor implemented with OpenNLP tools. It applies certain heuristics to clean a candidate noun phrase and
 * return it to the normalised root. These heuristics include:
 * <br>-Stopwords will be trimmed from the head and tails of a phrase. E.g, "the cat on the mat" becomes "cat on the mat".
 * <br>-phrases containing "or" "and" will be split, e.g., "Tom and Jerry" becomes "tom" "jerry"
 * <br>-must have letters
 * <br>-must have at least two characters
 * <br>-characters that are not letter, or digit, or - are replaced with whitespaces.
 * <br>-may or may not have digits, this is set by the property file
 * <br>-must contain no more than N words, this is set by the property file
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

public class NounPhraseExtractorOpenNLP extends CandidateTermExtractor {

	private static Logger _logger = Logger.getLogger(NounPhraseExtractorOpenNLP.class);

	/**
	 * Creates an instance with specified stopwords list and norm
	 *
	 * @param stop
	 * @param normaliser
	 * @throws IOException
	 */
	public NounPhraseExtractorOpenNLP(StopList stop, Normaliser normaliser) throws IOException {
		_stoplist = stop;
		_normaliser = normaliser;
	}

	public Map<String, Set<String>> extract(Corpus c) throws JATRException {
		Map<String, Set<String>> res = new THashMap<String, Set<String>>();
		for (Document d : c) {
			_logger.info("Extracting candidate NP... From Document " + d);
			for (Map.Entry<String, Set<String>> e : extract(d).entrySet()) {
				Set<String> variants = res.get(e.getKey());
				variants = variants == null ? new THashSet<String>() : variants;
				variants.addAll(e.getValue());
				res.put(e.getKey(), variants);
			}
		}
		return res;
	}

	public Map<String, Set<String>> extract(Document d) throws JATRException {
		Map<String, Set<String>> res = new THashMap<String, Set<String>>();
		try {
			for (String s : NLPToolsControllerOpenNLP.getInstance().getSentenceSplitter().sentDetect(d.getContent())) {
				for (Map.Entry<String, Set<String>> e : extract(s).entrySet()) {
					Set<String> variants = res.get(e.getKey());
					variants = variants == null ? new THashSet<String>() : variants;
					variants.addAll(e.getValue());
					res.put(e.getKey(), variants);
				}
			}
		} catch (WitTextException e) {
			throw new JATRException(e);
		}
		return res;
	}


	public Map<String, Set<String>> extract(String content) throws JATRException {
		Map<String, Set<String>> nouns = new THashMap<String, Set<String>>();
		try {
			String[] tokens = NLPToolsControllerOpenNLP.getInstance().getTokeniser().tokeniseAsString(content);
			String[] pos = NLPToolsControllerOpenNLP.getInstance().getPosTagger().tag(tokens);
			String[] candidates = NLPToolsControllerOpenNLP.getInstance().getPhraseChunker().chunk(tokens, pos);
			for (String c : candidates) {
				c = applyNPCharRemoval(c);
				String[] e = applyNPSplitList(c);

				for (String str : e) {
					/*String original=str;
					str = _normaliser.normalise(str).trim();*/
					String stopremoved = applyNPTrimStopwords(str, _stoplist);
					if (stopremoved == null) continue;
					String original = stopremoved;
					str = _normaliser.normalise(stopremoved.toLowerCase()).trim();

					String[] nelements = str.split("\\s+");
					if (nelements.length < 1 || nelements.length >
							Integer.valueOf(JATRProperties.getInstance().getMaxMultipleWords()))
						continue;
					if (JATRProperties.getInstance().isIgnoringDigits() &&
							containsDigit(str))
						continue;
					if (!containsLetter(str)) continue;
					if (!hasReasonableNumChars(str)) continue;

					if (c.toLowerCase().indexOf(str) != -1) {
						Set<String> variants = nouns.get(str);
						variants = variants == null ? new THashSet<String>() : variants;
						variants.add(original);
						nouns.put(str, variants);
					}
				}
			}
		}

		catch (WitTextException wte) {
			throw new JATRException(wte);
		}
		return nouns;
	}

	private String applyNPCharRemoval(String in) {
		return in.replaceAll(JATRProperties.NP_FILTER_PATTERN, " ").replaceAll("\\s+", " ").trim();
	}

	private String[] applyNPSplitList(String in) {
		StringBuilder sb = new StringBuilder();
		if (in.indexOf(" and ") != -1) {
			String[] parts = in.split("\\band\\b");
			for (String s : parts) sb.append(s.trim() + "|");
		}
		if (in.indexOf(" or ") != -1) {
			String[] parts = in.split("\\bor\\b");
			for (String s : parts) sb.append(s.trim() + "|");
		}
		if (in.indexOf(",") != -1) {
			if (!containsDigit(in)) {
				String[] parts = in.split("\\,");
				for (String s : parts) sb.append(s.trim() + "|");
			}
		} else {
			sb.append(in);
		}
		String v = sb.toString();
		if (v.endsWith("|")) v = v.substring(0, v.lastIndexOf("|"));
		return v.toString().split("\\|");
	}

	private String applyNPTrimStopwords(String in, StopList stop) {
		//check the entire string first (e.g., "e. g. " and "i. e. " which will fail the following checks
		if (stop.isStopWord(_normaliser.normalise(in).replaceAll("\\s+", "").trim())) return null;

		String[] e = in.split("\\s+");
		if (e == null || e.length < 1) return in;

		int head = e.length;
		int end = -1;
		for (int i = 0; i < e.length; i++) {
			if (!stop.isStopWord(e[i])) {
				head = i;
				break;
			}
		}

		for (int i = e.length - 1; i > -1; i--) {
			if (!stop.isStopWord(e[i])) {
				end = i;
				break;
			}
		}

		if (head <= end) {
			String trimmed = "";
			for (int i = head; i <= end; i++) {
				trimmed += e[i] + " ";
			}
			return trimmed.trim();
		}
		return null;
	}

	private boolean hasReasonableNumChars(String s) {
		int len = s.length();
		if (len < 2) return false;
		if (len < 5) {
			char[] chars = s.toCharArray();
			int num = 0;
			for (int i = 0; i < chars.length; i++) {
				if (Character.isLetter(chars[i]) || Character.isDigit(chars[i]))
					num++;
				if (num > 2) break;
			}
			if (num > 2) return true;
			return false;
		}
		return true;
	}


}