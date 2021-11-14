package com.mahariaz.i181652_180681;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabLayoutAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    public TabLayoutAdapter(Context context, FragmentManager fm,int totalTabs){
        super(fm);
        myContext=context;
        this.totalTabs=totalTabs;
    }
    //This is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                home_fragment homeFragment=new home_fragment();
                return homeFragment;
            case 1:
                cam_fragment camFragment=new cam_fragment();
                return camFragment;
            case 2:
                call_fragment callFragment=new call_fragment();
                return callFragment;
            case 3:
                new_grp_cnt_fragment group_contact=new new_grp_cnt_fragment();
                return group_contact;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
