/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

    public List<String> weatherDataList = new ArrayList<>();

    final private ForecastAdapterOnClickHandler mClickHandler;


    public interface ForecastAdapterOnClickHandler {
        void onClick(String weatherForDay);
    }

    public ForecastAdapter(ForecastAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public final TextView mWeatherTextView;

        public ForecastAdapterViewHolder(View view) {
            super(view);
            mWeatherTextView = (TextView) view.findViewById(R.id.tv_weather_data);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String weatherForDay = weatherDataList.get(adapterPosition);
            mClickHandler.onClick(weatherForDay);
        }
    }

    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.forecast_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new ForecastAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder forecastAdapterViewHolder, int position) {
        String weatherForThisDay = weatherDataList.get(position);
        forecastAdapterViewHolder.mWeatherTextView.setText(weatherForThisDay);
    }

    @Override
    public int getItemCount() {
        if (weatherDataList == null) {
            return 0;
        } else {
            return weatherDataList.size();
        }
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        // Collections.swap() 該方法是用來交換位置
        Collections.swap(weatherDataList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public void removeItem(int position) {
        weatherDataList.remove(position);
        // notify the item removed by position
        notifyItemRemoved(position);
    }

    public void setWeatherData(List<String> weatherData) {
        weatherDataList = weatherData;
        notifyDataSetChanged();
    }
}