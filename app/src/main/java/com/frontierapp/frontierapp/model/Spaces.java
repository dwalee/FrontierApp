package com.frontierapp.frontierapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Spaces extends ArrayList<Space> implements Comparator<Space> {

    public void sort(){
        Collections.sort(this, this);
    }

    @Override
    public int compare(Space space, Space t1) {

        return space.getName().compareTo(t1.getName());
    }
}
