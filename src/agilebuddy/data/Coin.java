package agilebuddy.data;

import agilebuddy.material.UIModel;

public class Coin {
	private int mX;
	private int mVirtuleY;
	private int mWidth;
	private int mHeight; 
	
	public int getWidth(){
		return mWidth;
	}
	public int getHeight(){
		return mHeight;
	} 
	public int getMinX(){
		return mX;
	}
	public int getMaxX(){
		return mX + mWidth;
	}
	public int getMinY(){
		return mVirtuleY / UIModel.GAME_ATTRIBUTE_PIXEL_DENSITY_Y;
	}
	public int getMaxY(){
		return mVirtuleY / UIModel.GAME_ATTRIBUTE_PIXEL_DENSITY_Y + mHeight;
	} 
	public void addY(int virtualPixel){
		mVirtuleY += virtualPixel;
	}
	public Coin(int x, int y, int width, int height){
		mX = x;
		mVirtuleY = y * UIModel.GAME_ATTRIBUTE_PIXEL_DENSITY_Y;
		mWidth = width;
		mHeight = height;
	}
}
