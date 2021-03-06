package com.wangdaye.mysplash.common.ui.popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangdaye.mysplash.R;
import com.wangdaye.mysplash.common._basic.MysplashPopupWindow;
import com.wangdaye.mysplash.common.utils.DisplayUtils;
import com.wangdaye.mysplash.common.utils.manager.ThemeManager;

import butterknife.ButterKnife;

/**
 * Search orientation popup window.
 *
 * This popup window is used to select orientation.
 *
 * */

public class SearchOrientationPopupWindow extends MysplashPopupWindow
        implements View.OnClickListener {
    // widget
    private OnSearchOrientationChangedListener listener;

    // data
    private String[] names;
    private String[] values;
    private String valueNow;

    /** <br> life cycle. */

    public SearchOrientationPopupWindow(Context c, View anchor, String valueNow) {
        super(c);
        this.initialize(c, anchor, valueNow);
    }

    @SuppressLint("InflateParams")
    private void initialize(Context c, View anchor, String valueNow) {
        View v = LayoutInflater.from(c).inflate(R.layout.popup_search_orientation, null);
        setContentView(v);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        initData(c, valueNow);
        initWidget();

        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(10);
        }
        showAsDropDown(anchor, 0, 0, Gravity.CENTER);
    }

    /** <br> UI. */

    private void initWidget() {
        View v = getContentView();

        v.findViewById(R.id.popup_search_orientation_all).setOnClickListener(this);
        v.findViewById(R.id.popup_search_orientation_landscape).setOnClickListener(this);
        v.findViewById(R.id.popup_search_orientation_portrait).setOnClickListener(this);
        v.findViewById(R.id.popup_search_orientation_squarish).setOnClickListener(this);

        TextView allTxt = ButterKnife.findById(v, R.id.popup_search_orientation_allTxt);
        DisplayUtils.setTypeface(v.getContext(), allTxt);
        allTxt.setText(v.getContext().getText(R.string.all));
        if (TextUtils.isEmpty(valueNow)) {
            allTxt.setTextColor(ThemeManager.getSubtitleColor(v.getContext()));
        }

        TextView landscapeTxt = ButterKnife.findById(v, R.id.popup_search_orientation_landscapeTxt);
        DisplayUtils.setTypeface(v.getContext(), landscapeTxt);
        landscapeTxt.setText(names[0]);
        if (values[0].equals(valueNow)) {
            landscapeTxt.setTextColor(ThemeManager.getSubtitleColor(v.getContext()));
        }

        TextView portraitTxt = ButterKnife.findById(v, R.id.popup_search_orientation_portraitTxt);
        DisplayUtils.setTypeface(v.getContext(), portraitTxt);
        portraitTxt.setText(names[1]);
        if (values[1].equals(valueNow)) {
            portraitTxt.setTextColor(ThemeManager.getSubtitleColor(v.getContext()));
        }

        TextView squarishTxt = ButterKnife.findById(v, R.id.popup_search_orientation_squarishTxt);
        DisplayUtils.setTypeface(v.getContext(), squarishTxt);
        squarishTxt.setText(names[2]);
        if (values[2].equals(valueNow)) {
            squarishTxt.setTextColor(ThemeManager.getSubtitleColor(v.getContext()));
        }

        if (ThemeManager.getInstance(v.getContext()).isLightTheme()) {
            ((ImageView) v.findViewById(R.id.popup_search_orientation_allIcon))
                    .setImageResource(R.drawable.ic_infinity_light);
            ((ImageView) v.findViewById(R.id.popup_search_orientation_landscapeIcon))
                    .setImageResource(R.drawable.ic_orientation_landscape_light);
            ((ImageView) v.findViewById(R.id.popup_search_orientation_portraitIcon))
                    .setImageResource(R.drawable.ic_orientation_portrait_light);
            ((ImageView) v.findViewById(R.id.popup_search_orientation_squarishIcon))
                    .setImageResource(R.drawable.ic_orientation_squarish_light);
        } else {
            ((ImageView) v.findViewById(R.id.popup_search_orientation_allIcon))
                    .setImageResource(R.drawable.ic_infinity_dark);
            ((ImageView) v.findViewById(R.id.popup_search_orientation_landscapeIcon))
                    .setImageResource(R.drawable.ic_orientation_landscape_dark);
            ((ImageView) v.findViewById(R.id.popup_search_orientation_portraitIcon))
                    .setImageResource(R.drawable.ic_orientation_portrait_dark);
            ((ImageView) v.findViewById(R.id.popup_search_orientation_squarishIcon))
                    .setImageResource(R.drawable.ic_orientation_squarish_dark);
        }
    }

    /** <br> data. */

    private void initData(Context c, String valueNow) {
        names = c.getResources().getStringArray(R.array.search_orientations);
        values = c.getResources().getStringArray(R.array.search_orientation_values);
        this.valueNow = valueNow;
    }

    /** <br> interface. */

    public interface OnSearchOrientationChangedListener {
        void onSearchOrientationChanged(String orientationValue);
    }

    public void setOnSearchOrientationChangedListener(OnSearchOrientationChangedListener l) {
        listener = l;
    }

    @Override
    public void onClick(View view) {
        String newValue = valueNow;
        switch (view.getId()) {
            case R.id.popup_search_orientation_all:
                newValue = "";
                break;

            case R.id.popup_search_orientation_landscape:
                newValue = values[0];
                break;

            case R.id.popup_search_orientation_portrait:
                newValue = values[1];
                break;

            case R.id.popup_search_orientation_squarish:
                newValue = values[2];
                break;
        }

        if (!newValue.equals(valueNow) && listener != null) {
            listener.onSearchOrientationChanged(newValue);
            dismiss();
        }
    }
}