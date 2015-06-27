package uk.ac.shef.wit.textractor.util.counter;

import uk.ac.shef.wit.textractor.model.CorpusImpl;
import uk.ac.shef.wit.textractor.model.DocumentImpl;
import uk.ac.shef.wit.textractor.model.Document;

import java.util.StringTokenizer;
import java.io.File;
import java.net.MalformedURLException;

/**
 * Count number of words.
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

public class WordCounter {

	public WordCounter(){
	}

	/**
	 * Count number of words in a corpus, delimited by a space character
	 * @param c
	 * @return
	 */
	public int countWords(final CorpusImpl c){
		int total=0;
		for(Document doc:c){
			StringTokenizer tokenizer = new StringTokenizer(doc.getContent().replaceAll("\\s+"," ")
					.replaceAll("\\s+"," ")," ");
			total+=tokenizer.countTokens();
		}
		return total;
	}

	/**
	 * Count number of words in a document, delimited by a space character
	 * @param d
	 * @return
	 */
	public int countWords(final Document d){
		StringTokenizer tokenizer = new StringTokenizer(d.getContent().replaceAll("\\s+"," ")
					.replaceAll("\\s+"," ")," ");
		return tokenizer.countTokens();			
	}

	/**
	 * Main testing method.
	 * @param args args[0] is the path to directory containing files
	 */
	public static void main(String[] args) {
		File targetFolder = new File(args[0]);
      File[] files = targetFolder.listFiles();
		CorpusImpl c =new CorpusImpl();
      for (File f : files) {
	      try {
		      c.add(new DocumentImpl(f.toURI().toURL()));
	      } catch (MalformedURLException e) {
		      e.printStackTrace();
	      }
      }//for

		System.out.println(new WordCounter().countWords(c));
	}
}
