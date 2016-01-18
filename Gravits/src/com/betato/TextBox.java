package com.betato;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.betato.gameDisplay.KeyStates;
import com.betato.gameDisplay.MouseStates;

public class TextBox {

	public TextBox(String title, int width, String[] headings, int buttons) {
		this.title = title;
		this.headings = headings;
		text = new String[headings.length];
		// this.buttons = buttons;
		boxes = new Rectangle[headings.length];
		this.buttons = new Rectangle[2];

		this.width = width;

		frame = new Dimension(width + (OFFSET * 2), (ROW_HEIGHT * headings.length * 2) + (OFFSET * 2));
		for (int i = 0; i < headings.length; i++) {
			boxes[i] = new Rectangle(OFFSET, OFFSET + ROW_HEIGHT + (i * ROW_HEIGHT * 2), width, ROW_HEIGHT);
			text[i] = "";
		}
	}

	public static final int NO_BUTTONS = 0;
	public static final int OK_BUTTON = 1;
	public static final int OK_CANCEL_BUTTONS = 2;
	public static final int YES_NO_BUTTONS = 2;

	private static final int ROW_HEIGHT = 30;
	private static final int OFFSET = 10;
	private static final int TEXT_ELEVATION = 8;

	// private Point boxPos = new Point(0, 0);

	private int width;
	private String title;
	private String[] headings;
	private Dimension frame;
	private Rectangle[] boxes;
	private Rectangle[] buttons;

	public String[] text;
	// public int buttons = 0;
	public int state = 0;

	public void update(KeyStates keys, MouseStates mouse, Point pos) {
		// zero mouse to TextBox position
		mouse.pos.x -= pos.x;
		mouse.pos.y -= pos.y;
		// System.out.println((mouse.pos.x - pos.x) + ", " + (mouse.pos.y -
		// pos.y));
		// Check for clicks on boxes
		if (mouse.pos.x > OFFSET && mouse.pos.x < width + OFFSET) {
			for (int i = 0; i < headings.length; i++) {
				if (mouse.pos.y > boxes[i].y && mouse.pos.y < boxes[i].y + ROW_HEIGHT) {
					System.out.println("box: " + boxes[i].y + ", " + boxes[i].y);
					System.out.println("mouse: " + mouse.pos.x + ", " + mouse.pos.y);
					if (keys.keyReleases[i]) {
						text[i] += keys.keyMap.get(i);
					}
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

		g.setColor(Color.white);
		for (int i = 0; i < headings.length; i++) {
			g.fillRect(pos.x + OFFSET, pos.y + boxes[i].y, boxes[i].width, boxes[i].height);
		}

		// Draw text
		g.setColor(Color.black);
		for (int i = 0; i < headings.length; i++) {
			g.drawString(headings[i], pos.x + OFFSET, boxes[i].y + pos.y - TEXT_ELEVATION);
			g.drawString(text[i], pos.x + (OFFSET * 2), pos.y + ROW_HEIGHT + boxes[i].y - TEXT_ELEVATION);
		}
	}
}
