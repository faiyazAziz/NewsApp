package com.example.recyclerview;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerNewsAdapter extends FragmentStateAdapter {


    public ViewPagerNewsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public Fragment createFragment(int position) {
        if(position==0)
            return new HomeFragment();
        else if(position==1)
            return new SportFragment();
        else if(position==2)
            return new BusinessFragment();
        else if(position==3)
            return new WorldFragment();
        else if (position==4)
            return new EnvironmentFragment();
        else if(position==5)
            return new ScienceFragment();
        else
            return new FilmFragment();
    }

    @Override
    public int getItemCount() {
        return 7;
    }

}
