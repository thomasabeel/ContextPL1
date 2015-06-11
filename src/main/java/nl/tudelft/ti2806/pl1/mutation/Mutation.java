package nl.tudelft.ti2806.pl1.mutation;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 * @version 1.0
 */
public abstract class Mutation {

	/**
	 * The IDs of the nodes before and after the mutation.
	 */
	private final int preNode, postNode;

	/**
	 * Score of the mutation.
	 */
	private double score = 10;

	/**
	 * 
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 */
	public Mutation(final int pre, final int post) {
		this.preNode = pre;
		this.postNode = post;
	}

	/**
	 * 
	 */
	protected void calculateScore() {

	}

	public final double getScore() {
		return score;
	}

	/**
	 * @return the preNode
	 */
	public final int getPreNode() {
		return preNode;
	}

	/**
	 * @return the postNode
	 */
	public final int getPostNode() {
		return postNode;
	}

}
