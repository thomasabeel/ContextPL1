package nl.tudelft.ti2806.pl1.zoomlevels;

import java.io.FileNotFoundException;

import nl.tudelft.ti2806.pl1.reader.NodePlacer;
import nl.tudelft.ti2806.pl1.reader.Reader;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GraphConverterTest {

	String nodes = "src/test/resources/collapsetest_nodes.txt";
	String edges = "src/test/resources/collapsetest_edges.txt";
	Graph graph = new SingleGraph("test graph");

	@Before
	public void setUp() throws FileNotFoundException {
		graph = Reader.read(nodes, edges);
	}

	@After
	public void tearDown() {
		graph = null;
		nodes = null;
		edges = null;
	}

	@Test
	public void test() {
	}

	public void main(final String[] args) throws FileNotFoundException {
		graph = Reader.read(nodes, edges);
		Graph g = GraphConverter.collapsePointMutations(graph);
		Viewer viewer = g.display();
		viewer.disableAutoLayout();
		NodePlacer.place(g, viewer);
	}

}
