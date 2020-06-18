package com.example.kanakku.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kanakku.R;
import com.example.kanakku.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<String> memberDocIDList;

    public interface  OnMemberSelectedListener {
        void onMemberSelected(int clickedItemIndex);
    }

    private OnMemberSelectedListener mListener;

    public MainAdapter(List<String> list, OnMemberSelectedListener listener) {
        memberDocIDList = list;
        mListener = listener;
    }

    public List<String> getMemberDocIDList() {
        return memberDocIDList;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MainAdapter.ViewHolder(inflater.inflate(R.layout.item_member, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if ((memberDocIDList == null) || (memberDocIDList.isEmpty())) {
            return 0;
        }
        return memberDocIDList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView memberName;

        public ViewHolder(View itemView) {
            super(itemView);
            memberName = itemView.findViewById(R.id.member_name);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            String memberDocID = memberDocIDList.get(position);
            FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
            DocumentReference reference = mFirestore.collection("users").document(memberDocID);
            reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot snapshot) {
                    User member = snapshot.toObject(User.class);
                    memberName.setText(member.getName());
                }
            });
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mListener.onMemberSelected(clickedPosition);
        }
    }
}
