package com.cctpl.fooddeliveryadmin.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.palettes.RangeColors;
import com.cctpl.fooddeliveryadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingFragment extends Fragment {

    Button mBtnEdit;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String UserId;

    CircleImageView mProfileImg;
    TextView mUserName,mMobileNumber;
    int pending,complete,cancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        UserId = firebaseAuth.getCurrentUser().getUid();
        mBtnEdit = view.findViewById(R.id.btnEdit);
        mProfileImg = view.findViewById(R.id.profileImg);
        mUserName = view.findViewById(R.id.userName);
        mMobileNumber = view.findViewById(R.id.mobileNumber);


        //Edit profile
        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditProfileFragment();
                getFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });

        firebaseFirestore.collection("Users").document(UserId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            String UserName = task.getResult().getString("UserName");
                            String MobileNumber = task.getResult().getString("MobileNumber");
                            String ProfileImg = task.getResult().getString("ProfileImgUrl");

                            mUserName.setText(UserName);
                            mMobileNumber.setText(MobileNumber);

                            if (!TextUtils.isEmpty(ProfileImg)){
                                Picasso.get().load(ProfileImg).into(mProfileImg);
                            }
                        }
                    }
                });

        firebaseFirestore.collection("Orders").whereEqualTo("Status","Pending")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        pending = value.size();
                    }
                });
        firebaseFirestore.collection("Orders").whereEqualTo("Status","Complete")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        complete = value.size();
                    }
                });
        firebaseFirestore.collection("Orders").whereEqualTo("Status","Cancel")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        cancel = value.size();
                    }
                });

//        AnyChartView anyChartView = view.findViewById(R.id.anyChart);
//        Pie pie = AnyChart.pie();
//        List<DataEntry> data = new ArrayList<>();
//        data.add(new ValueDataEntry("Pending",pending));
//        data.add(new ValueDataEntry("Complete",complete));
//        data.add(new ValueDataEntry("Cancel",cancel));
//
//        pie.data(data);
//        anyChartView.setChart(pie);

        return view;
    }
}