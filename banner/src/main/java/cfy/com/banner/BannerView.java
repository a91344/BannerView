package cfy.com.banner;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/4/24.
 */

public class BannerView extends FrameLayout {
    private ViewPager mainVp;
    private RelativeLayout mainRl;
    private TextView mainTvContent;
    private LinearLayout mainLl;

    private List icons;
    private List<String> iconTitles;
    private List<ImageView> ivs;

    private Timer timer;

    private boolean isOpenLoop = false;

    //default
    private long time;
    @DrawableRes
    private int selectTrue;
    @DrawableRes
    private int selectFalse;
    private int indicatorSize;
    private int indicatorMarginSize;
    private Object nullPlaceholder;

    public BannerView(Context context) {
        super(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void initView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.banner, this, false);
        mainVp = (ViewPager) v.findViewById(R.id.main_vp);
        mainRl = (RelativeLayout) v.findViewById(R.id.main_rl);
        mainTvContent = (TextView) v.findViewById(R.id.main_tv_content);
        mainLl = (LinearLayout) v.findViewById(R.id.main_ll);
        this.addView(v);

        //init default
        initDefault();
    }

    private void initDefault() {
        setIndicatorIcon(R.drawable.yuan_true, R.drawable.yuan_false);
        time = 1000;
        setIndicatorSize(8);
        setIndicatorMarginSize(3);
        setSlideSpeed(300);
        setIndicatorLocation(RelativeLayout.CENTER_IN_PARENT);
        setIndicatorBackgroundColor(Color.parseColor("#00000000"));
        setDefaultIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524632642232&di=963a8b0d90d46266a19fb2837d60f112&imgtype=0&src=http%3A%2F%2Fimg2.jiwu.com%2Fbuildpic%2F12%2F0627%2F627981_m.jpg");
    }

    public void setDefaultIcon(@NonNull @DrawableRes int iconId) {
        nullPlaceholder = iconId;
    }

    public void setDefaultIcon(@NonNull String url) {
        nullPlaceholder = url;
    }

    public void setIndicatorBackgroundColor(@ColorInt @ColorRes int color) {
        mainRl.setBackgroundColor(color);
    }

    public void setIndicatorBackground(@DrawableRes int color) {
        mainRl.setBackgroundResource(color);
    }

    public void setSlideSpeed(int time) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mainVp.getContext(),
                    new AccelerateInterpolator());
            field.set(mainVp, scroller);
            scroller.setmDuration(time);
        } catch (Exception e) {

        }
    }

    public void setIcons(@NonNull List icons) {
        this.icons = icons;
        this.ivs = new ArrayList<>();
        if (icons.size() == 0) {
            this.icons.add(nullPlaceholder);
        } else if (icons == null) {
            this.icons = new ArrayList<>();
            this.icons.add(nullPlaceholder);
        }
        initData();
        initEvent();
    }

    public void setIconTitles(@NonNull List<String> iconTitles) {
        this.iconTitles = iconTitles;
    }

    public void setIndicatorLocation(@NonNull int type) {
        if (RelativeLayout.ALIGN_PARENT_RIGHT != type) {
            mainTvContent.setVisibility(GONE);
        } else {
            mainTvContent.setVisibility(VISIBLE);
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mainLl.getLayoutParams();
        layoutParams.addRule(type);
    }

    public void openLoop(@NonNull long time) {
        isOpenLoop = true;
        this.time = time;
    }

    public void setIndicatorIcon(@DrawableRes int selectTrue, @DrawableRes int selectFalse) {
        this.selectTrue = selectTrue;
        this.selectFalse = selectFalse;
    }

    public void setIndicatorSize(int indicatorSize) {
        this.indicatorSize = indicatorSize;
    }

    public void setIndicatorMarginSize(int indicatorMarginSize) {
        this.indicatorMarginSize = indicatorMarginSize;
    }

    private void initData() {
        int oneDp = getResources().getDimensionPixelSize(R.dimen.one_dp);

        if (iconTitles == null) {
            iconTitles = new ArrayList<>();
            for (int i = 0; i < icons.size(); i++) {
                iconTitles.add("");
            }
        }

        for (int i = 0; i < icons.size(); i++) {
            ImageView iv = new ImageView(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(layoutParams);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(getContext()).load(icons.get(i)).asBitmap().into(iv);
            ivs.add(iv);

            iv = new ImageView(getContext());
            layoutParams = new LinearLayout.LayoutParams(oneDp * indicatorSize, oneDp * indicatorSize);
            layoutParams.leftMargin = oneDp * indicatorMarginSize;
            layoutParams.rightMargin = oneDp * indicatorMarginSize;
            iv.setLayoutParams(layoutParams);
            iv.setImageResource(i == 0 ? selectTrue : selectFalse);
            mainLl.addView(iv);
        }
        ImageView iv = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(layoutParams);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(getContext()).load(icons.get(icons.size() - 1)).asBitmap().into(iv);
        ivs.add(0, iv);

        iv = new ImageView(getContext());
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(layoutParams);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(getContext()).load(icons.get(0)).asBitmap().into(iv);
        ivs.add(ivs.size(), iv);

        mainVp.setAdapter(new BannerViewPagerAdapter(ivs));
        mainVp.setCurrentItem(1, false);
        mainVp.setPageTransformer(false, BannerPageTransformer.getPageTransformer(TransitionEffect.Accordion));
    }

    private void initEvent() {
        mainVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int position;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                this.position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (position == ivs.size() - 1 && state == 0) {
                    mainVp.setCurrentItem(1, false);
                } else if (position == 0 && state == 0) {
                    mainVp.setCurrentItem(ivs.size() - 2, false);
                }
                for (int i = 0; i < mainLl.getChildCount(); i++) {
                    ((ImageView) mainLl.getChildAt(i)).setImageResource(selectFalse);
                }
                int temp = 0;
                if (mainVp.getCurrentItem() == ivs.size() - 1) {
                    temp = icons.size() - 1;
                } else if (mainVp.getCurrentItem() == 0 || mainVp.getCurrentItem() == 1) {
                    temp = 0;
                } else {
                    temp = mainVp.getCurrentItem() - 1;
                }
                ((ImageView) mainLl.getChildAt(temp)).setImageResource(selectTrue);
                mainTvContent.setText(iconTitles.get(temp));
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.i("AX1", "onWindowFocusChanged: " + hasWindowFocus);
        if (!isOpenLoop) {
            return;
        }
        if (hasWindowFocus) {
            //onResume
            if (timer == null && ivs != null) {
                timer = new Timer();
                TimerTask timerTask = new BannerTimerTask(mainVp, ivs.size());
                timer.schedule(timerTask, time, time);
            }
        } else {
            //onPause
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Log.i("AX1", "onSaveInstanceState: ");
        return super.onSaveInstanceState();
    }

    //相当于onDestroy
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i("AX1", "onDetachedFromWindow: ");
        if (timer != null && isOpenLoop) {
            timer.cancel();
            timer = null;
        }
    }

    public void setOnItemClickListener(final BannerOnItemClickListener itemClickListener) {
        for (int i = 0; i < icons.size(); i++) {
            final int finalI = i;
            ivs.get(i + 1).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClickListener(v, finalI);
                }
            });
            mainLl.getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClickListener(v, finalI);
                    mainVp.setCurrentItem(finalI + 1);
                }
            });
        }
    }

    public interface BannerOnItemClickListener {
        void onItemClickListener(View v, int position);
    }
}
