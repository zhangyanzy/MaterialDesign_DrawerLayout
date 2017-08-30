package com.zhangyan.materialdesign_drawerlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private boolean isDrawer = false;
    private RelativeLayout mToolBarRight;
    private NavigationView mNavigationViewLeft;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        initToolBar(R.id.toolbar, R.id.toolbar_title, R.string.titleString);
    }

    private Toolbar initToolBar(int id, int titleId, int titleString) {
        Toolbar toolbar = (Toolbar) findViewById(id);
        TextView textView = (TextView) findViewById(titleId);
        textView.setText(titleString);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }


    private void initView() {
        mImageView = (ImageView) findViewById(R.id.imageView_toolbar_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mToolBarRight = (CoordinatorLayout) findViewById(R.id.right);
        mToolBarRight = (RelativeLayout) findViewById(R.id.right);
        mNavigationViewLeft = (NavigationView) findViewById(R.id.navigationView);
        mNavigationViewLeft.setNavigationItemSelectedListener(this);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }


    private void initEvent() {

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(mNavigationViewLeft);


            }
        });
        mToolBarRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (isDrawer) {
                    return mNavigationViewLeft.dispatchTouchEvent(motionEvent);
                } else {
                    return false;
                }
            }
        });
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                isDrawer = true;
                //获取屏幕的宽高
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                mToolBarRight.layout(mNavigationViewLeft.getRight(), 0, mNavigationViewLeft.getRight() + display.getWidth(), display.getHeight());
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawer = false;

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_gallery:
                Toast.makeText(getApplicationContext(), "相机", Toast.LENGTH_SHORT).show();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
