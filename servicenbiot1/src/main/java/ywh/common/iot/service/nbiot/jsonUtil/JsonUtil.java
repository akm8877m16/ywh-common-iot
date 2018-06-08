package ywh.common.iot.service.nbiot.jsonUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static final ObjectMapper mapper = new ObjectMapper();

    public static final Map string2Map(String string){
        Map map = null;
        try {
            map = mapper.readValue(string, Map.class);
        }catch (Exception e){
            logger.info(e.getMessage());
        }finally {
            return map;
        }
    }

}
