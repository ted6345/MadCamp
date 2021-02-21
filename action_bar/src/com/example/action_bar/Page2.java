package com.example.action_bar;

import java.util.ArrayList;

import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Page2 extends Fragment {

	ArrayList<String> imagelist = new ArrayList<String>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.page2, container, false);
		final int tag = ((MainActivity) getActivity()).getPageTag();
		switch (tag) {
		case 0:
			imagelist.add("cat1");
			imagelist.add("cat2");
			imagelist.add("cat3");
			imagelist.add("cat4");
			imagelist.add("cat5");
			imagelist.add("cat6");
			imagelist.add("cat7");
			imagelist.add("cat8");
			imagelist.add("cat9");
			imagelist.add("cat10");
			imagelist.add("cat11");
			break;
			
		case 1:
			imagelist.add("dog1");
			imagelist.add("dog2");
			imagelist.add("dog3");
			imagelist.add("dog4");
			imagelist.add("dog5");
			imagelist.add("dog6");
			imagelist.add("dog7");
			imagelist.add("dog8");
			imagelist.add("dog9");
			imagelist.add("dog10");
			imagelist.add("dog11");
			break;
		}
		
		

		final Gallery gallery = (Gallery) view.findViewById(R.id.gallery1);
		final ImageAdapter adapter = new ImageAdapter(getActivity());
		gallery.setAdapter(adapter);

		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view1,
					final int position, long id) {

				ImageView imageView = (ImageView) view
						.findViewById(R.id.imageView1);
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				String resName = "@drawable/" + imagelist.get(position);
				String packName = getActivity().getBaseContext()
						.getPackageName();
				final int resid = getActivity().getBaseContext().getResources()
						.getIdentifier(resName, "drawable", packName);
				imageView.setImageResource(resid);

				Button deletebutton = (Button) view.findViewById(R.id.delete);

				deletebutton.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {

						Toast.makeText(getActivity(), "Remove the image",
								Toast.LENGTH_SHORT).show();
						imagelist.remove(position);
						adapter.notifyDataSetChanged();
						gallery.setAdapter(adapter);
						
					}
				});



			}
		});

		return view;
	}

	public class ImageAdapter extends BaseAdapter {

		private int itemBackground;
		private Context context;

		public ImageAdapter(Context c) {
			context = c;
			TypedArray a = getActivity().obtainStyledAttributes(
					R.styleable.gallery1);
			itemBackground = a.getResourceId(
					R.styleable.gallery1_android_galleryItemBackground, 0);
			a.recycle();
		}

		public int getCount() {
			return imagelist.size();
		}

		public Integer getItem(int position) {
			String resName = "@drawable/" + imagelist.get(position);
			String packName = getActivity().getBaseContext().getPackageName();
			int resid = getActivity().getBaseContext().getResources()
					.getIdentifier(resName, "drawable", packName);

			return resid;
		}

		public Integer getItemid(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(getItem(position));
			imageView.setLayoutParams(new Gallery.LayoutParams(100, 80));
			imageView.setBackgroundResource(itemBackground);
			return imageView;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
}
