package br.com.rbqueiroz.valida_boleto.entity.enums;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicoConfig {

    @Value("${spring.kafka.topco-notificacao}")
    private String topicNotificacao;

    public NewTopic newTopic() {
        return TopicBuilder.name(this.topicNotificacao).build();
    }
}
