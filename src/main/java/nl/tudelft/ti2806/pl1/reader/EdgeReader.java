package nl.tudelft.ti2806.pl1.reader;

import java.io.BufferedReader;
import java.io.IOException;

import nl.tudelft.ti2806.pl1.DGraph.DEdge;
import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;
import nl.tudelft.ti2806.pl1.exceptions.InvalidFileFormatException;

/**
 * 
 * @author Marissa, Mark
 * @since 24-04-2015 Lets you read the Edges from the provided data.
 */
public final class EdgeReader {

	/**
	 * 
	 */
	private EdgeReader() {
	}

	/**
	 * 
	 * @param sc
	 *            Scanner from which the edge information will be read.
	 * @return ArrayList of all Edges.
	 * @throws IOException
	 */
	public static void readEdges(final BufferedReader reader, final DGraph graph)
			throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			String[] nodes = line.split("\\s");
			if (nodes.length != 2) {
				throw new InvalidFileFormatException(
						"There should be 2 node id's seperated by spaces in the edge file");
			}
			int start;
			int end;
			try {
				start = Integer.parseInt(nodes[0]);
				end = Integer.parseInt(nodes[1]);
			} catch (Exception e) {
				throw new InvalidFileFormatException(
						"The id's should be integers");
			}
			DNode src = graph.getDNode(start);
			DNode tar = graph.getDNode(end);
			if (src == null || tar == null) {
				throw new InvalidFileFormatException("The id's shoould exist");
			}
			DEdge edge = new DEdge(src, tar);
			graph.addDEdge(edge);
		}
		reader.close();
	}
}
