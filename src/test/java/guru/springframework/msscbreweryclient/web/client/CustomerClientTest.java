package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerClientTest {

    @Autowired
    CustomerClient client;

    @Test
    void getCustomerByUUID() {
        CustomerDto customerDto = client.getCustomerByUUID(UUID.randomUUID());

        assertNotNull(customerDto);
    }

    @Test
    void saveNewCustomer() {
        CustomerDto newCustomer = CustomerDto.builder().name("Pepito Gomez").build();

        URI uri = client.saveNewCustomer(newCustomer);

        assertNotNull(uri);

        System.out.println(uri.toString());
    }

    @Test
    void updateCustomer() {
        CustomerDto customer = CustomerDto.builder().name("Pepito Gomez").build();
        client.updateCustomer(UUID.randomUUID(), customer);
    }

    @Test
    void deleteCustomer() {
        client.deleteCustomer(UUID.randomUUID());
    }
}