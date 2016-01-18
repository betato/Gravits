package com.betato.gameDisplay;

import java.util.HashMap;

/**
 * The {@code KeyStates} class stores key states
 */
public class KeyStates {
	public static final int NUM_KEYS = 223;
	private static final int NUM_LOOPS = NUM_KEYS - 1;

	public boolean[] keyStates = new boolean[NUM_KEYS];
	public boolean[] keyPresses = new boolean[NUM_KEYS];
	public boolean[] keyReleases = new boolean[NUM_KEYS];
	private boolean[] lastState = new boolean[NUM_KEYS];

	public void update() {
		// Check for all changed keys
		for (int i = 0; i <= NUM_LOOPS; i++) {
			if (keyStates[i] != lastState[i]) {
				// Key has been changed
				if (keyStates[i]) {
					// Key is down
					keyPresses[i] = true;
				} else {
					// Key is up
					keyReleases[i] = true;
				}
			} else {
				// Key not changed, reset states
				keyPresses[i] = false;
				keyReleases[i] = false;
			}
		}
		// Save key states
		System.arraycopy(keyStates, 0, lastState, 0, NUM_KEYS);
	}

	public HashMap<Integer, String> keyMap = new HashMap<Integer, String>();

	public KeyStates() {
		keyMap.put(8, "BACKSPACE");
		keyMap.put(9, "TAB");
		keyMap.put(13, "ENTER");
		keyMap.put(16, "SHIFT");
		keyMap.put(17, "CTRL");
		keyMap.put(18, "ALT");
		keyMap.put(19, "PAUSE_BREAK");
		keyMap.put(20, "LOCK_CAPS");
		keyMap.put(27, "ESCAPE");
		keyMap.put(33, "PAGE_UP");
		keyMap.put(34, "PAGE_DOWN");
		keyMap.put(35, "END");
		keyMap.put(36, "HOME");
		keyMap.put(37, "ARROW_LEFT");
		keyMap.put(38, "ARROW_UP");
		keyMap.put(39, "ARROW_RIGHT");
		keyMap.put(40, "ARROW_DOWN");
		keyMap.put(45, "INSERT");
		keyMap.put(46, "DELETE");
		keyMap.put(48, "NUMROW_0");
		keyMap.put(49, "NUMROW_1");
		keyMap.put(50, "NUMROW_2");
		keyMap.put(51, "NUMROW_3");
		keyMap.put(52, "NUMROW_4");
		keyMap.put(53, "NUMROW_5");
		keyMap.put(54, "NUMROW_6");
		keyMap.put(55, "NUMROW_7");
		keyMap.put(56, "NUMROW_8");
		keyMap.put(57, "NUMROW_9");
		keyMap.put(65, "A");
		keyMap.put(66, "B");
		keyMap.put(67, "C");
		keyMap.put(68, "D");
		keyMap.put(69, "E");
		keyMap.put(70, "F");
		keyMap.put(71, "G");
		keyMap.put(72, "H");
		keyMap.put(73, "I");
		keyMap.put(74, "J");
		keyMap.put(75, "K");
		keyMap.put(76, "L");
		keyMap.put(77, "M");
		keyMap.put(78, "N");
		keyMap.put(79, "O");
		keyMap.put(80, "P");
		keyMap.put(81, "Q");
		keyMap.put(82, "R");
		keyMap.put(83, "S");
		keyMap.put(84, "T");
		keyMap.put(85, "U");
		keyMap.put(86, "V");
		keyMap.put(87, "W");
		keyMap.put(88, "X");
		keyMap.put(89, "Y");
		keyMap.put(90, "Z");
		keyMap.put(91, "WINDOWS_KEY_LEFT");
		keyMap.put(92, "WINDOWS_KEY_RIGHT");
		keyMap.put(93, "SELECT_KEY");
		keyMap.put(96, "NUMPAD_0");
		keyMap.put(97, "NUMPAD_1");
		keyMap.put(98, "NUMPAD_2");
		keyMap.put(99, "NUMPAD_3");
		keyMap.put(100, "NUMPAD_4");
		keyMap.put(101, "NUMPAD_5");
		keyMap.put(102, "NUMPAD_6");
		keyMap.put(103, "NUMPAD_7");
		keyMap.put(104, "NUMPAD_8");
		keyMap.put(105, "NUMPAD_9");
		keyMap.put(106, "MULTIPLY");
		keyMap.put(107, "ADD");
		keyMap.put(109, "SUBTRACT");
		keyMap.put(110, "DECIMAL_POINT");
		keyMap.put(111, "DIVIDE");
		keyMap.put(112, "F1");
		keyMap.put(113, "F2");
		keyMap.put(114, "F3");
		keyMap.put(115, "F4");
		keyMap.put(116, "F5");
		keyMap.put(117, "F6");
		keyMap.put(118, "F7");
		keyMap.put(119, "F8");
		keyMap.put(120, "F9");
		keyMap.put(121, "F10");
		keyMap.put(122, "F11");
		keyMap.put(123, "F12");
		keyMap.put(144, "LOCK_NUM");
		keyMap.put(145, "LOCK_SCROLL");
		keyMap.put(186, "SEMICOLON");
		keyMap.put(187, "EQUALS");
		keyMap.put(188, "COMMA");
		keyMap.put(189, "DASH");
		keyMap.put(190, "PERIOD");
		keyMap.put(191, "FORWARD_SLASH");
		keyMap.put(192, "GRAVE_ACCENT");
		keyMap.put(219, "BRACKET_OPEN");
		keyMap.put(220, "BACKSLASH");
		keyMap.put(221, "BRAKET_CLOSE");
		keyMap.put(222, "SINGLE_QUOTE");
	}

	public static final int BACKSPACE = 8;
	public static final int TAB = 9;
	public static final int ENTER = 13;
	public static final int SHIFT = 16;
	public static final int CTRL = 17;
	public static final int ALT = 18;
	public static final int PAUSE_BREAK = 19;
	public static final int LOCK_CAPS = 20;
	public static final int ESCAPE = 27;
	public static final int SPACE = 32;
	public static final int PAGE_UP = 33;
	public static final int PAGE_DOWN = 34;
	public static final int END = 35;
	public static final int HOME = 36;
	public static final int ARROW_LEFT = 37;
	public static final int ARROW_UP = 38;
	public static final int ARROW_RIGHT = 39;
	public static final int ARROW_DOWN = 40;
	public static final int INSERT = 45;
	public static final int DELETE = 46;
	public static final int NUMROW_0 = 48;
	public static final int NUMROW_1 = 49;
	public static final int NUMROW_2 = 50;
	public static final int NUMROW_3 = 51;
	public static final int NUMROW_4 = 52;
	public static final int NUMROW_5 = 53;
	public static final int NUMROW_6 = 54;
	public static final int NUMROW_7 = 55;
	public static final int NUMROW_8 = 56;
	public static final int NUMROW_9 = 57;
	public static final int A = 65;
	public static final int B = 66;
	public static final int C = 67;
	public static final int D = 68;
	public static final int E = 69;
	public static final int F = 70;
	public static final int G = 71;
	public static final int H = 72;
	public static final int I = 73;
	public static final int J = 74;
	public static final int K = 75;
	public static final int L = 76;
	public static final int M = 77;
	public static final int N = 78;
	public static final int O = 79;
	public static final int P = 80;
	public static final int Q = 81;
	public static final int R = 82;
	public static final int S = 83;
	public static final int T = 84;
	public static final int U = 85;
	public static final int V = 86;
	public static final int W = 87;
	public static final int X = 88;
	public static final int Y = 89;
	public static final int Z = 90;
	public static final int WINDOWS_KEY_LEFT = 91;
	public static final int WINDOWS_KEY_RIGHT = 92;
	public static final int SELECT_KEY = 93;
	public static final int NUMPAD_0 = 96;
	public static final int NUMPAD_1 = 97;
	public static final int NUMPAD_2 = 98;
	public static final int NUMPAD_3 = 99;
	public static final int NUMPAD_4 = 100;
	public static final int NUMPAD_5 = 101;
	public static final int NUMPAD_6 = 102;
	public static final int NUMPAD_7 = 103;
	public static final int NUMPAD_8 = 104;
	public static final int NUMPAD_9 = 105;
	public static final int MULTIPLY = 106;
	public static final int ADD = 107;
	public static final int SUBTRACT = 109;
	public static final int DECIMAL_POINT = 110;
	public static final int DIVIDE = 111;
	public static final int F1 = 112;
	public static final int F2 = 113;
	public static final int F3 = 114;
	public static final int F4 = 115;
	public static final int F5 = 116;
	public static final int F6 = 117;
	public static final int F7 = 118;
	public static final int F8 = 119;
	public static final int F9 = 120;
	public static final int F10 = 121;
	public static final int F11 = 122;
	public static final int F12 = 123;
	public static final int LOCK_NUM = 144;
	public static final int LOCK_SCROLL = 145;
	public static final int SEMICOLON = 186;
	public static final int EQUALS = 187;
	public static final int COMMA = 188;
	public static final int DASH = 189;
	public static final int PERIOD = 190;
	public static final int FORWARD_SLASH = 191;
	public static final int GRAVE_ACCENT = 192;
	public static final int BRACKET_OPEN = 219;
	public static final int BACKSLASH = 220;
	public static final int BRAKET_CLOSE = 221;
	public static final int SINGLE_QUOTE = 222;

}
