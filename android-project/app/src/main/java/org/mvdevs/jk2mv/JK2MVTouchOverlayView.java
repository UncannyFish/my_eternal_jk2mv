/************************************************************************************
 ** The MIT License (MIT)
 **
 ** Copyright (c) 2015-2017 EXL
 ** Copyright (c) 2023 Ilya Shabalin
 **
 ** Permission is hereby granted, free of charge, to any person obtaining a copy
 ** of this software and associated documentation files (the "Software"), to deal
 ** in the Software without restriction, including without limitation the rights
 ** to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 ** copies of the Software, and to permit persons to whom the Software is
 ** furnished to do so, subject to the following conditions:
 **
 ** The above copyright notice and this permission notice shall be included in all
 ** copies or substantial portions of the Software.
 **
 ** THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 ** IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 ** FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 ** AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 ** LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 ** OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 ** SOFTWARE.
 ************************************************************************************/

package org.mvdevs.jk2mv;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class JK2MVTouchOverlayView extends View {
	private List<JK2MVButton> initializedButtons = null;

	public JK2MVTouchOverlayView(Context context) {
		super(context);
		initButtonsRects();
	}

	// Touch events
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int touchId = event.getPointerCount() - 1;
		if (touchId < 0) {
			return false;
		}

		float touchX = event.getX(touchId) / getWidth();
		float touchY = event.getY(touchId) / getHeight();

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				checkTouchButtons(touchX, touchY, touchId);
				pressSingleTouchButtons();
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				checkTouchButtons(touchX, touchY, touchId);
				pressMultiTouchButtons();
				break;
			case MotionEvent.ACTION_POINTER_UP:
				checkTouchButtons(touchX, touchY, touchId);
				releaseMultiTouchButtons(touchId);
				break;
			case MotionEvent.ACTION_UP:
				releaseAllButtons();
				break;
			default:
				break;
		}

		//return JK2MVActivity.onMyTouchEvent(event);

		return true;
	}

	public void checkTouchButtons(float touchX, float touchY, int touchId) {
		for (JK2MVButton button : initializedButtons) {
			if (button.checkButtonRect(touchX, touchY)) {
				button.setTouchId(touchId);
			}
		}
	}

	public void pressSingleTouchButtons() {
		for (JK2MVButton button : initializedButtons) {
			if (button.getTouchId() == 0) {
				button.press();
			}
		}
	}

	public void pressMultiTouchButtons() {
		for (JK2MVButton button : initializedButtons) {
			if (button.getTouchId() > 0 && !button.getState()) {
				button.press();
			}
		}
	}

	public void releaseMultiTouchButtons(int touchId) {
		for (JK2MVButton button : initializedButtons) {
			if (button.getTouchId() == touchId) {
				button.release();
			}
		}
	}

	public void releaseAllButtons() {
		for (JK2MVButton button : initializedButtons) {
			button.release();
			button.setTouchId(-1);
		}
	}

	private void initButtonsRects() {
		/************************************************************************************
		 **     +------------------------------------------------+
		 **     |    overlay (overlay_width x overlay_height)    |
		 **     |                                                |
		 **     |                                                |
		 **     |    btn_x, btn_y =>  +--------+                 |
		 **     |                     | button |                 |
		 **     |                     |        |                 |
		 **     |                     |        |                 |
		 **     |                     +--------+ <= btn_w, btn_h |
		 **     |                                                |
		 **     +------------------------------------------------+
		 **
		 **     btn_x and btn_y is coordinates of start point of button on an overlay
		 **     btn_w and btn_h is coordinates of end point of button on an overlay
		 **
		 **     float x = btn_x / overlay_width;
		 **     float y = btn_y / overlay_height;
		 **     float width = btn_w / overlay_width;
		 **     float height = btn_h / overlay_height;
		 **
		 **     Example for 854x480 overlay:
		 **     float x = 125.0 / 854.0;
		 **     float y = 455.0 / 480.0;
		 **     float width = 200.0 / 854.0;
		 **     float height = 475.0 / 480.0;
		 ************************************************************************************/

		initializedButtons = new ArrayList<JK2MVButton>();
		//initializedButtons.add(new JK2MVButton("Left", 0.0421f, 0.6583f, 0.1569f, 0.2792f, KeyEvent.KEYCODE_DPAD_LEFT));
		//initializedButtons.add(new JK2MVButton("Down", 0.2295f, 0.6583f, 0.1569f, 0.2792f, KeyEvent.KEYCODE_DPAD_DOWN));
		//initializedButtons.add(new JK2MVButton("Right", 0.4180f, 0.6583f, 0.1569f, 0.2792f, KeyEvent.KEYCODE_DPAD_RIGHT));
		//initializedButtons.add(new JK2MVButton("Up", 0.2295f, 0.3250f, 0.1569f, 0.2792f, KeyEvent.KEYCODE_DPAD_UP));
		//initializedButtons.add(new JK2MVButton("A", 0.8079f, 0.3250f, 0.1569f, 0.2792f, KeyEvent.KEYCODE_A));
		//initializedButtons.add(new JK2MVButton("Space", 0.8079f, 0.6604f, 0.1569f, 0.2792f, KeyEvent.KEYCODE_SPACE));
		//initializedButtons.add(new JK2MVButton("Enter", 0.5503f, 0.0333f, 0.1218f, 0.2166f, KeyEvent.KEYCODE_ENTER));
		//initializedButtons.add(new JK2MVButton("S", 0.7025f, 0.0333f, 0.1218f, 0.2166f, KeyEvent.KEYCODE_S));
		//initializedButtons.add(new JK2MVButton("D", 0.8548f, 0.0333f, 0.1218f, 0.2166f, KeyEvent.KEYCODE_D));
		initializedButtons.add(new JK2MVButton("+attack", 0.7949f, 0.4681f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_CTRL_LEFT));
		initializedButtons.add(new JK2MVButton("+altattack", 0.9049f, 0.4681f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_ALT_LEFT));
		initializedButtons.add(new JK2MVButton("+back", 0.0928f, 0.8347f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_S));
		initializedButtons.add(new JK2MVButton("+forward", 0.0928f, 0.5514f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_W));
		initializedButtons.add(new JK2MVButton("+movedown", 0.9237f, 0.8583f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_C));
		initializedButtons.add(new JK2MVButton("+moveleft", 0.0180f, 0.6958f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_A));
		initializedButtons.add(new JK2MVButton("+moveright", 0.1677f, 0.6958f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_D));
		initializedButtons.add(new JK2MVButton("+moveup", 0.9237f, 0.1667f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_SPACE));
		initializedButtons.add(new JK2MVButton("+scores", 0.2425f, 0.0028f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_TAB));
		initializedButtons.add(new JK2MVButton("+speed", 0.0928f, 0.6958f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_SHIFT_LEFT));
		initializedButtons.add(new JK2MVButton("+use", 0.6168f, 0.0028f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_E));
		initializedButtons.add(new JK2MVButton("+useforce", 0.8862f, 0.3153f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_F));
		initializedButtons.add(new JK2MVButton("+button2", 0.8024f, 0.3153f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_ENTER));
		//initializedButtons.add(new JK2MVButton("cg_thirdperson !", 0.0f, 0.0f, 0.0f, 0.0f, KeyEvent.KEYCODE_P));
		initializedButtons.add(new JK2MVButton("engage_duel", 0.5000f, 0.0028f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_K));
		initializedButtons.add(new JK2MVButton("forcenext", 0.6168f, 0.8583f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_X));
		initializedButtons.add(new JK2MVButton("forceprev", 0.3129f, 0.8583f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_Z));
		initializedButtons.add(new JK2MVButton("invnext", 0.0015f, 0.1847f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_RIGHT_BRACKET));
		initializedButtons.add(new JK2MVButton("invprev", 0.0015f, 0.3986f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_LEFT_BRACKET));
		initializedButtons.add(new JK2MVButton("messagemode", 0.0816f, 0.0028f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_Y));
		//initializedButtons.add(new JK2MVButton("messagemode2", 0.0f, 0.0f, 0.0f, 0.0f, KeyEvent.KEYCODE_T));
		//initializedButtons.add(new JK2MVButton("messagemode3", 0.0f, 0.0f, 0.0f, 0.0f, KeyEvent.KEYCODE_U));
		//initializedButtons.add(new JK2MVButton("messagemode4", 0.0f, 0.0f, 0.0f, 0.0f, KeyEvent.KEYCODE_I));
		initializedButtons.add(new JK2MVButton("saberAttackCycle", 0.1609f, 0.0028f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_L));
		//initializedButtons.add(new JK2MVButton("scoresDown", 0.0f, 0.0f, 0.0f, 0.0f, KeyEvent.KEYCODE_FORWARD_DEL));
		//initializedButtons.add(new JK2MVButton("scoresUp", 0.0f, 0.0f, 0.0f, 0.0f, KeyEvent.KEYCODE_INSERT));
		initializedButtons.add(new JK2MVButton("toggleconsole", 0.9237f, 0.0028f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_GRAVE));
		initializedButtons.add(new JK2MVButton("weapnext", 0.6168f, 0.7056f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_R));
		initializedButtons.add(new JK2MVButton("weapprev", 0.3129f, 0.7056f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_Q));
		initializedButtons.add(new JK2MVButton("escape", 0.0015f, 0.0028f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_ESCAPE));
		initializedButtons.add(new JK2MVButton("showKeyboard", 0.3443f, 0.0028f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_M));

		initializedButtons.add(new JK2MVButton("diagonalFL", 0.0180f, 0.5514f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_W));
		initializedButtons.add(new JK2MVButton("diagonalFL2", 0.0180f, 0.5514f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_A));

		initializedButtons.add(new JK2MVButton("diagonalFR", 0.1677f, 0.5514f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_W));
		initializedButtons.add(new JK2MVButton("diagonalFR2", 0.1677f, 0.5514f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_D));

		initializedButtons.add(new JK2MVButton("diagonalBL", 0.0180f, 0.8347f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_S));
		initializedButtons.add(new JK2MVButton("diagonalBL2", 0.0180f, 0.8347f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_A));

		initializedButtons.add(new JK2MVButton("diagonalBR", 0.1677f, 0.8347f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_S));
		initializedButtons.add(new JK2MVButton("diagonalBR2", 0.1677f, 0.8347f, 0.0749f, 0.1389f, KeyEvent.KEYCODE_D));
	}

	private class JK2MVButton {
		private final float m_x0;
		private final float m_y0;
		private final float m_x1;
		private final float m_y1;

		private final int m_buttonCode;
		// Useful for DEBUG
		private final String m_buttonName;
		private boolean m_buttonPushed = false;
		// -1 for no touches on button
		private int m_buttonTouchId = -1;

		public JK2MVButton(String buttonName, float x, float y, float width, float height, int keyCode) {
			m_buttonName = buttonName;
			m_x0 = x;
			m_y0 = y;
			m_x1 = x + width;
			m_y1 = y + height;
			m_buttonCode = keyCode;
		}

		public boolean checkButtonRect(float touchX, float touchY) {
			return (touchX > m_x0 && touchX < m_x1 && touchY > m_y0 && touchY < m_y1);
		}

		public void press() {
			m_buttonPushed = true;

			if (m_buttonCode == KeyEvent.KEYCODE_M) {
				JK2MVActivity.showTextInput(0, 0, 640, 480);
			}

			if (m_buttonCode == KeyEvent.KEYCODE_ESCAPE) {
				JK2MVActivity.onNativeKeyDown(KeyEvent.KEYCODE_BACK);
			}

			JK2MVActivity.onNativeKeyDown(m_buttonCode);
		}

		public void release() {
			m_buttonPushed = false;

			m_buttonTouchId = -1;

			JK2MVActivity.onNativeKeyUp(m_buttonCode);
		}

		@SuppressWarnings("unused")
		public String getName() {
			return m_buttonName;
		}

		public int getTouchId() {
			return m_buttonTouchId;
		}

		public void setTouchId(int touchId) {
			m_buttonTouchId = touchId;
		}

		public boolean getState() {
			return m_buttonPushed;
		}
	}
}
