package br.com.rbqueiroz.valida_boleto.service.kafka;

import br.com.boleto.avro.Boleto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoProducer {

    @Value("${spring.kafka.topco-notificacao}")
    private String topicNotificacao;

    private final KafkaTemplate<String, Boleto> kafkaTemplate;

    public NotificacaoProducer(KafkaTemplate<String, Boleto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Boleto boleto) {
        kafkaTemplate.send(topicNotificacao, boleto);
    }
}
