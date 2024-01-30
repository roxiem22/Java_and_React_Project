package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.Consumer;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.ConsumerRepository;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.UUID;

@Service
public class ConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final ConsumerRepository consumerRepository;

    @Autowired
    public ConsumerService(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    public UUID insert(Consumer consumer) {
        consumer = consumerRepository.save(consumer);
        return consumer.getId();
    }

    public UUID update(Consumer consumer) {
        Consumer consumer1 = consumerRepository.findConsumerById(consumer.getId());

        consumer1.setDevice(consumer.getDevice());
        consumer1.setTimestamp(consumer.getTimestamp());
        consumer1.setValue(consumer.getValue());
        consumer1 = consumerRepository.save(consumer1);
        LOGGER.debug("Device with id {} was updated in db", consumer1.getId());
        return consumer1.getId();
    }

    public void delete(Consumer consumer) {
        consumerRepository.delete(consumer);
        LOGGER.debug("Device with id {} was deleted from db", consumer.getId());
    }

}
