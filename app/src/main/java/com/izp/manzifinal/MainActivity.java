package com.izp.manzifinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.izp.manzifinal.adapter.ManziPagerAdapter;
import com.izp.manzifinal.fragment.FragmentStats;
import com.izp.manzifinal.fragment.FragmentTodo;
import com.izp.manzifinal.fragment.FragmentWaste;
import com.izp.manzifinal.model.ItemTodo;
import com.izp.manzifinal.model.ItemWaste;
import com.izp.manzifinal.util.HidingScrollListener;
import com.izp.manzifinal.view.EmptySupportRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    /*界面*/
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TabLayout tabLayout;
    private NavigationView navigationView;
    private ViewPager viewPager;
    /*数据*/
    private ManziPagerAdapter pagerAdapter;
    private String[] titles;
    private List<Fragment> fragments;
    private FragmentTodo todo;
    private FragmentWaste waste;
    private FragmentStats stats;
    public FloatingActionButton fab;
    private int pagerID;
    private EmptySupportRecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MyAppTheme);
        setContentView(R.layout.activity_my);
        initView();
        initData();
        configView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (pagerID){
                    case 0:
                        Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                        ItemTodo itemTodo = new ItemTodo("",0,null,0,"");
                        intent.putExtra(FragmentTodo.TODO_ITEM,itemTodo);
                        startActivityForResult(intent,100);
                    break;
                    case 1:
                        Intent intentWaste = new Intent(MainActivity.this, AddWasteActivity.class);
                        ItemWaste itemWaste = new ItemWaste("",0,"");
                        intentWaste.putExtra(FragmentWaste.WASTE_ITEM,itemWaste);
                        startActivityForResult(intentWaste,200);
                }

            }
        });

    }

    public void initView(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerRoot);
        toolbar = (Toolbar) findViewById(R.id.toolbarContentMy);
        tabLayout = (TabLayout) findViewById(R.id.tabContentMy);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.inflateHeaderView(R.layout.navigation_header);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void initData() {
        titles = new String[]{"踌躇满志","混吃等死","一日三省"};
        fragments = new ArrayList<>();
        todo = new FragmentTodo();
        waste = new FragmentWaste();
        stats = new FragmentStats();
        fragments.add(0,todo);
        fragments.add(1,waste);
        fragments.add(2,stats);


    }

    private void configView() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("满志");
//        toolbar.setSubtitleTextColor(Color.WHITE);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.open,R.string.close);
        actionBarDrawerToggle.syncState();
        navigationView.inflateMenu(R.menu.menu_my);//navigationView的item的点击事件该会儿在control里面进行编写
        //初始化ViewPager的适配器，并设置给ViewPager
        pagerAdapter = new ManziPagerAdapter(getSupportFragmentManager(),titles,fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pagerID = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==100){
            todo.onActivityResult(requestCode,resultCode,data);
        }else if (requestCode == 200){
            waste.onActivityResult(requestCode,resultCode,data);
        }

    }
}
