package org.terrier.matching.models;


/**
 * This class implements the ATIRE BM25 weighting model. The
 * default parameters used are:<br>
 * k_1 = 0.9d<br>
 * b = 0.4d<br> The b parameter can be altered by using the setParameter method.
 * @author Gianni Amati, Ben He, Vassilis Plachouras
  */
public class BM25 extends WeightingModel {
	private static final long serialVersionUID = 1L;

	/** The constant k_1.*/
	private double k_1 = 0.9d;

	/** The parameter b.*/
	private double b;

	/** A default constructor.*/
	public BM25() {
		super();
		b=0.4d; //original 0.35
	}
	/**
	 * Returns the name of the model.
	 * @return the name of the model
	 */
	public final String getInfo() {
		return "ATIRE BM25b"+b;
	}
	/**
	 * Uses ATIRE BM25 to compute a weight for a term in a document.
	 * @param tf The term frequency in the document
	 * @param docLength the document's length
	 * @return the score assigned to a document with the given
	 *         tf and docLength, and other preset parameters
	 */
	public double score(double tf, double docLength) {
		return WeightingModelLibrary.log(numberOfDocuments / documentFrequency) *
				((k_1 + 1d) * tf) /
				(k_1 * (1 - b + b * (docLength / averageDocumentLength)) + termFrequency);
	}


	public void setParameter(double _b) {
	    this.b = _b;
	}

	public double getParameter() {
	    return this.b;
	}

}
