package com.example.win10.unitconverter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class HistoryFragment extends Fragment {

    private HistoryDataSource mHistoryDataSource;
    private HistoryAdapter mHistoryAdapter;
    private RecyclerView mRecordRecycleView;

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHistoryDataSource = new HistoryDataSource(getActivity());
        mHistoryDataSource.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        setHasOptionsMenu(true);
        mRecordRecycleView = (RecyclerView) view.findViewById(R.id.history_list);
        mRecordRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.historytoolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

   @Override
   public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.action_delete:
                mHistoryDataSource.deleteAllHistory();
                mHistoryAdapter.deleteAll();
                Toast.makeText(getActivity(),"All records was cleared.",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    private class RecordHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTitleTextView;
        private TextView mContentTitleTextView;
        private History mHistory;

        public RecordHolder(View itemView){
            super(itemView);

            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.title_textview);
            mDateTitleTextView = (TextView) itemView.findViewById(R.id.date_textview);
            mContentTitleTextView = (TextView) itemView.findViewById(R.id.content_textview);
        }

        @Override
        public void onClick(View v){
            //Do nothing
        }

        public void bindHistory(History history){
            mHistory = history;
            mTitleTextView.setText(mHistory.getTitle());
            mDateTitleTextView.setText("At: " + mHistory.getDate());
            mContentTitleTextView.setText("Content: " + mHistory.getContent());
        }
    }

    private class HistoryAdapter extends RecyclerView.Adapter<RecordHolder>{
        private List<History> mHistories;

        public HistoryAdapter(List<History> histories){
            mHistories = histories;
        }

        @Override
        public RecordHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_history, parent, false);
            return new RecordHolder(view);
        }

        @Override
        public void onBindViewHolder(RecordHolder holder, int position){
            History history = mHistories.get(position);
            holder.bindHistory(history);
        }

        public void deleteAll(){
            int size = getItemCount();
            this.mHistories.clear();
            notifyItemRangeRemoved(0, size);
        }

        @Override
        public int getItemCount(){
            return mHistories.size();
        }
    }

    private void updateUI(){
        List<History> histories = mHistoryDataSource.getAllHistory();
        mHistoryAdapter = new HistoryAdapter(histories);
        mRecordRecycleView.setAdapter(mHistoryAdapter);
    }

    @Override
    public void onResume() {
        mHistoryDataSource.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        mHistoryDataSource.close();
        super.onPause();
    }
}
