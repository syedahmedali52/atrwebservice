package uk.ac.shef.wit.textractor.util.counter;

import gnu.trove.TIntHashSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Count frequencies of noun phrases in corpus
 *
 * @author <a href="mailto:z.zhang@dcs.shef.ac.uk">Ziqi Zhang</a>
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

public class TermFreqCounter {

	/**
	 * Count number of occurrences of a string in a context.
	 * @param noun the string to be counted
	 * @param context the text in which the string can be found
	 * @return number of frequencies
	 */
   public int count(String noun, String context) {
      int next;
      int start = 0;
      int freq = 0;
      while (start <= context.length()) {
         next = context.indexOf(noun,start);
         char prefix = next - 1 < 0 ? ' ' : context.charAt(next - 1);
         char suffix = next + noun.length() >= context.length() ? ' ' : context.charAt(next + noun.length());
         if (next != -1 && isValidChar(prefix) && isValidChar(suffix)) {
            freq++;
         }

         if (next == -1||next==context.length()) break;
         start = next + noun.length();
      }
      return freq;
   }

	/**
	 * Count number of occurrences of a string in a context.
	 * @param noun the string to be counted
	 * @param context the text in which the string can be found
	 * @return int array which contains offsets of occurrences in the text.
	 */
   public int[] countOffsets(String noun, String context){
      TIntHashSet offsets = new TIntHashSet();
      int next;
      int start = 0;
      while (start <= context.length()) {
         next = context.indexOf(noun,start);
         char prefix = next - 1 < 0 ? ' ' : context.charAt(next - 1);
         char suffix = next + noun.length() >= context.length() ? ' ' : context.charAt(next + noun.length());
         if (next != -1 && isValidChar(prefix) && isValidChar(suffix)) {
            offsets.add(next);
         }
         if (next == -1) break;
         start = next + noun.length();
      }

      return offsets.toArray();
   }

	/**
	 * Check if a character is a valid separater of a word. A valid separater is non digit and non letter.
	 * @param c
	 * @return
	 */
   private boolean isValidChar(char c) {
      return !Character.isLetter(c) && !Character.isDigit(c);
   }
}
