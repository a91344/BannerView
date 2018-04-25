package cfy.com.bannerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cfy.com.banner.BannerView;

public class MainActivity extends AppCompatActivity {
    private BannerView bv;

    private List<String> icons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        bv = (BannerView) findViewById(R.id.bv);
    }

    private void initData() {
        icons = new ArrayList<>();
        icons.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524477376970&di=72c0df5cf09fd250d06f0920ade77ecf&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01d941590c5577a801214550416103.jpg%402o.jpg");
        icons.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524477376970&di=f584223c0eded58c5c353591dde6766f&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F140729%2F240450-140HZP45790.jpg");
        icons.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524477376970&di=e8f24587dc6571119b1daf06c4500926&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01bef15568672b0000012716336dd4.jpg%401280w_1l_2o_100sh.jpg");
        icons.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524477376970&di=e958fea35cce7c3113e6b196c7a0f07e&imgtype=0&src=http%3A%2F%2Fimg03.tooopen.com%2Fuploadfile%2Fdowns%2Fimages%2F20110714%2Fsy_20110714135215645030.jpg");
        bv.setIndicatorSize(6);
        bv.setIndicatorMarginSize(6);
        bv.setIconTitles(Arrays.asList("dad", "dad", "da", "das"));
        bv.setDefaultIcon(R.mipmap.ic_launcher);
        bv.setIcons(icons);

        bv.openLoop(800);
        bv.setOnItemClickListener(new BannerView.BannerOnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initEvent() {
    }

}
