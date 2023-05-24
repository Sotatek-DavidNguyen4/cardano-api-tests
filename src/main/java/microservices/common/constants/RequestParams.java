package microservices.common.constants;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
public class RequestParams {
    int page;

    int size;
    List<String> sort;
    public RequestParams(Map<String, Object> params){
        if (params.containsKey("page")) {
            try {
                page = Integer.parseInt((String) ((List<String>) params.get("page")).get(0));
                if (page < 0) {
                    page = 0;
                }
            } catch (NumberFormatException e) {
                page = 0;
            }
        } else {
            page = 0;
        }
        if (params.containsKey("size")) {
            try {
                size = Integer.parseInt((String) ((List<String>) params.get("size")).get(0));
                if (size < 0) {
                    size = 20;
                }
            } catch (NumberFormatException e) {
                size = 20;
            }
        } else {
            size = 20;
        }
        if (params.containsKey("sort")) {
            this.sort = (List<String>) params.get("sort");
        }

    }


}
