package cn.sswukang.library.lib.side;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

import cn.sswukang.library.adapter.base.BaseViewHolder;
import cn.sswukang.library.adapter.sticky.StickyHeaderAdapter;
import cn.sswukang.library.lib.sticky_header.sticky.StickyRecyclerHeadersDecoration;


/**
 * 选择条结合粘性头部的RecyclerView
 *
 * @author sswukang on 2017/2/22 15:39
 * @version 1.0
 */
public class SideAndStickyHeaderRecyclerView extends FrameLayout {

    private RecyclerView recyclerView;
    private SideBar sideBar;
    /**
     * sticky header 目前需要线性布局
     */
    private LinearLayoutManager linearLayoutManager;
    /**
     * 利用decoration添加header
     */
    private StickyRecyclerHeadersDecoration<BaseViewHolder> decoration;
    /**
     * 滑动监听，联动wave side
     */
    private RecyclerView.OnScrollListener onScrollListener;
    /**
     * recycler view 是否正在滚动
     */
    private boolean isMove;
    /**
     * move到哪个条目
     */
    private int movePosition;
    /**
     * 是否平滑滚动
     */
    private boolean isSmooth;

    public SideAndStickyHeaderRecyclerView(Context context) {
        this(context, null);
    }

    public SideAndStickyHeaderRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideAndStickyHeaderRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SideAndStickyHeaderRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        addView(recyclerView);
        sideBar = new SideBar(getContext());
        sideBar.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        sideBar.setPadding(0, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, getResources().getDisplayMetrics()), 0);
        addView(sideBar);
        linkageMove(false);
    }

    /**
     * 支持设置sticky header adapter
     *
     * @param adapter {@link StickyHeaderAdapter}
     */
    public <T> void setStickyHeaderAdapter(final StickyHeaderAdapter<T> adapter) {
        if (linearLayoutManager == null)
            linearLayoutManager = new LinearLayoutManager(getContext());
        if (decoration == null)
            decoration = new StickyRecyclerHeadersDecoration<>(adapter);
        if (onScrollListener == null)
            onScrollListener = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //在这里进行第二次滚动
                        if (isMove) {
                            isMove = false;
                            //获取要置顶的项在当前屏幕的位置，movePosition是记录要置顶项在RecyclerView中的位置
                            int n = movePosition - linearLayoutManager.findFirstVisibleItemPosition();
                            if (0 <= n && n < recyclerView.getChildCount()) {
                                //获取要置顶的项顶部离RecyclerView顶部的距离
                                int top = recyclerView.getChildAt(n).getTop() - adapter.getHeaderHeight();
                                //最后的移动
                                if (isSmooth)
                                    recyclerView.smoothScrollBy(0, top);
                                else
                                    recyclerView.scrollBy(0, top);
                            }
                        }
                    }
                }
            };

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.addOnScrollListener(onScrollListener);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 关联wave side移动到指定条目
     *
     * @param position 目标条目
     */
    public void moveToPosition(int position) {
        movePosition = position;
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = linearLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (position <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            if (isSmooth)
                recyclerView.smoothScrollToPosition(position);
            else
                recyclerView.scrollToPosition(position);
        } else if (position <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = recyclerView.getChildAt(position - firstItem).getTop();
            if (isSmooth)
                recyclerView.smoothScrollBy(0, top);
            else
                recyclerView.scrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            if (isSmooth)
                recyclerView.smoothScrollToPosition(position);
            else
                recyclerView.scrollToPosition(position);
            //这里这个变量是用在RecyclerView滚动监听里面的
            isMove = true;
        }
    }

    public void linkageMove(boolean linkageMove) {
        setLazyRespond(!linkageMove);
        setSmooth(!linkageMove);
    }

    private void setSmooth(boolean smooth) {
        this.isSmooth = smooth;
    }

    private void setLazyRespond(boolean lazyRespond) {
        sideBar.setLazyRespond(lazyRespond);
    }

    public void setIndexItems(String... indexItems) {
        sideBar.setIndexItems(indexItems);
    }

    public void setIndexItems(List<String> indexItems) {
        sideBar.setIndexItems(indexItems.toArray(new String[indexItems.size()]));
    }

    public void setTextColor(@ColorInt int color) {
        sideBar.setTextColor(color);
    }

    public void setPosition(int position) {
        sideBar.setPosition(position);
    }

    public void setMaxOffset(int offset) {
        sideBar.setMaxOffset(offset);
    }

    public void setTextAlign(int align) {
        sideBar.setTextAlign(align);
    }

    public void setOnSelectIndexItemListener(SideBar.OnSelectIndexItemListener onSelectIndexItemListener) {
        sideBar.setOnSelectIndexItemListener(onSelectIndexItemListener);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        recyclerView.setItemAnimator(animator);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        recyclerView.addItemDecoration(decor);
    }
}
