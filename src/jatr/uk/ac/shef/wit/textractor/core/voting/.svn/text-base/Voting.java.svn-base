package uk.ac.shef.wit.textractor.core.voting;

import uk.ac.shef.wit.textractor.model.Term;
import uk.ac.shef.wit.textractor.JATRException;

import java.util.*;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: zqz
 * Date: 02-Nov-2007
 * Time: 14:34:32
 * To change this template use File | Settings | File Templates.
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

public class Voting {

	/**
	 * Load standard jatr term recognition output into a list of ranked terms. The standard output has one term on a line,
	 * in the format of:
	 * <p>
	 * lemma |variantX |variantY        confidence
	 * </p>
	 *
	 * @param path
	 * @return
	 */
	public List<Term> load(String path) {
		List<Term> result = new ArrayList<Term>();
		try {
			final BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.startsWith("//")) continue;
				//String[] elements = line.split("\\\\|");
                                String[] elements = line.split("\\|");
				//if (elements.length < 2) continue;
				result.add(new Term(elements[0],0));
				//result.add(new Term(elements[0], Double.valueOf(elements[1])));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(result);

		return result;
	}

	/**
	 * Produce the voting result
	 *
	 * @param outputs
	 * @return
	 * @throws JATRException
	 */
	public List<Term> calculate(WeightedOutput... outputs) throws JATRException {
/*		for (WeightedOutput output : outputs) {
			List<Term> test = outputs[1].getOutputList();
			test.removeAll(output.getOutputList());
			if (test.size() != 0)
				throw new TEXTractorException("Data inconsistency! The results do not share identical concepts.");
		}*/

		List<Term> result = new ArrayList<Term>();
		int count=0;
		for (Term t : outputs[0].getOutputList()) {
			double score = 0.0;
			for (WeightedOutput output : outputs) {
				score += output.getWeight() * (output.getOutputList().size() - output.getOutputList().indexOf(t));
			}
			result.add(new Term(t.getConcept(), score));
			count++;
			if(count%1000==0)System.out.println(t.getConcept()+count);
		}

		Collections.sort(result);
		return result;
	}
        
        /**
	 * Output the result, one term on a line, in the format of
	 * <p>
	 * lemma       confidence
	 * </P>
	 * @param target
	 * @param filename
	 */
	public void output(List<Term> target, String filename) {
		Term[] result = target.toArray(new Term[0]);
		Arrays.sort(result);

		try {
			PrintWriter pw = new PrintWriter(new FileWriter(new File(filename)));
			for (Term c : result) {
				pw.println(c.getConcept() + "|" + c.getConfidence());
			}
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/*

	public static void main(String[] args) {
		try {
			Voting v = new Voting();


			List<Term> tfidf = v.load("X:\\Work_stuff\\project_remote\\JTEC_SDK\\JTEC\\TEXTractor_v2.1\\test\\animal/TfIdf_ATR_ALGORITHM.txt");
			List<Term> weridness =v.load("X:\\Work_stuff\\project_remote\\JTEC_SDK\\JTEC\\TEXTractor_v2.1\\test\\animal/Weirdness_ATR_ALGORITHM.txt");
			List<Term> glossex = v.load("X:\\Work_stuff\\project_remote\\JTEC_SDK\\JTEC\\TEXTractor_v2.1\\test\\animal/IBM_GlossEx_ATR_ALGORITHM.txt");
			List<Term> termex = v.load("X:\\Work_stuff\\project_remote\\JTEC_SDK\\JTEC\\TEXTractor_v2.1\\test\\animal/TermEx_ATR_ALGORITHM.txt");
			List<Term> cvalue = v.load("X:\\Work_stuff\\project_remote\\JTEC_SDK\\JTEC\\TEXTractor_v2.1\\test\\animal/CValue_ALGORITHM.txt");

			List<Term> output = v.calculate(new WeightedOutput(tfidf,0.2),new WeightedOutput(weridness,0.2),
					new WeightedOutput(glossex,0.2), new WeightedOutput(termex, 0.2), new WeightedOutput(cvalue,0.2));
			v.output(output, "voted.txt");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
