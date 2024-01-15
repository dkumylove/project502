//package org.choongang.team3.service;
//
//import lombok.RequiredArgsConstructor;
//import org.choongang.team3.entities.UpBitTicker;
//import org.choongang.team3.repositories.UpbitFeignClient;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class UpbitService {
//
//    private final UpbitFeignClient upbitFeignClient;
//
//    public List<UpBitTicker> getUpbitPrice(String markets){
//        return upbitFeignClient.getUpBitPriceList(markets);
//    }
//}
