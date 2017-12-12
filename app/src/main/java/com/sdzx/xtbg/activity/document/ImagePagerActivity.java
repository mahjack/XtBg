package com.sdzx.xtbg.activity.document;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.view.MyViewPager;
import com.sdzx.xtbg.view.photoview.ImageDetailFragment;
import com.sdzx.xtbg.view.photoview.RotateDownPageTransformer;


public class ImagePagerActivity extends FragmentActivity {
	private static final String STATE_POSITION = "STATE_POSITION";
	public static final String EXTRA_IMAGE_INDEX = "image_index";
	public static final String EXTRA_IMAGE_URLS = "image_urls";

//	private HackyViewPager mPager;
    private MyViewPager mPager;
    //ViewPager动画
    private RotateDownPageTransformer rotateDownPageTransformer;
	private int pagerPosition;
	private TextView indicator;
	private DisplayImageOptions options;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_detail_pager);
		initConstants();
		pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
		String[] urls = getIntent().getStringArrayExtra(EXTRA_IMAGE_URLS);

		mPager = (MyViewPager) findViewById(R.id.pager);
        //设置动画
        rotateDownPageTransformer = new RotateDownPageTransformer(mPager);
        mPager.setPageTransformer(false,rotateDownPageTransformer);
        rotateDownPageTransformer.setPageTransFormerAnimation(RotateDownPageTransformer.pageTransFormer.One);

		ImagePagerAdapter mAdapter = new ImagePagerAdapter(
				getSupportFragmentManager(), urls);
		mPager.setAdapter(mAdapter);
		indicator = (TextView) findViewById(R.id.indicator);

		CharSequence text = getString(R.string.viewpager_indicator, 1, mPager
				.getAdapter().getCount());
		indicator.setText(text);
        // 更新下标
        mPager.setOnPageChangeListener(new MyViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CharSequence text = getString(R.string.viewpager_indicator,
                        position + 1, mPager.getAdapter().getCount());
                indicator.setText(text);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}

		mPager.setCurrentItem(pagerPosition);
	}

	private void initConstants() {
		//显示图片的配置
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.mipmap.icon_load_ing)
				.showImageOnFail(R.mipmap.icon_load_fail)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, mPager.getCurrentItem());
	}

	private class ImagePagerAdapter extends FragmentStatePagerAdapter {

		public String[] fileList;

		public ImagePagerAdapter(FragmentManager fm, String[] fileList) {
			super(fm);
			this.fileList = fileList;
		}

		@Override
		public int getCount() {
			return fileList == null ? 0 : fileList.length;
		}

		@Override
		public Fragment getItem(int position) {
			String url = fileList[position];
			return ImageDetailFragment.newInstance(url);
		}

	}
    /**
     * 初始化图片加载类
     * */
    private void createImageLoader(){
//        ToolUtils.imageLoader = ImageLoader.getInstance();
//        ImageLoaderConfiguration imageLoaderConfiguration=new ImageLoaderConfiguration.Builder(this).build();
//        ToolUtils.imageLoader.init(imageLoaderConfiguration);
    }
}