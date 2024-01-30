package ro.tuc.ds2020.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class Config {

    static final String topicExchangeName = "spring-boot-exchange1";

    static final String queueName1 = "spring-boot1"; //create
    static final String queueName2 = "spring-boot2"; //delete
    static final String queueName3 = "spring-boot3"; //update

    @Bean
    Queue queue1() {
        return new Queue(queueName1, false);
    }

    @Bean
    Queue queue2() {
        return new Queue(queueName2, false);
    }

    @Bean
    Queue queue3() {
        return new Queue(queueName3, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding1() {
        return BindingBuilder
                .bind(queue1())
                .to(exchange())
                .with("foo.bar.#1");
    }

    @Bean
    Binding binding2() {
        return BindingBuilder
                .bind(queue2())
                .to(exchange())
                .with("foo.bar.#2");
    }

    @Bean
    Binding binding3() {
        return BindingBuilder
                .bind(queue3())
                .to(exchange())
                .with("foo.bar.#3");
    }

}
