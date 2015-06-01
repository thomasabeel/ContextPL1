package nl.tudelft.ti2806.pl1.DGraph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;

/**
 * The DGraph class for representing our data.
 * 
 * @author Marissa, Mark
 * @since 27-05-15
 */
public class DGraph {

	/** The neo4j database. **/
	private GraphDatabaseService graphDb;

	/** How long it calculates the index before stopping. **/
	private static final int TIMEOUT = 10;

	/**
	 * Defines the possible relationships.
	 * 
	 * @author Mark
	 *
	 */
	private static enum RelTypes implements RelationshipType {
		/** The relationshiptypes. **/
		NEXT, SOURCE;
	}

	/**
	 * Retrieve the neo4j graph on the given location.
	 * 
	 * @param dbPath
	 *            Location of the neo4j graph.
	 */
	public DGraph(final String dbPath) {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
		addUniqueConstraint();
		createIndexNodes();
		createIndexSources();
	}

	/**
	 * Make sure the id of the node is unique.
	 */
	protected void addUniqueConstraint() {
		try (Transaction tx = graphDb.beginTx()) {
			graphDb.schema().constraintFor(DynamicLabel.label("Nodes"))
					.assertPropertyIsUnique("id").create();
			tx.success();
		}
		try (Transaction tx = graphDb.beginTx()) {
			graphDb.schema().constraintFor(DynamicLabel.label("Sources"))
					.assertPropertyIsUnique("source").create();
			tx.success();
		}
	}

	/**
	 * Add index on id.
	 */
	protected void createIndexNodes() {
		IndexDefinition indexDefinition;
		try (Transaction tx = graphDb.beginTx()) {
			Schema schema = graphDb.schema();
			indexDefinition = schema.indexFor(DynamicLabel.label("Nodes"))
					.on("id").create();
			tx.success();
		}
		try (Transaction tx = graphDb.beginTx()) {
			Schema schema = graphDb.schema();
			schema.awaitIndexOnline(indexDefinition, TIMEOUT, TimeUnit.SECONDS);
			tx.success();
		}
	}

	/**
	 * Add index on sources.
	 */
	protected void createIndexSources() {
		IndexDefinition indexDefinition;
		try (Transaction tx = graphDb.beginTx()) {
			Schema schema = graphDb.schema();
			indexDefinition = schema.indexFor(DynamicLabel.label("Sources"))
					.on("source").create();
			tx.success();
		}
		try (Transaction tx = graphDb.beginTx()) {
			Schema schema = graphDb.schema();
			schema.awaitIndexOnline(indexDefinition, TIMEOUT, TimeUnit.SECONDS);
			tx.success();
		}
	}

	/**
	 * 
	 * @param id
	 *            The id of the Node.
	 * @param start
	 *            The place of the reference of the first base in the node.
	 * @param end
	 *            The place of the reference of the last base in the node.
	 * @param content
	 *            The content of the Node.
	 * @param coords
	 *            The coordinates of the Node in the graph.
	 * @param depth
	 *            The depth of the Node in the graph.
	 * @param sources
	 *            The sources of the Node.
	 */
	public void addNode(final int id, final int start, final int end,
			final String content, final Point coords, final int depth,
			final String[] sources) {
		Label label = DynamicLabel.label("Nodes");
		try (Transaction tx = graphDb.beginTx()) {
			Node addNode = graphDb.createNode(label);
			addNode.setProperty("id", id);
			addNode.setProperty("start", start);
			addNode.setProperty("end", end);
			addNode.setProperty("content", content);
			addNode.setProperty("x", coords.x);
			addNode.setProperty("y", coords.y);
			addNode.setProperty("dpeth", depth);
			tx.success();
		}
		label = DynamicLabel.label("Sources");
		for (String s : sources) {
			try (Transaction tx = graphDb.beginTx()) {
				if (graphDb.findNode(label, "source", s) == null) {
					Node src = graphDb.createNode(label);
					src.setProperty("source", s);
				}
				tx.success();
			}
		}
		addSources(id, sources);
	}

	/**
	 * Adds sources to the Node.
	 * 
	 * @param nodeId
	 *            Id of the node we want to add a source to.
	 * @param sources
	 *            Sources we want to add to the Node.
	 */
	protected void addSources(final int nodeId, final String[] sources) {
		Node node = getNode(nodeId);
		for (String s : sources) {
			Node source = getSource(s);
			node.createRelationshipTo(source, RelTypes.SOURCE);
		}
	}

	/**
	 * A node containing the source, having an incoming edge with all of its
	 * nodes.
	 * 
	 * @param s
	 *            The source we want to get the node from.
	 * @return Node containing the source.
	 */
	protected Node getSource(final String s) {
		Label label = DynamicLabel.label("Sources");
		Node node = null;
		try (Transaction tx = graphDb.beginTx()) {
			node = graphDb.findNode(label, "source", s);
			tx.success();
		}
		return node;
	}

	/**
	 * Get a Node from the graph.
	 * 
	 * @param id
	 *            Id of the Node we want to get.
	 * @return Node in the graph.
	 */
	public Node getNode(final int id) {
		Label label = DynamicLabel.label("Nodes");
		Node node = null;
		try (Transaction tx = graphDb.beginTx()) {
			node = graphDb.findNode(label, "id", id);
			tx.success();
		}
		return node;
	}

	/**
	 * Get all Nodes in the graph.
	 * 
	 * @return All the Nodes in the Graph.
	 */
	public Collection<Node> getNodes() {
		Collection<Node> nodes = new ArrayList<Node>();
		try (Transaction tx = graphDb.beginTx()) {
			ResourceIterator<Node> it = graphDb.findNodes(DynamicLabel
					.label("Nodes"));
			while (it.hasNext()) {
				nodes.add(it.next());
			}
		}
		return nodes;
	}

	/**
	 * Add an edge from node1 to node2.
	 * 
	 * @param node1
	 *            The Node we want to add an edge to.
	 * @param node2
	 *            The Node the edge is going to.
	 */
	public void addEdge(final Node node1, final Node node2) {
		node1.createRelationshipTo(node2, RelTypes.NEXT);
	}

	/**
	 * Add edge from node with nodeId1 to node with nodeId2.
	 * 
	 * @param nodeId1
	 *            Id of the node to which we want to add the node.
	 * @param nodeId2
	 *            Id of the node the edge is going to.
	 */
	public void addEdge(final int nodeId1, final int nodeId2) {
		Node n1 = getNode(nodeId1);
		Node n2 = getNode(nodeId2);
		n1.createRelationshipTo(n2, RelTypes.NEXT);
	}
}