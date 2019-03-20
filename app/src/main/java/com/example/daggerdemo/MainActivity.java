package com.example.daggerdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.support.v4.media.MediaDescriptionCompat;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private final static int REQ_CODE_PERMISSION = 1;
    MainContract.Presenter mPresenter;

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.msg)
    TextView textView;

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new MainPresenter(new Model(getApplicationContext()));
        getLifecycle().addObserver(mPresenter);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (checkPermission()) {
            mPresenter.loadData();
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
            boolean hasPermission = ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
            if (!hasPermission) {
                requestPermissions(new String[]{permission}, REQ_CODE_PERMISSION);
            }
            return hasPermission;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_CODE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.loadData();
            } else {
                TopUtilKt.showToast(this, "缺少权限!");
            }
        }
    }

    @Override
    public void onLoadSuccess(@NonNull List<MediaDescriptionCompat> list) {
        textView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        adapter.setData(list);
    }

    @Override
    public void onLoadFail(String msg) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(msg);
        listView.setVisibility(View.GONE);
        adapter.setData(null);
    }

    static class MyAdapter extends BaseAdapter {
        @Nullable
        List<MediaDescriptionCompat> mmList;

        void setData(List<MediaDescriptionCompat> list) {
            if (mmList != null) {
                mmList.clear();
            }
            mmList = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mmList == null ? 0 : mmList.size();
        }

        @Override
        public MediaDescriptionCompat getItem(int position) {
            return mmList == null ? null : mmList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            VH holder = null;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), android.R.layout.simple_list_item_2, null);
                convertView.setTag(holder = new VH(convertView));
            } else {
                holder = (VH) convertView.getTag();
            }
            if (holder != null) {
                MediaDescriptionCompat md = getItem(position);
                holder.text1.setText(md.getTitle());
                holder.text2.setText(md.getDescription());
                TopUtilKt.log("title " + md.getTitle() + ",,, " + md.getDescription(), "df");
            }
            return convertView;
        }
    }

    static class VH {
        @BindView(android.R.id.text1)
        TextView text1;
        @BindView(android.R.id.text2)
        TextView text2;

        VH(View root) {
            ButterKnife.bind(this, root);
        }
    }

}
