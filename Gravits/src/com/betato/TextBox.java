package com.betato;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.betato.gameDisplay.KeyStates;
import com.betato.gameDisplay.MouseStates;

public class TextBox {

	// Layout constants
	private static final int ROW_HEIGHT = 30;
	private static final int OFFSET = 10;
	private static final int TEXT_ELEVATION = 8;
	private static final int TITLE_HEIGHT = 20;

	// Modifiers for everything
	public boolean intOnly;
	public Point boxLocation;
	private Point relativeLocation = new Point(0, 0);
	public boolean visible;

	// Main frame
	private int width;
	private BufferedImage box;
	private Dimension frame;

	// Boxes
	public String[] text;
	private Point[] boxes;
	public int selectedBox = -1;
	public int maxInputLength;

	// Buttons
	private Rectangle[] buttons;
	public int selectedButton = -1;
	
	public TextBox(String title, int width, String[] headings,
			String[] buttons, boolean intOnly, int maxInputLength, Point pos) {
		// Init variables
		this.boxLocation = pos;
		this.intOnly = intOnly;
		this.maxInputLength = maxInputLength;
		text = new String[headings.length];
		boxes = new Point[headings.length];
		this.width = width;

		// Temporary variables for box layout creation
		int buttonRows = 0;
		int boxesHeight = 0;
		int buttonsHeight = 0;

		// Create text box bounding boxes
		for (int i = 0; i < headings.length; i++) {
			boxes[i] = new Point(OFFSET, OFFSET + ROW_HEIGHT + TITLE_HEIGHT
					+ (i * ROW_HEIGHT * 2));
			// Also initialize all values of text array
			text[i] = "";
		}

		boxesHeight = headings.length * ROW_HEIGHT * 2;

		// Create button bounding boxes
		if (buttons == null) {
			// No buttons if buttons argument was null
			this.buttons = new Rectangle[0];
		} else {
			this.buttons = new Rectangle[buttons.length];
		}
		for (int i = 0; i < buttons.length; i++) {
			if (i % 2 == 0) {
				// Even, create half button
				if (i >= buttons.length - 1) {
					// Last Button, fill the entire row
					this.buttons[i] = new Rectangle(OFFSET, (OFFSET * 2)
							+ boxesHeight + TITLE_HEIGHT
							+ ((ROW_HEIGHT + OFFSET) * buttonRows), width,
							ROW_HEIGHT);
				} else {
					// Half button
					this.buttons[i] = new Rectangle(OFFSET, (OFFSET * 2)
							+ boxesHeight + TITLE_HEIGHT
							+ ((ROW_HEIGHT + OFFSET) * buttonRows), (width / 2)
							- (OFFSET / 2), ROW_HEIGHT);
				}
			} else {
				// Odd, offset button
				this.buttons[i] = new Rectangle((OFFSET * 2)
						+ this.buttons[0].width, (OFFSET * 2) + boxesHeight
						+ TITLE_HEIGHT + ((ROW_HEIGHT + OFFSET) * buttonRows),
						(width / 2) - (OFFSET / 2), ROW_HEIGHT);
				buttonRows++;
			}
		}

		buttonsHeight = (OFFSET + ROW_HEIGHT) * buttonRows;

		// Create frame bounding box
		frame = new Dimension(width + (OFFSET * 2), TITLE_HEIGHT
				+ buttonsHeight + boxesHeight + (OFFSET * 2));

		// Save background box as bufferedImage
		drawBox(buttons, headings, title);
	}

	public void drawBox(String[] buttonLabels, String[] headings, String title) {
		// Get graphics
		box = new BufferedImage(frame.width, frame.height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = box.getGraphics();
		// Set font
		g.setFont(new Font("SansSerif", Font.BOLD, 18));

		// Outer box
		g.setColor(new Color(222, 184, 135, 128));
		g.fillRect(0, 0, frame.width, frame.height);

		// Draw buttons
		for (int i = 0; i < buttons.length; i++) {
			// Box
			g.setColor(Color.gray);
			g.fillRect(0 + this.buttons[i].x, this.buttons[i].y,
					this.buttons[i].width, this.buttons[i].height);
			// Label
			g.setColor(Color.black);
			drawStringCentered(g, buttonLabels[i], this.buttons[i]);
		}

		// Draw text
		g.setColor(Color.black);
		for (int i = 0; i < headings.length; i++) {
			g.drawString(headings[i], OFFSET, boxes[i].y - TEXT_ELEVATION);
		}

		// Draw title
		g.setFont(new Font("SansSerif", Font.BOLD, 24));
		if (10 > 0) {
			drawStringCentered(g, title, new Rectangle(OFFSET, OFFSET, width,
					TITLE_HEIGHT));
		}
	}

	public void drawStringCentered(Graphics g, String text, Rectangle rect) {
		// Get FontMetrics
		FontMetrics metrics = g.getFontMetrics();
		// Determine x coordinate
		int x = (rect.width - metrics.stringWidth(text)) / 2;
		// Determine y coordinate
		int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		// Draw the String
		g.drawString(text, x + rect.x, y + rect.y);
	}

	// Do not call update for non-editable boxes
	public void update(KeyStates keys, MouseStates mouse) {
		// zero mouse to TextBox position
		relativeLocation.x = mouse.pos.x - boxLocation.x;
		relativeLocation.y = mouse.pos.y - boxLocation.y;

		// get input and selection if click occurs
		if (mouse.buttonReleases[MouseStates.BUTTON_LEFT]) {
			getSelection(keys, relativeLocation);
		}
		// Only if box is selected
		if (selectedBox >= 0) {
			getInput(keys);
		}
	}

	private void getInput(KeyStates keys) {
		// Only add text if under maximum
		if (text[selectedBox].length() <= maxInputLength) {
			// Always get numbers
			for (int j = KeyStates.START_NUMROW; j <= KeyStates.END_NUMROW; j++) {
				// If number key is pressed
				if (keys.keyReleases[j]) {
					text[selectedBox] += keys.keyMap.get(j);
				}
			}

			for (int j = KeyStates.START_NUMPAD; j <= KeyStates.END_NUMPAD; j++) {
				// If number key is pressed
				if (keys.keyReleases[j]) {
					text[selectedBox] += keys.keyMap.get(j);
				}
			}

			// Get alphabetical characters if enabled
			if (!intOnly) {
				for (int j = KeyStates.START_LETTERS; j <= KeyStates.END_LETTERS; j++) {
					// If letter key is pressed
					if (keys.keyReleases[j]) {
						text[selectedBox] += keys.keyMap.get(j);
					}
				}
			}
		}
		// Backspace if pressed
		if (keys.keyReleases[KeyStates.BACKSPACE]) {
			if (keys.keyStates[KeyStates.CTRL]) {
				// Delete all
				text[selectedBox] = "";
			}
			if (text[selectedBox].length() > 0) {
				// Delete one line
				text[selectedBox] = text[selectedBox].substring(0,
						text[selectedBox].length() - 1);
			}
		}
	}

	private void getSelection(KeyStates keys, Point pos) {
		// Deselect if click is outside of box
		if (pos.x < 0 || pos.x > frame.width || pos.y < 0
				|| pos.y > frame.height) {
			selectedBox = -1;
			// Skip checking the other boxes if the cursor is outside the
			// boxes
			return;
		}
		if (pos.x > OFFSET && pos.x < width + OFFSET) {
			for (int i = 0; i < boxes.length; i++) {
				if (pos.y > boxes[i].y && pos.y < boxes[i].y + ROW_HEIGHT) {
					// If mouse is in box
					selectedBox = i;
				}
			}
		}
		for (int i = 0; i < buttons.length; i++) {
			if (pos.y > buttons[i].y
					&& pos.y < buttons[i].y + buttons[i].height
					&& pos.x > buttons[i].x
					&& pos.x < buttons[i].x + buttons[i].width) {
				// If mouse is in box
				selectedButton = i;
				break;
			}
		}

	}

	public void drawTextBox(Graphics g) {
		// Draw only if visible
		if (visible) {
			// Draw constant frame
			g.drawImage(box, boxLocation.x, boxLocation.y, null);
			g.setFont(new Font("SansSerif", Font.BOLD, 18));

			// Draw boxes
			for (int i = 0; i < boxes.length; i++) {
				if (i == selectedBox) {
					g.setColor(Color.yellow);
				} else {
					g.setColor(Color.white);
				}
				g.fillRect(boxLocation.x + OFFSET, boxLocation.y + boxes[i].y,
						width, ROW_HEIGHT);
			}

			// Draw text
			g.setColor(Color.black);
			for (int i = 0; i < boxes.length; i++) {
				g.drawString(text[i], boxLocation.x + (OFFSET * 2),
						boxLocation.y + ROW_HEIGHT + boxes[i].y
								- TEXT_ELEVATION);
			}
		}
	}
}
