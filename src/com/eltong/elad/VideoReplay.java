package com.eltong.elad;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.RTalk.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class VideoReplay extends Activity implements Callback,
		OnPreparedListener, OnCompletionListener, OnBufferingUpdateListener {

	private MediaPlayer mediaPlayer = null;
	private SurfaceView sv_ad_player = null;
	private SurfaceHolder holder = null;
	private LinearLayout video_lin_menu = null;
	private Button btn_ad_pause = null;
	private ProgressBar pb_ad_play = null;
	private int duration = 0;
	private Timer timer = null;
	private TimerTask timerTask = null;
	private Dialog mDialog;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				pb_ad_play.setMax(duration);
				if (mediaPlayer.isPlaying()) {
					pb_ad_play.setProgress(mediaPlayer.getCurrentPosition());
				}
				break;
			}
		}
	};

	private OnClickListener cl = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
				btn_ad_pause.setText(VideoReplay.this
						.getString(R.string.btn_txt_play));
			} else {
				mediaPlayer.start();
				btn_ad_pause.setText(VideoReplay.this
						.getString(R.string.btn_txt_pause));
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.elad_videoplayer);

		sv_ad_player = (SurfaceView) findViewById(R.id.sv_ad_player);
		video_lin_menu = (LinearLayout) findViewById(R.id.video_lin_menu);
		btn_ad_pause = (Button) findViewById(R.id.btn_ad_pause);
		pb_ad_play = (ProgressBar) findViewById(R.id.pb_ad_play);
		video_lin_menu.setOnClickListener(cl);
		btn_ad_pause.setOnClickListener(cl);
		holder = sv_ad_player.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		if (timerTask != null) {
			timerTask.cancel();
		}
		if (timer != null) {
			timer.cancel();
		}
		if (mediaPlayer != null) {
			mediaPlayer.release();
		}
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			doShow();
			break;
		}
		return false;
	}

	@Override
	public boolean onTrackballEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		doShow();
		return false;
	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		switch (keyCode) {
//		// 音量控制，不能小�?0%
//		case KeyEvent.KEYCODE_VOLUME_DOWN:
//			AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//			int criterion = Math.round((float) (am
//					.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * 0.2));
//			if (am.getStreamVolume(AudioManager.STREAM_MUSIC) <= criterion) {
//				return true;
//			}
//			break;
//		}
//		return super.onKeyDown(keyCode, event);
//	}

	/**
	 * @author XXD
	 * @note 播放视频
	 */
	@Override
	public void onPrepared(MediaPlayer arg0) {
		// TODO Auto-generated method stub

//		AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//		am.setStreamVolume(AudioManager.STREAM_MUSIC,
//				am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
		int width = mediaPlayer.getVideoWidth();
		int height = mediaPlayer.getVideoHeight();
		if (width != 0 && height != 0) {
			holder.setFixedSize(width, height);
		}
		duration = mediaPlayer.getDuration();
		mediaPlayer.start();
		if (timerTask != null) {
			timerTask.cancel();
		}
		timerTask = new TimerTask() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.what = 0;
				handler.sendMessage(msg);
			}
		};
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		timer.schedule(timerTask, 0, 500);
	}

	/**
	 * @author XXD
	 * @note 加载进度设置（可能有误）
	 */
	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int arg1) {
		// TODO Auto-generated method stub
		pb_ad_play.setSecondaryProgress(duration * arg1 / 99);
	}

	/**
	 * @author XXD
	 * @note 创建视频
	 */
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

		final String video_url = BasicProperties.video_name;
		mediaPlayer = new MediaPlayer();
		mediaPlayer.reset();
		mediaPlayer.setDisplay(holder);
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

		showRoundProcessDialog(this, R.layout.loading_process_dialog_anim);
		
		new Thread() {
			public void run() {
				try {
					mediaPlayer.setDataSource(video_url);
					mediaPlayer.prepare();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getApplicationContext(),
							getString(R.string.toast_msg_video_error),
							Toast.LENGTH_SHORT).show();
					VideoReplay.this.finish();
				} finally
				{
					mDialog.dismiss();
				}
			}
		}.start();

	}
	
    public void showRoundProcessDialog(Context mContext, int layout)
    {

        mDialog = new AlertDialog.Builder(mContext).create();

        mDialog.show();
        // ע��˴�Ҫ����show֮�� ����ᱨ�쳣
        mDialog.setContentView(layout);
    }

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @author XXD
	 * @note 播放完成并跳�?
	 */
	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		arg0.stop();
		VideoReplay.this.finish();
	}

	/**
	 * @author XXD
	 * @note 显示/隐藏菜单
	 */
	private void doShow() {
		if (video_lin_menu.isShown()) {
			video_lin_menu.setVisibility(View.INVISIBLE);
		} else {
			video_lin_menu.setVisibility(View.VISIBLE);
		}
	}

}
