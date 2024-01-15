//package org.choongang.team3.repositories;
//
//import com.querydsl.core.annotations.Config;
//import org.choongang.team3.entities.UpBitTicker;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//@FeignClient(name = "UpbitFeignClient", url = "https://api.upbit.com/v1/ticker", configuration = Config.class)
//public interface UpbitFeignClient {
//
//    @RequestMapping(method = RequestMethod.GET)
//    List<UpBitTicker> getUpBitPriceList(@RequestParam(value="markets") String markets);
//}