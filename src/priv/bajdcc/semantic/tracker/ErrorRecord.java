package priv.bajdcc.semantic.tracker;

import java.util.ArrayList;

/**
 * 错误记录器链表
 *
 * @author bajdcc
 */
public class ErrorRecord {
	
	/**
	 * 错误集
	 */
	public ArrayList<TrackerError> arrErrors = new ArrayList<TrackerError>();
	
	/**
	 * 前向指针
	 */
	public ErrorRecord prev = null;
	
	public ErrorRecord(ErrorRecord prev) {
		this.prev = prev;	
	}
}