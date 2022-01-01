package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import entity.cart.Cart;
import entity.cart.CartMedia;
import common.exception.InvalidDeliveryInfoException;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import views.screen.popup.PopupScreen;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nmtuan-20183652
 */
public class PlaceOrderController extends BaseController{

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder button
     * @throws SQLException
     */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException{
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), 
                                                   cartMedia.getQuantity(), 
                                                   cartMedia.getPrice());    
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }
    
    /**
   * The method validates the info
   * @param info
   * @throws InterruptedException
   * @throws IOException
   */
    public boolean validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    	String phoneNumber = info.get("address");
    	String name = info.get("name");
    	String address = info.get("phoneNumber");
    	
    	return validateName(name) && validatePhoneNumber(phoneNumber) && validateAddress(address);
    
    }
    
    public boolean validatePhoneNumber(String phoneNumber) {
    	// Nguyen Minh Tuan - 20183652
    	// Kiem tra do dai 10 chu so
    	if(phoneNumber.length() != 10) {
    		return false;
    	}
    	
    	// Kiem tra bat dau bang ky tu "0"
    	if(!phoneNumber.startsWith("0")) {
    		return false;
    	}
    	
    	// Kiem tra chi chua so
    	try {
			Integer.parseInt(phoneNumber);
		} catch (NumberFormatException e) {
			return false;
		}
    	
    	return true;
    }
    
    public boolean validateName(String name) {
    	// Nguyen Minh Tuan - 20183652
    	// Kiem tra khac null, khac blank
    	if(name == null || name.isBlank()) {
    		return false;
    	}
    	
    	// Kiem tra chi chua ky tu chu cai
    	return name.matches("[a-zA-Z]+");
    }
    
    public boolean validateAddress(String address) {
    	// Nguyen Minh Tuan - 20183652
    	// Kiem tra khac null, khac blank
    	if(address == null || address.isBlank()) {
    		return false;
    	}
    	
    	// Kiem tra khong chua ky tu dac biet
    	return address.matches("[0-9a-zA-Z ]+");
    }
    

    /**
     * This method calculates the shipping fees of order
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(Order order){
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
