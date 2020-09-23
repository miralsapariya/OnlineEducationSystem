package com .onlineeducationsyestem.widget;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.onlineeducationsyestem.adapter.QuestionTypeMatrixImgSecondAdapter;

public class ItemMoveCallback2 extends ItemTouchHelper.Callback {

    private final ItemTouchHelperContract mAdapter;

    public ItemMoveCallback2(ItemTouchHelperContract adapter) {
        mAdapter = adapter;
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
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        mAdapter.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder,
                                  int actionState) {


        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof QuestionTypeMatrixImgSecondAdapter.MyViewHolder) {
                QuestionTypeMatrixImgSecondAdapter.MyViewHolder myViewHolder=
                        (QuestionTypeMatrixImgSecondAdapter.MyViewHolder) viewHolder;
                mAdapter.onRowSelected(myViewHolder);
            }

        }

        super.onSelectedChanged(viewHolder, actionState);
    }
    @Override
    public void clearView(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (viewHolder instanceof QuestionTypeMatrixImgSecondAdapter.MyViewHolder) {
            QuestionTypeMatrixImgSecondAdapter.MyViewHolder myViewHolder=
                    (QuestionTypeMatrixImgSecondAdapter.MyViewHolder) viewHolder;
            mAdapter.onRowClear(myViewHolder);
        }
    }

    public interface ItemTouchHelperContract {

        void onRowMoved(int fromPosition, int toPosition);
        void onRowSelected(QuestionTypeMatrixImgSecondAdapter.MyViewHolder myViewHolder);
        void onRowClear(QuestionTypeMatrixImgSecondAdapter.MyViewHolder myViewHolder);

    }

}

