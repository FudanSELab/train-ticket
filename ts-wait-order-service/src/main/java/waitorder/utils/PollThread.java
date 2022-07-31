package waitorder.utils;

import edu.fudan.common.entity.Contacts;
import edu.fudan.common.util.Response;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import waitorder.entity.WaitListOrderStatus;
import waitorder.entity.WaitListOrderVO;
import waitorder.service.WaitListOrderService;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PollThread extends Thread{

    private Date waitUntil;

    private WaitListOrderVO waitListOrderVO;

    private HttpHeaders httpHeaders;

    private RestTemplate restTemplate;

    private WaitListOrderService waitListOrderService;

    final static Integer INTERVAL_MINUTES=5;

    public PollThread(Date waitUntilTime,WaitListOrderService service, WaitListOrderVO order, RestTemplate template, HttpHeaders headers){
        restTemplate=template;
        httpHeaders=headers;
        waitListOrderVO=order;
        waitListOrderService =service;
        waitUntil=waitUntilTime;
    }


    @Override
    public void run() {
        String service_url=getServiceUrl("ts-preserve-service");
        HttpEntity requestEntityPreserve = new HttpEntity(waitListOrderVO,httpHeaders);

        //TODO compare with waitUntilTime
        while(true){
            long currentTime=System.currentTimeMillis();
            if(waitUntil.getTime()>currentTime){
                // expired
                waitListOrderService.modifyWaitListOrderStatus(WaitListOrderStatus.EXPIRED.getCode(), waitListOrderVO.getAccountId());
                break;
            }
            Response postResult=doPreserve(service_url,requestEntityPreserve);
            if(postResult.getStatus()==0){
                //预定失败
                try {
                    TimeUnit.MINUTES.sleep(INTERVAL_MINUTES);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else{
                // preserve success
                waitListOrderService.modifyWaitListOrderStatus(WaitListOrderStatus.COLLECTED.getCode(),waitListOrderVO.getAccountId());
                break;
            }
        }
    }

    private String getServiceUrl(String serviceName) {
        return "http://" + serviceName;
    }

    private Response doPreserve(String url, HttpEntity requestParam){
        ResponseEntity<Response<Contacts>> rePostPreserveResult = restTemplate.exchange(
                url + "/api/v1/contactservice/preserve",
                HttpMethod.POST,
                requestParam,
                new ParameterizedTypeReference<Response<Contacts>>() {
                });
        return rePostPreserveResult.getBody();
    }

}
