package practice06.game.domain;

import java.io.IOException;

import org.itheima.game.utils.DrawUtils;

import practice06.game.business.Blockable;
import practice06.game.business.Destroyable;
import practice06.game.business.Hitable;

/**
 * 铁墙
 * @author 幽怜
 *
 */
public class Steel  extends Element implements Blockable,Hitable,Destroyable {
	//属性
	//3.血量
	private int blood = 5;
	
	//构造方法(有参)
	public Steel(int x ,int y) {
		super(x, y);
		
		//设置图片的宽高
		try {
			int [] size = DrawUtils.getSize("res\\img\\steel.gif");
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
			DrawUtils.draw("res\\img\\steel.gif", x, y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//挨打
	public Blast showAttack() {
		blood--;
		return new Blast(this,true);
		
	}
	//判断铁墙是否销毁
	public boolean isDestroy() {
		return blood == 0;
	}
	@Override
	public Blast showDestroy() {
		// false:表示销毁，true：表示挨打状态
		return new Blast(this);
	}
}
