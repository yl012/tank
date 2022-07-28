package practice06.game.domain;

import java.io.IOException;

import org.itheima.game.utils.DrawUtils;

import practice06.game.business.Blockable;

/**
 * 水墙类
 * @author 幽怜
 *
 */
public class Water extends Element implements Blockable {
	//属性

	//构造方法(有参)
	public Water(int x, int y) {
	   super(x, y);
		
		//设置图片的宽和高
		try {
		int []	size = DrawUtils.getSize("res\\img\\water.gif");
		width = size[0];
		height = size[1];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//方法
	//1.画
	public void draw() {
		try {
			DrawUtils.draw("res\\img\\water.gif", x, y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
