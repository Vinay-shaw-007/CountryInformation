package com.example.countryinformation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countryinformation.RoomArchitecture.CountryAdapter;
import com.example.countryinformation.RoomArchitecture.CountryViewModel;

public class MainActivity extends AppCompatActivity implements CountryAdapter.OnCountryClicked {

    private CountryAdapter mAdapter;
    private CountryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRecyclerView();//Initializing recycler view and setting it's adapter.
        setViewModel();//Initializing ViewModel and live data.
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(CountryViewModel.class);
        viewModel.getAllCountryInfo().observe(this, countryEntities -> {
            if (countryEntities != null){
                mAdapter.setCountryInfo(countryEntities);
            }
        });
        viewModel.fetchJSONData();
    }

    private void setRecyclerView() {
        RecyclerView mRecyclerView = findViewById(R.id.rvCountry);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CountryAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    //Handling expandable view and drop down arrow
    @Override
    public void OnItemClicked(LinearLayout linearLayout, RelativeLayout relativeLayout, ImageView drop_down_arrow) {
        int v = (relativeLayout.getVisibility() == View.GONE) ?  View.VISIBLE : View.GONE;
        relativeLayout.setVisibility(v);
        if (relativeLayout.getVisibility() == View.GONE){
            @SuppressLint("Recycle") ObjectAnimator animator = ObjectAnimator.ofFloat(drop_down_arrow, "rotation", 180f, 0f);
            animator.setDuration(500);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animator);
            animatorSet.start();
        }else {
            @SuppressLint("Recycle") ObjectAnimator animator = ObjectAnimator.ofFloat(drop_down_arrow, "rotation", 0f, 180f);
            animator.setDuration(500);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animator);
            animatorSet.start();
        }
    }

    //Initializing top menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Handling click functions of top menu delete button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteAll) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete All");
            builder.setMessage("Are you sure you want to delete all data?");
            builder.setPositiveButton("Delete", (dialogInterface, i) -> viewModel.deleteAll());
            builder.setNegativeButton("Cancel", null);
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
}