package com.android.baselibrary.utils;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * ����������
 * Created by Administrator on 2015/10/20 0020.
 */
public class AnimUtils {


    /**
     * �������������Ҷ���
     * @param context ������
     * @param v Ҫִ�ж�����view
     */
    public static void shake(Context context,View v){
        Animation shake = new TranslateAnimation(0, 10, 0, 0);//�ƶ�����
        shake.setDuration(1000);//ִ����ʱ��
        shake.setInterpolator(new CycleInterpolator(7));//ѭ������
        v.startAnimation(shake);
    }

    /**
     * ���Ŷ���������ʱ���ţ�̧��ʱ�ָ�
     * @param v Ҫִ�ж�����view
     * @param event �����¼�
     * @param listener ����¼�
     * @return  �������
     */
    public static boolean setClickAnim(View v,MotionEvent event,OnClickListener listener){
        float start = 1.0f;
        float end = 0.95f;
        Animation scaleAnimation = new ScaleAnimation(start, end, start, end,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        Animation endAnimation = new ScaleAnimation(end, start, end, start,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnimation.setDuration(200);
        scaleAnimation.setFillAfter(true);
        endAnimation.setDuration(200);
        endAnimation.setFillAfter(true);
        switch (event.getAction()) {
            // ����ʱ����
            case MotionEvent.ACTION_DOWN:
                v.startAnimation(scaleAnimation);
                v.invalidate();
                break;
            // ̧��ʱ����
            case MotionEvent.ACTION_UP:
                v.startAnimation(endAnimation);
                v.invalidate();
                if(listener!=null){
                    listener.onClick(v);
                }
                break;
            // ������ȥ�������action_up,����action_cancel
            case MotionEvent.ACTION_CANCEL:
                v.startAnimation(endAnimation);
                v.invalidate();
                break;
        }
        // ������true��Action_up����Ӧ����
        return true;
    }

}