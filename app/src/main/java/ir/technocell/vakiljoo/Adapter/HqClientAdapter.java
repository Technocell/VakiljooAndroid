package ir.technocell.vakiljoo.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import ir.technocell.vakiljoo.DataModel.MyQuestionModel;
import ir.technocell.vakiljoo.R;

public class HqClientAdapter extends RecyclerView.Adapter<HqClientAdapter.HqClientViewHolder> {
    private List<MyQuestionModel> questionList;

    public HqClientAdapter(List<MyQuestionModel> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public HqClientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_questions, viewGroup, false);
        return new HqClientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HqClientViewHolder hqClientViewHolder, int i) {
        MyQuestionModel myQuestionModel = questionList.get(i);
        hqClientViewHolder.profile.setText(myQuestionModel.getProfile());
        hqClientViewHolder.title.setText(myQuestionModel.getTitle());
        hqClientViewHolder.content.setText(myQuestionModel.getContent());
        hqClientViewHolder.date.setText(myQuestionModel.getDate());
        hqClientViewHolder.group.setText(myQuestionModel.getGroup());

    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }


    public class HqClientViewHolder extends RecyclerView.ViewHolder {

        private TextView profile;
        private TextView title;
        private TextView content;
        private TextView date;
        private TextView group;

        public HqClientViewHolder(@NonNull View itemView) {
            super(itemView);
            this.profile = itemView.findViewById(R.id.mUserImage);
            this.title = itemView.findViewById(R.id.mTitle);
            this.content = itemView.findViewById(R.id.mText);
            this.date = itemView.findViewById(R.id.mDate);
            this.group = itemView.findViewById(R.id.mGroup);
        }
    }

}
