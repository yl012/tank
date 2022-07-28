package practice06.game.business;
/**
 * 具有挨打能力的接口
 * @author 幽怜
 *
 */

import practice06.game.domain.Blast;

public interface Hitable {
	/**
	 * 挨打以后返回一个爆炸物
	 * @return
	 */
	public abstract Blast showAttack();
}
