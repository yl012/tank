package practice06.game.domain;

import java.io.IOException;

import org.itheima.game.utils.CollsionUtils;
import org.itheima.game.utils.DrawUtils;
import org.itheima.game.utils.SoundUtils;

import practice06.game.Config;
import practice06.game.business.Attackable;
import practice06.game.business.Blockable;
import practice06.game.business.Destroyable;
import practice06.game.business.Hitable;

/**
 * 子弹类
 * @author 幽怜
 *
 */
public class Bullet extends Element implements Attackable,Destroyable{

	private Direction direction;//用来记录子弹的移动方向，和坦克的一致
	private int speed = 5;//子弹的移动速度
	
	//构造方法
	public Bullet(MyTank mt) {
		//子弹的坐标是由坦克的坐标来定的
		int tankX = mt.x;
		int tankY = mt.y;
		int tankWidth = mt.width;
		int tankHeight = mt.height;
		this.direction = mt.getDirection();
		
		//播放子弹声音
		try {
			SoundUtils.play("res\\snd\\fire.wav");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//获取子弹的宽和高
		try {
			int[] size = DrawUtils.getSize("res\\img\\bullet_u.gif");
			width = size[0];
			height = size[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//计算子弹的坐标(四舍五入，防止像素过大)
		switch (direction) {
		case UP:
			x = Math.round(tankX + (tankWidth - width) / 2.0f);
			y = Math.round(tankY - height / 2.0f);
			break;
		case DOWN:
			x = Math.round(tankX + (tankWidth - width) / 2.0f);
			y = Math.round(tankY + tankHeight - height / 2.0f);
			break;
		case LEFT:
			//x = tankX - width / 2;  //这个是正确的算法, 但是图片素材有点问题, 我们把代码稍微优化下
			x = tankX - width;
			y = Math.round(tankY + (tankHeight - height) / 2.0f);
			break;
		case RIGHT:
			x = Math.round(tankX + tankWidth - width / 2.0f);
			y = Math.round(tankY + (tankHeight - height) / 2.0f);
			break;
		default:
			break;
		}
	}

	/**
	 * 绘制子弹
	 */
	@Override
	public void draw() {
		//加入子弹炮口随向转动功能和子弹的移动
		String res = "";
		switch (direction) {
		case UP:
			res = "res\\img\\bullet_u.gif";
			y -= speed;
			break;
		case DOWN:
			res = "res\\img\\bullet_d.gif";
			y += speed;
			break;
		case LEFT:
			res = "res\\img\\bullet_l.gif";
			x -= speed;
			break;
		case RIGHT:
			res = "res\\img\\bullet_r.gif";
			x += speed;
			break;

		default:
			break;
		}
		try {
			DrawUtils.draw(res, x, y);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 销毁出界子弹
	 * @return true : 需要销毁 false : 不需要销毁
	 */
	public boolean isDestroy() {
		//子弹完全出界才销毁
		if (x < 0 - width || x >Config.WIDTH || y < 0 - height || y > Config.HEIGHT) {
			return true;
		}
		return false;
	}
	/**
	 * 校验具有攻击力和具有挨打能力是否碰撞上
	 * @param steel 被攻击对象
	 * @return true 代表碰上 false 代表没有
	 */
	public boolean checkAttack(Hitable hit) {
		//获取铁墙的坐标和宽高
		Element steel = (Element)hit;
		int x1 = steel.x;
		int y1 = steel.y;
		int w1 = steel.width;
		int h1 = steel.height;
		
		return CollsionUtils.isCollsionWithRect(x1, y1, w1, h1, x, y, width, height);
		
	}

	@Override
	public Blast showDestroy() {
		//这里直接return 一个null，因为本身就是爆炸物，所以销毁的时候直接从集合中移除就可以
		return null;
	}

	
}
