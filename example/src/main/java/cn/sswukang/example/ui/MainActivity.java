package cn.sswukang.example.ui;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.BindView;
import cn.sswukang.example.R;
import cn.sswukang.example.base.BaseActivity;

/**
 * 主界面
 *
 * @author sswukang on 2017/2/21 11:57
 * @version 1.0
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.top_toolbar)
    Toolbar topToolbar;
    @BindView(R.id.main_container)
    FrameLayout mainContainer;

    // 左pop
    private ListPopupWindow leftMenuPop;
    // 右pop
    private ListPopupWindow rightMenuPop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        // 初始化ActionBar
        topToolbar.setTitleTextColor(Color.WHITE);
        topToolbar.setSubtitleTextColor(Color.argb(Math.round(255 * 0.8f), 255, 255, 255));
        setSupportActionBar(topToolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_top_menu);
        // 初始化PopupWindow
        initLeftMenuPop();
        initRightMenuPop();
        // 初始化Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new MainSingleFragment()).commitAllowingStateLoss();
    }

    private void initLeftMenuPop() {
        MenuBuilder menuBuilder = new MenuBuilder(getContext());
        menuBuilder.setOptionalIconsVisible(true);
        menuBuilder.add(R.string.main_single).setIcon(R.drawable.ic_main_single);
        menuBuilder.add(R.string.main_multi).setIcon(R.drawable.ic_main_multi);
        menuBuilder.add(R.string.main_sticky).setIcon(R.drawable.ic_main_sticky);
        menuBuilder.add(R.string.main_sticky_side).setIcon(R.drawable.ic_main_sticky_side);
        leftMenuPop = new ListPopupWindow(getContext());
        leftMenuPop.setAdapter(new MenuAdapter(menuBuilder, getLayoutInflater(), true));
        leftMenuPop.setWidth(getResources().getDisplayMetrics().widthPixels / 2);
        leftMenuPop.setHeight(ListPopupWindow.WRAP_CONTENT);
        leftMenuPop.setAnchorView(topToolbar);
        leftMenuPop.setDropDownGravity(Gravity.START);
        leftMenuPop.setModal(true);//设置是否是模式
        leftMenuPop.setOnItemClickListener((parent, view, position, id) -> {
            Log.e("leftMenuPop", "viewId:" + view.getId() + " pos:" + position + " id:" + id);
            switch (position) {
                case 0:
                    Snackbar.make(mainContainer, "single", 1000).show();
                    break;
                case 1:
                    Snackbar.make(mainContainer, "multi", 1000).show();
                    break;
                case 2:
                    Snackbar.make(mainContainer, "sticky", 1000).show();
                    break;
                case 3:
                    Snackbar.make(mainContainer, "sticky_side", 1000).show();
                    break;
            }
            leftMenuPop.dismiss();
        });
    }

    private void initRightMenuPop() {
        MenuBuilder menuBuilder = new MenuBuilder(getContext());
        menuBuilder.setOptionalIconsVisible(true);
        menuBuilder.add(R.string.main_asc).setIcon(R.drawable.ic_main_asc);
        menuBuilder.add(R.string.main_desc).setIcon(R.drawable.ic_main_desc);
        menuBuilder.add(R.string.main_shuffle).setIcon(R.drawable.ic_main_shuffle);
        rightMenuPop = new ListPopupWindow(getContext());
        rightMenuPop.setAdapter(new MenuAdapter(menuBuilder, getLayoutInflater(), true));
        rightMenuPop.setWidth(getResources().getDisplayMetrics().widthPixels / 2);
        rightMenuPop.setHeight(ListPopupWindow.WRAP_CONTENT);
        rightMenuPop.setAnchorView(topToolbar);
        rightMenuPop.setDropDownGravity(Gravity.END);
        rightMenuPop.setModal(true);//设置是否是模式
        rightMenuPop.setOnItemClickListener((parent, view, position, id) -> {
            Log.e("rightMenuPop", "viewId:" + view.getId() + " pos:" + position + " id:" + id);
            switch (position) {
                case 0:
                    Snackbar.make(mainContainer, "asc", 1000).show();
                    break;
                case 1:
                    Snackbar.make(mainContainer, "desc", 1000).show();
                    break;
                case 2:
                    Snackbar.make(mainContainer, "shuffle", 1000).show();
                    break;
            }
            rightMenuPop.dismiss();
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // 可对菜单进行设置，如visible
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                leftMenuPop.show();
                return true;
            case R.id.main_pop:
                rightMenuPop.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setTopToolbarText(String title, String subtitle) {
        topToolbar.setTitle(title);
        topToolbar.setSubtitle(subtitle);
    }
}