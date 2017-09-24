package com.example.omar_salem.mymovie;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

/**
 * Created by Omar_Salem on 9/7/2017.
 */

public class Sittings extends PreferenceActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new SittingsFragment()).commit();
    }

   public static class SittingsFragment extends PreferenceFragment {
       @Override
       public void onCreate(final Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           addPreferencesFromResource(R.xml.preferences);
       }
   }
}
