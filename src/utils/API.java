package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;

/**
 * Class cung cap cac phuong thuc giup gui request len server va  nhan du lieu tra ve
 * Date: 7/12/2021
 * @author nmtuan-20183652
 * @version 1.0
 */
public class API {

	/**
	 * Thuoc tinh giup format ngay thang theo dinh dang
	 */
	public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	/**
	 * Thuoc tinh giup log thong tin ra console
	 */
	private static Logger LOGGER = Utils.getLogger(Utils.class.getName());
	
	/**
	 * Thiet lap connection toi server
	 * @param url: duong dan toi server can request
	 * @param method: phuong thuc goi api
	 * @param token: doan ma bam can cung cap de xac thuc nguoi dung
	 * @return connection
	 * @throws IOException
	 * @author nmtuan - 20183652
	 */
	private static HttpURLConnection setupConnection(String url, String method, String token) throws IOException {
		// setup
		LOGGER.info("Request URL: " + url + "\n");
		URL line_api_url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		return conn;
	}
	
	/**
	 * Phuong thuc doc du lieu tra ve tu server
	 * @param conn: connection to server
	 * @return response: phan hoi tra ve tu server
	 * @throws IOException
	 * @author nmtuan - 20183652
	 */
	private static String readResponse(HttpURLConnection conn) throws IOException {
		// doc du lieu gui ve tu server
		BufferedReader in;
		String inputLine;
		if (conn.getResponseCode() / 100 == 2) {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		
		StringBuilder response = new StringBuilder(); // su dung StringBuilder de toi uu ve mat bo nho
		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);
		in.close();
		LOGGER.info("Respone Info: " + response.substring(0, response.length() - 1).toString());
		return response.substring(0, response.length() - 1).toString();
	}
	

	/**
	 * Phuong thuc giup goi cac API GET
	 * @param url: duong dan toi server can request
	 * @param token: doan ma bam can cung cap de xac thuc nguoi dung
	 * @return response: phan hoi tu server (dang String)
	 * @throws Exception
	 * @author nmtuan - 20183652
	 */
	public static String get(String url, String token) throws Exception {
		// Nguyen Minh Tuan - 20183652
		// Phan 1: setup
		HttpURLConnection conn = setupConnection(url, "GET", token);
		
		// Phan 2: doc du lieu tra ve tu server
		String response = readResponse(conn);
		
		return response;
	}


	int var;

	/**
	 * Phuong thuc giup goi cac API POST (thanh toan...)
	 * @param url: duong dan toi server can request
	 * @param token: doan ma bam can cung cap de xac thuc nguoi dung
	 * @param data: du lieu dua len server de xe ly (dang JSON)
	 * @return response: phan hoi tu server (dang string)
	 * @throws IOException
	 * @author nmtuan - 20183652
	 */
	public static String post(String url, String data, String token) throws IOException {
		// Nguyen Minh Tuan - 20183652
		// cho phep PATCH protocol
		allowMethods("PATCH");
		// Phan 1: setup
		HttpURLConnection conn = setupConnection(url, "PATCH", token);
		
		// Phan 2: gui du lieu
		Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(data);
		writer.close();
		
		// Phan 3: doc du lieu gui ve tu server
		String response = readResponse(conn);
		
		return response;
	}

	
	/**
	 * Phuong thuc cho phep goi cac loai giao thuc API khac nhau
	 * nhu PATCH, PUT,... (chi hoat dong voi Java 11)
	 * @deprecated chi hoat dong voi Java <= 11
	 * @param methods: giao thuc can cho phep (PATCH, PUT,...)
	 * @author nmtuan - 20183652
	 */
	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			methodsField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

}
