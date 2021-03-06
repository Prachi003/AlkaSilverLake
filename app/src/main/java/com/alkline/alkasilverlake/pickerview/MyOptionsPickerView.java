package com.alkline.alkasilverlake.pickerview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alkline.alkasilverlake.R;
import com.alkline.alkasilverlake.model.BottleData;
import com.alkline.alkasilverlake.model.RecycleBottleData;
import com.alkline.alkasilverlake.model.WaterNameData;
import com.alkline.alkasilverlake.pickerview.view.BasePickerView;
import com.alkline.alkasilverlake.pickerview.view.MyWheelOptions;
import com.alkline.alkasilverlake.roomdatabasemodel.AddOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramesh Bhupathi on 17/05/16.
 */
public class MyOptionsPickerView  extends BasePickerView implements View.OnClickListener {
    private MyWheelOptions wheelOptions;
    private TextView tvTitle;
    private OnOptionsSelectListener optionsSelectListener;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    public MyOptionsPickerView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.pickerview_options, contentContainer);
        // -----确定和取消按钮
        setBtnSubmit((Button)findViewById(R.id.btnSubmit));
        getBtnSubmit().setTag(TAG_SUBMIT);
        setBackGroundparent((ConstraintLayout)findViewById(R.id.constraint));
        setBtnCancel((Button)findViewById(R.id.btnCancel));

        getBtnCancel().setTag(TAG_CANCEL);
        getBtnSubmit().setOnClickListener(this);
        getBtnCancel().setOnClickListener(this);
        //顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        // ----转轮
        final View optionspicker = findViewById(R.id.optionspicker);
        wheelOptions = new MyWheelOptions(optionspicker);


    }





    public void setPicker(ArrayList<String> options1Items,
                          ArrayList<BottleData> options2Items,
                          ArrayList<WaterNameData> options3Items,
                          boolean linkage) {
        wheelOptions.setPicker(options1Items, options2Items, options3Items,
                linkage);
    }

    public void setPicKerRecycler(ArrayList<RecycleBottleData>recycleBottleData,boolean linkage){
        wheelOptions.setPickerRecycler(recycleBottleData,linkage);
    }

    public void method(String add, Context context, List<AddOrder> addOrderList) {
        if (add.equals("New") && addOrderList.size() != 0) {
            getConstraintLayout().setBackgroundColor(context.getResources().getColor(R.color.appColor));
        } else if (addOrderList.size() == 0 && add.equals("New")) {
            getConstraintLayout().setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
        } else {
            getConstraintLayout().setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
        }
    }

    @Override
    public void setCustomFont(@NonNull Typeface typeface){
        wheelOptions.setCustomTypeface(typeface);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1
     */
    public void setSelectOptions(int option1) {
        wheelOptions.setCurrentItems(option1, 0, 0);
    }



    /**
     * 设置选中的item位置
     *
     * @param option1
     * @param option2
     */
    public void setSelectOptions(int option1, int option2) {
        wheelOptions.setCurrentItems(option1, option2, 0);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1
     * @param option2
     * @param option3
     */
    public void setSelectOptions(int option1, int option2, int option3) {
        wheelOptions.setCurrentItems(option1, option2, option3);
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     */
    public void setLabels(String label1) {
        wheelOptions.setLabels(label1, null, null);
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     * @param label2
     */
    public void setLabels(String label1, String label2) {
        wheelOptions.setLabels(label1, label2, null);
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     * @param label2
     * @param label3
     */
    public void setLabels(String label1, String label2, String label3) {
        wheelOptions.setLabels(label1, label2, label3);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wheelOptions.setCyclic(cyclic);
    }

    public void setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        wheelOptions.setCyclic(cyclic1, cyclic2, cyclic3);
    }


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
        } else {
            if (optionsSelectListener != null) {
                int[] optionsCurrentItems = wheelOptions.getCurrentItems();
                optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2]);
            }
            dismiss();
        }
    }

    public interface OnOptionsSelectListener {
        void onOptionsSelect(int options1, int option2, int options3);
    }
    public interface OnOptionsSelectListenerRecycler {
        void onOptionsSelectRecycler(int options1);
    }


    public void setOnoptionsSelectListener(
            OnOptionsSelectListener optionsSelectListener) {
        this.optionsSelectListener = optionsSelectListener;
    }

    public void setOnOptionsSelectListenerRecycler(OnOptionsSelectListenerRecycler onOptionsSelectListenerRecycler){
        OnOptionsSelectListenerRecycler onOptionsSelectListenerRecycler1 = onOptionsSelectListenerRecycler;
    }





    public void setTitle(String title) {
        tvTitle.setText(title);
    }
    public void setTitleTextSize(float textSize) {
        tvTitle.setTextSize(textSize);
    }
    public void setTitleTextColor(int color) {
        tvTitle.setTextColor(color);
    }


    public MyWheelOptions getWheelOptions() {
        return wheelOptions;
    }
}

