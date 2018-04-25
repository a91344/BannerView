package cfy.com.banner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/4/24.
 */

public class BannerViewPagerAdapter extends PagerAdapter {
    private List<ImageView> ivs;

    public BannerViewPagerAdapter(List<ImageView> ivs) {
        this.ivs = ivs;
    }

    @Override
    public int getCount() {
        return ivs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 初始化一个条目
     *
     * @param container
     * @param position  当前需要加载条目的索引
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 把position对应位置的ImageView添加到ViewPager中
        ImageView iv = ivs.get(position % ivs.size());
        container.addView(iv);
        // 把当前添加ImageView返回回去.
        return iv;
    }

    /**
     * 销毁一个条目
     * position 就是当前需要被销毁的条目的索引
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 把ImageView从ViewPager中移除掉
        container.removeView(ivs.get(position % ivs.size()));
    }
}
