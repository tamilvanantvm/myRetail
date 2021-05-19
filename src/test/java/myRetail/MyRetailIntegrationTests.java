package myRetail;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import myRetail.dao.Price;
import myRetail.dao.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MyRetailIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	final static private int successfulID = 13860428;

	@Test
	public void testGetProductById() throws Exception {
		mockMvc.perform(get("/products/" + successfulID)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(successfulID)));
	}

	@Test
	public void testUpdateProductPriceById() throws Exception {
		Product requestBody = new Product();
		Price Price = new Price();
		Price.setPrice(new BigDecimal(14.95));
		requestBody.setId(successfulID);
		requestBody.setPrice(Price);

		mockMvc.perform(put("/products/" + successfulID).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(requestBody))).andExpect(status().isOk())
				.andExpect(jsonPath("$.current_price.value", is(requestBody.getPrice().getPrice())));
	}

	@Test
	public void testGetProductById_NotFound() throws Exception {
		mockMvc.perform(get("/products/" + 123456789)).andExpect(status().isNotFound());
	}

	@Test
	public void testUpdateProductPriceById_NotFound() throws Exception {
		Product requestBody = new Product();
		Price Price = new Price();
		Price.setPrice(new BigDecimal(14.95));
		requestBody.setId(12345678);
		requestBody.setPrice(Price);

		mockMvc.perform(put("/products/" + requestBody.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(requestBody))).andExpect(status().isNotFound());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}