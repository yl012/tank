package practice06.game.domain;

import java.io.IOException;

import org.itheima.game.utils.DrawUtils;

/**
 * 元素类
 * @author 幽怜
 *
 */
public abstract class Element {
	    //属性
		//1.坐标
		protected int x;
		protected int y;
		//2.宽和高
		protected int width;
		protected int height;

		
		//构造方法(有参)
		public Element(int x, int y) {
			this.x = x;
			this.y = y;
			
			
		}
		public Element() {
			
		}
		//常用方法
		//1.画
		public abstract void draw();
	/**
	 * 返回元素的渲染级别，默认都是零，渲染级别越大，元素越靠后	
	 */
		public int getOrder() {
			return 0;
		}
}
