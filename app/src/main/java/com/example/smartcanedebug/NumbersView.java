package com.example.smartcanedebug;

public class NumbersView {

    // the resource ID for the imageView
    private int ivNumbersImageId;

    // TextView 1


    // TextView 1
    private String mNumbersInText;

    // create constructor to set the values for all the parameters of the each single view
    public NumbersView(int  NumbersImageId, String NumbersInText) {
        ivNumbersImageId = NumbersImageId;

        mNumbersInText = NumbersInText;
    }

    // getter method for returning the ID of the imageview
    public int getNumbersImageId() {

        return ivNumbersImageId;
    }

    // getter method for returning the ID of the TextView 1


    // getter method for returning the ID of the TextView 2
    public String getNumbersInText() {
        return mNumbersInText;
    }
}
