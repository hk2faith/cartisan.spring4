package us.cartisan.spring.reactor.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.bus.Event;
import reactor.bus.EventBus;
import us.cartisan.spring.reactor.sample.handler.EventHandler;
import us.cartisan.spring.reactor.sample.model.Shipment;

import static reactor.bus.selector.Selectors.$;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final Logger LOG = LoggerFactory.getLogger("Application");

    private final EventBus eventBus;
    private final EventHandler eventHandler;

    @Autowired
    public Application(EventBus eventBus, EventHandler eventHandler) {
        this.eventBus = eventBus;
        this.eventHandler = eventHandler;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        eventBus.on($("eventHandler"), eventHandler);

        //Publish messages here
        for (int i = 0; i < 10; i++) {
            Shipment shipment = new Shipment();
            shipment.setShipmentId(String.valueOf(i));
            eventBus.notify("eventHandler", Event.wrap(shipment));
            LOG.info("Published shipment number {}.", i);
        }
    }
}
