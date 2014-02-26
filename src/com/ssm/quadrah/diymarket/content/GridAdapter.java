package com.ssm.quadrah.diymarket.content;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ssm.quadrah.diymarket.R;

public class GridAdapter extends BaseAdapter {

	Activity context;
	int images[] = { R.drawable.item1, R.drawable.item2,
			R.drawable.item3, R.drawable.item4, R.drawable.item5,
			R.drawable.item6, R.drawable.item7, R.drawable.item8,
			R.drawable.item9, R.drawable.item7, R.drawable.item8,
			R.drawable.item9};



	public class ViewHolder {
		public ImageView imageView;
		//public TextView textTitle;
	}
	ViewHolder viewHolder;
	private GridItems[] items;
	private LayoutInflater mInflater;

	public GridAdapter(Activity context, GridItems[] locations) {

		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		items = locations;

	}

	public GridItems[] getItems() {
		return items;
	}

	public void setItems(GridItems[] items) {
		this.items = items;
	}

	@Override
	public int getCount() {
		if (items != null) {
			return items.length;
		}
		return 0;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		if (items != null && position >= 0 && position < getCount()) {
			return items[position];
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (items != null && position >= 0 && position < getCount()) {
			return items[position].id;
		}
		return 0;
	}

	public void setItemsList(GridItems[] locations) {
		this.items = locations;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;


		if (view == null) {

			view = mInflater.inflate(R.layout.grid_custom, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) view
					.findViewById(R.id.grid_item_image);
			//viewHolder.textTitle = (TextView) view.findViewById(R.id.grid_item_label);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		GridItems gridItems = items[position];
		setCatImage(position, viewHolder, gridItems.title);


		return view;
	}

	private void setCatImage(int pos, ViewHolder viewHolder, String catTitle) {
		viewHolder.imageView.setImageResource(images[pos]);	 
		//viewHolder.textTitle.setText(catTitle);
	}
} 