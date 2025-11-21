package order.service.impl;

import edu.fudan.common.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.entity.Assurance;
import order.entity.AssuranceType;
import order.repository.AssuranceRepository;
import order.service.AssuranceOrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssuranceOrderServiceImpl implements AssuranceOrderService {

    private final AssuranceRepository assuranceRepository;

    @Override
    public Response<Assurance> create(int typeIndex, String orderId, HttpHeaders headers) {
        Assurance existing = assuranceRepository.findByOrderId(orderId);
        if (existing != null) {
            log.error("[create][Assurance already exists][typeIndex: {}, orderId: {}]", typeIndex, orderId);
            return failure("Fail.Assurance already exists", null);
        }

        AssuranceType type = AssuranceType.getTypeByIndex(typeIndex);
        if (type == null) {
            log.warn("[create][Assurance type doesn't exist][typeIndex: {}, orderId: {}]", typeIndex, orderId);
            return failure("Fail.Assurance type doesn't exist", null);
        }

        Assurance assurance = new Assurance(UUID.randomUUID().toString(), UUID.fromString(orderId).toString(), type);
        assuranceRepository.save(assurance);
        log.info("[create][Success]");
        return success("Success", assurance);
    }

    @Override
    public Response<UUID> deleteById(UUID assuranceId, HttpHeaders headers) {
        assuranceRepository.deleteById(assuranceId.toString());
        Optional<Assurance> a = assuranceRepository.findById(assuranceId.toString());
        if (!a.isPresent()) {
            log.info("[deleteById][Success][assuranceId: {}]", assuranceId);
            return success("Delete Success with Assurance id", null);
        }
        log.error("[deleteById][Fail][Assurance not clear][assuranceId: {}]", assuranceId);
        return failure("Fail.Assurance not clear", assuranceId);
    }

    @Override
    public Response<UUID> deleteByOrderId(UUID orderId, HttpHeaders headers) {
        assuranceRepository.removeAssuranceByOrderId(orderId.toString());
        Assurance isExistAssurace = assuranceRepository.findByOrderId(orderId.toString());
        if (isExistAssurace == null) {
            log.info("[deleteByOrderId][Success][orderId: {}]", orderId);
            return success("Delete Success with Order Id", null);
        }
        log.error("[deleteByOrderId][Fail][Assurance not clear][orderId: {}]", orderId);
        return failure("Fail.Assurance not clear", orderId);
    }

    @Override
    public Response<Assurance> changeAssuranceType(String assuranceId, int typeIndex, HttpHeaders headers) {
        Optional<Assurance> opt = assuranceRepository.findById(assuranceId);
        if (!opt.isPresent()) {
            log.error("[modify][Assurance not found][assuranceId: {}, typeIndex: {}]", assuranceId, typeIndex);
            return failure("Fail.Assurance not found.", null);
        }

        AssuranceType type = AssuranceType.getTypeByIndex(typeIndex);
        if (type == null) {
            log.error("[modify][Assurance Type not exist][assuranceId: {}, typeIndex: {}]", assuranceId, typeIndex);
            return failure("Assurance Type not exist", null);
        }

        Assurance oldAssurance = opt.get();
        oldAssurance.setType(type);
        assuranceRepository.save(oldAssurance);
        log.info("[modify][Success][assuranceId: {}, typeIndex: {}]", assuranceId, typeIndex);
        return success("Modify Success", oldAssurance);
    }

    private static <T> Response<T> success(String msg, T data) {
        return new Response<>(1, msg, data);
    }

    private static <T> Response<T> failure(String msg, T data) {
        return new Response<>(0, msg, data);
    }
}
