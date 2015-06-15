package nl.tudelft.ti2806.pl1.gui.optionpane;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.tudelft.ti2806.pl1.DGraph.DNode;
import nl.tudelft.ti2806.pl1.gui.contentpane.NodeSelectionObserver;

/**
 * @author Maarten
 * @since 18-5-2015
 * @version 1.0
 * 
 */
public class SelectedNodeGroup extends JPanel implements NodeSelectionObserver {

	/** The serial version UID. */
	private static final long serialVersionUID = -4851724739205792429L;

	/** The default visible title. */
	private static final String DEFAULT_TITLE = "Selected node";

	/** The insets. */
	private static final Insets INSETS = new Insets(2, 5, 2, 5);

	/** The size of the group. */
	private static final Dimension SIZE = new Dimension(
			OptionsPane.MAX_CHILD_WIDTH, 120);

	/** The grid bag constraints for the layout manager. */
	private GridBagConstraints gbc = new GridBagConstraints();

	/** The labels containing the node statistics. */
	private JLabel lblID = mkLabel(""), lblContentLength = mkLabel(""),
			lblSources = mkLabel("");

	/** The simple bar chart showing the distribution of nucleotides. */
	private NodeContentBar nodeChart;

	/** Whether this group should be visible or not. */
	private boolean show = false;

	/** Initialize the group layout panel. */
	public SelectedNodeGroup() {
		super();
		setLayout(new GridBagLayout());
		setMaximumSize(SIZE);
		setupGBC();
		setAlignmentY(TOP_ALIGNMENT);
		setBorder(BorderFactory.createTitledBorder(DEFAULT_TITLE));
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.weightx = 0;
		add(new JLabel("ID:"), gbc);
		gbc.gridx = 1;
		gbc.weightx = OptionsPane.GBC_WEIGHT_X;
		add(lblID, gbc);

		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.weightx = 1;
		add(new JLabel("Length:"), gbc);
		gbc.gridx = 1;
		gbc.weightx = OptionsPane.GBC_WEIGHT_X;
		add(lblContentLength, gbc);

		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.weightx = 1;
		add(new JLabel("Sources:"), gbc);
		gbc.gridy = OptionsPane.GBC_GRIDY_3;
		gbc.gridwidth = 2;
		gbc.weightx = 2.0;
		add(lblSources, gbc);

		nodeChart = new NodeContentBar(OptionsPane.NBC_WIDTH,
				OptionsPane.NBC_HEIGHT);

		gbc.fill = GridBagConstraints.NONE;
		gbc.gridy = OptionsPane.GBC_GRIDY_4;
		gbc.gridwidth = 2;
		add(nodeChart, gbc);

		gbc.weighty = OptionsPane.GBC_WEIGHT_Y;
		add(Box.createVerticalGlue());
	}

	/**
	 * Makes a label with the input text.
	 * 
	 * @param text
	 *            Input text.
	 * @return JLabel object with the input text.
	 */
	private JLabel mkLabel(final String text) {
		JLabel ret = new JLabel(text);
		ret.setAlignmentX(LEFT_ALIGNMENT);
		return ret;
	}

	/** Sets up the grid bag constraints. */
	private void setupGBC() {
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.insets = INSETS;
		gbc.weightx = 1;
		gbc.weighty = 1;
	}

	/**
	 * Sets the visible title for the grouping.
	 * 
	 * @param newTitle
	 *            The new title.
	 */
	public final void setTitle(final String newTitle) {
		setBorder(BorderFactory.createTitledBorder(newTitle));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void update(final HashSet<DNode> selectedNodes) {
		this.show = (selectedNodes.size() == 1);
		if (selectedNodes.size() == 1) {
			DNode selectedNode = selectedNodes.iterator().next();
			lblID.setText(String.valueOf(selectedNode.getId()));
			lblContentLength.setText(String.valueOf(selectedNode.getContent()
					.length()));
			lblSources.setText(collectionToString(selectedNode.getSources()));
			nodeChart.analyseString(selectedNode.getContent());
			nodeChart.repaint();
		}
		revalidate();
	}

	/**
	 * @param col
	 *            The collection to stringify.
	 * @param <A>
	 *            The type of the collection items.
	 * @return The basic string representation of <code>col</code>.
	 */
	private <A> String collectionToString(final Collection<A> col) {
		String ret = "<html>";
		for (A item : col) {
			ret += item.toString() + "<br>";
		}
		return ret + "</html>";
	}

	@Override
	public boolean isVisible() {
		return super.isVisible() && show;
	}

	@Override
	public final String toString() {
		return this.getClass().toString();
	}
}
