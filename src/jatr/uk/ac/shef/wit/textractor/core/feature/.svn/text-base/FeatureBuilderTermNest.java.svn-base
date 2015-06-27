package uk.ac.shef.wit.textractor.core.feature;

import uk.ac.shef.wit.textractor.JATRException;
import org.apache.log4j.Logger;

/**
 * A specific type of feature builder that builds an instance of FeatureTermNest from a GlobalIndexStore.
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

public class FeatureBuilderTermNest extends AbstractFeatureBuilder {

	private static Logger _logger = Logger.getLogger(FeatureBuilderTermNest.class);

	/**
	 * Default constructor
	 */
	public FeatureBuilderTermNest() {
		super(null,null,null);
	}

	/**
	 * Build an instance of FeatureTermNest from an instance of GlobalResourceIndex
	 * @param index
	 * @return
	 * @throws JATRException
	 */
	public FeatureTermNest build(GlobalResourceIndex index) throws JATRException {
		FeatureTermNest _feature=new FeatureTermNest(index);
		if (index.getTermsCanonical().size() == 0 || index.getDocuments().size() == 0) throw new
				JATRException("No resource indexed!");

		_logger.info("About to build FeatureTermNest...");
		int counter = 0;
		for (String np : index.getTermsCanonical()) {
			for (String anp : index.getTermsCanonical()) {
				if (anp.length() <= np.length()) continue;
				if (anp.indexOf(" " + np) != -1 || anp.indexOf(np + " ") != -1) //e.g., np=male, anp=the male
					_feature.termNestIn(np, anp);
			}
			counter++;
			if(counter%500==0) _logger.info("Batch done"+counter+" end: "+np);
		}
		return _feature;				
	}
}
