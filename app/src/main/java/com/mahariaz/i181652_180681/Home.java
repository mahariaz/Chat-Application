package com.mahariaz.i181652_180681;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //bottom nav
        BottomNavigationView btnNav=findViewById(R.id.bottomNavigationView);
        btnNav.setOnNavigationItemSelectedListener(navListner);

        //setting home fragment as main fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout,new home_fragment()).commit();
    }
    // listner nav bar
    private BottomNavigationView.OnNavigationItemSelectedListener navListner=new
            BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item){
                    Fragment selectedFragment=null;
                    switch(item.getItemId()){
                        case R.id.item1:
                            selectedFragment=new call_fragment();
                            break;
                        case R.id.item2:
                            selectedFragment=new cam_fragment();
                            break;
                        case R.id.item3:
                            selectedFragment=new home_fragment();
                            break;
                        case R.id.item4:
                            selectedFragment=new new_grp_cnt_fragment();
                            break;

                    }
                    // begin transaction
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_layout
                                    ,selectedFragment).commit();
                    return true;


                }
            };

}