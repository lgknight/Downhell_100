package agilebuddy.material; 
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import agilebuddy.data.*;
import agilebuddy.util.Global_data;

public class UIModel {

	/**
	 * 游戏属性常量
	 */

	// 帧刷新间隔(单位微妙)
	public static final int GAME_ATTRIBUTE_FRAME_DELAY = 30;

	// 游戏活动对象Y方向的像素密度(将1个单位像素拆分为更小单元)
	public static final int GAME_ATTRIBUTE_PIXEL_DENSITY_Y = 10;

	// 游戏等级提升因数
	public static final int GAME_ATTRIBUTE_LEVEL_UP_FACTOR = 40;

	// 重力速度(即主角离开踏板后的y方向速度)
	public static final int GAME_ATTRIBUTE_GRAVITY_VELOCITY = 5 * GAME_ATTRIBUTE_PIXEL_DENSITY_Y;

	// 游戏状态
	public static final int GAME_STATUS_PAUSE = 0;
	public static final int GAME_STATUS_RUNNING = 1;
	public static final int GAME_STATUS_GAMEOVER = 2;

	// 游戏效果标识(用来控制不同音效和震动的标志)
	public static final int EFFECT_FLAG_NO_EFFECT = 0;
	public static final int EFFECT_FLAG_NORMAL = 1;
	public static final int EFFECT_FLAG_UNSTABLE = 2;
	public static final int EFFECT_FLAG_SPRING = 3;
	public static final int EFFECT_FLAG_SPIKED = 4;
	public static final int EFFECT_FLAG_MOVING = 5;
	public static final int EFFECT_FLAG_TOOLS = 6;
	public static final int EFFECT_FLAG_GETCOIN = 7;

	/**
	 * 主角属性常量
	 */

	// 主角的长度和宽度
	public static final int ROLE_ATTRIBUTE_WIDTH = 32;
	public static final int ROLE_ATTRIBUTE_HEITH = 48;

	// 主角帧刷新间隔
	public static final int ROLE_ATTRIBUTE_FRAME_DELAY = 2;

	// 主角最大生命值
	public static final int ROLE_ATTRIBUTE_HP_MAX = 20;

	// 主角状态
	public static final int ROLE_STATUS_ON_FOOTBOARD = 0;
	public static final int ROLE_STATUS_ON_FOOTBOARD_LEFT = 1;
	public static final int ROLE_STATUS_ON_FOOTBOARD_RIGHT = 2;
	public static final int ROLE_STATUS_FREEFALL = 3;
	public static final int ROLE_STATUS_FREEFALL_LEFT = 4;
	public static final int ROLE_STATUS_FREEFALL_RIGHT = 5;

	// 主角帧
	public static final int ROLE_Shape_STANDING = 0;
	public static final int ROLE_Shape_FREEFALL = 1;
	public static final int ROLE_Shape_MOVE_LEFT_NO1 = 2;
	public static final int ROLE_Shape_MOVE_LEFT_NO2 = 3;
	public static final int ROLE_Shape_MOVE_LEFT_NO3 = 4;
	public static final int ROLE_Shape_MOVE_LEFT_NO4 = 5;
	public static final int ROLE_Shape_MOVE_RIGHT_NO1 = 6;
	public static final int ROLE_Shape_MOVE_RIGHT_NO2 = 7;
	public static final int ROLE_Shape_MOVE_RIGHT_NO3 = 8;
	public static final int ROLE_Shape_MOVE_RIGHT_NO4 = 9;

	/**
	 * 道具属性常量
	 */

	// 踏板的长宽
	public static final int BORDER_ATTRIBUTE_IMAGE_HEITH = 20;
	public static final int BORDER_ATTRIBUTE_IMAGE_WIDTH = 100;
	
	//金币长高
	public static final int COIN_ATTRIBUTE_IMAGE_HEIGHT = 25;
	public static final int COIN_ATTRIBUTE_IMAGE_WIDTH = 25;
	

	// 踏板偏向速度
	public static final int BOARD_ATTRIBUTE_LEFT_VELOCITY = -4;
	public static final int BOARD_ATTRIBUTE_RIGHT_VELOCITY = 4;

	// 不稳定踏板滞留因数(可滞留时间=滞留因数*帧刷新间隔)
	public static final int BOARD_ATTRIBUTE_UNSTABLE_DELAY_FACTOR = 10;

	// 踏板类别
	public static final int FOOTBOARD_TYPE_NORMAL = 0;
	public static final int FOOTBOARD_TYPE_UNSTABLE = 1;
	public static final int FOOTBOARD_TYPE_SPRING = 2;
	public static final int FOOTBOARD_TYPE_SPIKED = 3;
	public static final int FOOTBOARD_TYPE_MOVING_LEFT = 4;
	public static final int FOOTBOARD_TYPE_MOVING_RIGHT = 5;

	/**
	 * 游戏属性
	 */

	// 游戏界面属性
	private ScreenAttribute mScreenAttribute;

	// 游戏状态
	public int mGameStatus = GAME_STATUS_RUNNING;

	// 游戏得分
	private int mScore = 0;
	
	// 当前难度等级
	private int mLevel = 1;
	
	//金币数量
	private int mCoinNumber = 0; 
	// 生命值
	private int mHP = ROLE_ATTRIBUTE_HP_MAX;

	// 游戏等级提升计算器(等级计算器值等于等级提升因数时游戏等级提升1级,等级计算器重置为零)
	private int mLevelUpCounter = 0;

	// 随机数生成器
	private Random mRan = new Random();

	// 游戏效果标志(用于处理主角动作效果,比如:震动,音效)
	private int mEffectFlag = EFFECT_FLAG_NO_EFFECT;

	/**
	 * 游戏主角属性
	 */

	// 游戏主角
	private Role mRole;

	// 主角X方向移动速度
	private int mRoleVelocityX;

	// 主角Y方向移动速度
	private int mRoleVelocityY;
	// 附加速度(用于控制速度,在选项面板里设定)
	private int mAddVelocity;

	/**
	 * 道具属性
	 */
	private Coin mCoin;

	// 楼梯间隔距离因数(间隔距离(px)=因数/Y方向像素因数)
	private int mFootboardSpaceFactor = 120 * GAME_ATTRIBUTE_PIXEL_DENSITY_Y;

	// 移动间隔计算器
	private int mFootboardSpaceCounter = 0;

	// 踏板移动速度
	private int mFootboartVelocity = -2 * Global_data.model * GAME_ATTRIBUTE_PIXEL_DENSITY_Y;

	// 踏板列表
	private LinkedList<Footboard> mFootboardList;
	
	//金币列表
	private LinkedList<Coin> mCoinList;

	public UIModel(ScreenAttribute screenAttribute, int addVelocity) {
		mScreenAttribute = screenAttribute;
		mAddVelocity = addVelocity;
		mRole = new Role((screenAttribute.maxX - ROLE_ATTRIBUTE_WIDTH) / 2,
				screenAttribute.maxY * 3 / 4, ROLE_ATTRIBUTE_WIDTH,
				ROLE_ATTRIBUTE_HEITH, ROLE_ATTRIBUTE_FRAME_DELAY);
		mRoleVelocityY = GAME_ATTRIBUTE_GRAVITY_VELOCITY;
		mFootboardList = new LinkedList<Footboard>();
		mFootboardList.add(new Footboard(
				(screenAttribute.maxX - BORDER_ATTRIBUTE_IMAGE_WIDTH) / 2,
				screenAttribute.maxY, BORDER_ATTRIBUTE_IMAGE_WIDTH,
				BORDER_ATTRIBUTE_IMAGE_HEITH, FOOTBOARD_TYPE_NORMAL, 1, 1));
		mCoinList = new LinkedList<Coin>();
		mCoinList.add(new Coin((screenAttribute.maxX - COIN_ATTRIBUTE_IMAGE_WIDTH) / 2, 
				screenAttribute.maxY - 25, COIN_ATTRIBUTE_IMAGE_WIDTH, 
				COIN_ATTRIBUTE_IMAGE_HEIGHT));
	}

	/**
	 * 更新UI模型
	 */
	public void updateUIModel() {
		for (Footboard footboard : mFootboardList) {
			footboard.addY(mFootboartVelocity);
		}
		for (Coin coin:mCoinList){
			coin.addY(mFootboartVelocity);
		}
		mRole.addX(mRoleVelocityX);
		mRole.addY(mRoleVelocityY);
		handleBorder();
		handleRoleAction();
		mFootboardSpaceCounter = mFootboardSpaceCounter - mFootboartVelocity;
		if (mFootboardSpaceCounter >= mFootboardSpaceFactor) {
			mFootboardSpaceCounter -= mFootboardSpaceFactor;
			generateFootboard();
			generateCoin(mFootboardList.getLast().getMinX());
			mLevelUpCounter += 1;
			if (mLevelUpCounter == GAME_ATTRIBUTE_LEVEL_UP_FACTOR) {
				mLevelUpCounter = 0;
				increaseLevel();
			}
		}
	}

	/**
	 * 随机生成踏板
	 */
	private void generateFootboard() {
		int frameAmount = 1;
		int frameDelay = 1;
		int frameType = FOOTBOARD_TYPE_NORMAL;
		switch (mRan.nextInt(20)) {
		case 0:
		case 1:
		case 2:
			frameType = FOOTBOARD_TYPE_UNSTABLE;
			break;
		case 3:
		case 4:
		case 5:
			frameType = FOOTBOARD_TYPE_SPRING;
			frameAmount = 3;
			frameDelay = 15;
			break;
		case 6:
		case 7:
		case 8:
			frameType = FOOTBOARD_TYPE_SPIKED;
			break;
		case 9:
		case 10:
			frameType = FOOTBOARD_TYPE_MOVING_LEFT;
			frameAmount = 4;
			frameDelay = 15;
			break;
		case 11:
		case 12:
			frameType = FOOTBOARD_TYPE_MOVING_RIGHT;
			frameAmount = 4;
			frameDelay = 15;
			break;
		default:
			frameType = FOOTBOARD_TYPE_NORMAL;
		}
		mFootboardList.add(new Footboard(mRan.nextInt(mScreenAttribute.maxX
				- BORDER_ATTRIBUTE_IMAGE_WIDTH), mScreenAttribute.maxY
				+ ROLE_ATTRIBUTE_HEITH, BORDER_ATTRIBUTE_IMAGE_WIDTH,
				BORDER_ATTRIBUTE_IMAGE_HEITH, frameType, frameAmount,
				frameDelay));
	}
	
	/** 
	 * 随机生成金币
	 */
	
	private void generateCoin(int boardMinX){
		int randomInt = mRan.nextInt(10);
		int randomX = mRan.nextInt(BORDER_ATTRIBUTE_IMAGE_WIDTH - COIN_ATTRIBUTE_IMAGE_WIDTH);
		if (randomInt < 7) {
		for (int i = 0; i < randomInt % 2 + 1; i++) {
		mCoinList.add(new Coin(boardMinX + randomX + i * COIN_ATTRIBUTE_IMAGE_WIDTH,
				mScreenAttribute.maxY - 5 + COIN_ATTRIBUTE_IMAGE_HEIGHT, COIN_ATTRIBUTE_IMAGE_WIDTH, COIN_ATTRIBUTE_IMAGE_HEIGHT));
		}
		}
	}
	
	/**
	 * 处理主角移动
	 * 
	 * @param angleValue
	 */
	public void handleMoving(int option) {
			mRoleVelocityX = option * mAddVelocity + 5 * option;
			
	}

	/**
	 * 难度提升
	 */
	private void increaseLevel() {
		mLevel++;
		if (mLevel < 18 || mLevel % 20 == 0) {
			mFootboartVelocity -= 2;
			int roleStatus = mRole.getRoleStatus();
			if (roleStatus == ROLE_STATUS_ON_FOOTBOARD
					|| roleStatus == ROLE_STATUS_ON_FOOTBOARD_RIGHT
					|| roleStatus == ROLE_STATUS_ON_FOOTBOARD_LEFT) {
				mRoleVelocityY = mFootboartVelocity;
			}
		}
	}

	/**
	 * 处理边界
	 */
	private void handleBorder() {
		if (mFootboardList.size() > 0
				&& mFootboardList.getFirst().getMaxY() <= mScreenAttribute.minY) {
			mFootboardList.remove();
		}
		if (mCoinList.size() > 0 && mCoinList.getFirst().getMaxY() <= mScreenAttribute.minY) {
			mCoinList.remove();
		}
		if (mRole.getMinY() <= mScreenAttribute.minY) {
			mHP -= 3;
			if (mHP <= 0) {
				mGameStatus = GAME_STATUS_GAMEOVER;
			} else if (mRole.getRoleStatus() == ROLE_STATUS_ON_FOOTBOARD
					|| mRole.getRoleStatus() == ROLE_STATUS_ON_FOOTBOARD_LEFT
					|| mRole.getRoleStatus() == ROLE_STATUS_ON_FOOTBOARD_RIGHT) {
				mRole.addY(BORDER_ATTRIBUTE_IMAGE_HEITH
						* GAME_ATTRIBUTE_PIXEL_DENSITY_Y);
			}
			mRoleVelocityY = GAME_ATTRIBUTE_GRAVITY_VELOCITY;
			mEffectFlag = EFFECT_FLAG_SPIKED;
			return;
		}
		if ((mRole.getMinY() + mRole.getMaxY()) / 2 > mScreenAttribute.maxY) {
			mGameStatus = GAME_STATUS_GAMEOVER;
			return;
		}
		if (mRole.getMinX() < mScreenAttribute.minX) {
			mRoleVelocityX = 0;
			mRole.setX(0);
			return;
		}
		if (mRole.getMaxX() > mScreenAttribute.maxX) {
			mRoleVelocityX = 0;
			mRole.setX(mScreenAttribute.maxX - ROLE_ATTRIBUTE_WIDTH);
			return;
		}
	}

	/**
	 * 处理主角在踏板上的活动
	 */
	private void handleRoleAction() {
		Role role = mRole;

		for (Coin coin:mCoinList) {
			if((role.getMaxY() >= coin.getMinY() &&role.getMaxY() < coin.getMaxY() + ROLE_ATTRIBUTE_HEITH) 
					&&(role.getMaxX() > coin.getMinX() &&role.getMaxX() <= coin.getMaxX() + ROLE_ATTRIBUTE_WIDTH)
					) {
				mCoinNumber ++;
				mCoinList.remove(coin);
				mEffectFlag = EFFECT_FLAG_GETCOIN;
				}
		}
		for (Footboard footboard : mFootboardList) {
			if ((role.getMaxY() >= footboard.getMinY() && role.getMaxY() < footboard.getMaxY())
					&& ((role.getMaxX() + role.getMinX()) / 2 > footboard.getMinX() && ((role.getMaxX() + role.getMinX()) / 2 < footboard.getMaxX()))) {
				if (role.getRoleStatus() == ROLE_STATUS_ON_FOOTBOARD
						|| role.getRoleStatus() == ROLE_STATUS_ON_FOOTBOARD_RIGHT
						|| role.getRoleStatus() == ROLE_STATUS_ON_FOOTBOARD_LEFT) {
					if (footboard.getType() == FOOTBOARD_TYPE_SPRING) {
						mRoleVelocityY = 1 * (mFootboartVelocity - GAME_ATTRIBUTE_GRAVITY_VELOCITY);
						role.addY(-1 * GAME_ATTRIBUTE_PIXEL_DENSITY_Y);
						updateRoleStatus(ROLE_STATUS_FREEFALL);
						 
						return;
					}
					if (footboard.getType() == FOOTBOARD_TYPE_MOVING_LEFT) {
						role.addX(BOARD_ATTRIBUTE_LEFT_VELOCITY);
					} else if (footboard.getType() == FOOTBOARD_TYPE_MOVING_RIGHT) {
						role.addX(BOARD_ATTRIBUTE_RIGHT_VELOCITY);
					} else if (footboard.getType() == FOOTBOARD_TYPE_UNSTABLE
							&& footboard.isBoardBreak()) {
						mFootboardList.remove(footboard);
					}
					updateRoleStatus(ROLE_STATUS_ON_FOOTBOARD);
				} else {
					// 主角第一次触板
					mScore += mLevel;
					mRoleVelocityY = mFootboartVelocity;
					role.setVirtualY(footboard.getVirtualY()
							- ROLE_ATTRIBUTE_HEITH
							* GAME_ATTRIBUTE_PIXEL_DENSITY_Y);
					if (footboard.getType() == FOOTBOARD_TYPE_SPIKED) {
						mHP -= 3;
					} else if (mHP < ROLE_ATTRIBUTE_HP_MAX) {
						mHP += 1;
					}
					if (mHP <= 0) {
						mGameStatus = GAME_STATUS_GAMEOVER;
					}
					updateRoleStatus(ROLE_STATUS_ON_FOOTBOARD);
					switch (footboard.getType()) {
					case FOOTBOARD_TYPE_UNSTABLE:
						mEffectFlag = EFFECT_FLAG_UNSTABLE;
						break;
					case FOOTBOARD_TYPE_SPRING:
						mEffectFlag = EFFECT_FLAG_SPRING;
						break;
					case FOOTBOARD_TYPE_SPIKED:
						mEffectFlag = EFFECT_FLAG_SPIKED;
						break;
					case FOOTBOARD_TYPE_MOVING_LEFT:
					case FOOTBOARD_TYPE_MOVING_RIGHT:
						mEffectFlag = EFFECT_FLAG_MOVING;
						break;
					default:
						mEffectFlag = EFFECT_FLAG_NORMAL;
					}
				} 
				return;
			}
		}
		
		if (mRoleVelocityY < mFootboartVelocity) {
			mRoleVelocityY += 3;
		} else {
			mRoleVelocityY = GAME_ATTRIBUTE_GRAVITY_VELOCITY;
		}
		updateRoleStatus(ROLE_STATUS_FREEFALL);
	}

	private void updateRoleStatus(int status) {
		if (status == ROLE_STATUS_FREEFALL) {
			if (mRoleVelocityX > 0) {
				mRole.setRoleStatus(ROLE_STATUS_FREEFALL_RIGHT);
			} else if (mRoleVelocityX < 0) {
				mRole.setRoleStatus(ROLE_STATUS_FREEFALL_LEFT);
			} else {
				mRole.setRoleStatus(ROLE_STATUS_FREEFALL);
			}
		} else {
			if (mRoleVelocityX > 0) {
				mRole.setRoleStatus(ROLE_STATUS_ON_FOOTBOARD_RIGHT);
			} else if (mRoleVelocityX < 0) {
				mRole.setRoleStatus(ROLE_STATUS_ON_FOOTBOARD_LEFT);
			} else {
				mRole.setRoleStatus(ROLE_STATUS_ON_FOOTBOARD);
			}
		}
	}

	/**
	 * 清除操作
	 */
	public void destroy() {
		mScreenAttribute = null;
		mRole = null;
		mRan = null;
		mFootboardList.clear();
		mFootboardList = null;
	}

	public Role getRoleUIObject() {
		return mRole;
	}

	public List<Footboard> getFootboardUIObjects() {
		return mFootboardList;
	}
	
	public List<Coin> getCoinUIObjects(){
		return mCoinList;
	}

	public int getEffectFlag() {
		try {
			return mEffectFlag;
		} finally {
			mEffectFlag = EFFECT_FLAG_NO_EFFECT;
		}
	}

	public int getCoin() {
		return  mCoinNumber;
	}

	public String getScoreStr() {
		return "SC: " + mScore;
	}

	public int getScore() {
		return mScore;
	}

	public float getHp() {
		return (float) mHP / ROLE_ATTRIBUTE_HP_MAX;
	}
}
