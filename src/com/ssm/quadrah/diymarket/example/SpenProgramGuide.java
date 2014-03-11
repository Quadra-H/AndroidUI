package com.ssm.quadrah.diymarket.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ssm.quadrah.diymarket.R;

public class SpenProgramGuide extends Activity {

    private ListAdapter mListAdapter = null;
    private ListView mListView = null;

    // The item of list
    private static final int QH_FRAME = 0;
    private static final int QH_STICKER = 1;
    private static final int QH_LAYOUT = 2;
    private static final int QH_BACKGROUND = 3;

    private static final int TOTAL_LIST_NUM = 4;

    private final String EXAMPLE_NAMES[] = {
            "frame",
            "sticker",
            "layout",
            "background",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spensdk_demo);

        createUI();
    }

    private void createUI() {

        TextView textTitle = (TextView) findViewById(R.id.title);
        textTitle.setText("DIY market sample");

        mListAdapter = new ListAdapter(this);
        mListView = (ListView) findViewById(R.id.demo_list);
        mListView.setAdapter(mListAdapter);

        mListView.setItemsCanFocus(false);
        mListView.setTextFilterEnabled(true);
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // S Pen SDK Demo programs
                if (position == QH_FRAME) {
                    Intent intent = new Intent(SpenProgramGuide.this, QHSample_Frame.class);
                    startActivity(intent);
                } else if (position == QH_STICKER) {
                    Intent intent = new Intent(SpenProgramGuide.this, QHSample_Sticker.class);
                    startActivity(intent);
                } else if (position == QH_LAYOUT) {
                    Intent intent = new Intent(SpenProgramGuide.this, QHSample_Layout.class);
                    startActivity(intent);
                } else if (position == QH_BACKGROUND) {
                    Intent intent = new Intent(SpenProgramGuide.this, QHSample_Background.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public class ListAdapter extends BaseAdapter {

        public ListAdapter(Context context) {
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                final LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.spensdk_demolist_item,
                        parent, false);
            }
            // UI Item
            TextView tvListItemText = (TextView) convertView
                    .findViewById(R.id.listitemText);
            tvListItemText.setTextColor(0xFFFFFFFF);

            // ==================================
            // basic data display
            // ==================================
            if (position < TOTAL_LIST_NUM) {
                tvListItemText.setText(EXAMPLE_NAMES[position]);
            }

            return convertView;
        }

        public void updateDisplay() {
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return TOTAL_LIST_NUM;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
