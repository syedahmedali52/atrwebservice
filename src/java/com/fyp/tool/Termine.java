/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fyp.tool;

import com.fyp.model.MyDocumentImpl;
import java.io.IOException;
import java.util.List;
import uk.ac.shef.wit.textractor.JATRException;
import uk.ac.shef.wit.textractor.core.algorithm.CValueAlgorithm;
import uk.ac.shef.wit.textractor.core.algorithm.CValueFeatureWrapper;
import uk.ac.shef.wit.textractor.core.feature.FeatureBuilderCorpusTermFrequency;
import uk.ac.shef.wit.textractor.core.feature.FeatureBuilderTermNest;
import uk.ac.shef.wit.textractor.core.feature.FeatureCorpusTermFrequency;
import uk.ac.shef.wit.textractor.core.feature.FeatureTermNest;
import uk.ac.shef.wit.textractor.core.feature.GlobalResourceIndex;
import uk.ac.shef.wit.textractor.core.feature.GlobalResourceIndexBuilder;
import uk.ac.shef.wit.textractor.core.npextractor.CandidateTermExtractor;
import uk.ac.shef.wit.textractor.core.npextractor.NounPhraseExtractorOpenNLP;
import uk.ac.shef.wit.textractor.debug.AlgorithmTester;
import uk.ac.shef.wit.textractor.model.CorpusImpl;
import uk.ac.shef.wit.textractor.model.Term;
import uk.ac.shef.wit.textractor.util.control.Lemmatiser;
import uk.ac.shef.wit.textractor.util.control.StopList;
import uk.ac.shef.wit.textractor.util.counter.TermFreqCounter;
import uk.ac.shef.wit.textractor.util.counter.WordCounter;
/**
 *
 * @author AHMED ALI
 */
public class Termine {
  
    public static List<Term> execute(String input) throws IOException, JATRException{
    //creates instances of required processors and resources

			//stop word list
			StopList stop = new StopList(true);

			//lemmatiser
			Lemmatiser lemmatizer = new Lemmatiser();

			//noun phrase extractor
			CandidateTermExtractor npextractor = new NounPhraseExtractorOpenNLP(stop, lemmatizer);

			//counters
			TermFreqCounter npcounter = new TermFreqCounter();
			WordCounter wordcounter = new WordCounter();

			//create global resource index builder, which indexes global resources, such as documents and terms and their
			//relations
			GlobalResourceIndexBuilder builder = new GlobalResourceIndexBuilder();
                        
                        CorpusImpl corpus=new CorpusImpl();
                        corpus.add(new MyDocumentImpl(input));
			//build the global resource index
			GlobalResourceIndex termDocIndex = builder.build(corpus, npextractor);

			//build a feature store required by the tfidf algorithm, using the processors instantiated above
			FeatureCorpusTermFrequency termCorpusFreq =
					new FeatureBuilderCorpusTermFrequency(npcounter, wordcounter, lemmatizer).build(termDocIndex);
			FeatureTermNest termNest =
					new FeatureBuilderTermNest().build(termDocIndex);

			AlgorithmTester tester = new AlgorithmTester();
			tester.registerAlgorithm(new CValueAlgorithm(), new CValueFeatureWrapper(termCorpusFreq,termNest));
			return tester.execute(termDocIndex);
                        
    
    }
}
