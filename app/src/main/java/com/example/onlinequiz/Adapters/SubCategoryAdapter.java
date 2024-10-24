package com.example.onlinequiz.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinequiz.Models.SubCategoryModel;

import com.example.onlinequiz.QuestionsActivity;
import com.example.onlinequiz.R;
import com.example.onlinequiz.databinding.RvSubcategoryDesignBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.viewHolder>{

    Context context;
    ArrayList<SubCategoryModel> list;

    private  String catId;
    private  String subCatId;


    public SubCategoryAdapter(Context context, ArrayList<SubCategoryModel> list, String catId) {
        this.context = context;
        this.list = list;
        this.catId = catId;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_subcategory_design, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        SubCategoryModel subCategoryModel = list.get(position);
        holder.binding.subCategoryName.setText(subCategoryModel.getCategoryName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionsActivity.class);
                intent.putExtra("catId", catId);
                intent.putExtra("subCatId", subCategoryModel.getKey());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public  class viewHolder extends  RecyclerView.ViewHolder{
        RvSubcategoryDesignBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding = RvSubcategoryDesignBinding.bind(itemView);
        }
    }
}
