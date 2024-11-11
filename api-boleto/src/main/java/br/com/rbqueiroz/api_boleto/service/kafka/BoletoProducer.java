package br.com.rbqueiroz.api_boleto.service.kafka;

import br.com.boleto.avro.Boleto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BoletoProducer {

    private final KafkaTemplate<String, Boleto> kafkaTemplate;

    @Value("${spring.kafka.topco-boleto}")
    private String topico;

    public BoletoProducer(KafkaTemplate<String, Boleto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Boleto boleto) {
        kafkaTemplate.send(topico, getKey(boleto), boleto);
    }

    private String getKey(Boleto boleto) {
        if(boleto.getCodigoBarras().toString().substring(0,1).equals("2")){
            return "chave1";
        }
        return "chave2";
    }
}
