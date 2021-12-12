package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class nay dieu khien luong usecase Place rush order
 * @author nmtuan-20183652
 */
public class PlaceRushOrderController {
	
	/**
	 * Thuoc tinh giup format thoi gian theo dinh dang
	 */
	public static DateFormat TIME_FORMATER = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	/**
	 * Danh sach dia chi ho tro giao hang nhanh
	 */
	public static List<String> ADDRESS_SUPPORT_RUSH_ORDER = List.of("Hà Nội");
	
	/**
	 * Danh sach san pham ho tro giao hang nhanh
	 */
	public static List<Integer> MEDIA_ID_SUPPORT_RUSH_ORDER = List.of(1, 2, 3, 4, 5, 6);
	
	/**
     * Thuoc tinh giup log thong tin ra console
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceRushOrderController.class.getName());
    
    
    /**
     * Kiem tra co ho tro giao hang nhanh hay khong
     * @param location: dia chi
     * @param mediaIds: danh sach id san pham
     * @return true - co, false - khong
     */
    public boolean checkSupportRushOrder(String location, List<Integer> mediaIds) {
    	return checkAddressSupportRushOrder(location) && checkExistMediaSupportRushOrder(mediaIds);
    }
    
    /**
     * Phuong thuc kiem tra dia chi co ho tro rush order khong
     * @param location: dia chi 
     * @return true - co, false - khong
     * @author nmtuan
     */
    public boolean checkAddressSupportRushOrder(String location) {
    	// Nguyen Minh Tuan - 20183652
    	if(location == null) {
    		return false;
    	}
    	
    	return ADDRESS_SUPPORT_RUSH_ORDER.contains(location.toLowerCase());
    }
    
    /**
     * Phuong thuc kiem tra co ton tai san pham (it nhat 1) ho tro giao hang nhanh
     * @param mediaIds: danh sach id san pham
     * @return true - co, false - khong
     * @author nmtuan
     */
    public boolean checkExistMediaSupportRushOrder(List<Integer> mediaIds) {
    	// Nguyen Minh Tuan - 20183652
    	if(mediaIds == null) {
    		return false;
    	}
    	
    	for(int id: mediaIds) {
    		if(MEDIA_ID_SUPPORT_RUSH_ORDER.contains(id)) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    
    /**
     * Phuong thuc validate thong tin rush order
     * @param info: thong tin rush order
     * @return true - hop le, false - khong hop le
     * @author nmtuan
     */
    public boolean validateRushOrderInfo(String info) {
    	// Nguyen Minh Tuan - 20183652
    	return validateSimpleString(info);
    }
    
    /**
     * Phuong thuc validate chi dan rush order
     * @param instruction: chi dan rush order
     * @return true - hop le, false - khong hop le
     * @author nmtuan
     */
    public boolean validateRushOrderInstruction(String instruction) {
    	// Nguyen Minh Tuan - 20183652
    	return validateSimpleString(instruction);
    }
    
    /**
     * Phuong thuc validate thoi gian nhan
     * @param timeStr: thoi gian nhan
     * @return true - hop le, false - khong hop le
     * @author nmtuan
     */
    public boolean validateReceiveTime(String timeStr) {
    	if(timeStr == null) {
    		return false;
    	}
    	
    	DateFormat df = TIME_FORMATER;
        df.setLenient(false);
    	try {
            TIME_FORMATER.parse(timeStr);
//            System.out.println(TIME_FORMATER.parse(timeStr));
        } catch (ParseException e) {
            return false;
        }
    	
    	return true;
	}
    
    /**
     * Phuong thuc validate co ban 1 xau ky tu
     * @param str: xau ky tu
     * @return true - hop le, false - khong hop le
     * @author nmtuan
     */
    public boolean validateSimpleString(String str) {
    	// Nguyen Minh Tuan - 20183652
    	// Kiem tra khac null, khac blank
    	if(str == null || str.isBlank()) {
    		return false;
    	}
    	
    	// Kiem tra khong chua ky tu dac biet
    	return str.matches("[0-9a-zA-Z., ]+");
    }
}
