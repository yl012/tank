package practice06.game;

public class App {
	public static void main(String[] args) {
		/*
		 * 游戏的主入口
		 */
		GameWindow gw = new GameWindow(Config.TITLE,Config.WIDTH,Config.HEIGHT,Config.FPS);
		gw.start();//开启游戏
		//绘制图片，要求图片的长宽必须是二的整数倍
	}
}
