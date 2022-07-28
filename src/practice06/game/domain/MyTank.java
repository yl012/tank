package practice06.game.domain;

import java.io.IOException;

import org.itheima.game.utils.CollsionUtils;
import org.itheima.game.utils.DrawUtils;

import practice06.game.Config;
import practice06.game.business.Blockable;
import practice06.game.business.Moveable;

/**
 * 己方坦克
 * @author 幽怜
 *
 */
public class MyTank extends Element implements Moveable {


	//属性
	//1.血量
	private int blood;
	//2.攻击力
	private int power;
	//3.移动方向
	private Direction direction = Direction.UP;//要有初始化值，小细节
	//4.移动速度
	private int speed = 16;//要有初始化
	//5.用来记录最后一颗子弹的发射时间
	private  long lastshottime;
	//6.坦克不能移动的方向
	private Direction badDirection;
	//7.用来记录坦克的最小间隙
	private int badSpeed;
	
	//构造方法
	
	public MyTank(int x, int y) {
		super(x, y);
		
		//设置坦克的宽和高
		try {
			int [] size = DrawUtils.getSize("res\\img\\tank_u.gif");
			width = size [0];
			height = size[1];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void draw() {
		//加入坦克炮口随向转动功能
		String res = "";
		switch (direction) {
		case UP:
			res = "res\\img\\tank_u.gif";
			break;
		case DOWN:
			res = "res\\img\\tank_d.gif";
			break;
		case LEFT:
			res = "res\\img\\tank_l.gif";
			break;
		case RIGHT:
			res = "res\\img\\tank_r.gif";
			break;

		default:
			break;
		}
		try {
			DrawUtils.draw(res, x, y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 坦克的移动方向(用户录入的键传入的)
	 * @param direction
	 */
	public void move(Direction direction) {
		//如果坦克不能移动的方向，和传入的方向一致，就return，提示不能移动
		if (badDirection != null && badDirection == direction) {
			System.out.println("不能移动");
			//让坦克移动最小间隙
			switch (direction) {
			case UP:	
				y -= badSpeed;
				break;
			case DOWN:	
				y += badSpeed;
				break;
			case LEFT:	
				x -= badSpeed;
				break;
			case RIGHT:	
				x += badSpeed;
				break;
			default:
				break;
			}
			return ;
		}
		//如果传过来的方向和坦克当前方向不一致，就把传过来的方向赋值个坦克方向，然后return
		if (this.direction != direction ) {
			this.direction = direction;
			return ;
		} 
		switch (direction) {
		case UP:	
			y -= speed;
			break;
		case DOWN:	
			y += speed;
			break;
		case LEFT:	
			x -= speed;
			break;
		case RIGHT:	
			x += speed;
			break;
		default:
			break;
		}
	//越界处理
		if (x < 0) {
			x = 0;
		}//左越界处理
		if(y < 0) {
			y = 0;
		}//上越界处理
		if (x > Config.WIDTH - 64) {
			x = Config.WIDTH - 64;
		}//右越界处理
		if (y > Config.HEIGHT - 64) {
			y = Config.HEIGHT - 64;
		}//下越界处理
		
	}
	/**
	 * 坦克发射子弹
	 * @return 子弹对象
	 */
	public Bullet shot() {
		
		//如果最后一颗子弹的发射时间和即将发射子弹的发射时间间隔小于400ms，就不发射
		long nowtime = System.currentTimeMillis();//获取当前时间
		if (nowtime - lastshottime < 500) {
			return null;
		}else {
			lastshottime = nowtime;
			return new Bullet(this);//谁调用，this就代表谁
		}
		
	}
	/**
	 * 获取坦克的移动方向
	 * 
	 */
	public Direction getDirection() {
		return direction;
		
	}
	/**
	 * 判断是否撞上
	 */
	public boolean checkHit(Blockable block) {
		Element steel = (Element)block;//因为Blockable接口有几个子类：Wall，Steel，Water
		//获取铁墙的坐标和宽高
		int x1 = steel.x;
		int y1 = steel.y;
		int w1 = steel.width;
		int h1 = steel.height;
		
		//预判坦克的下一步坐标
		int x2 = x;
		int y2 = y;
		switch (direction) {
		case UP:	
			y2 -= speed;
			break;
		case DOWN:	
			y2 += speed;
			break;
		case LEFT:	
			x2 -= speed;
			break;
		case RIGHT:	
			x2 += speed;
			break;
		default:
			break;
		}
		
		boolean flag = CollsionUtils.isCollsionWithRect(x1, y1, w1, h1, x2, y2, width, height);
		if (flag) {
			//说明撞上了,获取坦克不能移动的方向和最小间隙值
			badDirection = direction;
			//最小间隙的计算方式：是通过坦克现有坐标进行计算的，不用预判坐标
			switch (direction) {
			case UP:	
				badSpeed = y - y1 - h1; //坦克的y轴 - 铁墙y轴 - 铁墙的高
				break;
			case DOWN:	
				badSpeed = y1 - y - height;
				break;
			case LEFT:	
				badSpeed = x - x1 - w1;//坦克的x轴 - 铁墙的x轴 -铁墙的宽
				break;
			case RIGHT:	
				badSpeed = x1 - x - width;
				break;
			default:
				break;
			}
		}else {
			//说明没有撞上,重置badDirection  badSpeed
			badDirection = null;
			badSpeed = 0;
		}
		return flag;
		
	}
}
