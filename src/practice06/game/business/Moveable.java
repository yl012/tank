package practice06.game.business;
/**
 * 具有移动功能的事物
 * @author 幽怜
 *
 */
public interface Moveable {
	//具有移动功能的事物和具有阻挡功能的事物有没有撞上
	public abstract boolean checkHit(Blockable block);
}
