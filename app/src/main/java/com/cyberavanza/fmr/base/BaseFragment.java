package com.cyberavanza.fmr.base;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Talha khan on 9/23/2016.
 */

public class BaseFragment extends Fragment {

    private String  setTitle = "Page";
    private View fragmentView = null;

    public String getTitle() {
        return setTitle;
    }

    public void setTitle(String setTitle) {
        this.setTitle = setTitle;
    }

    public void setFragmentView(View fragmentView) {
        this.fragmentView = fragmentView;
    }

    public View getFragmentView() {
        return fragmentView;
    }

    public View findViewById(int viewId){
        return fragmentView.findViewById(viewId);
    }


}