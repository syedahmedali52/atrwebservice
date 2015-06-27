package uk.ac.shef.wit.textractor.core.feature;

import uk.ac.shef.wit.textractor.JATRException;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * A specific type of feature builder that builds an instance of FeatureRefCorpusTermFrequency. This is a dummy class
 * which reads the data from a text file which stores information as:
 * <br> [freq_in_corpus]      [term]
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

public class FeatureBuilderRefCorpusTermFrequency extends AbstractFeatureBuilder {

	private static Logger _logger = Logger.getLogger(FeatureBuilderRefCorpusTermFrequency.class);

	private final String _refStatsPath;

	/**
	 * Default constructor
	 * @param refStatsPath file path to the reference corpus statistics file. The file should store one term on a line, and in the format of:
	 * <br>[freq_in_ref_corpus]   [term]
	 * <br> Any terms with frequency < 2 will be ignored.
	 */
	public FeatureBuilderRefCorpusTermFrequency(String refStatsPath) {
		super(null, null, null);
		_refStatsPath=refStatsPath;
	}


	/**
	 * Dummy method which does nothing with the GlobalResourceIndex instance but load statistics and creates
	 * and instance of FeatureRefCorpusTermFrequency from the file
	 * specified in the constructor
	 * @param nullValue can be either an instance or null value
	 * @return
	 * @throws JATRException
	 */
	public FeatureRefCorpusTermFrequency build(GlobalResourceIndex nullValue) throws JATRException {
		FeatureRefCorpusTermFrequency _feature = new FeatureRefCorpusTermFrequency();

		try{
			final BufferedReader reader = new BufferedReader(new FileReader(_refStatsPath));
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				String[] elements = line.split("\\s+");
                                
                                //System.err.println("RefCorpusTermFreq " + line);
                                
                                
				if(Integer.valueOf(elements[0])<2) continue;
                                
                                //System.err.println("RefCorpusTermFreq " + elements[1].trim() + " " + Integer.valueOf(elements[0]));
                                
				_feature.addToTermFreq(elements[1].trim(), Integer.valueOf(elements[0]));
			}

		}
		catch(Exception e){e.printStackTrace();}

		return _feature;
	}
}
