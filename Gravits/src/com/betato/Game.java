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
	FileIO fio = new FileIO();

	boolean running = false; // Simulator steps only while running
	boolean fullscreen; // If frame is fullscreen
	boolean showBodyPositions; // If indicators are to be shown on bodies
	Point center = new Point(); // Simulator center, chosen by user panning
	Point lastCenter = new Point(); // Last center, used to get current center
	Point newBodyVel = new Point(); // Velocity of new body, chosen by arrow
	Vec2d newBodyVec = new Vec2d(); // Velocity of new body as vector, scaled to simulator
	int camMode = 1; // Camera mode of the simulator, eg. pan, tracking

	private static final int VELOCITY_SCALE = 100; // Multiplier to get velocity from user input arrow
	private static final String SAVE_DIRECTORY = "saves/"; // Where save files are stored
	Body newBody = new Body(0, 0, new Vec2d(0, 0), new Vec2d(0, 0), new Vec2d(
			0, 0)); // Body added with the newBody panel
	
	// Input panels
	InputPanel newBodyBox = new InputPanel("New Body", 180, new String[] {
			"Mass (kg)", "Radius (m)", "X Velocity (m/s)", "Y Velocity (m/s)" }, new String[] { "Cancel", "OK" },
			true, 14, new Point(0, 0), false);

	InputPanel saveBox = new InputPanel("Save Simulation", 280,
			new String[] { "File Name" }, new String[] { "Cancel", "Save" },
			false, 21, new Point(100, 100), true);

	InputPanel openBox = new InputPanel("Open Simulation", 320,
			new String[] { "File Name" }, new String[] { "Cancel", "Open" },
			false, 21, new Point(100, 100), true);

	public Game() {
		init(60, 120, "Gravits", new Dimension(800, 800), false, false);
	}

	@Override
	public void onInit() {
		sim.bodies.add(new Body(7.97E25, 6371000, new Vec2d(0, 0), new Vec2d(0,
				0), new Vec2d(1000, 0))); // Earth

		sim.bodies.add(new Body(7.35E22, 937000, new Vec2d(37400000, 0),
				new Vec2d(0, 0), new Vec2d(1000, -12000))); // Moon

		// Size renderer
		rn = new Renderer(getContentSize().getSize().height, getContentSize()
				.getSize().width, 6E6);
	}

	@Override
	public void onUpdate(KeyStates keys, MouseStates mouse, boolean resized) {
		// Exit on escape
		if (keys.keyReleases[KeyStates.ESCAPE]) {
			exit();
		}

		// Go fullscreen on f11
		if (keys.keyReleases[KeyStates.F11]) {
			// Space released
			fullscreen = !fullscreen;
			setFullscreen(fullscreen);
		}

		updateNewBodyBox(keys, mouse);
		updateSaveBox(keys, mouse);
		updateOpenBox(keys, mouse);
		updateRenderer(keys, mouse, resized);

		// Advance the simulator every update
		if (running && !newBodyBox.visible) {
			sim.step();
		}
	}

	private void updateRenderer(KeyStates keys, MouseStates mouse,
			boolean resized) {
		// Pause on space
		if (keys.keyReleases[KeyStates.SPACE]) {
			running = !running;
		}

		if (keys.keyReleases[KeyStates.V]) {
			showBodyPositions = !showBodyPositions;
		}

		// Switch camera on c
		if (keys.keyReleases[KeyStates.C]) {
			if (camMode >= 2) {
				camMode = 0;
			} else {
				camMode++;
			}
		}

		// Pan when clicked
		if (mouse.buttonStates[MouseStates.BUTTON_LEFT] && !newBodyBox.visible) {
			center.x += mouse.pos.x - lastCenter.x;
			center.y += mouse.pos.y - lastCenter.y;
		}

		// Resize the renderer if the window was resized
		if (resized) {
			rn.reSize(getContentSize().getSize().height, getContentSize()
					.getSize().width);
		}

		// Recalibrate renderer based on mouse input
		lastCenter.setLocation(mouse.pos);
		rn.reScale(7E5 + mouse.wheel * 10000);
	}

	private void updateNewBodyBox(KeyStates keys, MouseStates mouse) {
		// Show box on right click
		if (mouse.buttonReleases[MouseStates.BUTTON_RIGHT]) {
			newBodyBox.boxLocation.setLocation(mouse.pos);
			newBodyVel.setLocation(mouse.pos);;
			newBodyBox.visible = true;
		}

		// Update new body display if visible
		if (newBodyBox.visible) {
			// Set velocity vector on left control click
			if (mouse.buttonStates[MouseStates.BUTTON_LEFT]) {
				if (keys.keyStates[KeyStates.CTRL]) {
					// Update chosen velocity to vector
					newBodyVel = mouse.pos;
					newBodyArrowToVec();
				}
			}
			// Set velocity vector on scroll click
			if (mouse.buttonStates[MouseStates.BUTTON_MIDDLE]) {
				// Update chosen velocity to vector
				newBodyVel = mouse.pos;
				newBodyArrowToVec();
			}

			// Set vector and arrow with box values
			if (newBodyBox.selectedBox == 2 || newBodyBox.selectedBox == 3) {
				// Update chosen values to vector and arrow
				newBodyVecToArrow();
			}
			
			// Update display body
			newBodyBox.update(keys, mouse);
			newBody.mass = newBodyBox.getDouble(0);
			newBody.radius = newBodyBox.getDouble(1);
			newBody.position = rn.pointToSim(newBodyBox.boxLocation);
		}

		// Cancel and clear if cancel button clicked
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

				sim.bodies.add(new Body(newBodyBox.getDouble(0), newBodyBox
						.getDouble(1), rn.pointToSim(newBodyBox.boxLocation),
						new Vec2d(newBodyVec)));

				newBodyBox.visible = false;
				newBodyBox.clearPanel();
				newBody.clear();
			}
		}
	}

	private void newBodyArrowToVec() {
		// Update velocity vector
		newBodyVec.set((newBodyVel.x - newBodyBox.boxLocation.x) * VELOCITY_SCALE,
				(newBodyVel.y - newBodyBox.boxLocation.y) * VELOCITY_SCALE);
		// Update display
		newBodyBox.text[2] = String.valueOf((int)newBodyVec.x);
		newBodyBox.text[3] = String.valueOf((int)newBodyVec.y);
	}
	
	private void newBodyVecToArrow() {
		// Update vector
		newBodyVec = newBodyVec.set(newBodyBox.getDouble(2), newBodyBox.getDouble(3));
		// Update arrow
		newBodyVel.x = (int) ((newBodyVec.x / VELOCITY_SCALE) + newBodyBox.boxLocation.x);
		newBodyVel.y = (int) ((newBodyVec.y / VELOCITY_SCALE) + newBodyBox.boxLocation.y);
	}
	
	private void updateSaveBox(KeyStates keys, MouseStates mouse) {
		// Update saveBox if visible
		if (saveBox.visible) {
			saveBox.update(keys, mouse);
		}

		// Cancel if cancel clicked
		if (saveBox.selectedButton == 0) {
			saveBox.clearPanel();
			saveBox.visible = false;
		}

		// Save if save clicked
		if (saveBox.selectedButton == 1) {
			if (saveBox.text[0] != "") {
				fio.write(sim.bodies, new File(SAVE_DIRECTORY + saveBox.text[0]
						+ ".SAV"));
				saveBox.visible = false;
				saveBox.clearPanel();
			} else {
				// No save
				saveBox.selectedBox = -1;
			}
		}
		
		// Open save box on control + S
		if (keys.keyReleases[KeyStates.S] && keys.keyStates[KeyStates.CTRL]) {
			saveBox.visible = true;
		}
	}

	private void updateOpenBox(KeyStates keys, MouseStates mouse) {
		// Open on control + O
		if (keys.keyReleases[KeyStates.O] && keys.keyStates[KeyStates.CTRL]) {
			ArrayList<String> files = fio.getFiles(SAVE_DIRECTORY, "sav");
			if (files == null) {
				// Display no files message
				openBox.reformatBox("Open File",
						new String[] { "No Files Found" }, new String[] {
								"Cancel"});
			} else {
				String[] headings = new String[files.size()];
				for (int i = 0; i < headings.length; i++) {
					headings[i] = "File " + (i + 1);
				}
				// Display all files
				openBox.reformatBox("Open File", headings, new String[] {
						"Cancel", "Open" });
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
			sim.bodies = new ArrayList<Body>(fio.read(SAVE_DIRECTORY
					+ openBox.text[openBox.selectedBox]));
			openBox.visible = false;
			openBox.clearPanel();
		}
		// Update selection
		if (openBox.visible) {
			openBox.updateSelection(mouse);
		}
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
		g.drawImage(rn.frame(sim, center, newBodyBox.visible ? 0 : camMode,
				showBodyPositions), 0, 0, null);

		// Draw interface components
		g.setColor(Color.black);
		if (newBodyBox.visible) {
			rn.drawBody(g, newBody.position.x, newBody.position.y,
					newBody.radius);

			InterfaceRenderer.drawArrow(g, newBodyVel.x, newBodyVel.y,
					newBodyBox.boxLocation.x, newBodyBox.boxLocation.y, 10, 10,
					3);
		}
		newBodyBox.drawPanel(g);
		saveBox.drawPanel(g);
		openBox.drawPanel(g);

		// Draw info text
		InterfaceRenderer.text(g, message);

		// Reset mouse input positions
		center.setLocation(0, 0);
	}

	@Override
	public void onExit() {
		// I don't really need to clean anything up on exit
	}
}
