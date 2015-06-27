package uk.ac.shef.wit.textractor.core.feature;

import gnu.trove.THashMap;
import gnu.trove.TIntHashSet;

/**
 * A feature store that contains information of nested terms. E.g., "hedgehog" is a
 * nested term in "European hedgehog". See details in K. Frantz et al 2000. This contains following information:
 * 
 * <br>- total number of occurrences of all terms found in the corpus, which is the sum of occrrences of each term
 * <br>- number of occurrences of each term found in the corpus
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

public class FeatureTermNest extends AbstractFeature {

	private THashMap<Integer, TIntHashSet> _termNested = new THashMap<Integer, TIntHashSet>();

   protected FeatureTermNest(GlobalResourceIndex index){
      _index=index;
   }

	/**
	 * @param nested
	 * @return the ids of terms in which the provided term is nested
	 */
	public TIntHashSet getNestIdsOf(String nested){
		return getNestIdsOf(_index.indexTermCanonical(nested));
	}

	/**
	 * @param nested
	 * @return the ids of terms in which the provided term with id "nested" is nested
	 */
	public TIntHashSet getNestIdsOf(int nested){
		TIntHashSet res = _termNested.get(nested);
		return res==null? new TIntHashSet():res;
	}

	/**
	 * Index a term "nested" as nested in a term "nest"
	 * @param nested
	 * @param nest
	 */
	public void termNestIn(String nested, String nest){
		termNestIn(_index.indexTermCanonical(nested),_index.indexTermCanonical(nest));
	}

	/**
	 * Index a term with id "nested" as nested in a term with id "nest"
	 * @param nested
	 * @param nest
	 */
	public void termNestIn(int nested, int nest){
		TIntHashSet res = _termNested.get(nested);
		res=res==null?new TIntHashSet():res;
		res.add(nest);
		_termNested.put(nested,res);
	}

	
}
