package com.example.jadynai.adapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewConfiguration;
import android.widget.ListView;

import com.example.adapterloli.ItemsAdapter;
import com.example.adapterloli.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private ListView mListView;

    private List<TestBean> mData;
    private ItemsAdapter mListAdapter;
    private RecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.listView2);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView3);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mListAdapter = new ItemsAdapter.Builder(this).putItemClass(TestItemView.class).build();
        mRecyclerAdapter = new RecyclerAdapter.Builder(this).putItemClass(TestItemView.class).build();
        mListView.setAdapter(mListAdapter);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        setData();

        
        Log.d(TAG, "initViews: " + ViewConfiguration.get(this).hasPermanentMenuKey());
    }

    private void setData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            TestBean testBean = new TestBean();
            testBean.s = "测试   " + i;
            mData.add(testBean);
        }
        mListAdapter.setData(mData);
        mRecyclerAdapter.setData(mData);
    }
}
