package practice06.game.domain;

import java.io.IOException;

import org.itheima.game.utils.DrawUtils;

/**
 * 草地
 * @author 幽怜
 *
 */
public class Grass extends Element {
	//属性

		//构造方法(有参)
		public Grass(int x, int y) {
			super(x, y);
			//设置图片的宽和高
			try {
				int [] size = DrawUtils.getSize("res\\img\\grass.gif");
				width = size[0];
				height = size[1];
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		/**
		 * 设计草坪的渲染级别
		 */
		public int getOrder() {
			// TODO Auto-generated method stub
			return 1;
		}
		//常用方法
		//1.画
		public void draw() {
			try {
				DrawUtils.draw("res\\img\\grass.gif", x, y);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
