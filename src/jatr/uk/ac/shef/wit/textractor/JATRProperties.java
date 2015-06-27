package uk.ac.shef.wit.textractor;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Author: Ziqi Zhang
 * Organisation: Department of Computer Science, University of Sheffield
 * Email: z.zhang@dcs.shef.ac.uk
 * Date: 25-Apr-2007
 * Time: 14:21:27
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

public class JATRProperties {
	private Properties _properties = new Properties();
   private static JATRProperties _ref=null;

	public static final String NP_FILTER_PATTERN="[^a-zA-Z0-9\\-]";

	public static final String NLP_PATH ="jatr.system.nlp";
	public static final String TERM_CANONICAL_2_VARIANTS_MAPING ="jatr.system.term.variant_mapping";
	public static final String TERM_MAX_WORDS = "jatr.system.term.maxwords";
	public static final String TERM_IGNORE_DIGITS = "jatr.system.term.ignore_digits";
        public static final String REFCORPUS_COUNTS_PATH = "jatr.system.refcorpus";
   private JATRProperties() {
      read();
   }

   public static JATRProperties getInstance(){
       if(_ref==null){
           _ref=new JATRProperties();
       }
       return _ref;
   }

   private void read() {
        InputStream in=null;
        try {
            /*InputStream x= getClass().getResourceAsStream("/indexing.properties");*/
            in = getClass().getResourceAsStream("/jatr.properties");
            _properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if(in!=null) try {
                in.close();
                in=null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

   private String getProperty (String key) {
      return _properties.getProperty(key);
   }

	public String getNLPPath(){
		return getProperty(NLP_PATH);
	}

	public int getMaxMultipleWords(){
		try{
			return Integer.valueOf(getProperty(TERM_MAX_WORDS));
		}catch(NumberFormatException e){
			return 5;
		}
	}

	public boolean isIgnoringDigits(){
		try{
			return Boolean.valueOf(getProperty(TERM_IGNORE_DIGITS));
		}catch(Exception e){
			return true;
		}
	}

	public String getTermVariantsMappingPath(){
		return getProperty(TERM_CANONICAL_2_VARIANTS_MAPING);
	}
        
        public String getRefCorpusPath() {
            return getProperty(REFCORPUS_COUNTS_PATH);
        }
}
