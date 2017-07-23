package com.microprocessoremulator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MicroprocessorEmulator extends AppCompatActivity implements ExitDialog.Communicator,NewSubprocedureDialog.DialogCommunicator, GOTODialog.SeekCommunication{

    int currentPage=1;
    ViewPager pager;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microprocessor_emulator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MypagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(2);
        pager.setCurrentItem(1);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                switch (position) {
                    case 0:
                        fab.setImageResource(R.drawable.plus);
                        break;
                    case 1:
                        fab.setImageResource(R.drawable.execute);
                        break;
                    case 2:
                        fab.setImageResource(R.drawable.find);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment page;
                switch (currentPage) {
                    case 0:
                        page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":0");
                        ((SubprogramFragment)page).createNewSubprocedure();
                        break;
                    case 1:
                        page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":1");
                        ((EmulatorFragment)page).execute();
                        break;
                    case 2:
                        page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":2");
                        ((MemoryFragment)page).search();
                        break;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() != 1) {
            pager.setCurrentItem(1, true);
        } else {
            android.app.FragmentManager manager = getFragmentManager();
            ExitDialog exitDialog = new ExitDialog();
            exitDialog.show(manager, "Exit Dialog");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void dialogResult(boolean exit) {
        if(exit){
            finish();
        }
    }

    @Override
    public void respond(String memoryAddress, boolean result) {
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":0");
        ((SubprogramFragment)page).addSubprocedureView(memoryAddress, result);
    }

    @Override
    public void gotoLocation(int location) {
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":2");
        ((MemoryFragment)page).gotoLocation(location);
    }
}
class MypagerAdapter extends FragmentPagerAdapter {
    String[] titles;
    public MypagerAdapter(FragmentManager fm) {
        super(fm);
        titles = new String[]{"Tab 1", "Tab 2","Tab 3"};

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new SubprogramFragment();
                break;
            case 1:
                fragment = new EmulatorFragment();
                break;
            case 2:
                fragment = new MemoryFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}