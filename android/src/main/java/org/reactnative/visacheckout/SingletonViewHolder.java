package org.reactnative.visacheckout;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jgfidelis on 09/02/18.
 */

public class SingletonViewHolder {

    private static final SingletonViewHolder instance = new SingletonViewHolder();

    private SingletonViewHolder() {
//        myViewTags = new ArrayList<Integer>;
    }

    private ArrayList<Integer> myViewTags; //TODO implement this

    private Map<Integer, Integer> viewTagToCodeMap = new ConcurrentHashMap<>();//view tag to view code //TODO use this

    private RNVisaCheckoutButtonView mView;

    public static SingletonViewHolder getInstance() {
        return instance;
    }

    public void setView(RNVisaCheckoutButtonView view) {
        mView = view;
    }

    public RNVisaCheckoutButtonView getView() {
        return mView;
    }
}
