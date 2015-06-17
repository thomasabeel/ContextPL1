package nl.tudelft.ti2806.pl1.gui.optionpane;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import nl.tudelft.ti2806.pl1.gui.Window;

import com.wordpress.tips4java.ScrollablePanel;
import com.wordpress.tips4java.ScrollablePanel.ScrollableSizeHint;

/**
 * @author Maarten
 *
 */
public class OptionsPane extends JScrollPane {

	/** The serial version UID. */
	private static final long serialVersionUID = -8048134952005779117L;

	/** The width of the container. */
	private static final int WIDTH = 220;

	/** The width of the pane in a dimension object. */
	private static final Dimension SIZE = new Dimension(WIDTH,
			Integer.MAX_VALUE);

	/**
	 * The maximum width of the (grouped) elements in this pane.
	 */
	public static final int MAX_CHILD_WIDTH = 200;

	/**
	 * The insets values.
	 */
	private static final int HOR_INSETS = 10, VER_INSETS = 5;

	/**
	 * The insets.
	 */
	private static final Insets INSETS = new Insets(VER_INSETS, HOR_INSETS,
			VER_INSETS, HOR_INSETS);

	// ***** Fields ***** //

	/**
	 * The window this option pane is part of.
	 */
	private Window window;

	// ***** Elements ***** //

	/** The panel containing the control elements. */
	private ScrollablePanel pane;

	/** */
	private GridBagLayout gridBagLayout;

	/** The grid bag constraints used by the layout manager. */
	private GridBagConstraints gbc;

	/** The genome table. */
	private GenomeTable tblGenomes;

	/**
	 * @return the genome table
	 */
	public final GenomeTable getGenomes() {
		return tblGenomes;
	}

	/**
	 * The group of elements showing information about a selected genome in the
	 * genome table.
	 */
	// private SelectedGenomeGroup grpSelectedGenome;

	/** The group of elements showing information about a selected node. */
	private SelectedNodeGroup grpSelectedNode;

	/**
	 * @return the selected node group
	 */
	public final SelectedNodeGroup getSelectedNodeGroup() {
		return grpSelectedNode;
	}

	/**  */
	private ZoomConfigureGroup grpZoomSettings;

	/**
	 * @return the grpZoomSettings
	 */
	public final ZoomConfigureGroup getGrpZoomSettings() {
		return grpZoomSettings;
	}

	/**
	 * Initialize the option pane.
	 * 
	 * @param w
	 *            The window this option pane is part of.
	 */
	public OptionsPane(final Window w) {
		this.window = w;
		this.gridBagLayout = new GridBagLayout();
		this.gbc = new GridBagConstraints();
		this.pane = new ScrollablePanel(gridBagLayout);
		pane.setAlignmentY(TOP_ALIGNMENT);
		pane.setScrollableWidth(ScrollableSizeHint.FIT);
		// pane.setScrollableHeight(ScrollableSizeHint.STRETCH);

		this.tblGenomes = new GenomeTable();
		this.grpSelectedNode = new SelectedNodeGroup();
		// this.grpSelectedGenome = new SelectedGenomeGroup(this);
		this.grpZoomSettings = new ZoomConfigureGroup();

		// setMinimumSize(new Dimension(WIDTH, 10));
		setMaximumSize(SIZE);
		setPreferredSize(SIZE);
		addControls();

		setViewportView(pane);
	}

	/**
	 * Method called after all window components are initialized.
	 * 
	 * @see Window#Window()
	 */
	public final void componentsLoaded() {
		window.getContent().getGraphPanel().registerObserver(grpSelectedNode);
	}

	/**
	 * Adds the elements to the option pane.
	 */
	private void addControls() {
		setupConstraints();

		place(new JLabel("Genomes:"), 0);
		place(tblGenomes);
		// place(grpSelectedGenome);
		place(grpZoomSettings);
		place(grpSelectedNode);

		final int boxWY = 100;
		gbc.weighty = boxWY;
		place(Box.createGlue());
	}

	/**
	 * Sets up the grid bag constraints.
	 */
	private void setupConstraints() {
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.insets = INSETS;
	}

	/**
	 * Creates a list of check box items based on a list of genome names and
	 * adds them to the genomes list.
	 * 
	 * @param genomeIds
	 *            A list of genome identifiers/names
	 * @param empty
	 *            If true, the list will first be emptied before filled with the
	 *            new rows.
	 * @param selected
	 *            The value of the check box for all genome rows created.
	 */
	public final void fillGenomeList(final Collection<String> genomeIds,
			final boolean empty, final boolean selected) {
		tblGenomes.fill(genomeIds, empty, selected);
	}

	/**
	 * Places a component on the next empty spot in the layout.
	 * 
	 * @param elem
	 *            The element to place.
	 */
	private void place(final Component elem) {
		gbc.gridy = GridBagConstraints.RELATIVE;
		pane.add(elem, gbc);
	}

	/**
	 * Places a component on a given row in the layout, adding a given amount of
	 * vertical insets below the element.
	 * 
	 * @param elem
	 *            The element to place.
	 * @param belowInset
	 *            The vertical insets below the element.
	 */
	private void place(final Component elem, final int belowInset) {
		gbc.insets = new Insets(VER_INSETS, HOR_INSETS, belowInset, HOR_INSETS);
		place(elem);
		gbc.insets = INSETS;
	}

	@Override
	public final String toString() {
		return this.getClass().toString();
	}

}
