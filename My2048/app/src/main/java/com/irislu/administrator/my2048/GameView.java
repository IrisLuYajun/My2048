package com.irislu.administrator.my2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016-5-23.
 */
public class GameView extends GridLayout{
   // private Card[][] cards = new Card[4][4](getContext());
    private  boolean isContinue = true;
    private Card[][] cardsMap= new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<Point>();
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }


    private  void initGameView (){

        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offSetX, offSetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {//getAction()是得到触屏的动作
                    case MotionEvent.ACTION_DOWN://起始坐标,DOWN的意思是按下
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP://终点坐标，UP的意思是松开
                        offSetX = event.getX() - startX;
                        offSetY = event.getY() - startY;
                        if (Math.abs(offSetX) > Math.abs(offSetY)) {//左右方向的意图
                            if (offSetX < -5) {
                                //.d("GameView","left");
                                swipLeft();
                            } else if (offSetX > 5) {
                                //Log.d("GameView", "right");
                                swipRight();
                            } else {
                                Log.d("GameView", "left or right");
                            }
                        } else {
                            if (offSetY < -5) {
                                //Log.d("GameView","up");
                                swipUp();
                            } else if (offSetY > 5) {
                                // Log.d("GameView","down");
                                swipDown();
                            } else {
                                Log.d("GameView", "up or down");
                            }
                        }
                        break;

                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {//动态设置布局宽高
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(w,h)-10)/4;
        addCards(cardWidth, cardWidth);
        startGame();
    }
    private void addCards(int cardWidth,int cardHeight) {
        Card c;
        for (int y = 0;y < 4; y++) {
            for (int x = 0;x < 4; x++) {
                c = new Card(getContext());
                c.setNum(0);
                addView(c,cardWidth,cardHeight);
                cardsMap[x][y] = c;//添加card
            }
        }
    }
    public  void startGame() {
        MainActivity.getMainActivity().clearScore();
        for (int y = 0;y < 4; y++) {
            for (int x = 0;x < 4; x++) {
                cardsMap[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();

    }
   private   void addRandomNum() {
        emptyPoints.clear();
        for (int y = 0 ;y< 4 ; y++ ) {
            for (int x = 0 ; x < 4;x++){
                if (cardsMap[x][y].getNum()<=0) {
                    emptyPoints.add(new Point(x,y));
                }
            }
        }
        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
    }
    private void swipLeft() {
        boolean merge = false;
        for (int y = 0;y < 4; y++) {
            for (int x = 0;x < 4; x++) {
               for (int x1 = x+1;x1<4;x1++) {
                   if (cardsMap[x1][y].getNum()>0) {
                       if (cardsMap[x][y].getNum()<=0) {
                           cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                           cardsMap[x1][y].setNum(0);
                           x--;
                           merge = true;
                       }else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                           cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                           cardsMap[x1][y].setNum(0);
                           MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                           merge = true;
                       }
                       break;
                   }
               }
            }
        }
        if (merge) {
            addRandomNum();
            checkDown();
            if (isContinue&&(isSucceed())) {
                checkSucceed();
            }
            //changeColor();
        }
    }
    private void swipRight() {
        boolean merge = false;
        for (int y = 0;y < 4; y++) {
            for (int x = 3;x>= 0; x--) {
                for (int x1 = x-1;x1>=0;x1--) {
                    if (cardsMap[x1][y].getNum()>0) {
                        if (cardsMap[x][y].getNum()<=0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            merge = true;
                            x++;

                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;

                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandomNum();
            checkDown();
            if (isContinue&&(isSucceed())) {
                checkSucceed();
            }
            //changeColor();
        }
    }
    private void swipUp() {
        boolean merge = false;
        for (int x = 0;x < 4; x++) {
            for (int y = 0;y < 4; y++) {
                for (int y1 = y+1;y1<4;y1++) {
                    if (cardsMap[x][y1].getNum()>0) {
                        if (cardsMap[x][y].getNum()<=0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            merge = true;
                            y--;

                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandomNum();
            checkDown();
            if (isContinue&&(isSucceed())) {
                checkSucceed();
            }
            //changeColor();
        }
    }
    private void swipDown() {
        boolean merge = false;
        for (int x = 0;x < 4; x++) {
            for (int y = 3;y >= 0; y--) {
                for (int y1 = y-1;y1>=0;y1--) {
                    if (cardsMap[x][y1].getNum()>0) {
                        if (cardsMap[x][y].getNum()<=0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            merge = true;
                            y++;

                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;

                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandomNum();
            checkDown();
            if (isContinue&&(isSucceed())) {
                checkSucceed();
            }
            //changeColor();
        }
    }
    public void checkDown() {
        boolean down = true;
        ALL:
        for (int y = 0;y < 4;y++) {
            for (int x = 0;x<4;x++) {
                if (cardsMap[x][y].getNum()==0||
                        (x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
                        (x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
                        (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
                        (y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))){
                    down = false;
                    break ALL;
                }
            }
        }
        if (down) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("您好").setMessage("游戏结束").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  startGame();
                }
            }).show();
            builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    exitGame();
                }
            }).show();
        }
    }
    //退出游戏
    public void exitGame() {
        MainActivity.getMainActivity().finish();
    }
    //改变Card颜色
    public void changeColor() {
        for (int y = 0;y < 4;y++) {
            for (int x = 0;x<4;x++) {
                switch(cardsMap[x][y].getNum()){
                    case 2:
                    case 4:
                        cardsMap[x][y].setColor(0x33ffffff);
                        break;
                    case 8:
                        cardsMap[x][y].setColor(0xffcc00);
                        break;
                    case 16:
                        cardsMap[x][y].setColor(0xff9900);
                        break;
                    case 32:
                        cardsMap[x][y].setColor(0xff6600);
                        break;
                    case 64:
                        cardsMap[x][y].setColor(0x006600);
                        break;
                    case 128:
                        cardsMap[x][y].setColor(0x00cc00);
                        break;
                    case 256:
                        cardsMap[x][y].setColor(0x99cc33);
                        break;
                    case 512:
                        cardsMap[x][y].setColor(0xecb04d);
                        break;
                    case 1024:
                        cardsMap[x][y].setColor(0xeb9437);
                        break;
                    case 2048:
                        cardsMap[x][y].setColor(0xea7821);
                        break;
                    default:
                        cardsMap[x][y].setColor(0xea7821);
                        break;

                }



            }
            }
    }
    //游戏成功
    public boolean isSucceed() {
        Boolean flag =false;
        ALL:
        for (int y = 0;y < 4;y++) {
            for (int x = 0;x<4;x++) {
               if(cardsMap[x][y].getNum() == 2048){
                   flag = true;
                  break ALL;
               }
            }
            //return flag;
        }
        return flag;
    }

    public void checkSucceed() {
        final AlertDialog.Builder succeedBuilder = new AlertDialog.Builder(getContext());
        succeedBuilder.setTitle("恭喜您，游戏成功！");
        succeedBuilder.setMessage("请选择：");
        succeedBuilder.setNegativeButton("重新开始", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startGame();
            }
        });
        succeedBuilder.setPositiveButton("继续游戏", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //AlertDialog dialogUp = succeedBuilder.show();
                //dialogUp.dismiss();
                dialog.dismiss();
                isContinue = false;
            }
        }).show();
    }

}
