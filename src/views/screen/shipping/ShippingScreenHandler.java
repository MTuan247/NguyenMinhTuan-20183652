package views.screen.shipping;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import controller.PlaceOrderController;
import controller.PlaceRushOrderController;
import common.exception.InvalidDeliveryInfoException;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.popup.PopupScreen;

public class ShippingScreenHandler extends BaseScreenHandler implements Initializable {

	@FXML
	private Label screenTitle;

	@FXML
	private TextField name;

	@FXML
	private TextField phone;

	@FXML
	private TextField address;

	@FXML
	private TextField instructions;

	@FXML
	private ComboBox<String> province;

	@FXML
	private CheckBox isRushOrder;
	
	private Order order;

	public ShippingScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
		super(stage, screenPath);
		this.order = order;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
		name.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                content.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
		this.province.getItems().addAll(Configs.PROVINCES);
	}

	@FXML
	void submitDeliveryInfo(MouseEvent event) throws IOException, InterruptedException, SQLException {

		// add info to messages
		HashMap messages = new HashMap<>();
		messages.put("name", name.getText());
		messages.put("phone", phone.getText());
		messages.put("address", address.getText());
		messages.put("instructions", instructions.getText());
		messages.put("province", province.getValue());
		try {
			// process and validate delivery info
			getBController().processDeliveryInfo(messages);
		} catch (InvalidDeliveryInfoException e) {
			throw new InvalidDeliveryInfoException(e.getMessage());
		}
	
		// xử lý rush order
		if(isRushOrder.isSelected()) {
			boolean supportRushOrder = true;
			String message = "";
			PlaceRushOrderController placeRushOrderController = new PlaceRushOrderController();
			String location = province.getValue();
			List<Integer> mediaIds = new ArrayList<>();
			this.order.getlstOrderMedia().forEach(c -> {
				OrderMedia orderMedia = (OrderMedia) c;
				mediaIds.add(orderMedia.getMedia().getId());
			});
			
			if(!placeRushOrderController.checkAddressSupportRushOrder(location)) {
				supportRushOrder = false;
				message = "Your location not support rush order";
			} else if (!placeRushOrderController.checkExistMediaSupportRushOrder(mediaIds)) {
				supportRushOrder = false;
				message = "Not support rush order for any medias";
			}
			
			if(!supportRushOrder) {
				PopupScreen.error(message);
			} else {
				// calculate shipping fees
				int shippingFees = getBController().calculateShippingFee(order);
				order.setShippingFees(shippingFees);
				order.setDeliveryInfo(messages);
				
				// create invoice screen
				BaseScreenHandler RushOrderInfoScreenHandler = new RushOrderScreenHandler(this.stage, Configs.RUSH_ORDER_SCREEN_PATH, order);
				RushOrderInfoScreenHandler.setPreviousScreen(this);
				RushOrderInfoScreenHandler.setHomeScreenHandler(homeScreenHandler);
				RushOrderInfoScreenHandler.setScreenTitle("Shipping Screen");
				RushOrderInfoScreenHandler.setBController(getBController());
				RushOrderInfoScreenHandler.show();
			}
		} else {
			// calculate shipping fees
			int shippingFees = getBController().calculateShippingFee(order);
			order.setShippingFees(shippingFees);
			order.setDeliveryInfo(messages);
			
			// create invoice screen
			Invoice invoice = getBController().createInvoice(order);
			BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
			InvoiceScreenHandler.setPreviousScreen(this);
			InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
			InvoiceScreenHandler.setScreenTitle("Invoice Screen");
			InvoiceScreenHandler.setBController(getBController());
			InvoiceScreenHandler.show();
		}
		
	}

	public PlaceOrderController getBController(){
		return (PlaceOrderController) super.getBController();
	}

	public void notifyError(){
		// TODO: implement later on if we need
	}

}
