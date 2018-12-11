package ir.technocell.vakiljoo.RecyclerAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.technocell.vakiljoo.R;
import ir.technocell.vakiljoo.RecyclerItems.MySoalsItem;

public class MyQuestionAdapter  extends  RecyclerView.Adapter<MyQuestionAdapter.MyViewHolder>{

    private List<MySoalsItem> QuestionsList;

    public MyQuestionAdapter(List<MySoalsItem> questionsList) {
        QuestionsList = questionsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_questions, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            MySoalsItem mySoalsItem=QuestionsList.get(i);
        myViewHolder.mTitle.setText(mySoalsItem.getTitle());
        myViewHolder.mText.setText(mySoalsItem.getText());
        myViewHolder.mGroup.setText(mySoalsItem.getGroup());
        myViewHolder.mDate.setText(mySoalsItem.getDate());
        Picasso.get().load(mySoalsItem.getImageUrl().toString()).into(myViewHolder.imageView);


    }

    @Override
    public int getItemCount() {
        return QuestionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle,mText,mGroup,mDate;
        private CircleImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mDate=itemView.findViewById(R.id.mDate);
            mGroup=itemView.findViewById(R.id.mGroup);
            mText=itemView.findViewById(R.id.mText);
            mTitle=itemView.findViewById(R.id.mTitle);
            imageView=itemView.findViewById(R.id.mUserImage);


        }
    }
}
