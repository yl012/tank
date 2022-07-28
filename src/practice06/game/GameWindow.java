package practice06.game;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.itheima.game.Window;
import org.itheima.game.utils.DrawUtils;
import org.itheima.game.utils.SoundUtils;
import org.lwjgl.input.Keyboard;

import practice06.game.business.Attackable;
import practice06.game.business.Blockable;
import practice06.game.business.Destroyable;
import practice06.game.business.Hitable;
import practice06.game.business.Moveable;
import practice06.game.domain.Blast;
import practice06.game.domain.Bullet;
import practice06.game.domain.Direction;
import practice06.game.domain.Element;
import practice06.game.domain.Grass;
import practice06.game.domain.MyTank;
import practice06.game.domain.Steel;
import practice06.game.domain.Wall;
import practice06.game.domain.Water;

public class GameWindow extends Window{
	
	MyTank mt;
	CopyOnWriteArrayList<Element> list = new CopyOnWriteArrayList<Element>();//元素的集合

//	//按enter键的时候停止或播放
//	boolean flag = true; //true是播放false是停止
	
	//调用父类的有参构造
	public GameWindow(String title, int width, int height, int fps) {
		super(title, width, height, fps);
		// TODO Auto-generated constructor stub
	}
	//窗体的创建
	@Override
	protected void onCreate() {
	//窗体加载播放音乐
		try {
			SoundUtils.play("res\\snd\\start.wav");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//创建一堆砖墙
		for (int i = 0; i < Config.WIDTH / 64 - 1; i++) {
			Wall wall = new Wall(64 * i,64);
			//把砖墙对象添加到集合中
			addElment(wall);
		}
	//创建一堆水墙
		for (int i = 1; i < Config.WIDTH/64; i++) {
			Water water = new Water(64 * i, 64 *3);
			//把水墙对象添加到集合中
			addElment(water);
		}
	//创建一堆铁墙
		for (int i = 0; i < Config.WIDTH/64 -1; i++) {
			Steel steel = new Steel(64 * i, 64 *5);
	//把铁墙对象添加到集合中
			addElment(steel);
				}
	//创建一堆草地
		for (int i = 1; i < Config.WIDTH/64; i++) {
			Grass grass = new Grass(64 * i, 64 *7);
			addElment(grass);
		}
		/*
		 * 让坦克隐藏到草坪中
		 * 简单做法：先绘制坦克，再绘制草坪，扩展性查
		 * 推荐做法：Comparator比较器接口实现
		 * 我们可以这样做，每往集合中添加一个元素，就按照渲染级别进行排序，渲染级别越高，元素越靠后，
		 * 这样，最终绘制元素的时候渲染级别高的元素肯定是最后绘制。
		 * 
		 * 按照渲染级别对元素进行升序排列
		 */
	//创建己方坦克
		mt = new MyTank(Config.WIDTH/2 - 32, Config.HEIGHT - 64);
		addElment(mt);
		
//		try {
//			SoundUtils.play("res\\snd\\start.wav");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	//鼠标监听事件
	@Override
	protected void onMouseEvent(int key, int x, int y) {
		if (key == 0) {
			System.out.println("左键");
			Bullet shot = mt.shot();
			if (shot == null) {
				
			}else {
				
				addElment(shot);
			}
		}else if (key == 1) {
			System.out.println("右键");
		}
	}
	//键盘监听事件
	@Override
	protected void onKeyEvent(int key) {
		if (key == Keyboard.KEY_UP|| key ==Keyboard.KEY_W) {
			System.out.println("↑");
			mt.move(Direction.UP);
		}else if (key == Keyboard.KEY_LEFT|| key ==Keyboard.KEY_A) {
			mt.move(Direction.LEFT);
			System.out.println("←");
		}else if (key == Keyboard.KEY_RIGHT|| key ==Keyboard.KEY_D) {
			mt.move(Direction.RIGHT);
			System.out.println("→");
		}else if (key == Keyboard.KEY_DOWN|| key ==Keyboard.KEY_S) {
			mt.move(Direction.DOWN);
			System.out.println("↓");
		}else if (key == Keyboard.KEY_RETURN) {
			Bullet shot = mt.shot();
			if (shot == null) {
				
			}else {
				
				addElment(shot);
			}
		
//			//判断flag的值是什么，true就是播放，false就暂停
//			if (flag) {
//				try {
//					SoundUtils.play("res\\snd\\start.wav");
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				flag = false;
//			}else {
//				SoundUtils.stop("res\\snd\\start.wav");
//				flag = true;
//			}
		}
	}
	//实时刷新(1s 50次)
	@Override
	protected void onDisplayUpdate() {
		
		//绘制砖墙
		for (Element element : list) {
			element.draw();
		}
		//销毁需要销毁的事物
		for (Element element : list) {
			if (element instanceof Destroyable) {
				//判断元素是否为子弹
				boolean flag = ((Destroyable) element).isDestroy();
				if (flag) {
					//销毁该元素的时候，需要获取到该元素返回的爆炸物对象
					Blast b = ((Destroyable)element).showDestroy();
					if (b != null) {
						addElment(b);
					}
					list.remove(element);//如果出界就移除
				}
			}
		}
	
		//System.out.println(list.size());
		/*
		 * 1. 校验 坦克 和 铁墙有没有碰撞上
		 * 2. 校验 具有移动功能的事物 和 具有阻挡功能的事物 有没有碰撞到一起
		 */
		for (Element e1 : list) {
			for (Element e2 : list) {
				// 如果e1是 具有移动功能的事物, 并且 e2 是具有阻挡功能的事物, 就校验他们有没有碰撞上.
				if (e1 instanceof Moveable && e2 instanceof Blockable) {
					// 调用MyTank#checkHit(), 校验有没有碰撞上
					boolean flag = ((Moveable) e1).checkHit((Blockable) e2);
					// 如果碰上了, 直接return即可
					if (flag) {
						break;
					}
				}
			}
		}
		/*
		 * 校验子弹和铁墙是否有碰撞
		 * 校验具有攻击能力和挨打能力
		 * 
		 */
		for (Element e1 : list) {
			for (Element e2 : list) {
				//如果e1是子弹，并且e2是铁墙，就校验他们是否有碰撞上
				if (e1 instanceof Attackable&& e2 instanceof Hitable) {
					//校验他们有没有碰上
					boolean flag = ((Attackable)e1).checkAttack((Hitable)e2);
					if (flag) {
						//如果碰上，针对子弹
						list.remove(e1);
						//针对铁墙，给一个相应的爆炸物
						Blast blast =((Hitable)e2).showAttack();
						addElment(blast);
					}
				}
			}
		}
}
	
	
	/**
	 * 往集合中添加元素，每添加一个元素，都按照渲染级别进行升序排序
	 * @param e
	 */
	public void addElment(Element e) {
		list.add(e);
		
		//每添加一个元素，都按照渲染级别进行排序
		list.sort(new Comparator<Element>() {

			@Override
			public int compare(Element e1, Element e2) {
				//前减后 升序  后减前 降序
				return e1.getOrder() - e2.getOrder() ;
			}
	});
  }

}
