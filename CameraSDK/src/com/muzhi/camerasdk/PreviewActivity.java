package com.muzhi.camerasdk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muzhi.camerasdk.model.CameraSdkParameterInfo;
import com.muzhi.camerasdk.view.MyViewPager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * 图片预览
 *
 * @author zengxiaofeng
 */
public class PreviewActivity extends BaseActivity {

    private TextView button_delete;
    private int position;

    private MyViewPager mViewPager;
    private CameraSdkParameterInfo mCameraSdkParameterInfo;
    private ArrayList<String> resultList;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camerasdk_activity_preview);
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
            ImageLoader.getInstance().init(config);
        }
        showLeftIcon();
        title = getString(R.string.camerasdk_preview_image);
        button_delete = (TextView) findViewById(R.id.camerasdk_title_txv_right_text);
        button_delete.setVisibility(View.VISIBLE);
;

        mViewPager = (MyViewPager) findViewById(R.id.viewpager);

        ImagePagerAdapter sAdapter;
        Bundle b = getIntent().getExtras();
        if (b != null) {
            mCameraSdkParameterInfo =
                    (CameraSdkParameterInfo) b.getSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER);
            resultList = mCameraSdkParameterInfo.getImage_list();
            position = mCameraSdkParameterInfo.getPosition();
            setActionBarTitle(title + "(" + (position + 1) + "/" + resultList.size() + ")");
            sAdapter = new ImagePagerAdapter(this, resultList);
            mViewPager.setAdapter(sAdapter);
            mViewPager.setCurrentItem(position);
            initEvent();
        }
    }

    @SuppressWarnings("deprecation")
    private void initEvent() {

        button_delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                delete();
            }
        });
    /*mViewPager.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});*/
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                position = arg0;
                mViewPager.setCurrentItem(arg0);
                setActionBarTitle(title + "(" + (position + 1) + "/" + resultList.size() + ")");
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    public void delete() {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        finish();
    }

    //*************************************** Adapter  *************************************************//

    class ImagePagerAdapter extends PagerAdapter {

        private Context mContext;
        private List<String> imageList;
        private List<View> viewList = new ArrayList<View>();// 将要分页显示的View装入数组中

        public ImagePagerAdapter(Context context, List<String> images) {
            mContext = context;
            imageList = images;

            LayoutInflater lf = LayoutInflater.from(mContext);
            for (int i = 0; i < images.size(); i++) {
                viewList.add(lf.inflate(R.layout.camerasdk_list_item_preview_image, null));
            }
        }

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {

            View view = viewList.get(position);
            try {
                if (view.getParent() == null) {
                    ((ViewPager) container).addView(view, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            PhotoView photoView = (PhotoView) view.findViewById(R.id.image);
            // final ProgressBar pb_load_local=(ProgressBar)view.findViewById(R.id.pb_load_local);
            String path = imageList.get(position);
//            File imageFile = new File(path);
            ImageLoader.getInstance().displayImage("file://" + path, photoView);
            //判断文件是否存在
//            if (imageFile.exists()) {
//                Bitmap bitmap = ImageUtils.getimage(path);
//                Bitmap rotateBitmap = ImageUtils.rotateBitmap(bitmap, ImageUtils.readPictureDegree(path));
//                photoView.setImageBitmap(rotateBitmap);
//            }
            return view;
        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
