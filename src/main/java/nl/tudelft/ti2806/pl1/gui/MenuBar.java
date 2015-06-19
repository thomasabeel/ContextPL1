package nl.tudelft.ti2806.pl1.gui;

import java.awt.Event;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Maarten
 *
 */
public class MenuBar extends JMenuBar {

	/** The serial version UID. */
	private static final long serialVersionUID = -3046759850795865308L;

	/** The window this menu bar is part of. */
	private Window window;

	/**
	 * Initializes the menu bar.
	 * 
	 * @param w
	 *            The window this menu bar is part of.
	 */
	public MenuBar(final Window w) {
		this.window = w;
		this.add(fileMenu());
		this.add(viewMenu());
		this.add(helpMenu());
	}

	/**
	 * Creates and fills the file menu.
	 * 
	 * @return the file menu
	 */
	private JMenu fileMenu() {
		JMenu ret = new JMenu("File");
		JMenuItem impGraph = new JMenuItem();
		setMenuItem(impGraph, "Import graph", null, 'I',
				"Import a sequence graph (.node.graph and .edge.graph)",
				AppEvent.IMPORT_GRAPH);

		setAcc(impGraph, KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		ret.add(impGraph);

		JMenu export = new JMenu("Export graph layout as...") {
			/** The serial version UID. */
			private static final long serialVersionUID = 6733151149511110189L;

			@Override
			public boolean isEnabled() {
				return window.getContent().isGraphLoaded();
			}
		};
		export.setMnemonic('E');
		JMenuItem exportDGS = new JMenuItem();
		setMenuItem(exportDGS, "DGS format", null, 'D', null,
				AppEvent.WRITE_TO_DGS);
		export.add(exportDGS);
		ret.add(export);

		JMenuItem exit = new JMenuItem();
		setMenuItem(exit, "Exit", null, 'E', "Exit the program",
				AppEvent.EXIT_APP);
		setAcc(exit, KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
		ret.add(exit);

		return ret;
	}

	/**
	 * Creates and fills the info menu.
	 * 
	 * @return the info menu
	 */
	private JMenu helpMenu() {
		JMenu ret = new JMenu("Help");
		JMenuItem help = new JMenuItem();
		setMenuItem(help, "Help", null, 'h', "Press to show shortcuts",
				AppEvent.HELP);
		setAcc(help, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		ret.add(help);
		return ret;
	}

	/**
	 * Creates and fills the view menu.
	 * 
	 * @return the view menu
	 */
	private JMenu viewMenu() {
		JMenu ret = new JMenu("View") {

			/** The serial version UID. */
			private static final long serialVersionUID = -8732114494095898765L;

			@Override
			public boolean isEnabled() {
				return window != null && window.getContent().isGraphLoaded();
			}
		};

		JMenuItem reset = new JMenuItem();
		setMenuItem(reset, "Reset view", null, 'R',
				"Reset to the initial view.", AppEvent.RESET_GRAPH);
		setAcc(reset,
				KeyStroke.getKeyStroke(reset.getMnemonic(), Event.CTRL_MASK));
		ret.add(reset);

		JMenuItem zoomIn = new JMenuItem();
		setMenuItem(zoomIn, "Zoom in", null, 'I',
				"<html>Zoom in one zoom level.<br>Nodes might be uncollapsed.",
				AppEvent.ZOOMLEVEL_IN);
		setAcc(zoomIn, KeyStroke.getKeyStroke(KeyEvent.VK_ADD, Event.CTRL_MASK));
		ret.add(zoomIn);

		JMenuItem zoomOut = new JMenuItem();
		setMenuItem(zoomOut, "Zoom out", null, 'O',
				"<html>Go to previous zoomlevel.<br>Nodes might be collapsed.",
				AppEvent.ZOOMLEVEL_OUT);
		setAcc(zoomOut,
				KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, Event.CTRL_MASK));
		ret.add(zoomOut);

		ret.add(makeShowToolBar());
		ret.add(makeShowOptionPane());
		return ret;
	}

	/**
	 * @return The show tool bar check box menu item.
	 */
	private JCheckBoxMenuItem makeShowToolBar() {

		final JCheckBoxMenuItem showToolBar = new JCheckBoxMenuItem(
				"Show tool bar", true);
		showToolBar.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				window.getToolBar().setVisible(showToolBar.isSelected());
				window.revalidate();
			}
		});
		return showToolBar;
	}

	/**
	 * @return The show option pane check box menu item.
	 */
	private JCheckBoxMenuItem makeShowOptionPane() {

		final JCheckBoxMenuItem showOptionPane = new JCheckBoxMenuItem(
				"Show option panel", true) {
			/** The serial version UID. */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return window.getContent().isGraphLoaded();
			}
		};
		showOptionPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				window.getOptionPanel().setVisible(showOptionPane.isSelected());
				window.revalidate();
			}
		});
		return showOptionPane;
	}

	/**
	 * Creates and sets up a menu item.
	 * 
	 * @param item
	 *            The menuItem we want to set properties for.
	 * 
	 * @param text
	 *            The text to show on the menu item.
	 * @param iconName
	 *            The name of the icon file in the resources/images folder
	 * @param mnemonic
	 *            The mnemonic (fast key)
	 * @param toolTipText
	 *            The tool tip.
	 * @param action
	 *            The event name.
	 * 
	 * @see AppEvent
	 */
	private void setMenuItem(final JMenuItem item, final String text,
			final String iconName, final char mnemonic,
			final String toolTipText, final AppEvent action) {
		item.setText(text);
		item.setMnemonic(mnemonic);
		item.setToolTipText(toolTipText);
		item.addActionListener(action);
		if (iconName != null) {
			String imgLocation = "images/" + iconName;
			URL imageURL = ToolBar.class.getResource(imgLocation);
			if (imageURL != null) {
				item.setIcon(new ImageIcon(imageURL, text));
			} else {
				System.err.println("Resource not found: " + imgLocation);
			}
		}
	}

	/**
	 * 
	 * @param item
	 *            JMenuItem we want to add an acceleator to.
	 * @param keyStroke
	 *            The set of keys it will use.
	 */
	private void setAcc(final JMenuItem item, final KeyStroke keyStroke) {
		item.setAccelerator(keyStroke);
	}
}
