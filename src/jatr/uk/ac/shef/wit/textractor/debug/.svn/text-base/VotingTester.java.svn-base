
package uk.ac.shef.wit.textractor.debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import uk.ac.shef.wit.textractor.JATRException;
import uk.ac.shef.wit.textractor.core.algorithm.AbstractFeatureWrapper;
import uk.ac.shef.wit.textractor.core.algorithm.Algorithm;
import uk.ac.shef.wit.textractor.core.voting.Voting;
import uk.ac.shef.wit.textractor.core.voting.WeightedOutput;
import uk.ac.shef.wit.textractor.model.Term;

/**
 *
 * @author ischmidt
 */
public class VotingTester {
    
    public void voting(String filename, ArrayList<String> algorithms) throws JATRException, IOException {
        Voting vo = new Voting();
        WeightedOutput[] wlist = new WeightedOutput[algorithms.size()];        
        int i = 0;        
        for (String alg : algorithms) {
            
            List<Term> termList = vo.load(alg);            
            wlist[i] = new WeightedOutput(termList, 1.0);
            i++;
        }

        List<Term> votedTerms = vo.calculate(wlist);
        vo.output(votedTerms, filename);
    }

    public static void main(String[] args) {
        try {
            AlgorithmTester tester = new AlgorithmTester();
            
            ArrayList<String> al = new ArrayList<String>();
            
            String output = null;
            if (args.length == 0) {
                output = "Voting.txt";
                al.add("Weirdness_ATR_ALGORITHM.txt");
                al.add("TfIdf_ATR_ALGORITHM.txt");
            }
            else {
                output = args[0];
                for (int i = 1; i < args.length; ++i) {
                    al.add(args[i]);
                }
            }
               
            tester.voting(args[0], al);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
