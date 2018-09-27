package com.tj.deliverylist.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.tj.deliverylist.R;
import com.tj.deliverylist.databinding.DeliveryItemBinding;
import com.tj.deliverylist.databinding.LoadingItemBinding;
import com.tj.deliverylist.db.model.Delivery;
import com.tj.deliverylist.net.NetworkState;

public class DeliveryAdapter extends PagedListAdapter<Delivery, RecyclerView.ViewHolder> {

    private static final int TYPE_PROGRESS_ITEM = 0;
    private static final int TYPE_DELIVERY_ITEM = 1;

    private NetworkState networkState;


    public DeliveryAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_DELIVERY_ITEM) {

            DeliveryItemBinding itemBinding = DeliveryItemBinding.inflate(layoutInflater, parent, false);
            DeliveryItemViewHolder viewHolder = new DeliveryItemViewHolder(itemBinding);
            return viewHolder;
        } else {
            LoadingItemBinding loadingBinding = LoadingItemBinding.inflate(layoutInflater, parent, false);
            LoadingItemViewHolder viewHolder = new LoadingItemViewHolder(loadingBinding);
            return viewHolder;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof DeliveryItemViewHolder) {

            Delivery delivery = getItem(position);
            ((DeliveryItemViewHolder)holder).bindTo(delivery);

        } else {
            ((LoadingItemViewHolder) holder).bindView(networkState);
        }
    }


    public static class DeliveryItemViewHolder extends RecyclerView.ViewHolder {

        private DeliveryItemBinding binding;
        public DeliveryItemViewHolder(DeliveryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(Delivery delivery) {
            Picasso.get()
                    .load(delivery.getImageUrl())
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_image_black_24dp)
                    .into(binding.imageView);

            binding.descriptionTv.setText(delivery.getId()+ " "+ delivery.getDescription() + " at " + delivery.getLocation().getAddress());
        }
    }

    public class LoadingItemViewHolder extends RecyclerView.ViewHolder {

        private LoadingItemBinding binding;
        public LoadingItemViewHolder(LoadingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(NetworkState networkState) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }
        }
    }


    public void setNetworkState(NetworkState newNetworkState) {

        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasLoadingRow();

        this.networkState = newNetworkState;
        boolean newExtraRow = hasLoadingRow();

        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                //notifyItemRemoved(getItemCount());
                notifyItemChanged(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    private boolean hasLoadingRow() {
        if (networkState != null && networkState != NetworkState.DONE) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasLoadingRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS_ITEM;
        } else {
            return TYPE_DELIVERY_ITEM;
        }
    }


    private static DiffUtil.ItemCallback<Delivery> DIFF_CALLBACK = new DiffUtil.ItemCallback<Delivery>() {

        // Delivery description may have changed if reloaded from the database, but ID is fixed.
        @Override
        public boolean areItemsTheSame(Delivery oldConcert, Delivery newConcert) {
            return oldConcert.getId() == newConcert.getId();
        }

        @Override
        public boolean areContentsTheSame(Delivery oldConcert, Delivery newConcert) {

            //We can make check on location and address as well but lets keep it simple here.
            return oldConcert.getDescription().equals(newConcert.getDescription());
        }
    };

    public Delivery getLastDelivery(){

        Delivery d = null;
        if (getItemCount() > 0) {
            d = getItem(getItemCount() - 1);
        }
        return d;
    }
}
