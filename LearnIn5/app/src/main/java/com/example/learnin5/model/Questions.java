package com.example.learnin5.model;

public class Questions {
    private String question;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String answer;
    private String correct;

   public Questions(String[] array) {
        this.question = array[0];
        this.choiceA = array[1];
        this.choiceB = array[2];
        this.choiceC = array[3];
        this.choiceD = array[4];
        this.answer = array[5];
        this.correct = array[6];
    }
    public String getQuestion() { return question; }

    public String getChoiceA() {
        return choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setCorrect(String correct) { this.correct = correct; }

    public String getCorrect() { return correct; }
}
