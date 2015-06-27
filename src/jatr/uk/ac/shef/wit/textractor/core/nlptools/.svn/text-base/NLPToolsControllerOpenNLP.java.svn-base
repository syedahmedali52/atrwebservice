package uk.ac.shef.wit.textractor.core.nlptools;

import uk.ac.shef.wit.text.partsofspeech.PosTagger;
import uk.ac.shef.wit.text.partsofspeech.PosTaggerOpenNLP;
import uk.ac.shef.wit.text.phrasechunker.PhraseChunker;
import uk.ac.shef.wit.text.phrasechunker.PhraseChunkerOpenNLP;
import uk.ac.shef.wit.text.sentencesplitter.SentenceSplitter;
import uk.ac.shef.wit.text.sentencesplitter.SentenceSplitterOpenNLP;
import uk.ac.shef.wit.text.tokeniser.Tokeniser;
import uk.ac.shef.wit.text.tokeniser.TokeniserOpenNLP;
import uk.ac.shef.wit.text.WitTextException;
import uk.ac.shef.wit.textractor.JATRProperties;

/**
 * A singleton class which controls creation and dispatches of OpenNLP tools
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

public class NLPToolsControllerOpenNLP {

	private static NLPToolsControllerOpenNLP _ref;
	private PosTaggerOpenNLP _posTagger;
	private PhraseChunker _npChunker;
	private SentenceSplitter _sentDetect;
	private Tokeniser _tokenizer;

	private NLPToolsControllerOpenNLP() throws WitTextException {
		_posTagger = new PosTaggerOpenNLP(JATRProperties.getInstance().getNLPPath()+"/EnglishPOS.bin.gz",
				JATRProperties.getInstance().getNLPPath()+"/EnglishPOSDict.bin.gz",
				JATRProperties.getInstance().getNLPPath()+"/EnglishPOSTagdict.bin.gz");
		_npChunker = new PhraseChunkerOpenNLP(JATRProperties.getInstance().getNLPPath()+"/EnglishChunk.bin.gz");
		_sentDetect = new SentenceSplitterOpenNLP(JATRProperties.getInstance().getNLPPath()+"/EnglishSD.bin.gz");
		_tokenizer = new TokeniserOpenNLP(JATRProperties.getInstance().getNLPPath()+"/EnglishTok.bin.gz");
	}

	public static NLPToolsControllerOpenNLP getInstance() throws WitTextException {
		if(_ref ==null) _ref=new NLPToolsControllerOpenNLP();
		return _ref;
	}

	public Object clone() throws CloneNotSupportedException {
      throw new CloneNotSupportedException();
   }

	public PosTagger getPosTagger() {
		return _posTagger;
	}

	public PhraseChunker getPhraseChunker() {
		return _npChunker;
	}

	public SentenceSplitter getSentenceSplitter() {
		return _sentDetect;
	}

	public Tokeniser getTokeniser() {
		return _tokenizer;
	}
}
