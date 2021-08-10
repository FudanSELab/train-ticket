package assurance.service;

import assurance.entity.*;
import assurance.repository.AssuranceRepository;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author fdse
 */
@Service
public class AssuranceServiceImpl implements AssuranceService {

    @Autowired
    private AssuranceRepository assuranceRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AssuranceServiceImpl.class);

    @Override
    public Response findAssuranceById(UUID id, HttpHeaders headers) {
        Assurance assurance = assuranceRepository.findById(id);
        if (assurance == null) {
            return new Response<>(0, "No Conotent by this id", null);
        } else {
            return new Response<>(1, "Find Assurace Success", assurance);
        }
    }

    @Override
    public Response findAssuranceByOrderId(UUID orderId, HttpHeaders headers) {
        Assurance assurance = assuranceRepository.findByOrderId(orderId);
        if (assurance == null) {
            return new Response<>(0, "No Content by this orderId", null);
        } else {
            return new Response<>(1, "Find Assurace Success", assurance);
        }
    }

    @Override
    public Response create(int typeIndex, String orderId, HttpHeaders headers) {
        Assurance a = assuranceRepository.findByOrderId(UUID.fromString(orderId));
        AssuranceType at = AssuranceType.getTypeByIndex(typeIndex);
        if (a != null) {
            AssuranceServiceImpl.LOGGER.info("[Assurance-Add&Delete-Service][AddAssurance] Fail.Assurance already exists");
            return new Response<>(0, "Fail.Assurance already exists", null);
        } else if (at == null) {
            AssuranceServiceImpl.LOGGER.info("[Assurance-Add&Delete-Service][AddAssurance] Fail.Assurance type doesn't exist");
            return new Response<>(0, "Fail.Assurance type doesn't exist", null);
        } else {
            Assurance assurance = new Assurance(UUID.randomUUID(), UUID.fromString(orderId), at);
            assuranceRepository.save(assurance);
            AssuranceServiceImpl.LOGGER.info("[Assurance-Add&Delete-Service][AddAssurance] Success.");
            return new Response<>(1, "Success", assurance);
        }
    }

    @Override
    public Response deleteById(UUID assuranceId, HttpHeaders headers) {
        assuranceRepository.deleteById(assuranceId);
        Assurance a = assuranceRepository.findById(assuranceId);
        if (a == null) {
            AssuranceServiceImpl.LOGGER.info("[Assurance-Add&Delete-Service][DeleteAssurance] Success.");
            return new Response<>(1, "Delete Success with Assurance id", null);
        } else {
            AssuranceServiceImpl.LOGGER.info("[Assurance-Add&Delete-Service][DeleteAssurance] Fail.Assurance not clear.");
            return new Response<>(0, "Fail.Assurance not clear", assuranceId);
        }
    }

    @Override
    public Response deleteByOrderId(UUID orderId, HttpHeaders headers) {
        assuranceRepository.removeAssuranceByOrderId(orderId);
        Assurance isExistAssurace = assuranceRepository.findByOrderId(orderId);
        if (isExistAssurace == null) {
            AssuranceServiceImpl.LOGGER.info("[Assurance-Add&Delete-Service][DeleteAssurance] Success.");
            return new Response<>(1, "Delete Success with Order Id", null);
        } else {
            AssuranceServiceImpl.LOGGER.info("[Assurance-Add&Delete-Service][DeleteAssurance] Fail.Assurance not clear.");
            return new Response<>(0, "Fail.Assurance not clear", orderId);
        }
    }

    @Override
    public Response modify(String assuranceId, String orderId, int typeIndex, HttpHeaders headers) {
        Response oldAssuranceResponse = findAssuranceById(UUID.fromString(assuranceId), headers);
        Assurance oldAssurance = (Assurance) oldAssuranceResponse.getData();
        if (oldAssurance == null) {
            AssuranceServiceImpl.LOGGER.info("[Assurance-Modify-Service][ModifyAssurance] Fail.Assurance not found.");
            return new Response<>(0, "Fail.Assurance not found.", null);
        } else {
            AssuranceType at = AssuranceType.getTypeByIndex(typeIndex);
            if (at != null) {
                oldAssurance.setType(at);
                assuranceRepository.save(oldAssurance);
                AssuranceServiceImpl.LOGGER.info("[Assurance-Modify-Service][ModifyAssurance] Success.");
                return new Response<>(1, "Modify Success", oldAssurance);
            } else {
                AssuranceServiceImpl.LOGGER.info("[Assurance-Modify-Service][ModifyAssurance] Fail.Assurance Type not exist.");
                return new Response<>(0, "Assurance Type not exist", null);
            }
        }
    }

    @Override
    public Response getAllAssurances(HttpHeaders headers) {
        List<Assurance> as = assuranceRepository.findAll();
        if (as != null && !as.isEmpty()) {
            ArrayList<PlainAssurance> result = new ArrayList<>();
            for (Assurance a : as) {
                PlainAssurance pa = new PlainAssurance();
                pa.setId(a.getId());
                pa.setOrderId(a.getOrderId());
                pa.setTypeIndex(a.getType().getIndex());
                pa.setTypeName(a.getType().getName());
                pa.setTypePrice(a.getType().getPrice());
                result.add(pa);
            }
            return new Response<>(1, "Success", result);
        } else {
            return new Response<>(0, "No Content, Assurance is empty", null);
        }
    }

    @Override
    public Response getAllAssuranceTypes(HttpHeaders headers) {

        List<AssuranceTypeBean> atlist = new ArrayList<>();
        for (AssuranceType at : AssuranceType.values()) {
            AssuranceTypeBean atb = new AssuranceTypeBean();
            atb.setIndex(at.getIndex());
            atb.setName(at.getName());
            atb.setPrice(at.getPrice());
            atlist.add(atb);
        }
        if (!atlist.isEmpty()) {
            return new Response<>(1, "Find All Assurance", atlist);
        } else {
            return new Response<>(0, "Assurance is Empty", null);
        }
    }
}
