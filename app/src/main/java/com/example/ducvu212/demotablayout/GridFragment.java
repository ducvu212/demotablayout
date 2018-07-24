package com.example.ducvu212.demotablayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GridFragment extends Fragment implements interfaceImage {

    private AdapterImage mAdapterImage;
    private ArrayList<Item> mItemArrayList;
    private RecyclerView mRecyclerView;

    public GridFragment() {
        // Required empty public constructor
    }

    public static GridFragment newInstance() {
        return new GridFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grid, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViewByIds();
        addData();
        initComponents();
    }

    private void initComponents() {
        GridLayoutManager gridLayoutManager =  new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapterImage = new AdapterImage(mRecyclerView, getActivity(),this, mItemArrayList, gridLayoutManager);
        mRecyclerView.setAdapter(mAdapterImage);
        mRecyclerView.addItemDecoration(new ItemOffsetDecoration(10));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapterImage.setLoadMore(new AdapterImage.ILoadMore() {
            @Override
            public void onLoadMore() {
                if (mItemArrayList.size() <= 100) {
                    mItemArrayList.add(null);
                    mAdapterImage.notifyItemInserted(mItemArrayList.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mItemArrayList.remove(mItemArrayList.size() - 1) ;
                            mAdapterImage.notifyItemRemoved(mItemArrayList.size());
                            addData();
                            mAdapterImage.notifyDataSetChanged();
                            mAdapterImage.setLoaded();
                        }
                    }, 1000);
                }
            }
        });
    }

    private void addData() {
        mItemArrayList.add(new Item(R.drawable.image1));
        mItemArrayList.add(new Item(R.drawable.image2));
        mItemArrayList.add(new Item(R.drawable.image3));
        mItemArrayList.add(new Item(R.drawable.image4));
        mItemArrayList.add(new Item(R.drawable.image5));
        mItemArrayList.add(new Item(R.drawable.image6));
        mItemArrayList.add(new Item(R.drawable.image7));
        mItemArrayList.add(new Item(R.drawable.image8));
    }

    private void findViewByIds() {
        mRecyclerView = getActivity().findViewById(R.id.recycleview_grid);
        mItemArrayList = new ArrayList<>();
    }

    @Override
    public Item getItem(int position) {
        return mItemArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return mItemArrayList.size();
    }
}
