package nl.tudelft.ti2806.pl1.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 * The status bar of the application at the bottom of the window.
 * 
 * @author Maarten
 */
public class StatusBar extends JPanel {

	/** The serial version UID. */
	private static final long serialVersionUID = -2304279241558941973L;

	/** The main text label of the status bar. */
	private JLabel mainLabel = new JLabel();

	/**
	 * 
	 */
	private JLabel midLabel = new JLabel();

	/**
	 * 
	 */
	private JLabel rightLabel = new JLabel();

	/**
	 * Initializes the status bar.
	 */
	public StatusBar() {
		setLayout(new BorderLayout());
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		midLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(mainLabel, BorderLayout.WEST);
		add(midLabel, BorderLayout.CENTER);
		add(rightLabel, BorderLayout.EAST);
	}

	// /**
	// * Shows an error message in the main text label.
	// *
	// * @param message
	// * The error message to show.
	// */
	// public final void error(final String message) {
	// mainLabel.setText("Error: " + message);
	// }
	//
	// /**
	// * Shows an info message in the main text label.
	// *
	// * @param message
	// * The info message to show.
	// */
	// public final void info(final String message) {
	// mainLabel.setText(message);
	// }

	/**
	 * Shows a message in the left text label.
	 * 
	 * @param message
	 *            The message to show.
	 * @param type
	 *            The type of message
	 * @see MessageType
	 */
	public final void main(final String message, final MessageType type) {
		mainLabel.setText(type.getPrefix() + message);
	}

	/**
	 * Shows a message in the right text label.
	 * 
	 * @param message
	 *            The message to show.
	 */
	public final void right(final String message) {
		rightLabel.setText(message);
	}

	/**
	 * Shows a message in the left text label.
	 * 
	 * @param message
	 *            The message to show.
	 */
	public final void mid(final String message) {
		midLabel.setText(message);
	}

	/**
	 * @author Maarten
	 */
	public enum MessageType {

		/** An informative message. */
		INFO {
			@Override
			String getPrefix() {
				return "";
			}
		},

		/** An error message. */
		ERROR {
			@Override
			String getPrefix() {
				return "Error: ";
			}
		};

		/**
		 * 
		 * @return The prefix to put in front of every message of this
		 *         kind/type.
		 */
		abstract String getPrefix();
	}

}
