package com.example.sensorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SensorActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private List<Sensor> sensorList;
    private RecyclerView recyclerView;
    private SensorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_activity);

        recyclerView = findViewById(R.id.sensor_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        if (adapter == null) {
            adapter = new SensorAdapter(sensorList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

    }

    private class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorHolder> {

        private final List<Sensor> mValues;

        public SensorAdapter(List<Sensor> items) {
            mValues = items;
        }

        @Override
        public SensorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflate = LayoutInflater.from(parent.getContext());
            return new SensorHolder(inflate, parent);
        }

        @Override
        public void onBindViewHolder(final SensorHolder holder, int position) {
            Sensor sensor = sensorList.get(position);
            holder.bind(sensor);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class SensorHolder extends RecyclerView.ViewHolder {
            private TextView textView;

            public SensorHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.sensor_list_item, parent, false));

                textView = itemView.findViewById(R.id.firstLine);
            }

            public void bind(Sensor sensor) {
                textView.setText(sensor.getName());
            }
        }
    }
}