package Salomax.address;

import Salomax.validation.ValidationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AddressServiceTest {

    private String VALID_COUNTRY;
    private String VALID_VOIVODESHIP;
    private String VALID_CITY;
    private String VALID_POSTAL_CODE;
    private String VALID_STREET;
    private String VALID_HOUSE_NUMBER;
    private Address VALID_ADDRESS;
    private AddressService addressService;
    @Mock
    private AddressDao addressDao;


    @Before
    public void setUp() {
        addressDao = mock(AddressDao.class);
        ValidationService validationService = new ValidationService();
        addressService = new AddressService(validationService, addressDao);

        VALID_COUNTRY = "Jhds";
        VALID_VOIVODESHIP = "asak";
        VALID_CITY = "Kpsds";
        VALID_POSTAL_CODE = "00-000";
        VALID_STREET = "Dsdsds";
        VALID_HOUSE_NUMBER = "00b";

        VALID_ADDRESS = Address.builder()
                .country(VALID_COUNTRY)
                .voivodeship(VALID_VOIVODESHIP)
                .city(VALID_CITY)
                .postalCode(VALID_POSTAL_CODE)
                .street(VALID_STREET)
                .houseNumber(VALID_HOUSE_NUMBER)
                .build();

        when(addressDao.findById(1L)).thenReturn(Optional.of(Address.builder()
                        .country(VALID_COUNTRY)
                        .voivodeship(VALID_VOIVODESHIP)
                        .city(VALID_CITY)
                        .postalCode(VALID_POSTAL_CODE)
                        .street(VALID_STREET)
                        .houseNumber(VALID_HOUSE_NUMBER)
                        .build()));

        when(addressDao.save(Mockito.any(Address.class))).then(returnsFirstArg());
    }

    @Test
    public void createAddressValidDataShouldPass() {
        //given
        AddressDto addressDto = AddressDto.builder()
                .country(VALID_COUNTRY)
                .voivodeship(VALID_VOIVODESHIP)
                .city(VALID_CITY)
                .postalCode(VALID_POSTAL_CODE)
                .street(VALID_STREET)
                .houseNumber(VALID_HOUSE_NUMBER)
                .build();

        //when
        Address createdAddress = addressService.createAddress(addressDto);

        //then
        assertEquals(VALID_ADDRESS, createdAddress);
    }

    @Test
    public void updateAddressCheckIfCountryWasUpdated() {
        //given
        AddressDto addressDto = AddressDto.builder()
                .id(1L)
                .country("new country")
                .voivodeship(VALID_VOIVODESHIP)
                .city(VALID_CITY)
                .postalCode(VALID_POSTAL_CODE)
                .street(VALID_STREET)
                .houseNumber(VALID_HOUSE_NUMBER)
                .build();

        //when
        Address address = addressService.updateAddress(addressDto);

        //then
        assertEquals("new country", address.getCountry());
    }

    @Test
    public void updateAddressCheckIfVoivodeshipWasUpdated() {
        //given
        AddressDto addressDto = AddressDto.builder()
                .id(1L)
                .country(VALID_COUNTRY)
                .voivodeship("new voivodeship")
                .city(VALID_CITY)
                .postalCode(VALID_POSTAL_CODE)
                .street(VALID_STREET)
                .houseNumber(VALID_HOUSE_NUMBER)
                .build();

        //when
        Address address = addressService.updateAddress(addressDto);

        //then
        assertEquals("new voivodeship", address.getVoivodeship());
    }

    @Test
    public void updateAddressCheckIfCityWasUpdated() {
        //given
        AddressDto addressDto = AddressDto.builder()
                .id(1L)
                .country(VALID_COUNTRY)
                .voivodeship(VALID_VOIVODESHIP)
                .city("new city")
                .postalCode(VALID_POSTAL_CODE)
                .street(VALID_STREET)
                .houseNumber(VALID_HOUSE_NUMBER)
                .build();

        //when
        Address address = addressService.updateAddress(addressDto);

        //then
        assertEquals("new city", address.getCity());
    }

    @Test
    public void updateAddressCheckIfPostalCodeWasUpdated() {
        //given
        AddressDto addressDto = AddressDto.builder()
                .id(1L)
                .country(VALID_COUNTRY)
                .voivodeship(VALID_VOIVODESHIP)
                .city(VALID_CITY)
                .postalCode("new postal code")
                .street(VALID_STREET)
                .houseNumber(VALID_HOUSE_NUMBER)
                .build();

        //when
        Address address = addressService.updateAddress(addressDto);

        //then
        assertEquals("new postal code", address.getPostalCode());
    }

    @Test
    public void updateAddressCheckIfStreetWasUpdated() {
        //given
        AddressDto addressDto = AddressDto.builder()
                .id(1L)
                .country(VALID_COUNTRY)
                .voivodeship(VALID_VOIVODESHIP)
                .city(VALID_CITY)
                .postalCode(VALID_POSTAL_CODE)
                .street("new street")
                .houseNumber(VALID_HOUSE_NUMBER)
                .build();

        //when
        Address address = addressService.updateAddress(addressDto);

        //then
        assertEquals("new street", address.getStreet());
    }

    @Test
    public void updateAddressCheckIfHouseNumberWasUpdated() {
        //given
        AddressDto addressDto = AddressDto.builder()
                .id(1L)
                .country(VALID_COUNTRY)
                .voivodeship(VALID_VOIVODESHIP)
                .city(VALID_CITY)
                .postalCode(VALID_POSTAL_CODE)
                .street(VALID_STREET)
                .houseNumber("new house number")
                .build();

        //when
        Address address = addressService.updateAddress(addressDto);

        //then
        assertEquals("new house number", address.getHouseNumber());
    }

}