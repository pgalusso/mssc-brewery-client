package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient client;

    @Test
    void getBeerByUUID() {
        BeerDto dto = client.getBeerByUUID(UUID.randomUUID());

        assertNotNull(dto);
    }

    @Test
    void saveNewBeer(){
        BeerDto beerDto = BeerDto.builder().beerName("New Beer").build();
        URI uri = client.saveNewBeer(beerDto);

        assertNotNull(uri);

        System.out.println(uri.toString());
    }

    @Test
    void updateBeer(){
        BeerDto beerDto = BeerDto.builder().id(UUID.randomUUID()).beerName("New Beer").build();

        client.updateBeer(beerDto.getId(), beerDto);
    }

    @Test
    void deleteBeer() {
        client.deleteBeer(UUID.randomUUID());
    }

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