package practice06.game.business;
/**
 * 具有攻击能力的接口
 * @author 幽怜
 *
 */
public interface Attackable {
	/**
	 * 校验具有攻击能力的事物 是否和具有挨打能力的事物是否碰撞到一起
	 * @param hit 要校验的具有挨打能力的事物
	 * @return true 碰到一起， false 没有碰到一起
	 */
	public abstract boolean checkAttack(Hitable hit);
}
