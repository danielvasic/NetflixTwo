package ba.sum.fpmoz.netflixtwo.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import ba.sum.fpmoz.netflixtwo.fragments.*;

public class MoviesViewPagerAdapter extends FragmentStateAdapter {

    public MoviesViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        switch (position) {
            case 0:
                return new MovieFragment();
            case 1:
                return new AddMovieDialogFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Number of tabs
    }
}
