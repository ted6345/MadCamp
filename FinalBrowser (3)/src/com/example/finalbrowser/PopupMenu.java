package com.example.finalbrowser;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.finalbrowser.htmlview.HtmlModify;
import com.example.finalbrowser.htmlview.JSInterpreter;
import com.example.finalbrowser.menu.MenuAdapter;
import com.example.finalbrowser.menu.MenuData;
import com.example.finalbrowser.menu.MenuData.Menu;
import com.example.finalbrowser.normal.History;
import com.example.finalbrowser.tool.DrawView;
import com.kakao.KakaoLink;
import com.kakao.KakaoTalkLinkMessageBuilder;

public class PopupMenu extends PopupWindow {
	public static Context mContext;
	private ImageView mCursor;
	private int mCursorWidth;
	CustomWebView web;
	private boolean isNight;
	public boolean isMobile;

	public PopupMenu(Context context) {
		super(context);
		mContext = context;
		web = ((MainActivity) mContext).getWeb();
		isNight = false;
		isMobile = true;
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final LinearLayout popupMenu = (LinearLayout) inflater.inflate(
				R.layout.popup_menu, null);

		final ViewPager menuViewPager = (ViewPager) popupMenu
				.findViewById(R.id.menu_viewpager);
		mCursor = (ImageView) popupMenu.findViewById(R.id.cursor);
		LinearLayout cursorBg = (LinearLayout) popupMenu
				.findViewById(R.id.cursor_bg);
		TextView normalTitle = (TextView) popupMenu
				.findViewById(R.id.normal_title);
		TextView settingTitle = (TextView) popupMenu
				.findViewById(R.id.setting_title);
		TextView toolTitle = (TextView) popupMenu.findViewById(R.id.tool_title);

		cursorBg.setBackgroundColor(Color.DKGRAY);
		mCursor.setImageDrawable(new ColorDrawable(Color.WHITE));
		DisplayMetrics disp = mContext.getResources().getDisplayMetrics();
		mCursorWidth = disp.widthPixels / 3;
		LayoutParams lp = mCursor.getLayoutParams();
		lp.width = mCursorWidth;

		normalTitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menuViewPager.setCurrentItem(0, true);
			}
		});
		settingTitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menuViewPager.setCurrentItem(1, true);
			}
		});
		toolTitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menuViewPager.setCurrentItem(2, true);
			}
		});

		final MenuData menuData = new MenuData(mContext);
		ArrayList<View> arrayList = new ArrayList<View>();
		final MenuAdapter temp;

		GridView gridViewNormal = (GridView) inflater.inflate(
				R.layout.menu_gridview, null);
		gridViewNormal.setAdapter((temp = new MenuAdapter(mContext, menuData
				.getMenuList(MenuData.MENU_TYPE_NORMAL))));

		gridViewNormal.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				NormalMenuHandler(position, menuData, temp);
			}
		});
		arrayList.add(gridViewNormal);

		GridView gridViewSetting = (GridView) inflater.inflate(
				R.layout.menu_gridview, null);
		gridViewSetting.setAdapter(new MenuAdapter(mContext, menuData
				.getMenuList(MenuData.MENU_TYPE_SETTING)));
		gridViewSetting.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SettingMenuHandler(position);
			}
		});
		arrayList.add(gridViewSetting);

		GridView gridViewTool = (GridView) inflater.inflate(
				R.layout.menu_gridview, null);
		gridViewTool.setAdapter(new MenuAdapter(mContext, menuData
				.getMenuList(MenuData.MENU_TYPE_TOOL)));
		gridViewTool.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ToolMenuHandler(position);
			}
		});
		arrayList.add(gridViewTool);

		menuViewPager.setAdapter(new MenuViewPagerAdapter(arrayList));
		menuViewPager.setOnPageChangeListener(new MenuOnPageChangeListener());

		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(mContext.getResources().getDimensionPixelSize(
				R.dimen.popupmenu_height));
		setFocusable(true);
		setContentView(popupMenu);

		popupMenu.setFocusableInTouchMode(true);
		popupMenu.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_MENU && isShowing()) {
					dismiss();
					return true;
				}
				return false;
			}
		});

	}

	private class MenuViewPagerAdapter extends PagerAdapter {
		public ArrayList<View> arrayList;

		public MenuViewPagerAdapter(ArrayList<View> arrayList) {
			this.arrayList = arrayList;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(arrayList.get(arg1));
		}

		@Override
		public int getCount() {
			return arrayList.size();
		}

		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(arrayList.get(position), 0);
			return arrayList.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	private class MenuOnPageChangeListener implements OnPageChangeListener {
		private float startOffset = 0.0f;
		private float endOffset = 0.0f;

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			endOffset = position * mCursorWidth + mCursorWidth * positionOffset;

			Animation animation = new TranslateAnimation(startOffset,
					endOffset, 0, 0);
			animation.setFillAfter(true);
			animation.setDuration(0);
			mCursor.startAnimation(animation);

			startOffset = endOffset;
		}

		@Override
		public void onPageSelected(int arg0) {
		}

	}

	@SuppressWarnings("deprecation")
	private void NormalMenuHandler(int position, MenuData menudata,
			MenuAdapter menuad) {
		switch (position) {
		case 0: // 즐겨찾기
			((MainActivity) mContext).showBookmarkDialog();
			break;
		case 1: // 즐겨찾기 추가
			((MainActivity) mContext).showAddBookmarkDialog();
			break;
		case 2: // 야간 모드
			if (isNight) {
				isNight = false;
				ArrayList<Menu> temp = menudata
						.getMenuList(MenuData.MENU_TYPE_NORMAL);
				temp.get(position).iconId = R.drawable.menu_icon_nightmode_normal;// mContext.getResources().getIdentifier("@drawble/menu_nightmode_selected",
																					// "drawble",mContext.getPackageName());
				temp.get(position).title = "야간모드 해제";
				menuad.changeList(temp);
				WindowManager.LayoutParams params = ((MainActivity) mContext)
						.getWindow().getAttributes();
				System.out.println(params.screenBrightness);
				params.screenBrightness = (float) -1;
				((MainActivity) mContext).getWindow().setAttributes(params);

			} else {
				isNight = true;
				ArrayList<Menu> temp = menudata
						.getMenuList(MenuData.MENU_TYPE_NORMAL);
				temp.get(position).iconId = R.drawable.menu_icon_nightmode_selected;// mContext.getResources().getIdentifier("@drawble/menu_nightmode_selected",
																					// "drawble",mContext.getPackageName());
				temp.get(position).title = "야간모드";
				menuad.changeList(temp);
				WindowManager.LayoutParams params = ((MainActivity) mContext)
						.getWindow().getAttributes();
				System.out.println(params.screenBrightness);
				params.screenBrightness = (float) 0.01;
				((MainActivity) mContext).getWindow().setAttributes(params);

			}
			break;
		case 3: // 공유하기
			try {
				final KakaoLink kakaoLink = KakaoLink.getKakaoLink(mContext);
				final KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder = kakaoLink
						.createKakaoTalkLinkMessageBuilder();

				AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

				alert.setTitle("카카오톡으로 공유하기");
				alert.setMessage("링크와 함께 보낼 메시지를 작성해주세요.");

				// Set an EditText view to get user input
				final EditText input = new EditText(mContext);
				alert.setView(input);

				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String value = input.getText().toString();
								try {
									if (value.equals("") || value == null) {
										kakaoTalkLinkMessageBuilder.addText(web
												.getTitle()
												+ "\n( "
												+ web.getUrl() + " )");
									} else
										kakaoTalkLinkMessageBuilder
												.addText(value + "\n\n"
														+ web.getTitle()
														+ "\n( " + web.getUrl()
														+ " )");

									final String linkContents = kakaoTalkLinkMessageBuilder
											.build();
									kakaoLink.sendMessage(linkContents,
											mContext);
								} catch (Exception e) {
								}
							}
						});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Canceled.
							}
						});

				alert.show();

			} catch (Exception e) {
			}
			break;
		case 4: // 다운로드 목록 보기
			((MainActivity) mContext).startActivity(new Intent(
					DownloadManager.ACTION_VIEW_DOWNLOADS));

			break;
		case 5: // 홈 화면 설정
			((MainActivity) mContext).showHomeBookmarkDialog();
			break;
		case 6: // 모바일 <-> PC 보기
			if (isMobile) {
				isMobile = false;
				ArrayList<Menu> temp = menudata
						.getMenuList(MenuData.MENU_TYPE_NORMAL);
				temp.get(position).iconId = R.drawable.menu_icon_phone;// mContext.getResources().getIdentifier("@drawble/menu_nightmode_selected",
																		// "drawble",mContext.getPackageName());
				temp.get(position).title = "모바일버전 보기";
				menuad.changeList(temp);
				((MainActivity) mContext).isMobile = false;
				((MainActivity) mContext).mhome= false;
				((MainActivity) mContext).webInit();
				((MainActivity) mContext).reload();
				web.getSettings().setDefaultZoom(ZoomDensity.FAR);
			} else {
				isMobile = true;
				ArrayList<Menu> temp = menudata
						.getMenuList(MenuData.MENU_TYPE_NORMAL);
				temp.get(position).iconId = R.drawable.menu_icon_pc;// mContext.getResources().getIdentifier("@drawble/menu_nightmode_selected",
																	// "drawble",mContext.getPackageName());
				temp.get(position).title = "PC버전 보기";
				menuad.changeList(temp);
				((MainActivity) mContext).isMobile = true;
				((MainActivity) mContext).mhome= false;
				((MainActivity) mContext).webInit();
				((MainActivity) mContext).reload();
				// ((MainActivity) mContext).getWindow().setAttributes(params);

			}
			break;
		case 7: // 방문 기록
			this.dismiss();
			((MainActivity) mContext).startActivity(new Intent(mContext,
					History.class));
			break;
		case 8: // 나가기
			((MainActivity) mContext).finish();
			break;
		default:
			break;
		}
	}

	private void SettingMenuHandler(int position) {
		switch (position) {
		case 0:
			Intent customsetting = new Intent(((MainActivity) mContext),
					CustomSetting.class);
			((MainActivity) mContext).startActivity(customsetting);
			break;
		case 1:
			CustomWebView customWebView = new CustomWebView(BroApplication.getInstance());
			ViewFlipper mViewFlipper = BroApplication.getInstance()
					.getTabList();
			mViewFlipper.addView(customWebView);
			mViewFlipper.setDisplayedChild(mViewFlipper.getChildCount() - 1);
			TextView mTabNumTv = ((MainActivity) mContext).mTabNumTv;
			mTabNumTv.setText(mViewFlipper.getChildCount() + "");

			((MainActivity) mContext).setWeb((CustomWebView) mViewFlipper
					.getCurrentView());
			((MainActivity) mContext).webInit();
			this.dismiss();
			break;
		default:
			break;
		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void ToolMenuHandler(int position) {
		switch (position) {
		case 0: // 검색
			web.showFindDialog("", true);
			this.dismiss();
			break;
		case 1: // 스크린컷
			this.dismiss();
			DrawView drawView = ((MainActivity) mContext).mDrawView;
			drawView.setVisibility(View.VISIBLE);
			 ((MainActivity) mContext).mSearchIv.setVisibility(View.GONE);
			 ((MainActivity) mContext).mCamera.setVisibility(View.VISIBLE);
			break;
		case 2: // HTML 수정
			web.loadUrl("javascript:console.log(document.documentElement.innerHTML)");
			Intent intent = new Intent(mContext, HtmlModify.class);
			this.dismiss();
			((MainActivity) mContext).startActivityForResult(intent, 0x03);
			break;
		case 3: // JS
			this.dismiss();
			((MainActivity) mContext).startActivity(new Intent(mContext,
					JSInterpreter.class));
			break;
		case 4: // 페이지 저장
			((MainActivity)mContext).inSave = true;
			web.loadUrl("javascript:console.log(document.documentElement.innerHTML)");
			break;
		case 5:
			SharedPreferences pref_5 = ((MainActivity) MainActivity.mContext).getPreferences(0);
			String base_url = pref_5.getString("base_url", "1");
			String site = pref_5.getString("page", "2");
			if(base_url.equals("1") || base_url.equals("") || site.equals("2")){
				Toast.makeText(mContext, "저장된 페이지가 없습니다", Toast.LENGTH_SHORT).show();
				break;
			}
			((MainActivity) mContext).getWeb().loadDataWithBaseURL(base_url,
					"<html>" + site + "</html>",
					"text/html; charset=UTF-8", "utf-8", null);
			dismiss();
			break;
		default:
			break;
		}
	}
}
