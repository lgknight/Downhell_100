package agilebuddy.data;


import agilebuddy.material.UIModel;

/**
 * 
 * @version 1.2.3
 * 
 */
public class Role {

	// ���Ͻ�X����
	private int mX;

	// ���Ͻ�Y����(Y��������ֵ=mY/Y������������)
	private int mVirtualY;

	// ���
	private int mWidth;

	// �߶�
	private int mHeith;

	// ����״̬
	private int mRoleStatus;

	// ������״
	private int mRoleShape;

	// ֡�ӳ�
	private int mFrameDelay;

	// ֡�ӳ�ʱ�������
	private int mFrameCounter = 0;

	public int getMinX() {
		return mX;
	}

	public int getMaxX() {
		return mX + mWidth;
	}

	public int getMinY() {
		return mVirtualY / UIModel.GAME_ATTRIBUTE_PIXEL_DENSITY_Y;
	}

	public int getMaxY() {
		return mVirtualY / UIModel.GAME_ATTRIBUTE_PIXEL_DENSITY_Y + mHeith;
	}

	public void setX(int x) {
		mX = x;
	}

	public void setVirtualY(int virtualY) {
		mVirtualY = virtualY;
	}

	public void addX(int pixel) {
		mX += pixel;
	}

	public void addY(int virtualPixel) {
		mVirtualY += virtualPixel;
	}

	public int getRoleStatus() {
		return mRoleStatus;
	}

	public void setRoleStatus(int roleStatus) {
		mRoleStatus = roleStatus;
	}

	public int getRoleShape() {
		if (mRoleStatus == UIModel.ROLE_STATUS_ON_FOOTBOARD) {
			mRoleShape = UIModel.ROLE_Shape_STANDING;
			return mRoleShape;
		}
		mFrameCounter++;
		if (mFrameCounter == mFrameDelay) {
			mFrameCounter = 0;
			if (mRoleStatus == UIModel.ROLE_STATUS_FREEFALL) {
				mRoleShape = UIModel.ROLE_Shape_FREEFALL;
			} else if (mRoleStatus == UIModel.ROLE_STATUS_FREEFALL_RIGHT
					|| mRoleStatus == UIModel.ROLE_STATUS_ON_FOOTBOARD_RIGHT) {
				if (mRoleShape == UIModel.ROLE_Shape_MOVE_RIGHT_NO1) {
					mRoleShape = UIModel.ROLE_Shape_MOVE_RIGHT_NO2;
				} else if (mRoleShape == UIModel.ROLE_Shape_MOVE_RIGHT_NO2) {
					mRoleShape = UIModel.ROLE_Shape_MOVE_RIGHT_NO3;
				} else if (mRoleShape == UIModel.ROLE_Shape_MOVE_RIGHT_NO3) {
					mRoleShape = UIModel.ROLE_Shape_MOVE_RIGHT_NO4;
				} else {
					mRoleShape = UIModel.ROLE_Shape_MOVE_RIGHT_NO1;
				}
			} else {
				if (mRoleShape == UIModel.ROLE_Shape_MOVE_LEFT_NO1) {
					mRoleShape = UIModel.ROLE_Shape_MOVE_LEFT_NO2;
				} else if (mRoleShape == UIModel.ROLE_Shape_MOVE_LEFT_NO2) {
					mRoleShape = UIModel.ROLE_Shape_MOVE_LEFT_NO3;
				} else if (mRoleShape == UIModel.ROLE_Shape_MOVE_LEFT_NO3) {
					mRoleShape = UIModel.ROLE_Shape_MOVE_LEFT_NO4;
				} else {
					mRoleShape = UIModel.ROLE_Shape_MOVE_LEFT_NO1;
				}
			}
		}
		return mRoleShape;
	}

	public Role(int x, int y, int width, int heith, int frameDelay) {
		mX = x;
		mVirtualY = y * UIModel.GAME_ATTRIBUTE_PIXEL_DENSITY_Y;
		mWidth = width;
		mHeith = heith;
		mFrameDelay = frameDelay;
		mRoleStatus = UIModel.ROLE_STATUS_ON_FOOTBOARD;
		mRoleShape = UIModel.ROLE_Shape_STANDING;
	}
}
