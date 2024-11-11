package br.com.rbqueiroz.valida_boleto.mapper;

import br.com.boleto.avro.Boleto;
import br.com.rbqueiroz.valida_boleto.entity.BoletoEntity;
import br.com.rbqueiroz.valida_boleto.entity.enums.SituacaoBoleto;

public class BoletoMapper {

    public static BoletoEntity toEntity(Boleto boleto) {
        return BoletoEntity.builder()
                .codigoBarras(boleto.getCodigoBarras().toString())
                .situacaoBoleto(SituacaoBoleto.values()[boleto.getSitaucacaoBoleto()])
                .build();
    }

    public static Boleto toAvro(BoletoEntity boleto) {
        return Boleto.newBuilder()
                .setCodigoBarras(boleto.getCodigoBarras())
                .setSitaucacaoBoleto(boleto.getSituacaoBoleto().ordinal())
                .build();
    }
}
