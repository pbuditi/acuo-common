package com.acuo.common.model.trade;


import lombok.Data;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Data
public class TradeInfo {
    private String tradeId;
    private String tradingAccountId;
    private LocalDate tradeDate;
    private LocalDate maturityDate;
    private String clearedTradeId;
    private LocalDate clearedTradeDate;
    private String book;
    private String portfolio;
    private TradeStatus tradeStatus;
    private String derivativeType;
    /*private Set<Attribute> attributes;

    @Data
    public static class Attribute {
        private TradeAttributeType<?> type;
        private String value;
    }*/
}
