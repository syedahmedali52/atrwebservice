package uk.ac.shef.wit.textractor.core.feature;

import uk.ac.shef.wit.textractor.model.Document;

import java.util.*;

import gnu.trove.*;

/**
 * GlobalResourceIndex stores information of binary relations between candidate terms (word or phrase) and corpus. These
 * include:
 * <br>- candidate term canonical forms and their int ids
 * <br>- candidate term variant forms and their int ids
 * <br>- mapping from candidate term canonical form to variant forms
 * <br>- corpus elements (document) and their int ids
 * <br>- candidate term and their containing documents (id - ids)
 * <br>- document ids and their contained candidate terms (id - ids)
 * <p/>
 * In other words, a GlobalResourceIndex contains mappings from a candidate term to its id, mappings from a candidate
 * term's canonical form to its variants found in the corpus,  a document to its id,
 * a lexical unit id to the document ids that contain the unit, a document id to the lexical unit ids which the document
 * contains.
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

public class GlobalResourceIndex {
	protected int _termCounter = 0;
	protected int _variantCounter = 0;
	protected int _docCounter = 0;

	protected HashMap<String, Integer> _termIdMap = new HashMap<String, Integer>();
	protected HashMap<String, Integer> _variantIdMap = new HashMap<String, Integer>();
	protected HashMap<Document, Integer> _docIdMap = new HashMap<Document, Integer>();

	protected THashMap<Integer, TIntHashSet> _term2Docs = new THashMap<Integer, TIntHashSet>();
	protected THashMap<Integer, TIntHashSet> _doc2Terms = new THashMap<Integer, TIntHashSet>();
	protected THashMap<Integer, TIntHashSet> _term2Variants = new THashMap<Integer, TIntHashSet>();

	protected GlobalResourceIndex() {
	}

	/**
	 * Given a candidate term's canonical form, index it and return its id.
	 *
	 * @param term
	 * @return the id
	 */
	protected int indexTermCanonical(String term) {
		Integer index = _termIdMap.get(term);
		if (index == null) _termIdMap.put(term, index = _termCounter++);
		return index;
	}

	/**
	 * Given an id, retrieve the candidate term's canonical form
	 *
	 * @param id
	 * @return
	 */
	protected String retrieveTermCanonical(int id) {
		for (Map.Entry<String, Integer> en : _termIdMap.entrySet()) {
			if (en.getValue() == id) return en.getKey();
		}
		return null;
	}

	/**
	 * @return all indexed candidate term canonical form ids
	 */
	public Set<Integer> getTermCanonicalIds() {
		return new HashSet<Integer>(_termIdMap.values());
	}

	/**
	 * @return all candidate term canonical forms
	 */
	public Set<String> getTermsCanonical() {
		return _termIdMap.keySet();
	}

	/**
	 * Given a candidate term variant, index it and return its id.
	 *
	 * @param termV
	 * @return the id
	 */
	protected int indexTermVariant(String termV) {
		Integer index = _variantIdMap.get(termV);
		if (index == null) _variantIdMap.put(termV, index = _variantCounter++);
		return index;
	}

	/**
	 * Given an id of a candidate term variant, retrieve the text
	 *
	 * @param id
	 * @return
	 */
	protected String retrieveTermVariant(int id) {
		for (Map.Entry<String, Integer> en : _variantIdMap.entrySet()) {
			if (en.getValue() == id) return en.getKey();
		}
		return null;
	}

	public Set<Integer> getTermVariantIds() {
		return new HashSet<Integer>(_variantIdMap.values());
	}

	public Set<String> getTermVariants() {
		return _variantIdMap.keySet();
	}

	/**
	 * Given a document, index it and return its id.
	 *
	 * @param d
	 * @return the id
	 */
	protected int indexDocument(Document d) {
		Integer index = _docIdMap.get(d);
		if (index == null) _docIdMap.put(d, index = _docCounter++);
		return index;
	}

	/**
	 * Given a document id return the document
	 *
	 * @param id
	 * @return
	 */
	protected Document retrieveDocument(int id) {
		for (Map.Entry<Document, Integer> en : _docIdMap.entrySet()) {
			if (en.getValue() == id) return en.getKey();
		}
		return null;
	}

	/**
	 * @return all indexed documents
	 */
	public Set<Document> getDocuments() {
		return _docIdMap.keySet();
	}

	/**
	 * @return return all indexed document ids
	 */
	public Set<Integer> getDocumentIds() {
		return new HashSet<Integer>(_docIdMap.values());
	}

	/*
	term - - variant
	 */
	/**
	 * Given a map containing [term canonical form - term variant forms], index the mapping
	 * @param map
	 */
	protected void indexTermWithVariant(Map<String, Set<String>> map) {
		for (Map.Entry<String, Set<String>> e : map.entrySet()) {
			TIntHashSet variants = _term2Variants.get(indexTermCanonical(e.getKey()));
			if (variants == null) variants = new TIntHashSet();
			for(String v:e.getValue())
				variants.add(indexTermVariant(v));
			_term2Variants.put(indexTermCanonical(e.getKey()), variants);
		}
	}

	/**
	 * Given a term canonical form, retrieve its variant forms found in the corpus
	 * @param term
	 * @return
	 */
	public Set<String> retrieveVariantsOfTermCanonical(String term) {
		Set<String> variants = new HashSet<String>();
	   TIntHashSet vIds = _term2Variants.get(indexTermCanonical(term));
		if(vIds == null) return variants;

		TIntIterator it = vIds.iterator();
		while(it.hasNext())
			variants.add(retrieveTermVariant(it.next()));
		return variants;
	}

	
	/*
		term - - docs
		 */

	/**
	 * Given a candidate term's canonical form t found in document d, index the binary relation "t found_in d"
	 *
	 * @param t
	 * @param d
	 */
	protected void indexTermCanonicalInDoc(String t, Document d) {
		indexTermCanonicalInDoc(indexTermCanonical(t), indexDocument(d));
	}

	/**
	 * Given a candidate term's canonical form id t found in document with id d, index the binary relation "t found_in d"
	 *
	 * @param t
	 * @param d
	 */
	protected void indexTermCanonicalInDoc(int t, int d) {
		TIntHashSet docs = _term2Docs.get(t);
		if (docs == null) docs = new TIntHashSet();
		docs.add(d);
		_term2Docs.put(t, docs);
	}

	/**
	 * @param t the candidate term's canonical form in question
	 * @return the document ids of which documents contain the candidate term t
	 */
	public TIntHashSet retrieveDocIdsContainingTermCanonical(String t) {
		return retrieveDocIdsContainingTermCanonical(indexTermCanonical(t));
	}

	/**
	 * @param id the candidate term's canonical form in questoin
	 * @return the document ids of which documents contain the candidate term t
	 */
	public TIntHashSet retrieveDocIdsContainingTermCanonical(int id) {
		TIntHashSet res = _term2Docs.get(id);
		return res == null ? new TIntHashSet() : res;
	}

	/**
	 * @param t the candidate term's canonical form in question
	 * @return the documents which contain the candidate term t
	 */
	public THashSet<Document> retrieveDocsContainingTermCanonical(String t) {
		return retrieveDocsContainingTermCanonical(indexTermCanonical(t));
	}

	/**
	 * @param t the candidate term's canonical form id in question
	 * @return the documents which contain the candidate term t
	 */
	public THashSet<Document> retrieveDocsContainingTermCanonical(int t) {
		THashSet<Document> docs = new THashSet<Document>();
		TIntHashSet docids = retrieveDocIdsContainingTermCanonical(t);
		TIntIterator it = docids.iterator();
		while (it.hasNext()) docs.add(retrieveDocument(it.next()));
		return docs;
	}

	/**
	 * @param t the candidate term's canonical form
	 * @return number of documents that contain the candidate term (any variants)
	 */
	public int sizeTermInDocs(String t) {
		return retrieveDocIdsContainingTermCanonical(t).size();
	}

	/**
	 * @param t the id of candidate term's canonical form
	 * @return number of documents that contain the candidate term (any variants)
	 */
	public int sizeTermInDocs(int t) {
		return retrieveDocIdsContainingTermCanonical(t).size();
	}

	/*
		doc - - terms
		 */

	/**
	 * Given a document d which contains a set of terms (canonical form), index the binary relation "document contains term canonical"
	 *
	 * @param d
	 * @param terms canonical forms of candidate terms found in document d
	 */
	protected void indexDocWithTermsCanonical(Document d, Set<String> terms) {
		TIntHashSet termids = new TIntHashSet();
		for (String t : terms) termids.add(indexTermCanonical(t));
		indexDocWithTermsCanonical(indexDocument(d), termids);
	}

	/**
	 * Given a document with id d which contains a set of terms (canonical form), index the binary relation "document contains term canonical"
	 *
	 * @param d id of document
	 * @param terms canonical forms of candidate terms found in document d
	 */
	protected void indexDocWithTermsCanonical(int d, TIntHashSet terms) {
		TIntHashSet termids = _doc2Terms.get(d);
		if (termids != null) termids.addAll(terms.toArray());
		_doc2Terms.put(d, terms);
	}

	/**
	 * @param d
	 * @return the ids of canonical forms of terms found in the document d
	 */
	public TIntHashSet retrieveTermCanonicalIdsInDoc(Document d) {
		return retrieveTermCanonicalIdsInDoc(indexDocument(d));
	}

	/**
	 * @param d
	 * @return the ids of canonical forms of terms found in the document d
	 */
	public TIntHashSet retrieveTermCanonicalIdsInDoc(int d) {
		TIntHashSet res = _doc2Terms.get(d);
		return res == null ? new TIntHashSet() : res;
	}

	/**
	 * @param d
	 * @return the canonical form of terms found in the document d
	 */
	public THashSet<String> retrieveTermsCanonicalInDoc(Document d) {
		return retrieveTermCanonicalInDoc(indexDocument(d));
	}

	/**
	 * @param d
	 * @return the canonical form of terms found in the document d
	 */
	public THashSet<String> retrieveTermCanonicalInDoc(int d) {
		THashSet<String> res = new THashSet<String>();
		TIntHashSet termids = retrieveTermCanonicalIdsInDoc(d);
		TIntIterator it = termids.iterator();
		while (it.hasNext()) {
			res.add(retrieveTermCanonical(it.next()));
		}
		return res;
	}

	/**
	 * @param d
	 * @return number of unique candidate terms (canonical form) found in document d
	 */
	public int sizeDocHasTerms(Document d) {
		return retrieveTermsCanonicalInDoc(d).size();
	}

	/**
	 * @param d
	 * @return number of unique candidate terms (canonical form) found in document d
	 */
	public int sizeDocHasTerms(int d) {
		return retrieveTermCanonicalInDoc(d).size();
	}


}
