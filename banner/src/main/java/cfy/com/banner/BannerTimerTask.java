package cfy.com.banner;

import android.support.v4.view.ViewPager;

import java.util.TimerTask;

/**
 * Created by Administrator on 2018/4/24.
 */

public class BannerTimerTask extends TimerTask {
    private ViewPager vp;
    private int currentPosition;
    private int size;

    public BannerTimerTask(ViewPager vp, int size) {
        this.vp = vp;
    }

    @Override
    public void run() {
        currentPosition = vp.getCurrentItem();
        if (currentPosition == 0) {
            currentPosition = 1;
        } else if (currentPosition == size - 1) {
            currentPosition = size - 2;
        }
        if (currentPosition == size - 2) {
            vp.post(new Runnable() {
                @Override
                public void run() {
                    vp.setCurrentItem(1);
                }
            });
            return;
        }
        currentPosition++;
        vp.post(new Runnable() {
            @Override
            public void run() {
                vp.setCurrentItem(currentPosition);
            }
        });
    }
}
