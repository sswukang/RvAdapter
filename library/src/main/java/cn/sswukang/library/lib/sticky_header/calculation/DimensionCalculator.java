package cn.sswukang.library.lib.sticky_header.calculation;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

/**
 * Helper to calculate various view dimensions
 */
public class DimensionCalculator {

    /**
     * Populates {@link Rect} with margins for any view.
     *
     * @param margins rect to populate
     * @param view    for which to get margins
     */
    public void initMargins(Rect margins, View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            initMarginRect(margins, (ViewGroup.MarginLayoutParams) layoutParams);
        } else {
            margins.set(0, 0, 0, 0);
        }
    }

    /**
     * Converts {@link ViewGroup.MarginLayoutParams} into a representative {@link Rect}.
     *
     * @param marginRect         Rect to be initialized with margins coordinates, where
     *                           {@link ViewGroup.MarginLayoutParams#leftMargin} is equivalent to {@link Rect#left}, etc.
     * @param marginLayoutParams margins to populate the Rect with
     */
    private void initMarginRect(Rect marginRect, ViewGroup.MarginLayoutParams marginLayoutParams) {
        marginRect.set(
                marginLayoutParams.leftMargin,
                marginLayoutParams.topMargin,
                marginLayoutParams.rightMargin,
                marginLayoutParams.bottomMargin
        );
    }

}
