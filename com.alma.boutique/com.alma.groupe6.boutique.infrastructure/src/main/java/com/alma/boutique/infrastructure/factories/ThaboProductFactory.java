package com.alma.boutique.infrastructure.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.product.Price;
import com.alma.boutique.infrastructure.conversion.ThaboProduct;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @author Thomas Minier
 */
public class ThaboProductFactory implements IFactory<ThaboProduct> {
	
	private String id;
  private String name;
  private String price;
  private String description;
  private String productType;
  private int quantity;
  


	public ThaboProductFactory(String id) {
		
		this.id = id;
	}

  /**
   * Private method used to create a HTTP connexion pre-configured via an API
   * @param url the url of the webservice that we are trying to contact
   * @return an HTTP connexion configured for the webservice
   * @throws IOException
   */
  private HttpURLConnection setupConnection(String url) throws IOException {
      URL service = new URL(url);
      HttpURLConnection httpCon = (HttpURLConnection) service.openConnection();
      httpCon.addRequestProperty("User-Agent", "Mozilla/4.76");
      return httpCon;
  }

	@Override
  public ThaboProduct create() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		HttpURLConnection httpCon = setupConnection("https://fluffy-stock.herokuapp.com/api/product/" + id + "/order/1");
		return mapper.readValue(httpCon.getInputStream(), ThaboProduct.class);
  }
}
