//package com.example.register.util;
//
//import com.sts.erp.dto.MasterReqApprovePageDto;
//import com.sts.erp.dto.MaterialRequestDTO;
//import com.sts.erp.model.Quantity;
//import com.sts.erp.repository.QuantityRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Component
//public class QuantityApproveCheck {
//
//    private final QuantityRepository quantityRepository;
//
//    @Autowired
//    public QuantityApproveCheck(QuantityRepository quantityRepository) {
//        this.quantityRepository = quantityRepository;
//    }
//
//    public boolean checkQuantity(MasterReqApprovePageDto materialId) {
//        for (MaterialRequestDTO request : materialId.getMaterialRequestDTOList()) {
//            System.err.println(request + "siteid " + request.getSiteId() + "materialid site id " + materialId.getSiteName());
//            Optional<Quantity> quantities = quantityRepository.findByMaterialIdAndSiteId(
//                    request.getMaterialId(), request.getSiteId());
//
//            if (quantities.isPresent() && quantities.get().getAvailableQuantity() >= request.getRequestedQuantity()) {
//            } else {
//                return false;
//            }
//        }
//        return true;
//    }
//}
