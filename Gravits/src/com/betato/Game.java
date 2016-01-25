package com.betato;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;

import com.betato.gameDisplay.GameWindow;
import com.betato.gameDisplay.InputPanel;
import com.betato.gameDisplay.KeyStates;
import com.betato.gameDisplay.MouseStates;

public class Game extends GameWindow {

	Simulator sim = new Simulator(6.67384E-11, 60);
	Renderer rn;

	boolean running = false;
	boolean fullscreen;
	boolean showBodyPositions;
	Point center = new Point(0, 0);
	Point lastCenter;
	Point newBodyVel;
	Vec2d newBodyVec = new Vec2d();
	int camMode = 1;

	FileIO fio = new FileIO();

	Body newBody = new Body(0, 0, new Vec2d(0, 0), new Vec2d(0, 0), new Vec2d(0, 0));
	InputPanel newBodyBox = new InputPanel("New Body", 180, new String[] { "Mass", "Radius", "Velocity" },
			new String[] { "Cancel", "OK" }, true, 14, new Point(0, 0), false);

	InputPanel saveBox = new InputPanel("Save Simulation", 280, new String[] { "File Name" },
			new String[] { "Cancel", "Save" }, false, 28, new Point(100, 100), true);

	InputPanel openBox = new InputPanel("Open Simulation", 280, new String[] { "File Name" },
			new String[] { "Cancel", "Open" }, false, 28, new Point(100, 100), true);;

	public Game() {
		init(60, 120, "Gravits", new Dimension(800, 800), false, false);
	}

	@Override
	public void onInit() {
		sim.bodies.add(new Body(7.97E25, 6371000, new Vec2d(0, 0), new Vec2d(0, 0), new Vec2d(1000, 0))); // Earth

		sim.bodies.add(new Body(7.35E22, 937000, new Vec2d(37400000, 0), new Vec2d(0, 0), new Vec2d(1000, -12000))); // Moon

		// Size renderer
		rn = new Renderer(getContentSize().getSize().height, getContentSize().getSize().width, 6E6);
	}

	@Override
	public void onUpdate(KeyStates keys, MouseStates mouse, boolean resized) {
		// Exit on escape
		if (keys.keyReleases[KeyStates.ESCAPE]) {
			exit();
		}

		// Save on control + S
		if (keys.keyReleases[KeyStates.S] && keys.keyStates[KeyStates.CTRL]) {
			saveBox.visible = true;
		}

		// Open on control + O
		if (keys.keyReleases[KeyStates.O] && keys.keyStates[KeyStates.CTRL]) {
			ArrayList<String> files = fio.getFiles("C:\\Users\\Liam\\git\\Gravits\\Gravits", "sav");
			String[] headings = new String[files.size()];
			for (int i = 0; i < headings.length; i++) {
				headings[i] = "File " + (i + 1);
			}
			if (files.isEmpty()) {
				// Display message
				openBox.reformatBox("Open File", new String[] { "No Files Found" }, new String[] { "Cancel", "Open"});
			} else {
				// Display all files
				openBox.reformatBox("Open File", headings,
						new String[] { "Cancel", "Open" });
				openBox.text = files.toArray(new String[files.size()]);
			}
			openBox.visible = true;
		}

		// Close openBox
		if (openBox.selectedButton == 0) {
			openBox.visible = false;
			openBox.clearPanel();
		}
		// Open selected file
		if (openBox.selectedButton == 1) {
			sim.bodies = new ArrayList<Body>(fio.read(openBox.text[openBox.selectedBox]));
			openBox.visible = false;
			openBox.clearPanel();
		}

		// Update selection
		if (openBox.visible) {
			openBox.updateSelection(mouse);
		}

		// Pause on space
		if (keys.keyReleases[KeyStates.SPACE]) {
			running = !running;
		}

		if (keys.keyReleases[KeyStates.V]) {
			showBodyPositions = !showBodyPositions;
		}

		// Create body on right click
		if (mouse.buttonReleases[MouseStates.BUTTON_RIGHT]) {
			// Almost done now
			newBodyBox.boxLocation = mouse.pos;
			newBodyVel = mouse.pos;
			newBodyBox.visible = true;
		}

		// Cancel if cancel button clicked
		if (newBodyBox.selectedButton == 0) {
			// Hide and clear
			newBodyBox.visible = false;
			newBodyBox.clearPanel();
		}
		// Create body if OK clicked
		if (newBodyBox.selectedButton == 1) {
			newBody.mass = newBodyBox.getDouble(0);
			newBody.radius = newBodyBox.getDouble(1);
			newBody.position = rn.pointToSim(newBodyBox.boxLocation);
			if (newBody.isValid()) {

				sim.bodies.add(new Body(newBodyBox.getDouble(0), newBodyBox.getDouble(1),
						rn.pointToSim(newBodyBox.boxLocation), new Vec2d(newBodyVec.mul(100))));

				newBodyBox.visible = false;
				newBodyBox.clearPanel();
				newBody.clear();
			}
		}

		if (saveBox.visible) {
			saveBox.update(keys, mouse);
		}

		if (saveBox.selectedButton == 0) {
			// Cancel
			saveBox.clearPanel();
			saveBox.visible = false;
		}

		if (saveBox.selectedButton == 1) {
			// Save
			if (saveBox.text[0] != "") {
				fio.write(sim.bodies, new File(saveBox.text[0] + ".SAV"));
				saveBox.visible = false;
				saveBox.clearPanel();
			} else {
				// No save
				saveBox.selectedBox = -1;
			}
		}

		// Update new body display if visible
		if (newBodyBox.visible) {
			newBodyBox.update(keys, mouse);
			newBody.mass = newBodyBox.getDouble(0);
			newBody.radius = newBodyBox.getDouble(1);
			newBody.position = rn.pointToSim(newBodyBox.boxLocation);
			// Update velocity vector
			newBodyVec.set(newBodyVel.x - newBodyBox.boxLocation.x, newBodyVel.y - newBodyBox.boxLocation.y);
		}

		// Switch camera on c
		if (keys.keyReleases[KeyStates.C]) {
			if (camMode >= 2) {
				camMode = 0;
			} else {
				camMode++;
			}
		}

		// Go fullscreen on f11
		if (keys.keyReleases[KeyStates.F11]) {
			// Space released
			fullscreen = !fullscreen;
			setFullscreen(fullscreen);
		}

		// Pan when clicked
		if (mouse.buttonStates[MouseStates.BUTTON_LEFT]) {
			if (newBodyBox.visible) {
				if (keys.keyStates[KeyStates.CTRL]) {
					newBodyVel = mouse.pos;
				}
			} else {
				center.x += mouse.pos.x - lastCenter.x;
				center.y += mouse.pos.y - lastCenter.y;
			}
		}

		// Set velocity vector on scroll click
		if (mouse.buttonStates[MouseStates.BUTTON_MIDDLE]) {
			if (newBodyBox.visible) {
				newBodyVel = mouse.pos;
			}
		}

		// Advance the simulator every update
		if (running && !newBodyBox.visible) {
			sim.step();
		}

		// Resize the renderer if the window was resized
		if (resized) {
			rn.reSize(getContentSize().getSize().height, getContentSize().getSize().width);
		}

		// Recalibrate renderer based on mouse input
		lastCenter = mouse.pos;
		rn.reScale(7E5 + mouse.wheel * 10000);
	}

	@Override
	public void onRender(Graphics g, int fps, int ups) {

		// Info messages
		String message = "";
		switch (camMode) {
		case 0:
			message += "Camera: Static";
			break;
		case 1:
			message += "Camera: Pan";
			break;
		case 2:
			message += "Camera: Chase";
			break;
		}
		message += ",";
		if (!running) {
			message += "Paused";
		}

		// Draw bodies
		g.drawImage(rn.frame(sim, center, newBodyBox.visible ? 0 : camMode, showBodyPositions), 0, 0, null);

		// Draw interface components
		g.setColor(Color.black);
		if (newBodyBox.visible) {
			rn.drawBody(g, newBody.position.x, newBody.position.y, newBody.radius);

			InterfaceRenderer.drawArrow(g, newBodyVel.x, newBodyVel.y, newBodyBox.boxLocation.x,
					newBodyBox.boxLocation.y, 10, 10, 3);
		}
		newBodyBox.drawPanel(g);
		saveBox.drawPanel(g);
		openBox.drawPanel(g);

		// Draw info text
		InterfaceRenderer.text(g, message);

		// Reset positions
		center.x = 0;
		center.y = 0;
	}

	@Override
	public void onExit() {

	}
}
