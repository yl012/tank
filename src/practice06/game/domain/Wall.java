package practice06.game.domain;

import java.io.IOException;

import org.itheima.game.utils.DrawUtils;

import practice06.game.business.Blockable;
import practice06.game.business.Destroyable;
import practice06.game.business.Hitable;
/**
 * 砖墙类
 * @author 幽怜
 *
 */
public class Wall extends Element  implements Blockable,Hitable,Destroyable{
	//属性

	//3.血量
	private int blood = 3;
	
	//构造方法(有参)
	public Wall(int x, int y) {
		super(x, y);
		
		//设置图片的宽和高
		try {
			int [] size = DrawUtils.getSize("res\\img\\wall.gif");
			width = size[0];
			height = size[1];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//常用方法
	//1.画
	public void draw() {
		try {
			DrawUtils.draw("res\\img\\wall.gif", x, y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//2.挨打

	@Override
	public Blast showAttack() {
		blood--;
		return  new Blast(this,true);
	}

	@Override
	public boolean isDestroy() {
		return blood == 0;
	}

	@Override
	public Blast showDestroy() {
		return new Blast(this);
	}
}
