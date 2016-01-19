package com.betato;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.betato.gameDisplay.KeyStates;
import com.betato.gameDisplay.MouseStates;

public class TextBox {

	public TextBox(String title, int width, String[] headings, String[] buttons) {
		this.title = title;
		this.headings = headings;
		text = new String[headings.length];
		this.buttonLabels = buttons;
		boxes = new Point[headings.length];
		this.buttons = new Rectangle[buttons.length];

		this.width = width;
		// Get frame dimension

		// Get text box locations
		for (int i = 0; i < headings.length; i++) {
			boxes[i] = new Point(OFFSET, OFFSET + ROW_HEIGHT
					+ (i * ROW_HEIGHT * 2));
			text[i] = "";
		}

		// Get button locations
		int boxesLen = headings.length * ROW_HEIGHT * 2;

		for (int i = 0; i < buttons.length; i++) {
			if (i % 2 == 0) {
				// Even
				if (i >= buttons.length - 1) {
					// Last Button
					this.buttons[i] = new Rectangle(OFFSET, (OFFSET * 2)
							+ boxesLen + ((ROW_HEIGHT + OFFSET) * buttonRows),
							width, ROW_HEIGHT);
				} else {
					// Half button
					this.buttons[i] = new Rectangle(OFFSET, (OFFSET * 2)
							+ boxesLen + ((ROW_HEIGHT + OFFSET) * buttonRows),
							(width / 2) - (OFFSET / 2), ROW_HEIGHT);
				}
			} else {
				// Odd
				// Offset button
				this.buttons[i] = new Rectangle((OFFSET * 2)
						+ this.buttons[0].width, (OFFSET * 2) + boxesLen
						+ ((ROW_HEIGHT + OFFSET) * buttonRows), (width / 2)
						- (OFFSET / 2), ROW_HEIGHT);
				buttonRows++;
			}
		}

		frame = new Dimension(width + (OFFSET * 2),
				this.buttons[buttons.length - 1].y + OFFSET + ROW_HEIGHT);
	}

	private static final int ROW_HEIGHT = 30;
	private static final int OFFSET = 10;
	private static final int TEXT_ELEVATION = 8;

	private int width;
	private String title;
	private String[] headings;
	private Dimension frame;
	private Point[] boxes;
	private Rectangle[] buttons;
	private String[] buttonLabels;
	private int buttonRows;

	public String[] text;
	public int selectedButton = -1;
	public int selectedBox = -1;

	// Do not call update for non-editable boxes
	public void update(KeyStates keys, MouseStates mouse, Point pos) {
		// zero mouse to TextBox position
		mouse.pos.x -= pos.x;
		mouse.pos.y -= pos.y;
		// Check for clicks on boxes
		if (mouse.pos.x > OFFSET && mouse.pos.x < width + OFFSET) {
			for (int i = 0; i < headings.length; i++) {
				if (mouse.pos.y > boxes[i].y
						&& mouse.pos.y < boxes[i].y + ROW_HEIGHT) {
					// If mouse is in box
					if (mouse.buttonReleases[MouseStates.BUTTON_LEFT]) {
						selectedBox = i;
					}
				}
			}
		}

		// Deselect if click is outside of box
		if (mouse.pos.x < 0 || mouse.pos.x > frame.width || mouse.pos.y < 0
				|| mouse.pos.y > frame.height) {
			if (mouse.buttonReleases[MouseStates.BUTTON_LEFT]) {
				selectedBox = -1;
			}
		}

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

		// Check for mouse clicks on buttons
		for (int i = 0; i < buttons.length; i++) {
			if (mouse.pos.y > buttons[i].y
					&& mouse.pos.y < buttons[i].y + buttons[i].height
					&& mouse.pos.x > buttons[i].x
					&& mouse.pos.x < buttons[i].x + buttons[i].width) {
				// If mouse is in box
				if (mouse.buttonReleases[MouseStates.BUTTON_LEFT]) {
					selectedButton = i;
					break;
				}
			}
		}
	}

	public void drawTextBox(Graphics g, Point pos) {

		g.setFont(new Font("SansSerif", Font.BOLD, 18));

		// Outer box
		g.setColor(new Color(222, 184, 135, 128));
		g.fillRect(pos.x, pos.y, frame.width, frame.height);

		// Draw boxes
		for (int i = 0; i < headings.length; i++) {
			if (i == selectedBox) {
				g.setColor(Color.yellow);
			} else {
				g.setColor(Color.white);
			}
			g.fillRect(pos.x + OFFSET, pos.y + boxes[i].y, width, ROW_HEIGHT);
		}

		// Draw text
		g.setColor(Color.black);
		for (int i = 0; i < headings.length; i++) {
			g.drawString(headings[i], pos.x + OFFSET, boxes[i].y + pos.y
					- TEXT_ELEVATION);
			g.drawString(text[i], pos.x + (OFFSET * 2), pos.y + ROW_HEIGHT
					+ boxes[i].y - TEXT_ELEVATION);
		}

		// Draw buttons
		for (int i = 0; i < buttons.length; i++) {
			g.setColor(Color.gray);
			g.fillRect(pos.x + buttons[i].x, pos.y + buttons[i].y,
					buttons[i].width, buttons[i].height);
			g.setColor(Color.black);
			drawCenteredString(g, buttonLabels[i], buttons[i], pos);
		}
	}

	public void drawCenteredString(Graphics g, String text, Rectangle rect,
			Point location) {
		// Get the FontMetrics
		FontMetrics metrics = g.getFontMetrics();
		// Determine the X coordinate for the text
		int x = (rect.width - metrics.stringWidth(text)) / 2;
		// Determine the Y coordinate for the text
		int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		// Draw the String
		g.drawString(text, x + location.x + rect.x, y + location.y + rect.y);
	}
}
