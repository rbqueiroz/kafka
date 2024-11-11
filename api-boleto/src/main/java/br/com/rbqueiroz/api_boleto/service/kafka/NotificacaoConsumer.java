package br.com.rbqueiroz.api_boleto.service.kafka;

import br.com.boleto.avro.Boleto;
import br.com.rbqueiroz.api_boleto.mapper.BoletoMapper;
import br.com.rbqueiroz.api_boleto.service.BoletoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoConsumer {

    private static Logger LOGGER = LoggerFactory.getLogger(NotificacaoConsumer.class);

    private final BoletoService boletoService;

    public NotificacaoConsumer(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    @KafkaListener(topics = "${spring.kafka.topco-notificacao}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Boleto boleto) {
        boletoService.atualizar(BoletoMapper.toEntity(boleto));
        LOGGER.info("Boleto recebido: -> %s", boleto.getCodigoBarras());
    }
}
