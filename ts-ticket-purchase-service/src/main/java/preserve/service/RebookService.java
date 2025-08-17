package preserve.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;
import preserve.entity.RebookInfo;

public interface RebookService {
    Response rebook(RebookInfo info, HttpHeaders headers);
}
