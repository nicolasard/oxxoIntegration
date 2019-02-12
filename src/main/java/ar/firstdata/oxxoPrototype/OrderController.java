package ar.firstdata.oxxoPrototype;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.json.JsonParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import ar.firstdata.oxxoPrototype.entity.*;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RestController
public class OrderController {

	Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping("/createOrder")
    public String createOrder(@RequestParam(value="name", defaultValue="Nico Ard") String name,
    					      @RequestParam(value="amount", defaultValue="100") Double amount) throws IOException {
    	
    	logger.info("/createOrder : Creating an order ...");
    	
    	Order or = new Order();
    	or.setCurrency("MXN");
    	or.setAmount(130);
    	or.setLine_items(new LineItem[]{ new LineItem() {{ //Required for Oxxo Pay
    	    setName("Pizzas");
    	    setQuantity(1);
    	    setUnit_price(1000);
    	}}});
    	or.setCharges(new Charge[] { new Charge() {
    		{ setPayment_method(new PaymentMethod() {{setType("oxxo_cash");}});}
    	}});
    	or.setCustomer_info(new CustomerInfo() {{ //Required for Oxxo Pay
    		setEmail("nicolas.ard@gmail.com");
    		setName("nico");
    		setPhone("+5242761442"); //OXXO Validates the phone number
    	}});
    	
    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    	String json = ow.writeValueAsString(or);
    	
    	logger.info(json);
    	
    	OkHttpClient client = new OkHttpClient();
    	
    	MediaType mediaType = MediaType.parse("application/json");
    	RequestBody body = RequestBody.create(mediaType,json);
    	Request request = new Request.Builder()
    	  .url("https://api.conekta.io/orders")
    	  .post(body)
    	  .addHeader("Content-type", "application/json")
    	  .addHeader("Accept", "application/vnd.conekta-v2.0.0+json")
    	  .addHeader("Authorization", "Basic a2V5X2g0ZHJNN2EyeTRERnljNkdhbWRWcUE6Og==")
    	  .addHeader("cache-control", "no-cache")
    	  .build();

    	Response response = client.newCall(request).execute();
    	
        return response.body().string();
    }
}