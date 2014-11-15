package com.example.downhell_100;

import java.util.HashMap;
import java.util.List; 

import agilebuddy.data.*;
import agilebuddy.material.UIModel;
import agilebuddy.util.ConstantInfo;
import agilebuddy.util.Global_data;

import android.R.string;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Audio.Media;
import android.util.AttributeSet;
import android.util.Log; 
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import graphic.*;
import android.graphics.Matrix;

/**
* 
* view of AgileBuddyActivity
* 
*/
public class AgileBuddyView extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String HANDLE_MESSAGE_GAME_SCORE = "1";
	private static final String HANDLE_MESSAGE_GAME_COIN = "0";
	private AgileThread mUIThread;

	private Context mContext;

	private Handler mHandler;

	private ScreenAttribute mScreenAttribute;

	private int mActionPower;

	private boolean mVibratorFlag;
	private Vibrator mVibrator;

	private boolean mSoundsFlag;
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;

	private Bitmap mBackgroundImage;

	private Bitmap mRole;	
	private ImageSplitter mRoleSplitter;
	private List <ImagePiece> mRolePiece;
	
	private Bitmap mFootboard;	
	private ImageSplitter mFootboardSplitter;
	private List <ImagePiece> mFootboardPieces;

	private Bitmap mCoinBitmap;
	
	private Bitmap mRoleDeadmanImage;

	private Drawable mTopBarImage;
	private Drawable mHpBarTotalImage;
	private Drawable mHpBarRemainImage;

	private Paint mGameMsgRightPaint;
	private Paint mGameMsgLeftPaint;

	public AgileBuddyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message m) {
				// 更新本地记录文件
				int curScore = m.getData().getInt(HANDLE_MESSAGE_GAME_SCORE);
				int finalcoin = m.getData().getInt(HANDLE_MESSAGE_GAME_COIN);
				Global_data.money += finalcoin;
//				boolean recordRefreshed = updateLocalRecord(curScore);
				LayoutInflater factory = LayoutInflater.from(mContext);
				View dialogView = factory.inflate(R.layout.score_post_panel,null);
				dialogView.setFocusableInTouchMode(true);
				dialogView.requestFocus();

				final AlertDialog dialog = new AlertDialog.Builder(mContext)
						.setView(dialogView).create();
					if (curScore < 100) {
						dialog.setIcon(R.drawable.tip_pool_guy);
						dialog.setTitle(R.string.gameover_dialog_text_poolguy);
					} else if (curScore < 500) {
						dialog.setIcon(R.drawable.tip_not_bad);
						dialog.setTitle(R.string.gameover_dialog_text_notbad);
					} else {
						dialog.setIcon(R.drawable.tip_awesome);
						dialog.setTitle(R.string.gameover_dialog_text_awesome);
					}
				dialog.show();	
				dialogView.findViewById(R.id.retry).setOnClickListener(
						new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								restartGame();
							}
						});
				dialogView.findViewById(R.id.goback).setOnClickListener(
						new OnClickListener() {
							@Override
							public void onClick(View v) {
							dialog.dismiss();	
							((AgileBuddyActivity) mContext).finish();
							}
						});
			}
		};
		// 初始化资源
		int role = Global_data.tempRole;
		initRes(role);
		mUIThread = new AgileThread(holder, context, mHandler);
		setFocusable(true);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mScreenAttribute = new ScreenAttribute(0, 20, width, height);
		mUIThread.initUIModel(mScreenAttribute);
		mUIThread.setRunning(true);
		mUIThread.start();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		mUIThread.setRunning(false);
		while (retry) {
			try {
				mUIThread.join();
				retry = false;
			} catch (InterruptedException e) {
				Log.d("", "Surface destroy failure:", e);
			}
		}
	}

	public void handleMoving(int option) {
		if (mUIThread != null) {
			mUIThread.handleMoving(option);
		}
	}

	public void restartGame() {
		mUIThread = new AgileThread(this.getHolder(), this.getContext(),
				mHandler);
		mUIThread.initUIModel(mScreenAttribute);
		mUIThread.setRunning(true);
		mUIThread.start();
	}


	/**
	 * 初始化资源
	 */
	private void initRes(int role) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		mSoundsFlag = preferences.getBoolean(
				ConstantInfo.PREFERENCE_KEY_SOUNDS, true);
		mVibratorFlag = preferences.getBoolean(
				ConstantInfo.PREFERENCE_KEY_VIBRATE, true);
		mActionPower = preferences
				.getInt(ConstantInfo.PREFERENCE_KEY_POWER, 40);

		// 初始化活动音效
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
		soundPoolMap = new HashMap<Integer, Integer>();
		soundPoolMap.put(UIModel.EFFECT_FLAG_NORMAL, soundPool.load(
				getContext(), R.raw.normal, 1));
		soundPoolMap.put(UIModel.EFFECT_FLAG_UNSTABLE, soundPool.load(
				getContext(), R.raw.unstable, 1));
		soundPoolMap.put(UIModel.EFFECT_FLAG_SPRING, soundPool.load(
				getContext(), R.raw.spring, 1));
		soundPoolMap.put(UIModel.EFFECT_FLAG_SPIKED, soundPool.load(
				getContext(), R.raw.spiked, 1));
		soundPoolMap.put(UIModel.EFFECT_FLAG_MOVING, soundPool.load(
				getContext(), R.raw.moving, 1));
		soundPoolMap.put(UIModel.EFFECT_FLAG_TOOLS, soundPool.load(
				getContext(), R.raw.tools, 1));
		soundPoolMap.put(UIModel.EFFECT_FLAG_GETCOIN, soundPool.load(
		getContext(), R.raw.getcoin, 1));

		mGameMsgLeftPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mGameMsgLeftPaint.setColor(Color.YELLOW);
		mGameMsgLeftPaint.setStyle(Style.FILL);
		mGameMsgLeftPaint.setTextSize(15f);
		mGameMsgLeftPaint.setTextAlign(Paint.Align.LEFT);
		mGameMsgLeftPaint.setTypeface(Typeface.DEFAULT_BOLD);

		mGameMsgRightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mGameMsgRightPaint.setColor(Color.YELLOW);
		mGameMsgRightPaint.setStyle(Style.FILL);
		mGameMsgRightPaint.setTextSize(15f);
		mGameMsgRightPaint.setTextAlign(Paint.Align.RIGHT);
		mGameMsgRightPaint.setTypeface(Typeface.DEFAULT_BOLD);

		Resources res = mContext.getResources();
		
		mTopBarImage = res.getDrawable(R.drawable.rect);
		mHpBarTotalImage = res.getDrawable(R.drawable.hp_bar_total);
		mHpBarRemainImage = res.getDrawable(R.drawable.hp_bar_remain);
		switch (role){
		case 1:
			mRole = Bitmap.createScaledBitmap(BitmapFactory
					.decodeResource(res, R.drawable.role1),
					7 * UIModel.ROLE_ATTRIBUTE_WIDTH, 
					UIModel.ROLE_ATTRIBUTE_HEITH,false);
			break;
		case 2:
			mRole = Bitmap.createScaledBitmap(BitmapFactory
					.decodeResource(res, R.drawable.role2),
					7 * UIModel.ROLE_ATTRIBUTE_WIDTH, 
					UIModel.ROLE_ATTRIBUTE_HEITH,false);
			break;
		case 3:
			mRole = Bitmap.createScaledBitmap(BitmapFactory
					.decodeResource(res, R.drawable.role3),
					7 * UIModel.ROLE_ATTRIBUTE_WIDTH, 
					UIModel.ROLE_ATTRIBUTE_HEITH,false);
			break;
		case 4:
			mRole = Bitmap.createScaledBitmap(BitmapFactory
					.decodeResource(res, R.drawable.role4),
					7 * UIModel.ROLE_ATTRIBUTE_WIDTH, 
					UIModel.ROLE_ATTRIBUTE_HEITH,false);
			break;
		case 5:
			mRole = Bitmap.createScaledBitmap(BitmapFactory
					.decodeResource(res, R.drawable.role5),
					7 * UIModel.ROLE_ATTRIBUTE_WIDTH, 
					UIModel.ROLE_ATTRIBUTE_HEITH,false);
			break;
		default :
			mRole = Bitmap.createScaledBitmap(BitmapFactory
					.decodeResource(res, R.drawable.role1),
					7 * UIModel.ROLE_ATTRIBUTE_WIDTH, 
					UIModel.ROLE_ATTRIBUTE_HEITH,false);
			break;
		}
		mRoleSplitter = new ImageSplitter();
		mRolePiece = mRoleSplitter.split(mRole, 7, 1);

		for(int i = 3 ; i < 7 ; i++) {
			Matrix matrix = new Matrix();
			float matrixValues[] = { -1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f };
			matrix.setValues(matrixValues);
			matrix.postTranslate(mRolePiece.get(i).bitmap.getHeight() * 2 , 0);
			Bitmap bitmap = Bitmap.createBitmap(mRolePiece.get(i).bitmap,0,0,mRolePiece.get(i).bitmap.getWidth(),
				mRolePiece.get(i).bitmap.getHeight(),matrix,true);
			ImagePiece imagePiece = new ImagePiece();
			imagePiece.index = 4+i;
			imagePiece.bitmap = bitmap;
			mRolePiece.add(imagePiece);
		}
		
		mFootboard = Bitmap.createScaledBitmap(BitmapFactory
				.decodeResource(res, R.drawable.block),
				7 * UIModel.BORDER_ATTRIBUTE_IMAGE_WIDTH,
				5 * UIModel.BORDER_ATTRIBUTE_IMAGE_HEITH, true);
		mFootboardSplitter = new ImageSplitter();
		mFootboardPieces = mFootboardSplitter.split(mFootboard, 7, 2);
		
		for(int i = 5; i < 9; i++) {
			Matrix matrix = new Matrix();
			float matrixValues[] = { -1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f };
			matrix.setValues(matrixValues);
			matrix.postTranslate(mFootboardPieces.get(i).bitmap.getHeight() * 2 , 0);
			Bitmap bitmap = Bitmap.createBitmap(mFootboardPieces.get(i).bitmap,0,0,mFootboardPieces.get(i).bitmap.getWidth(),
				mFootboardPieces.get(i).bitmap.getHeight(),matrix,true);
			ImagePiece imagePiece = new ImagePiece();
			imagePiece.index = 9+i;
			imagePiece.bitmap = bitmap;
			mFootboardPieces.add(imagePiece);
		}
		
		mCoinBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.coin1),
				UIModel.COIN_ATTRIBUTE_IMAGE_WIDTH, UIModel.COIN_ATTRIBUTE_IMAGE_HEIGHT, true);
		
		mRoleDeadmanImage = Bitmap.createScaledBitmap(BitmapFactory
				.decodeResource(res, R.drawable.role_dead),
				UIModel.ROLE_ATTRIBUTE_WIDTH, UIModel.ROLE_ATTRIBUTE_HEITH,
				true);
		
		mBackgroundImage = BitmapFactory
				.decodeResource(res, R.drawable.bg0);
	}

	// thread for updating UI
	class AgileThread extends Thread {

		private SurfaceHolder mSurfaceHolder;
		private Context mContext;
		private Handler mHandler;

		// 运行标志
		private boolean mRun = true;
		// UI模型
		private UIModel mUIModel;
		// 时间记录器
		private long mTimeLogger;

		public AgileThread(SurfaceHolder surfaceHolder, Context context,
				Handler handler) {
			this.mSurfaceHolder = surfaceHolder;
			this.mContext = context;
			this.mHandler = handler;
		}

		@Override
		public void run() {
			while (mRun) {
				Canvas c = null;
				try {
					mTimeLogger = System.currentTimeMillis();
					try {
						mUIModel.updateUIModel();
						c = mSurfaceHolder.lockCanvas(null);
						synchronized (mSurfaceHolder) {
							doDraw(c);
						}
						handleEffect(mUIModel.getEffectFlag());
					} catch (Exception e) {
						Log.d("", "Error at 'run' method", e);
					} finally {
						if (c != null) {
							mSurfaceHolder.unlockCanvasAndPost(c);
						}
					}
					mTimeLogger = System.currentTimeMillis() - mTimeLogger;
					if (mTimeLogger < UIModel.GAME_ATTRIBUTE_FRAME_DELAY) {
						Thread.sleep(UIModel.GAME_ATTRIBUTE_FRAME_DELAY
								- mTimeLogger);
					}
					if (mUIModel.mGameStatus == UIModel.GAME_STATUS_GAMEOVER) {
						Message message = new Message();
						Bundle bundle = new Bundle();
						bundle.putInt(AgileBuddyView.HANDLE_MESSAGE_GAME_SCORE,
								mUIModel.getScore());
						bundle.putInt(AgileBuddyView.HANDLE_MESSAGE_GAME_COIN, 
								mUIModel.getCoin());
						message.setData(bundle);
						mHandler.sendMessage(message);
						mRun = false;
					}
				} catch (Exception ex) {
					Log.d("", "Error at 'run' method", ex);
				}
			}
		}

		private void doDraw(Canvas canvas) {
			Bitmap tempBitmap = null;
			canvas.drawBitmap(mBackgroundImage, 0, 0, null);
			mTopBarImage.setBounds(0, 0,
					AgileBuddyView.this.mScreenAttribute.maxX,
					AgileBuddyView.this.mScreenAttribute.maxY);
			mTopBarImage.draw(canvas);

			List<Footboard> footboards = mUIModel.getFootboardUIObjects();
			for (Footboard footboard : footboards) {
				switch (footboard.getType()) {
				case UIModel.FOOTBOARD_TYPE_UNSTABLE:
					if (!footboard.isMarked()) {
						tempBitmap = mFootboardPieces.get(9).bitmap;
					} else {
						tempBitmap = mFootboardPieces.get(10).bitmap;
					}
					break;
				case UIModel.FOOTBOARD_TYPE_SPRING:
					if (footboard.nextFrame() == 0) {
						tempBitmap = mFootboardPieces.get(2).bitmap;
					} else if (footboard.nextFrame() == 1) {
						tempBitmap = mFootboardPieces.get(3).bitmap;
					} else {
						tempBitmap = mFootboardPieces.get(4).bitmap;
					}
					break;
				case UIModel.FOOTBOARD_TYPE_SPIKED:
					tempBitmap = mFootboardPieces.get(1).bitmap;
					break;
				case UIModel.FOOTBOARD_TYPE_MOVING_LEFT:
					if (footboard.nextFrame() == 0) {
						tempBitmap = mFootboardPieces.get(14).bitmap;
					} else if (footboard.nextFrame() == 1) {
						tempBitmap = mFootboardPieces.get(15).bitmap;
					} else if (footboard.nextFrame() == 2) {
						tempBitmap = mFootboardPieces.get(16).bitmap;
					} else {
						tempBitmap = mFootboardPieces.get(17).bitmap;
					} 
					break;
				case UIModel.FOOTBOARD_TYPE_MOVING_RIGHT:
					if (footboard.nextFrame() == 0) {
						tempBitmap = mFootboardPieces.get(5).bitmap;
					} else if (footboard.nextFrame() == 1) {
						tempBitmap = mFootboardPieces.get(6).bitmap;
					} else if (footboard.nextFrame() == 2) {
						tempBitmap = mFootboardPieces.get(7).bitmap;
					} else {
						tempBitmap = mFootboardPieces.get(8).bitmap;
					}
					break;
				default:
					tempBitmap = mFootboardPieces.get(0).bitmap;
				}
				canvas.drawBitmap(tempBitmap, footboard.getMinX(), footboard
						.getMinY() - 30, null);
			}
			
			Role role = mUIModel.getRoleUIObject();
			if (mUIModel.mGameStatus == UIModel.GAME_STATUS_GAMEOVER) {
				canvas.drawBitmap(mRoleDeadmanImage, role.getMinX(), role.getMinY(), null);
			} else {
			switch (role.getRoleShape()) {
			case UIModel.ROLE_Shape_FREEFALL:
				tempBitmap = mRolePiece.get(1).bitmap;
				break;
			case UIModel.ROLE_Shape_MOVE_LEFT_NO1:
				tempBitmap = mRolePiece.get(7).bitmap;
				break;
			case UIModel.ROLE_Shape_MOVE_LEFT_NO2:
				tempBitmap = mRolePiece.get(8).bitmap;
				break;
			case UIModel.ROLE_Shape_MOVE_LEFT_NO3:
				tempBitmap = mRolePiece.get(9).bitmap;
				break;
			case UIModel.ROLE_Shape_MOVE_LEFT_NO4:
				tempBitmap = mRolePiece.get(10).bitmap;
				break;
			case UIModel.ROLE_Shape_MOVE_RIGHT_NO1:
				tempBitmap = mRolePiece.get(3).bitmap;
				break;
			case UIModel.ROLE_Shape_MOVE_RIGHT_NO2:
				tempBitmap = mRolePiece.get(4).bitmap;
				break;
			case UIModel.ROLE_Shape_MOVE_RIGHT_NO3:
				tempBitmap = mRolePiece.get(5).bitmap;
				break;
			case UIModel.ROLE_Shape_MOVE_RIGHT_NO4:
				tempBitmap = mRolePiece.get(6).bitmap;
				break;
			default:
				tempBitmap = mRolePiece.get(0).bitmap;
			}
				canvas.drawBitmap(tempBitmap, role.getMinX(), role.getMinY(),
						null);
			}

			List<Coin> coinList = mUIModel.getCoinUIObjects();
			for(Coin coin : coinList){
				canvas.drawBitmap(mCoinBitmap, coin.getMinX(), coin.getMinY(),null);
			}
			
			FontMetrics fmsr = mGameMsgLeftPaint.getFontMetrics();
			canvas.drawText(mUIModel.getScoreStr(), (float) 5,
					(float) 20 - (fmsr.ascent + fmsr.descent), mGameMsgLeftPaint);

			mHpBarTotalImage.setBounds(
					(AgileBuddyView.this.mScreenAttribute.maxX / 3),
					AgileBuddyView.this.mScreenAttribute.maxY - 20,
					(AgileBuddyView.this.mScreenAttribute.maxX * 2 / 3),
					AgileBuddyView.this.mScreenAttribute.maxY - 15);
			mHpBarTotalImage.draw(canvas);

			mHpBarRemainImage.setBounds(
							(AgileBuddyView.this.mScreenAttribute.maxX / 3),
							AgileBuddyView.this.mScreenAttribute.maxY - 20,
							(int) (AgileBuddyView.this.mScreenAttribute.maxX / 3 + AgileBuddyView.this.mScreenAttribute.maxX
									* mUIModel.getHp() / 3),
							AgileBuddyView.this.mScreenAttribute.maxY - 15);
			mHpBarRemainImage.draw(canvas);

			fmsr = mGameMsgRightPaint.getFontMetrics();
			canvas.drawText("$: " + mUIModel.getCoin(),
					(float) (AgileBuddyView.this.mScreenAttribute.maxX - 5),
					(float) 20 - (fmsr.ascent + fmsr.descent), mGameMsgRightPaint);
		}

		public void initUIModel(ScreenAttribute screenAttribut) {
			mBackgroundImage = Bitmap.createScaledBitmap(mBackgroundImage,
					screenAttribut.maxX, screenAttribut.maxY, true);
			if (mUIModel != null) {
				mRun = false;
				mUIModel.destroy();
			}
			int addVelocity = 0;
			if (mActionPower < 10) {
				addVelocity = -2;
			} else if (mActionPower < 25) {
				addVelocity = -1;
			} else if (mActionPower < 50) {
				addVelocity = 0;
			} else if (mActionPower < 60) {
				addVelocity = 1;
			} else if (mActionPower < 70) {
				addVelocity = 2;
			} else if (mActionPower < 80) {
				addVelocity = 3;
			} else if (mActionPower < 90) {
				addVelocity = 4;
			} else {
				addVelocity = 5;
			}
			mUIModel = new UIModel(screenAttribut, addVelocity);
		}

		public void handleMoving(int option) {
			if (mUIModel != null) {
				mUIModel.handleMoving(option);
			}
		}

		private void handleEffect(int effectFlag) {
			if (effectFlag == UIModel.EFFECT_FLAG_NO_EFFECT)
				return;
			// 处理音效
			if (mSoundsFlag) {
				playSoundEffect(effectFlag);
			}
			// 处理震动
			if (mVibratorFlag) {
				if (mVibrator == null) {
					mVibrator = (Vibrator) mContext
							.getSystemService(Context.VIBRATOR_SERVICE);
				}
				mVibrator.vibrate(25);
			}
		}

		/**
		 * 播放音效
		 * 
		 * @param soundId
		 */
		private void playSoundEffect(int soundId) {
			try {
				AudioManager mgr = (AudioManager) getContext()
						.getSystemService(Context.AUDIO_SERVICE);
				float streamVolumeCurrent = mgr
						.getStreamVolume(AudioManager.STREAM_RING);
				float streamVolumeMax = mgr
						.getStreamMaxVolume(AudioManager.STREAM_RING);
				float volume = streamVolumeCurrent / streamVolumeMax;
				soundPool.play(soundPoolMap.get(soundId), volume, volume, 1, 0,
						1f);
			} catch (Exception e) {
				Log.d("PlaySounds", e.toString());
			}
		}

		public void setRunning(boolean run) {
			mRun = run;
		}
	}// Thread
	
//	class BgMusicThread extends Thread {
//		MediaPlayer Player;
//		public void run() {
//			Player = MediaPlayer.create(AgileBuddyView.this,R.raw.bg1);
//		}
//	}
}
