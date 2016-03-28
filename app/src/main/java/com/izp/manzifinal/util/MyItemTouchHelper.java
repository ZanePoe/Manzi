package com.izp.manzifinal.util;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by izp on 2016/3/11.
 */
public class MyItemTouchHelper extends ItemTouchHelper.Callback {
    private ItemTouchHelperAdapter adapter;
    public interface ItemTouchHelperAdapter{
        void onItemMoved(int fromPosition, int toPosition);
//        void onItemRemoved(int position);
    }

    public MyItemTouchHelper(ItemTouchHelperAdapter ad){
        adapter = ad;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int upFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(upFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        adapter.onItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//        adapter.onItemRemoved(viewHolder.getAdapterPosition());
    }
}
