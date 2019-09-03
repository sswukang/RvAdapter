package cn.sswukang.library.lib.sticky_header.sticky;

import android.graphics.Canvas;
import android.graphics.Rect;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import cn.sswukang.library.lib.sticky_header.caching.HeaderProvider;
import cn.sswukang.library.lib.sticky_header.caching.HeaderViewCache;
import cn.sswukang.library.lib.sticky_header.calculation.DimensionCalculator;
import cn.sswukang.library.lib.sticky_header.rendering.HeaderRenderer;
import cn.sswukang.library.lib.sticky_header.util.LinearLayoutOrientationProvider;
import cn.sswukang.library.lib.sticky_header.util.OrientationProvider;


public class StickyRecyclerHeadersDecoration<VH extends RecyclerView.ViewHolder> extends RecyclerView.ItemDecoration {

    private final StickyRecyclerHeadersAdapter<VH> mAdapter;
    private final ItemVisibilityAdapter mVisibilityAdapter;
    private final OrientationProvider mOrientationProvider;
    private final DimensionCalculator mDimensionCalculator;
    private final HeaderRenderer mHeaderRenderer;
    private final HeaderProvider mHeaderProvider;
    private final HeaderPositionCalculator<VH> mHeaderPositionCalculator;
    private final SparseArray<Rect> mHeaderRects = new SparseArray<>();

    /**
     * The following field is used as a buffer for internal calculations. Its sole purpose is to avoid
     * allocating new Rect every time we need one.
     */
    private final Rect mTempRect = new Rect();

    //  Consider passing in orientation to simplify orientation accounting within calculation
    public StickyRecyclerHeadersDecoration(StickyRecyclerHeadersAdapter<VH> adapter) {
        this(adapter, null);
    }

    public StickyRecyclerHeadersDecoration(StickyRecyclerHeadersAdapter<VH> adapter, ItemVisibilityAdapter visibilityAdapter) {
        this(adapter, visibilityAdapter, new LinearLayoutOrientationProvider(), new DimensionCalculator());
    }

    private StickyRecyclerHeadersDecoration(StickyRecyclerHeadersAdapter<VH> adapter, ItemVisibilityAdapter visibilityAdapter,
                                           OrientationProvider orientationProvider, DimensionCalculator dimensionCalculator) {
        this(adapter, visibilityAdapter, orientationProvider, dimensionCalculator,
                new HeaderRenderer(orientationProvider), new HeaderViewCache<>(adapter, orientationProvider));
    }

    private StickyRecyclerHeadersDecoration(StickyRecyclerHeadersAdapter<VH> adapter, ItemVisibilityAdapter visibilityAdapter,
                                           OrientationProvider orientationProvider, DimensionCalculator dimensionCalculator,
                                           HeaderRenderer headerRenderer, HeaderProvider headerProvider) {
        this(adapter, visibilityAdapter, orientationProvider, dimensionCalculator, headerRenderer, headerProvider,
                new HeaderPositionCalculator<>(adapter, headerProvider, orientationProvider, dimensionCalculator));
    }

    private StickyRecyclerHeadersDecoration(StickyRecyclerHeadersAdapter<VH> adapter, ItemVisibilityAdapter visibilityAdapter,
                                            OrientationProvider orientationProvider, DimensionCalculator dimensionCalculator,
                                            HeaderRenderer headerRenderer, HeaderProvider headerProvider,
                                            HeaderPositionCalculator<VH> headerPositionCalculator) {
        mAdapter = adapter;
        mVisibilityAdapter = visibilityAdapter;
        mOrientationProvider = orientationProvider;
        mDimensionCalculator = dimensionCalculator;
        mHeaderRenderer = headerRenderer;
        mHeaderProvider = headerProvider;
        mHeaderPositionCalculator = headerPositionCalculator;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition == RecyclerView.NO_POSITION) {
            return;
        }
        if (mHeaderPositionCalculator.hasNewHeader(itemPosition, mOrientationProvider.isReverseLayout(parent))) {
            View header = getHeaderView(parent, itemPosition);
            setItemOffsetsForHeader(outRect, header, mOrientationProvider.getOrientation(parent));
        }
    }

    /**
     * Sets the offsets for the first item in a section to make room for the header view
     *
     * @param itemOffsets rectangle to define offsets for the item
     * @param header      view used to calculate offset for the item
     * @param orientation used to calculate offset for the item
     */
    private void setItemOffsetsForHeader(Rect itemOffsets, View header, int orientation) {
        mDimensionCalculator.initMargins(mTempRect, header);
        if (orientation == LinearLayoutManager.VERTICAL) {
            itemOffsets.top = header.getHeight() + mTempRect.top + mTempRect.bottom;
        } else {
            itemOffsets.left = header.getWidth() + mTempRect.left + mTempRect.right;
        }
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);

        final int childCount = parent.getChildCount();
        if (childCount <= 0 || mAdapter.getItemCount() <= 0) {
            return;
        }

        for (int i = 0; i < childCount; i++) {
            View itemView = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(itemView);
            if (position == RecyclerView.NO_POSITION) {
                continue;
            }

            boolean hasStickyHeader = mHeaderPositionCalculator.hasStickyHeader(itemView, mOrientationProvider.getOrientation(parent), position);
            if (hasStickyHeader || mHeaderPositionCalculator.hasNewHeader(position, mOrientationProvider.isReverseLayout(parent))) {
                View header = mHeaderProvider.getHeader(parent, position);
                //re-use existing Rect, if any.
                Rect headerOffset = mHeaderRects.get(position);
                if (headerOffset == null) {
                    headerOffset = new Rect();
                    mHeaderRects.put(position, headerOffset);
                }
                mHeaderPositionCalculator.initHeaderBounds(headerOffset, parent, header, itemView, hasStickyHeader);
                mHeaderRenderer.drawHeader(parent, canvas, header, headerOffset);
            }
        }
    }

    /**
     * Gets the position of the header under the specified (x, y) coordinates.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return position of header, or -1 if not found
     */
    public int findHeaderPositionUnder(int x, int y) {
        for (int i = 0; i < mHeaderRects.size(); i++) {
            Rect rect = mHeaderRects.get(mHeaderRects.keyAt(i));
            if (rect.contains(x, y)) {
                int position = mHeaderRects.keyAt(i);
                if (mVisibilityAdapter == null || mVisibilityAdapter.isPositionVisible(position)) {
                    return position;
                }
            }
        }
        return -1;
    }

    /**
     * Gets the header view for the associated position.  If it doesn't exist yet, it will be
     * created, measured, and laid out.
     *
     * @param parent   the recyclerview
     * @param position the position to get the header view for
     * @return Header view
     */
    public View getHeaderView(RecyclerView parent, int position) {
        return mHeaderProvider.getHeader(parent, position);
    }

    /**
     * Invalidates cached headers.  This does not invalidate the recyclerview, you should do that manually after
     * calling this method.
     */
    public void invalidateHeaders() {
        mHeaderProvider.invalidate();
        mHeaderRects.clear();
    }
}
