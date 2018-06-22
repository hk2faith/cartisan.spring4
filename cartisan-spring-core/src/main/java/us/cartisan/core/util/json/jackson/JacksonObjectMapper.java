package us.cartisan.core.util.json.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author Hyungkook Kim
 *
 */
public class JacksonObjectMapper {
	public static ObjectMapper pureMapper = null;
	public static ObjectMapper baseMapper = null;
	public static ObjectMapper camelMapper = null;

	public static ObjectMapper getPureMapper() {
		return pureMapper;
	}

	public static ObjectMapper get() {
		return baseMapper;
	}

	public static ObjectMapper getCamelMapper() {
		return camelMapper;
	}

	static {
		baseMapper = new ObjectMapper();
		baseMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		baseMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		baseMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

		pureMapper = new ObjectMapper();

		camelMapper = new ObjectMapper();
		camelMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		camelMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}
}
