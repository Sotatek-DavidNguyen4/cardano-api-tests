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
    public RequestParams(Map<String, Object> params, int defaultPage, int defaultSize){
        if (params.containsKey("page")) {
            try {
                page = Integer.parseInt(((List<String>) params.get("page")).get(0));
                if (page < 0) {
                    page = defaultPage;
                }
            } catch (NumberFormatException e) {
                page = defaultPage;
            }
        } else {
            page = defaultPage;
        }
        if (params.containsKey("size")) {
            try {
                size = Integer.parseInt((String) ((List<String>) params.get("size")).get(0));
                if (size < 0) {
                    size = defaultSize;
                }
            } catch (NumberFormatException e) {
                size = defaultSize;
            }
        } else {
            size = defaultSize;
        }
        if (params.containsKey("sort")) {
            this.sort = (List<String>) params.get("sort");
        }

    }
}
