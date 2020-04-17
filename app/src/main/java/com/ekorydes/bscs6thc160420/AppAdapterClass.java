package com.ekorydes.bscs6thc160420;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AppAdapterClass extends FirestoreRecyclerAdapter<AppModelClass,AppAdapterClass.AppViewHolderClass> {


    public AppAdapterClass(@NonNull FirestoreRecyclerOptions<AppModelClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final AppViewHolderClass appViewHolderClass, int i, @NonNull final AppModelClass appModelClass) {
        appViewHolderClass.userNameTV.setText(appModelClass.getName());
        appViewHolderClass.userStatusTV.setText(appModelClass.getStatus());

        appViewHolderClass.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(appViewHolderClass.iv.getContext(), "Image is clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public AppViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View varOfView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item,parent,false);
        AppViewHolderClass varOfAppViewHolderClass=new AppViewHolderClass(varOfView);
        return varOfAppViewHolderClass;
    }

    class AppViewHolderClass extends RecyclerView.ViewHolder
    {

        TextView userNameTV,userStatusTV;
        ImageView iv;
        public AppViewHolderClass(@NonNull View itemView) {
            super(itemView);

            userNameTV=itemView.findViewById(R.id.singleRow_userNameTV);
            userStatusTV=itemView.findViewById(R.id.singleRow_userStatusTV);

            iv=itemView.findViewById(R.id.singleRow_iv);
        }
    }

}
