package com.sdzx.xtbg.view.photoview;

import android.util.Log;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;
import com.sdzx.xtbg.view.MyViewPager;


public class RotateDownPageTransformer implements MyViewPager.PageTransformer {
	private static final float MIN_SCALE = 0.5f;
	private static final float MIN_ALPHA = 0.5f;
	private MyViewPager mViewPager;
	int pageWidth;
	int pageHeight;
	private View view;
	private float position;
	float scaleFactor;
	private pageTransFormer page = pageTransFormer.One;

	public  enum pageTransFormer {
		One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Elevent, Twelve, Thirteen, Fourteen
	}

	public RotateDownPageTransformer(MyViewPager mMyViewPager) {
		this.mViewPager = mMyViewPager;
	}

	public void setPageTransFormerAnimation(pageTransFormer page) {
		this.page = page;
	}

	@Override
	public void transformPage(View view, float position) {
		pageWidth = view.getWidth();
		pageHeight = view.getHeight();
		this.view = view;
		this.position = position;

		if (position < -1) { // [-Infinity,-1)
								// This page is way off-screen to the left.

			ViewHelper.setAlpha(view, 0);

		} else if (position <= 1) // aҳ������bҳ �� aҳ�� 0.0 -1 ��bҳ��1 ~ 0.0
		{ // [-1,1]
			// Modify the default slide transition to shrink the page as well
			scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));

			// Scale the page down (between MIN_SCALE and 1)
			Log.e("dsa", page + "");
			switch (page) {

			case One:
				animationOne();
				break;
			case Two:
				animationTwo();
				break;
			case Three:
				animationThree();
				break;
			case Four:
				animationFour();
				break;
			case Five:
				animationFive();
				break;

			case Six:
				animationSix();
				break;
			case Seven:
				animationSeven();
				break;
			case Eight:
				animationEight();
				break;
			case Nine:
				animationnine();
				break;
			case Ten:
				animationten();
				break;
			case Elevent:
				animationeleven();
				break;
			case Twelve:
				animationtwelve();
				break;
			case Thirteen:
				animationthirteen();
				break;
			case Fourteen:
				animationfourteen();
				break;
			default:
				// animationOne();
				break;
			}
			// Fade the page relative to its size.
			ViewHelper.setAlpha(view, MIN_ALPHA + (scaleFactor - MIN_SCALE)
					/ (1 - MIN_SCALE) * (1 - MIN_ALPHA));

		} else { // (1,+Infinity]
					// This page is way off-screen to the right.

			ViewHelper.setAlpha(view, 0);
		}
	}

	private void animationtwelve() {
		if (position < 0) {

			ViewHelper.setPivotX(view, pageWidth);
			ViewHelper.setPivotY(view, 0);

			ViewHelper.setRotationY(view, 90 * position);
		} else {

			ViewHelper.setPivotX(view, 0);
			ViewHelper.setPivotY(view, 0);
			ViewHelper.setRotationY(view, 90 * position);

		}
	}

	private void animationnine() {
		if (position < 0) {

			ViewHelper.setPivotX(view, pageWidth);
			ViewHelper.setPivotY(view, 0);

			ViewHelper.setRotationY(view, -90 * position);
		} else {

			ViewHelper.setPivotX(view, pageWidth);
			ViewHelper.setPivotY(view, 0);
			ViewHelper.setScrollX(view, (int) (-90 * position));
			ViewHelper.setRotation(view, 90 * position);
		}
	}

	private void animationten() {

		ViewHelper.setPivotX(view, pageWidth / 2);
		ViewHelper.setPivotY(view, pageHeight / 2);
		ViewHelper.setScaleX(view, 1 - Math.abs(position));
		ViewHelper.setScaleY(view, 1 - Math.abs(position));

	}

	private void animationeleven() {
		if (position < 0) {

			ViewHelper.setScrollY(view, (int) (pageWidth * position));
			ViewHelper.setScaleX(view, 1 - (-position));
			ViewHelper.setScaleY(view, 1 - (-position));
		} else {
			ViewHelper.setScrollY(view, (int) (pageWidth * position));
			ViewHelper.setScaleX(view, 1 - position);
			ViewHelper.setScaleY(view, 1 - position);
		}
	}

	public void animationOne() {
		ViewHelper.setScrollX(view, (int) (pageWidth * position));
	}

	public void animationTwo() {
		ViewHelper.setScrollY(view, (int) (pageHeight * (Math.abs(position))));
	}

	public void animationThree() {
		if (position < 0) {

			ViewHelper.setPivotX(view, pageHeight);
			ViewHelper.setPivotY(view, 0);

			ViewHelper.setRotationX(view, 90 * position);
		} else {

			ViewHelper.setPivotX(view, 0);
			ViewHelper.setPivotY(view, pageHeight);
			ViewHelper.setRotationX(view, 90 * position);
		}

	}

	public void animationFour() {
		if (position < 0) {

			ViewHelper.setPivotX(view, pageWidth);
			ViewHelper.setPivotY(view, 0);

			ViewHelper.setRotationY(view, -90 * position);
		} else {

			ViewHelper.setPivotX(view, 0);
			ViewHelper.setPivotY(view, pageHeight);
			ViewHelper.setRotationY(view, 90 * position);

		}

	}

	public void animationFive() {
		if (position < 0) {

			ViewHelper.setPivotX(view, 0);
			ViewHelper.setPivotY(view, pageWidth);

			ViewHelper.setRotationY(view, -90 * position);
		} else {

			ViewHelper.setPivotX(view, 0);
			ViewHelper.setPivotY(view, pageHeight);
			ViewHelper.setRotationY(view, 90 * position);

		}
	}

	public void animationSix() {
		if (position < 0) {

			ViewHelper.setPivotX(view, 0);
			ViewHelper.setPivotY(view, pageWidth);

			ViewHelper.setRotationY(view, 45 * position);
		} else {

			ViewHelper.setPivotX(view, pageHeight);
			ViewHelper.setPivotY(view, pageWidth);
			ViewHelper.setRotationY(view, 45 * position);

		}
	}

	public void animationSeven() {
		if (position < 0) {

			ViewHelper.setPivotX(view, 0);
			ViewHelper.setPivotY(view, pageHeight);

			ViewHelper.setRotationY(view, -45 * position);
		} else {

			ViewHelper.setPivotX(view, -pageWidth);
			ViewHelper.setPivotY(view, -pageHeight);
			ViewHelper.setRotationY(view, -45 * position);

		}
	}

	public void animationEight() {
		if (position < 0) {

			ViewHelper.setPivotX(view, pageWidth / 2);
			ViewHelper.setPivotY(view, pageHeight / 2);
			ViewHelper.setTranslationY(view, (int) (pageHeight * position));
			ViewHelper.setTranslationX(view, (int) (pageWidth * position));
		} else {

			ViewHelper.setPivotX(view, -pageWidth);
			ViewHelper.setPivotY(view, -pageHeight);
			ViewHelper.setTranslationY(view, -(int) (pageHeight * position));
			ViewHelper.setTranslationX(view, -(int) (pageWidth * position));

		}
	}

	private void animationthirteen() {
		if (position < 0) {
			ViewHelper.setPivotX(view, pageWidth / 2);
			ViewHelper.setPivotY(view, pageHeight / 2 - 100);
			ViewHelper.setScrollX(view, (int) (pageHeight * position));

		} else {
			ViewHelper.setPivotX(view, pageWidth / 2);
			ViewHelper.setPivotY(view, pageHeight / 2);
			ViewHelper.setScrollX(view, (int) (pageHeight * position));
		}
	}

	private void animationfourteen() {
		if (position < 0) {

			ViewHelper.setPivotX(view, pageHeight);
			ViewHelper.setPivotY(view, 0);
			ViewHelper.setScrollX(view, (int) (pageWidth * position));
			ViewHelper.setScrollY(view, (int) (pageWidth * position));
			ViewHelper.setRotationY(view, 90 * position);
		} else {

			ViewHelper.setPivotX(view, -pageWidth);
			ViewHelper.setPivotY(view, 0);
			ViewHelper.setRotationY(view, 90 * position);
			ViewHelper.setScrollX(view, (int) (pageWidth * position));
			ViewHelper.setScrollY(view, (int) (pageWidth * position));

		}
	}

}