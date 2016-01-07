package com.betato;

public class Gameloop {
	int targetFps;
	int nanoFps;
	int targetUps;
	int nanoUps;

	Display dsp = new Display(targetFps);
	
	public Gameloop(int targetFps, int targetUps) {
		this.targetFps = targetFps;
		this.targetFps = targetUps;
		nanoFps = 1000000000 / targetFps;
		nanoUps = 1000000000 / targetUps;
	}

	public void run() {
		long startTime = System.nanoTime();
		long deltaFps = 0;
		long deltaUps = 0;
		long deltaDisplay = 0;
		int framecount = 0;
		int updatecount = 0;

		while (true) {
			// Get current time
			long currentTime = System.nanoTime();
			// Get time since last loop
			deltaFps += currentTime - startTime;
			deltaUps += currentTime - startTime;
			deltaDisplay += currentTime - startTime;

			// Set start time of this loop for use in next cycle
			startTime = currentTime;

			// Render if target time has been reached
			if (deltaFps >= nanoFps) {
				// Render
				dsp.repaint();
				framecount++;
				deltaFps = 0;
			}

			// Update if target time has been reached
			if (deltaUps >= nanoUps) {
				// Update
				dsp.update();
				updatecount++;
				deltaUps = 0;
			}

			// Update fps display if one second has passed
			if (deltaDisplay >= 1000000000) {
				System.out.println("FPS: " + framecount);
				System.out.println("UPS: " + updatecount);
				framecount = 0;
				updatecount = 0;
				deltaDisplay = 0;
			}
		}
	}
}
