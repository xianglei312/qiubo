package com.eltong.elad;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.RTalk.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

/**
 * @author XXD
 * @note 闂備浇宕甸崰鎰版偡閵夈儙娑樷攽鐎ｎ煉鎷烽梺鍓插亝濞叉牜绮荤紒妯镐簻闁规崘娉涢弸鎴︽煛婢跺矈鍎朿tivity
 */
public class VideoPlayerActivity extends Activity implements Callback,
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
	private String contentID = null;
	private Dialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.elsd_videoplayer);

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
					btn_ad_pause.setText(VideoPlayerActivity.this
							.getString(R.string.btn_txt_play));
				} else {
					mediaPlayer.start();
					btn_ad_pause.setText(VideoPlayerActivity.this
							.getString(R.string.btn_txt_pause));
				}
		}
	};

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
		super.onPause();
		
		if (timerTask != null) {
			timerTask.cancel();
		}
		if (timer != null) {
			timer.cancel();
		}
		if(mediaPlayer != null){
			if(mediaPlayer.isPlaying())
			{
				mediaPlayer.stop();
			}
			mediaPlayer.release();
			mediaPlayer = null;
		}
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
//		// 闂傚倸鍊搁崐鎼佹晝閵夛妇绠惧┑鐘叉搐闂傤垱绻涘顔荤盎缂佺姴纾幉绋款吋婢跺﹥妲┑掳鍊曢幊蹇涘疾椤掑嫭鍊堕柣鎰硾娴滃綊鏌ｉ悢鍝ョ疄闁哄被鍊楅敓浠嬫偐閹颁焦顥ラ梻浣规た閻撳牓宕滃杈╃焿鐎广儱顦奸弲鎼佹煥閻曞倹瀚�
//		case KeyEvent.KEYCODE_VOLUME_DOWN:
//			AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//			int criterion = Math.round((float) (am
//					.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * 0.3));
//			if (am.getStreamVolume(AudioManager.STREAM_MUSIC) <= criterion) {
//				return true;
//			}
//			break;
//		case KeyEvent.KEYCODE_BACK:
//			VideoPlayerActivity.this.finish();
//			break;
//		}
//		return super.onKeyDown(keyCode, event);
//	}

	/**
	 * @author XXD
	 * @note 闂傚倷绀佸﹢閬嶆惞鎼淬劌鍌ㄥù鐘差儏濡ê霉閿濆洤鍔嬮柛鐘叉閺屾盯鍩勯崘褏绻侀梺鍝勬４閹凤拷	 */
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
	 * @note 闂傚倷绀侀幉鈥愁瀴缂佹ɑ鍙忛柟顖ｇ亹瑜版帒鐐婇柕濠忕細濮橈箑鈹戦悙鏉戠仸闁挎碍銇勯弬鍖¤含闁诡喛顬冮幏鍛矙鎼存挻瀵栭梻浣圭湽閸婃洖螞濠靛鏋佺�銉ヮ樆闁卞洦绻濋棃娑欘棴妞ゆ柨顧佸娲偂鎼达絾鐎繝娈垮枔閸婃洟鏁冮姀鈩冨闁绘垶锚閻濇澘顪㈤妶鍡欏ⅵ闁稿﹥顨熺划鍫ユ晸閿燂拷 */
	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int arg1) {
		// TODO Auto-generated method stub
		pb_ad_play.setSecondaryProgress(duration * arg1 / 99);
	}

	/**
	 * @author XXD
	 * @note 闂傚倷绀侀幉锛勬暜濡ゅ啰鐭欓柟瀵稿Х绾句粙鏌熼幑鎰厫闁哥姴妫濋弻娑㈠焺閸愌呯箒闂佸搫妫撮幏锟� */
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
			Bundle bundle = getIntent().getExtras();
			final String video_url = bundle.getString(BasicProperties.INTENT_KEY2);
			contentID = bundle.getString(BasicProperties.INTENT_KEY3);
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
						VideoPlayerActivity.this.finish();
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
        // 注意此处要放在show之后 否则会报异常
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
	 * @note 闂傚倷绀佸﹢閬嶆惞鎼淬劌鍌ㄥù鐘差儏濡ê霉閿濆洤鐨戦柟椋庡厴閹﹢鎮欓弶鎴濆Б闂佽绻愬ú锕傘�婵傚憡鍊烽柡澶嬪灥婵悂姊虹紒妯虹厐闂傚嫬瀚…鍥敂閸喐娅㈤梺璺ㄥ櫐閹凤拷*/
	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		if (timerTask != null) {
			timerTask.cancel();
		}
		if (timer != null) {
			timer.cancel();
		}
		arg0.stop();
		Intent intent = new Intent();
		intent.putExtra(BasicProperties.INTENT_KEY3, contentID);
		intent.setClass(VideoPlayerActivity.this, QuestionActivity.class);
		startActivity(intent);
		VideoPlayerActivity.this.finish();
	}

	/**
	 * @author XXD
	 * @note 闂傚倷绀侀幖顐わ拷椤掍焦娅犲ù鐘差儏妗呴梺璺ㄥ枍缁瑥顬夋繝姘窛妞ゆ梻鍘ч‖瀣攽閳藉棗浜滈柣顓炲�楠炲﹪鏁愰崪浣哄弳闂佸憡鍔戦崝搴ㄋ囬敓锟� */
	private void doShow() {
		if (video_lin_menu.isShown()) {
			video_lin_menu.setVisibility(View.INVISIBLE);
		} else {
			video_lin_menu.setVisibility(View.VISIBLE);
		}
	}
	
}
