package cn.sswukang.library.common.single;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * 拖拽侧滑CallBack
 *
 * @author sswukang on 2016/11/24 11:25
 * @version 1.0
 */
public class ItemTouchCallBack extends ItemTouchHelper.Callback {

    private OnMoveSwipeListener listener;
    private static final float ALPHA_FULL = 1.0f;

    public ItemTouchCallBack(OnMoveSwipeListener listener) {
        this.listener = listener;
    }

    //设置拖动方向和侧滑方向
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // GridLayout样式
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT; // 拖动方向:上下左右
            final int swipeFlags = 0; // 不支持侧滑
            return makeMovementFlags(dragFlags, swipeFlags);
        } else { // linearLayout样式
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN; // 拖动方向:上下
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END; // 侧滑方向:左右
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    // 拖动时调用
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //两个item不是一个类型, 不可以拖拽
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        // 回调adapter中的onMove
        return listener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    // 侧滑时调用
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // 回调adapter中的onSwipe
        listener.onItemSwipe(viewHolder.getAdapterPosition());
    }

    // 拖拽或侧滑某一项
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // 不是空闲状态(正在拖拽或侧滑)
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            // 判断viewHolder是否实现了OnStateChangedListener
            if (viewHolder instanceof OnStateChangedListener) {
                ((OnStateChangedListener) viewHolder).onItemPressed();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);

    }

    // 拖拽侧滑完清除状态
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (viewHolder instanceof OnStateChangedListener) {
            ((OnStateChangedListener) viewHolder).onItemClear();
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {//正在侧滑, 根据位移修改item透明度
            final float alpha = ALPHA_FULL - Math.abs(dX) / viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    //拖拽或侧滑回调
    public interface OnMoveSwipeListener {
        boolean onItemMove(int fromPosition, int toPosition); // 某一项从fromPosition拖到toPosition

        void onItemSwipe(int position); // 侧滑某一项
    }

    public interface OnStateChangedListener {
        void onItemPressed(); // 长按选中某一项

        void onItemClear();
    }
}