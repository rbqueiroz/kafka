package br.com.rbqueiroz.valida_boleto.service.kafka;

import br.com.boleto.avro.Boleto;
import br.com.rbqueiroz.valida_boleto.mapper.BoletoMapper;
import br.com.rbqueiroz.valida_boleto.service.ValidarBoletoService;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class BoletoConsumer {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(BoletoConsumer.class);

    private final ValidarBoletoService validarBoletoService;

    public BoletoConsumer(ValidarBoletoService validarBoletoService) {
        this.validarBoletoService = validarBoletoService;
    }

    @KafkaListener(topics = "${spring.kafka.topco-boleto}", groupId = "${spring.kafka.consumer.group-id}")
    public void comsumeBoleto(Boleto boleto, Acknowledgment ack){
        LOGGER.info(String.format("Consumindo boleto -> %s ", boleto.getCodigoBarras()));
        validarBoletoService.validar(BoletoMapper.toEntity(boleto));
        ack.acknowledge();
    }
}

