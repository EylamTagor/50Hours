package com.fiftyhours.ui.drive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fiftyhours.R;

public class DriveFragment extends Fragment {

    private DriveViewModel driveViewModel;
    private TextView dailyGoal;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        driveViewModel =
                ViewModelProviders.of(this).get(DriveViewModel.class);
        View root = inflater.inflate(R.layout.fragment_drive, container, false);

        dailyGoal = root.findViewById(R.id.daily_goal);
        dailyGoal.setText(getDailyGoal());

        progressBar = root.findViewById(R.id.daily_progress_bar);
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setProgress(progressBar.getProgress() + 25);
            }
        });

        return root;
    }

    public String getDailyGoal() {
        return "1.5 hours, day";
    }
}