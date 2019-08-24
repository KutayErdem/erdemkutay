package com.example.learnin5.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.learnin5.R;
import com.example.learnin5.model.Questions;

import java.util.ArrayList;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private ArrayList<Questions> questions;
    public QuestionsAdapter(ArrayList<Questions> questions) { this.questions = questions; }

    // Create new views (invoked by the layout manager)
    @Override
    public QuestionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
       View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_questions, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Questions _questions = questions.get(position);
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        viewHolder.ViewTitle.setText(_questions.getQuestion());
        viewHolder.ViewButtonA.setText(_questions.getChoiceA());
        viewHolder.ViewButtonB.setText(_questions.getChoiceB());
        viewHolder.ViewButtonC.setText(_questions.getChoiceC());
        viewHolder.ViewButtonD.setText(_questions.getChoiceD());

        viewHolder.ViewButtonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.ViewButtonA.setClickable(false);
                viewHolder.ViewButtonB.setClickable(false);
                viewHolder.ViewButtonC.setClickable(false);
                viewHolder.ViewButtonD.setClickable(false);
                    if (viewHolder.ViewButtonA.getText() == _questions.getAnswer()) {
                        viewHolder.ViewButtonA.setBackgroundColor(GREEN);
                        questions.get(position).setCorrect("1");
                    } else {
                        viewHolder.ViewButtonA.setBackgroundColor(RED);
                        questions.get(position).setCorrect("-1");
                        if (viewHolder.ViewButtonB.getText() == _questions.getAnswer()) {
                            viewHolder.ViewButtonB.setBackgroundColor(GREEN);
                        } else if (viewHolder.ViewButtonC.getText() == _questions.getAnswer()) {
                            viewHolder.ViewButtonC.setBackgroundColor(GREEN);
                        } else if (viewHolder.ViewButtonD.getText() == _questions.getAnswer()) {
                            viewHolder.ViewButtonD.setBackgroundColor(GREEN);
                        }
                    }
            }
        });
        viewHolder.ViewButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.ViewButtonB.setClickable(false);
                viewHolder.ViewButtonA.setClickable(false);
                viewHolder.ViewButtonC.setClickable(false);
                viewHolder.ViewButtonD.setClickable(false);
                    if (viewHolder.ViewButtonB.getText() == _questions.getAnswer()) {
                        viewHolder.ViewButtonB.setBackgroundColor(GREEN);
                        questions.get(position).setCorrect("1");
                    } else {
                        viewHolder.ViewButtonB.setBackgroundColor(RED);
                        questions.get(position).setCorrect("-1");
                        if (viewHolder.ViewButtonA.getText() == _questions.getAnswer()) {
                            viewHolder.ViewButtonA.setBackgroundColor(GREEN);
                        } else if (viewHolder.ViewButtonC.getText() == _questions.getAnswer()) {
                            viewHolder.ViewButtonC.setBackgroundColor(GREEN);
                        } else if (viewHolder.ViewButtonD.getText() == _questions.getAnswer()) {
                            viewHolder.ViewButtonD.setBackgroundColor(GREEN);
                        }
                    }

            }
        });
        viewHolder.ViewButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.ViewButtonC.setClickable(false);
                viewHolder.ViewButtonA.setClickable(false);
                viewHolder.ViewButtonB.setClickable(false);
                viewHolder.ViewButtonD.setClickable(false);
                    if (viewHolder.ViewButtonC.getText() == _questions.getAnswer()) {
                        viewHolder.ViewButtonC.setBackgroundColor(GREEN);
                        questions.get(position).setCorrect("1");
                    } else {
                        viewHolder.ViewButtonC.setBackgroundColor(RED);
                        questions.get(position).setCorrect("-1");
                        if (viewHolder.ViewButtonA.getText() == _questions.getAnswer()) {
                            viewHolder.ViewButtonA.setBackgroundColor(GREEN);
                        } else if (viewHolder.ViewButtonB.getText() == _questions.getAnswer()) {
                            viewHolder.ViewButtonB.setBackgroundColor(GREEN);
                        } else if (viewHolder.ViewButtonD.getText() == _questions.getAnswer()) {
                            viewHolder.ViewButtonD.setBackgroundColor(GREEN);
                        }
                    }

            }
        });
        viewHolder.ViewButtonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.ViewButtonD.setClickable(false);
                viewHolder.ViewButtonA.setClickable(false);
                viewHolder.ViewButtonB.setClickable(false);
                viewHolder.ViewButtonC.setClickable(false);
                    if (viewHolder.ViewButtonD.getText() == _questions.getAnswer()) {
                        viewHolder.ViewButtonD.setBackgroundColor(GREEN);
                        questions.get(position).setCorrect("1");
                    } else {
                        viewHolder.ViewButtonD.setBackgroundColor(RED);
                        questions.get(position).setCorrect("-1");
                        if (viewHolder.ViewButtonA.getText() == _questions.getAnswer()) {
                            viewHolder.ViewButtonA.setBackgroundColor(GREEN);
                        } else if (viewHolder.ViewButtonB.getText() == _questions.getAnswer()) {
                            viewHolder.ViewButtonB.setBackgroundColor(GREEN);
                        } else if (viewHolder.ViewButtonC.getText() == _questions.getAnswer()) {
                            viewHolder.ViewButtonC.setBackgroundColor(GREEN);
                        }
                    }


            }
        });
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView ViewTitle;
        public Button ViewButtonA;
        public Button ViewButtonB;
        public Button ViewButtonC;
        public Button ViewButtonD;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            ViewButtonA = (Button) itemLayoutView.findViewById(R.id.item_buttonA);
            ViewButtonB = (Button) itemLayoutView.findViewById(R.id.item_buttonB);
            ViewButtonC = (Button) itemLayoutView.findViewById(R.id.item_buttonC);
            ViewButtonD = (Button) itemLayoutView.findViewById(R.id.item_buttonD);
        }
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() { return questions.size(); }
}