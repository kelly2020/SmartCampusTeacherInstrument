package com.johnson.commonlibs.common_utils.views;

import android.content.Context;
import android.graphics.PointF;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhangwt on 15/11/13.<br/>
 * 录音按钮
 *
 * @author zhangwt
 * @version 1.0
 * @date 15/11/13
 */
public class VoiceRecordButton extends TextView {
    /**
     * 根目录
     */
    private static final String ROOT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/chat/";
    /**
     * 录音的路径
     */
    private String voicePath;
    private Context mContext;
    private static long RECORD_TIME = 1000;

    private boolean isContinueRecord = false;
    /**
     * 记录开始时间
     */
    private long startTime;
    /**
     * 记录结束时间
     */
    private long endTime;

    private OnRecordListener onRecordListener;
    private MediaRecorder mMediaRecorder;

    public VoiceRecordButton(Context context) {
        this(context, null);
    }

    public VoiceRecordButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoiceRecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
//        setText("按下说话");
        setGravity(Gravity.CENTER);
    }

    //开始录音
    private void startRecord() {
        File file = new File(ROOT_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        voicePath = ROOT_DIR + System.currentTimeMillis() + ".amr";
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mMediaRecorder.setOutputFile(voicePath);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //结束录音
    private void stopRecord() {
        if (mMediaRecorder != null) {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

    //删除临时的录音文件
    private void deleteTempFile(String voicePath) {
        File file = new File(voicePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 处理录音事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float pointY = 0;
        float endY = 0;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                pointY = event.getY();
                setText("松开发送");
                startTime = System.currentTimeMillis();
                startRecord();
                return true;
            case MotionEvent.ACTION_UP:
                setText("按下说话");
                endTime = System.currentTimeMillis();

                if (!isContinueRecord) {
                    stopRecord();
                    deleteTempFile(voicePath);
                    break;
                }

                if (endTime - startTime < RECORD_TIME) {
                    onRecordListener.onTime2Short();
                    stopRecord();
                    deleteTempFile(voicePath);
                    break;
                }
                stopRecord();
                onRecordListener.onRecordFinish(voicePath);
                break;

            case MotionEvent.ACTION_MOVE:
                endY = event.getY();
                float deltaY = endY - pointY;
                Log.e("ACTION_MOVE:down",deltaY+"......." +pointY);
                if (deltaY > 100) {
                    isContinueRecord = true;
                    Log.e("ACTION_MOVE:down","继续录音");

                }else {
                    isContinueRecord = false;
                    Log.e("ACTION_MOVE:up","取消录音");
                }
                Log.e("isContinueRecord" ,isContinueRecord +"");
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.e("ACTION_MOVE:cancel","删除录音");
                //删除语音文件
                stopRecord();
                deleteTempFile(voicePath);
                break;
        }
        return super.onTouchEvent(event);//返回这个是这个控件完全处理view的事件，并不向下传送
    }

    /**
     * 设置录音的监听
     *
     * @param onRecordListener
     */
    public void setOnRecordListener(OnRecordListener onRecordListener) {
        this.onRecordListener = onRecordListener;
    }

    public interface OnRecordListener {

        /**
         * 录音完成
         *
         * @param filepath
         */
        void onRecordFinish(String filepath);

        /**
         * 时间太短
         */
        void onTime2Short();
    }
}
