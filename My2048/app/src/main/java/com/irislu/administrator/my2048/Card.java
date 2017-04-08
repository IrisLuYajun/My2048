package com.irislu.administrator.my2048;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016-5-23.
 */
public class Card extends FrameLayout{
    private TextView label;
    private int num = 0;
    public Card(Context context) {
        super(context);

        label = new TextView(getContext());//为什么不能是this呢？
        label.setTextSize(32);
        label.setGravity(Gravity.CENTER);
        label.setBackgroundColor(0x33ffffff);
        //ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(-1,-1);//布局参数，-1表示填充满整个父布局
        LayoutParams lp = new LayoutParams(-1,-1);//注意LayoutParams有多个，不同，比如说上面的就不能设置下面这个参数
        lp.setMargins(10,10,0,0);
        addView(label, lp);
        setNum(0);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if (num <= 0) {
            label.setText("");
        }else {
            label.setText(num + "");//不能直接加上num,因为setText()接收的是String型
        }
    }

    public void setColor(int color) {
        label.setBackgroundColor(color);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;

        Card card = (Card) o;

        return num == card.num;

    }




}
