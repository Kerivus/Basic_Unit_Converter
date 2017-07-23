package com.example.win10.unitconverter;

import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class FavoriteFragment extends Fragment {
    public static final String SELECTED_ID = "Unit_Type_Id_Selected";
    private TypeDataSource mTypeDataSource;
    private RecyclerView mTypeRecycleView;
    private TypeAdapter mAdapter;
    private boolean isUncheckAll = false;

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTypeDataSource = new TypeDataSource(getActivity());
        mTypeDataSource.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        setHasOptionsMenu(true);

        mTypeRecycleView = (RecyclerView) view.findViewById(R.id.fav_recycle_view);
        mTypeRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.favoritetoolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.action_clear:
                uncheckall();
                break;
            case R.id.action_checked:
                isUncheckAll = false;
                mAdapter.notifyDataSetChanged();
            default:
                break;
        }
        return true;
    }

    private class TypeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTypeTitleTextView;
        private CheckBox mFavCheckbox;
        private Type mType;

        public TypeHolder(View itemView){
            super(itemView);

            itemView.setOnClickListener(this);
            mTypeTitleTextView = (TextView) itemView.findViewById(R.id.list_item_type_name);
            mFavCheckbox = (CheckBox) itemView.findViewById(R.id.list_item_type_check_box);
            mFavCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mType.setFavourited(isChecked);
                    mTypeDataSource.updateFavType(mType);
                }
            });
        }

        @Override
        public void onClick(View v){
            Intent intent = new Intent(getActivity(), ConversionActivity.class);
            intent.putExtra(SELECTED_ID, mType.getTypeId());
            startActivity(intent);
        }

        public void bindType(Type type){
            mType = type;
            mTypeTitleTextView.setText(mType.getTypeName());
            mFavCheckbox.setChecked(mType.isFavourited());
        }
    }

    private class TypeAdapter extends RecyclerView.Adapter<TypeHolder>{
        private List<Type> mTypes;

        public TypeAdapter(List<Type> types){
            mTypes = types;
        }

        @Override
        public TypeHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_type, parent, false);
            return new TypeHolder(view);
        }

        @Override
        public void onBindViewHolder(TypeHolder holder, int position){
            Type type = mTypes.get(position);
            holder.bindType(type);
            if(isUncheckAll) {
                holder.mFavCheckbox.setChecked(false);
            } else
                holder.mFavCheckbox.setChecked(true);
        }

        @Override
        public int getItemCount(){
            return mTypes.size();
        }
    }

    private void updateUI(){
        List<Type> values = mTypeDataSource.getFavTypes();
        mAdapter = new TypeAdapter(values);
        mTypeRecycleView.setAdapter(mAdapter);
    }

    private void uncheckall(){
        isUncheckAll = true;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        mTypeDataSource.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        mTypeDataSource.close();
        super.onPause();
    }

}
