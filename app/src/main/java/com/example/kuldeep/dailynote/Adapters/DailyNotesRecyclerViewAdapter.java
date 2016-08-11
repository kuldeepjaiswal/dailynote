package com.example.kuldeep.dailynote.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kuldeep.dailynote.Activities.NoteDescriptionActivity;
import com.example.kuldeep.dailynote.Constants.DailyNoteMacros;
import com.example.kuldeep.dailynote.Interface.Interface;
import com.example.kuldeep.dailynote.Models.DailyNoteDataModel;
import com.example.kuldeep.dailynote.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kuldeep on 1/8/2016.
 */
public class DailyNotesRecyclerViewAdapter extends RecyclerView
        .Adapter<DailyNotesRecyclerViewAdapter
        .DataObjectHolder> {
    private ArrayList<DailyNoteDataModel> mDailyNoteModelArrayList;
    Activity mContext;
    Interface.getDailyNotePosition mGetDailyNotePosition;

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.SubjectTextView)
        TextView mSubjectTextView;
        @Bind(R.id.DateTimeTextView)
        TextView mDateTimeTextView;
        @Bind(R.id.DescriptionTextView)
        TextView mDescriptionTextView;
        @Bind(R.id.ParentLayout)
        RelativeLayout mParentLayout;


        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


    public DailyNotesRecyclerViewAdapter(Activity mContext, ArrayList<DailyNoteDataModel> pDailyNoteModelArrayList, Interface.getDailyNotePosition pDailyNotePosition) {
        this.mDailyNoteModelArrayList = pDailyNoteModelArrayList;
        this.mContext = mContext;
        mGetDailyNotePosition=pDailyNotePosition;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_daily_note, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.mSubjectTextView.setText(Html.fromHtml(mDailyNoteModelArrayList.get(position).getSubject()));
        holder.mDateTimeTextView.setText(mDailyNoteModelArrayList.get(position).getDateTime());
        holder.mDescriptionTextView.setText(mDailyNoteModelArrayList.get(position).getDescription());
        holder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mGetDailyNotePosition.getDailyNotePosition(position);


            }
        });

    }

    @Override
    public int getItemCount() {
        return mDailyNoteModelArrayList.size();
    }
}
