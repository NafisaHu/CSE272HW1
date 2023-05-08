package com.luence.app;

import org.apache.lucene.search.similarities.TFIDFSimilarity;

public class CustomTFIDFSimilarity extends TFIDFSimilarity {
    /**
     * Computes a score factor based on a term or phrase's frequency in a document. This value is
     * multiplied by the {@link #idf(long, long)} factor for each term in the query and these products
     * are then summed to form the initial score for a document.
     *
     * <p>Terms and phrases repeated in a document indicate the topic of the document, so
     * implementations of this method usually return larger values when <code>freq</code> is large,
     * and smaller values when <code>freq</code> is small.
     *
     * @param freq the frequency of a term within a document
     * @return a score factor based on a term's within-document frequency
     */
    @Override
    public float tf(float freq) {
        return 0;
    }

    /**
     * Computes a score factor based on a term's document frequency (the number of documents which
     * contain the term). This value is multiplied by the {@link #tf(float)} factor for each term in
     * the query and these products are then summed to form the initial score for a document.
     *
     * <p>Terms that occur in fewer documents are better indicators of topic, so implementations of
     * this method usually return larger values for rare terms, and smaller values for common terms.
     *
     * @param docFreq  the number of documents which contain the term
     * @param docCount the total number of documents in the collection
     * @return a score factor based on the term's document frequency
     */
    @Override
    public float idf(long docFreq, long docCount) {
        return 0;
    }

    /**
     * Compute an index-time normalization value for this field instance.
     *
     * @param length the number of terms in the field, optionally {@link #setDiscountOverlaps(boolean)
     *               discounting overlaps}
     * @return a length normalization value
     */
    @Override
    public float lengthNorm(int length) {
        return 0;
    }
}
