package uk.ac.shef.wit.textractor.util.control;

/**
 * <p>
 * Normaliser returns text units to its canonical forms
 * </p>
 *
 * @author <a href="mailto:z.zhang@dcs.shef.ac.uk">Ziqi Zhang</a>
 */
public abstract class Normaliser {

	/**
	 * Normalise only the RHS head word of the input text unit
	 *
	 * @param unit the variant form of a single text unit, e.g., word, phrase
	 * @return the normalised canonical form of input
	 */
	public abstract String normalise(String unit);

	/**
	 * Normalise every token found in the input content, assuming tokens are delimited by a whitespace character.
	 * @param content
	 * @return
	 */
	public abstract String normaliseContent(String content);
}
