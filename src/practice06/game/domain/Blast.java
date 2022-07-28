package practice06.game.domain;

import java.io.IOException;

import javax.sound.sampled.Line;
import javax.sound.sampled.LineListener;

import org.itheima.game.utils.DrawUtils;

import practice06.game.business.Destroyable;
import practice06.game.business.Hitable;

/**
 * 爆炸物类
 * @author 幽怜
 *
 */
public class Blast extends Element implements Destroyable{

	//属性
	//记录是所以爆炸物图的路径
	private String [] arr = {"res\\img\\blast_1.gif","res\\img\\blast_2.gif","res\\img\\blast_3.gif",
			"res\\img\\blast_4.gif","res\\img\\blast_5.gif",
			"res\\img\\blast_6.gif","res\\img\\blast_7.gif",
			"res\\img\\blast_8.gif"};
	//记录索引，表示要绘制哪张爆炸物的图片
	private int index;
	
	//定义一个变量，用来记录何时销毁
	boolean isDestroy;
	
	//构造方法
	//爆炸物的坐标是根据铁墙的坐标来计算的
	public Blast(Hitable h){
		this(h,false);
	}
	//flag的值，如果是true：挨打（绘制一部分图）false：销毁（绘制全部图片）
	public Blast(Hitable h,boolean flag){
		Element steel = (Element)h;
		//获取挨打事物的宽高
		int x1 = steel.x;
		int y1 = steel.y;
		int widht1 = steel.width;
		int height1 = steel.height;
		//获取图片的宽高
		try {
			int [] size = DrawUtils.getSize("res\\img\\blast_1.gif");
			width = size[0];
			height = size[1];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//计算爆炸物的坐标
		x = x1 + (widht1 - width)/2;//铁墙的x轴 + （铁墙的宽/2+爆炸物的宽/2）
		y = y1 + (height1 - height)/2;//铁墙的y轴 + (铁墙的高/2 + 爆炸物的高/2)
		//如果是挨打状态就修改数组arr的图片的个数
		if (flag) {
			 arr =new String []{"res\\img\\blast_1.gif","res\\img\\blast_2.gif","res\\img\\blast_3.gif",
					"res\\img\\blast_4.gif","res\\img\\blast_5.gif"};
		}
	}
	//成员方法
	public void draw() {
		//获取要绘制的图片，绘制完成后就绘制下一张
		String res = arr[index++];
		//做越界处理
		if (index >= arr.length) {
			index = 0;//index归零，说明爆炸物图片已经打印一圈。
			isDestroy = true; //true 销毁，false不销毁
			return;
		}
		try {
			DrawUtils.draw(res, x, y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 返回爆炸物的销毁状态，即是否需要销毁
	 *@return true 销毁，false不销毁
	 */
	public boolean isDestroy() {
		return isDestroy;
	}
	//这里直接return 一个null，因为本身就是爆炸物，所以销毁的时候直接从集合中移除就可以
	public Blast showDestroy() {
		return null;
	}
}
