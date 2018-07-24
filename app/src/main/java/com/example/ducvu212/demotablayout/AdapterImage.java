package com.example.ducvu212.demotablayout;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class AdapterImage extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements interfaceImage {

    private interfaceImage mInterfaceImage;
    private ArrayList<Item> mItems;
    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    private ILoadMore mLoadMore;
    private boolean mIsLoading;
    private int mVisibleThreshold = 5;
    private int mLastVisibleItem;
    private int mTotalItemCount;
    private Activity mActivity;

    public AdapterImage(RecyclerView recyclerView, Activity activity, interfaceImage interfaceImage
            , ArrayList<Item> mListImages, final RecyclerView.LayoutManager manager) {
        mInterfaceImage = interfaceImage;
        mItems = mListImages;
        mActivity = activity;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                mTotalItemCount = linearLayoutManager.getItemCount();
                mLastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!mIsLoading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {
                    if (mLoadMore != null) mLoadMore.onLoadMore();
                    mIsLoading = true;
                }
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.mLoadMore = loadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item, parent, false);
            return new RecycleHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.progressbar, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecycleHolder) {
            RecycleHolder recycleHolder = (RecycleHolder) holder;
            recycleHolder.mImage.setImageResource(mItems.get(position).getId());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        mIsLoading = false;
    }

    @Override
    public Item getItem(int position) {
        return mInterfaceImage.getItem(position);
    }

    @Override
    public int getItemCount() {
        return mInterfaceImage.getItemCount();
    }

    private static class RecycleHolder extends RecyclerView.ViewHolder {

        private ImageView mImage;

        private RecycleHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image_im);
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        private LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public interface ILoadMore {
        void onLoadMore();
    }
}


