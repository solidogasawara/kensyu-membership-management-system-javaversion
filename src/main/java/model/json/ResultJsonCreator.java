package model.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResultJsonCreator {
	private SearchResult result;

	public ResultJsonCreator(SearchResult result) {
		this.result = result;
	}

	public String create() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		String json = mapper.writeValueAsString(result);
		
		return json;
	}
}
