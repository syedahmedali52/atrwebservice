package uk.ac.shef.wit.textractor.model;

/**
 * Represents a term.
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

public class Term implements Comparable<Term> {

	private String _singular;
	private double _confidence;

	/**
	 * Creates a term
	 * @param lemma the lemmatised text of the term
	 * @param confidence the relevance of the term to the corpus from which it is extracted
	 */
	public Term(java.lang.String lemma, double confidence) {
		_singular = lemma;
		_confidence = confidence;
	}

	public java.lang.String getConcept() {
		return _singular;
	}

	public double getConfidence() {
		return _confidence;
	}

	public void setConcept(java.lang.String singular){
		_singular = singular;
	}
	public void setConfidence(double c){
		_confidence=c;
	}


	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Term that = (Term) o;

		return that.getConcept().equals(getConcept()) || that.getConcept().equals(getConcept());

	}

	public int hashCode(){
		return getConcept().hashCode();
	}

	public int compareTo(final Term c) {
      return getConfidence() > c.getConfidence() ? -1 : getConfidence() < c.getConfidence() ? 1 : 0;
   }
}
