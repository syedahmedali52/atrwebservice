package uk.ac.shef.wit.textractor.io;

import uk.ac.shef.wit.textractor.JATRException;
import uk.ac.shef.wit.textractor.core.feature.GlobalResourceIndex;
import uk.ac.shef.wit.textractor.model.Term;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 * A writer class which outputs term recognition results
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

public class ResultWriter2File {

	private GlobalResourceIndex _index;

	/**
	 * @param index an instance of GlobalResourceIndex. The writer will read mapping data from term canonical from to variant
	 * forms and output the result
	 */
	public ResultWriter2File(GlobalResourceIndex index) {
		this._index = index;
	}

	/**
	 * Output the result. This writes one term on a line in the format of:
	 * <p>
	 * canonical |variant1 |variant2 |variantX       confidence
	 * </p>
	 * @param result term recognition result sorted descendingly by confidence
	 * @param path output file path
	 * @throws JATRException
	 */
	public void output(Term[] result, String path) throws JATRException {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(path));
			if (_index == null) {
				for (Term c : result) {
					pw.println(c.getConcept() + "\t\t\t" + c.getConfidence());
				}
			}
			else{
				for (Term c : result) {
					Set<String> originals = _index.retrieveVariantsOfTermCanonical(c.getConcept());
					if(originals==null)
						pw.println(c.getConcept() + "\t\t\t" + c.getConfidence());
					else
						pw.println(c.getConcept()+" |"+writeToString(originals) + "\t\t\t" + c.getConfidence());
				}
			}
			pw.close();
		}
		catch (IOException ioe) {
			throw new JATRException(ioe);
		}
	}

	private String writeToString(Set<String> container){
		StringBuilder sb = new StringBuilder();
		for(String s:container){
			sb.append(s).append(" |");
		}
		return sb.toString().substring(0,sb.lastIndexOf("|"));
	}
}
