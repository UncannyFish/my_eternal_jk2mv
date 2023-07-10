/************************************************************************************
 ** The MIT License (MIT)
 **
 ** Copyright (c) 2017 EXL
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

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import org.libsdl.app.SDLActivity;

public class JK2MVActivity extends SDLActivity {

	public static boolean onMyTouchEvent(MotionEvent event) {
		return mSurface.onTouchEvent(event);
	}

	@Override
	protected String[] getLibraries() {
		return new String[]{
			"SDL2",
			"openal",
			"GL",
			"jk2mvmp"
		};
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		JK2MVTouchOverlayView view = new JK2MVTouchOverlayView(this);
		view.setBackground(getResources().getDrawable(R.drawable.overlay_controls));
		addContentView(view, new LinearLayout.LayoutParams(
			LayoutParams.MATCH_PARENT,
			LayoutParams.MATCH_PARENT));
	}
	public static native void toggleKeyboard();
}
