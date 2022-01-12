package com.example.countryinformation.RoomArchitecture;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.countryinformation.R;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    ArrayList<CountryEntity> countryEntities = new ArrayList<>();
    Context context;
    OnCountryClicked onCountryClicked;

    public CountryAdapter(OnCountryClicked onCountryClicked, Context context) {
        this.onCountryClicked = onCountryClicked;
        this.context = context;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_info_layout, parent, false);
        return new CountryViewHolder(view, onCountryClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        CountryEntity entity = countryEntities.get(position);
        holder.name.setText(entity.getName());
        holder.capital.setText(entity.getCapital());
        holder.region.setText(String.format("Region - %s", entity.getRegion()));
        holder.subRegion.setText(String.format("Sub Region - %s", entity.getSubRegion()));
        holder.population.setText(String.format("Population - %s", entity.getPopulation()));
        holder.language.setText(String.format("Languages - %s", entity.getLanguage()));
        holder.border.setText(String.format("Borders - %s", entity.getBorder()));
        Uri uri = Uri.parse(entity.getFlag());
        try {
            Glide.with(context).load(uri).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_launcher_round)
                    .into(holder.country_flag);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCountryInfo(List<CountryEntity> newEntities){
        countryEntities.clear();
        countryEntities.addAll(newEntities);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return countryEntities.size();
    }

    public static class CountryViewHolder extends RecyclerView.ViewHolder{
        ImageView country_flag;
        TextView name, capital, region, subRegion, population, language, border;
        LinearLayout touchLayout;
        RelativeLayout expandableLayout;
        OnCountryClicked onCountryClicked;
        ImageView drop_down_arrow;
        public CountryViewHolder(@NonNull View itemView, OnCountryClicked onCountryClicked) {
            super(itemView);
            country_flag = itemView.findViewById(R.id.country_flag);
            name = itemView.findViewById(R.id.country_name);
            capital = itemView.findViewById(R.id.country_capital);
            region = itemView.findViewById(R.id.region);
            subRegion = itemView.findViewById(R.id.subRegion);
            population = itemView.findViewById(R.id.population);
            language = itemView.findViewById(R.id.language);
            border = itemView.findViewById(R.id.border);
            touchLayout = itemView.findViewById(R.id.touchLayout);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            drop_down_arrow = itemView.findViewById(R.id.drop_down_arrow);
            this.onCountryClicked = onCountryClicked;
            touchLayout.setOnClickListener(view -> onCountryClicked.OnItemClicked(touchLayout, expandableLayout, drop_down_arrow));
        }
    }
    public interface OnCountryClicked{
        void OnItemClicked(LinearLayout linearLayout, RelativeLayout relativeLayout, ImageView drop_down_arrow);
    }
}
