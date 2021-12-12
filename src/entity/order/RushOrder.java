package entity.order;

import java.util.HashMap;

/**
 * Lop thuc the RushOrder
 * @author Hieu Pham
 *
 */
public class RushOrder {
	private Order order;
	private HashMap<String, String> rushOrderInfomation;
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public HashMap<String, String> getRushOrderInfomation() {
		return rushOrderInfomation;
	}
	public void setRushOrderInfomation(HashMap<String, String> rushOrderInfomation) {
		this.rushOrderInfomation = rushOrderInfomation;
	}	
	
}
