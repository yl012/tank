package practice06.game.business;

import practice06.game.domain.Blast;

/**
 * 具有销毁功能的事物
 * @author 幽怜
 *
 */
public interface Destroyable {
	/**
	 * 判断是否需要销毁
	 * @return true 销毁 false 不销毁
	 */
	public abstract boolean isDestroy();
	/**
	 * 获取一个销毁时候的爆炸物
	 * @return 获取的爆炸物
	 */
	public abstract Blast showDestroy();
}
