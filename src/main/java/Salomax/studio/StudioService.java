package Salomax.studio;

import Salomax.address.Address;
import Salomax.address.AddressService;
import Salomax.employee.*;
import Salomax.validation.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudioService {

    private StudioDao studioDao;
    private EmployeeDao employeeDao;
    private AddressService addressService;
    private ValidationService validationService;
    private EmployeeService employeeService;
    private StudioMapper studioMapper;
    private EmployeeMapper employeeMapper;

    public CreateStudioResponse createStudioAndAdmin(CreateStudioRequest createStudioRequest) {
        try {
            validateRequest(createStudioRequest);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }

        Studio studio = createStudio(createStudioRequest);
        Address address = addressService.createAddress(createStudioRequest.getStudioDto().getAddressDto());
        studio.setAddress(address);
        Employee owner = employeeService.createOwner(createStudioRequest.getEmployeeDto());
        owner.setAssignedStudio(studio);

        studio = studioDao.save(studio);

        StudioDto studioDto = studioMapper.toDto(studio);
        EmployeeDto ownerDto = employeeMapper.toDto(owner);

        return CreateStudioResponse.builder()
                .studioDto(studioDto)
                .employeeDto(ownerDto)
                .build();
    }

    public Studio updateStudio(StudioDto studioDto) {
        String messageException = validateStudio(studioDto);
        messageException += addressService.validateAddress(studioDto.getAddressDto());
        if (messageException.length() > 1) {
            throw new IllegalArgumentException(messageException);
        }

        Studio studio = studioDao.findById(studioDto.getId())
                .orElseThrow();
        reassignValues(studio, studioDto);
        addressService.updateAddress(studioDto.getAddressDto());

        return studioDao.save(studio);
    }

    public void deleteStudio(Long id) {
        List<Employee> employees = employeeDao.findByAssignedStudioId(id);
        employeeDao.deleteAll(employees);
        studioDao.deleteById(id);
    }

    private void validateRequest(CreateStudioRequest createStudioRequest) {
        String messageException = "";

        messageException = validateStudio(createStudioRequest.getStudioDto());
        messageException += addressService.validateAddress(createStudioRequest.getStudioDto().getAddressDto());
        messageException += employeeService.validateEmployee(createStudioRequest.getEmployeeDto());

        if (!messageException.equals("")) {
            throw new IllegalArgumentException(messageException);
        }
    }

    private String validateStudio(StudioDto studioDto) {
        String messageException = "";
        if (!validationService.validateString(studioDto.getName())) {
            messageException += "Bad value of studio's name. ";
        }
        if (!validateNIP(studioDto.getNip())) {
            messageException += "Bad value of NIP number. ";
        }
        if (!validateRegon(studioDto.getRegon())) {
            messageException += "Bad value of REGON number. ";
        }
        if (!validationService.validatePhoneNumber(studioDto.getPhoneNumber())) {
            messageException += "Bad value of studio's phone number. ";
        }
        if (!validationService.validateEmail(studioDto.getEmail())) {
            messageException += "Bad value of studio's email. ";
        }

        return messageException;
    }

    private Studio createStudio(CreateStudioRequest createStudioRequest) {
        StudioDto studioDto = createStudioRequest.getStudioDto();
        return Studio.builder()
                .name(studioDto.getName())
                .nip(studioDto.getNip())
                .regon(studioDto.getRegon())
                .phoneNumber(studioDto.getPhoneNumber())
                .email(studioDto.getEmail())
                .build();
    }

    private boolean validateNIP(String nip) {
        if (nip == null) {
            return false;
        }
        int nsize = nip.length();
        if (nsize != 10) {
            return false;
        }
        int[] weights = { 6, 5, 7, 2, 3, 4, 5, 6, 7 };
        int j, sum = 0, control;
        int csum = Integer.parseInt(nip.substring(nsize - 1));
        for (int i = 0; i < nsize - 1; i++) {
            char c = nip.charAt(i);
            j = Integer.parseInt(String.valueOf(c));
            sum += j * weights[i];
        }
        control = sum % 11;
        return (control == csum);
    }

    private boolean validateRegon(String regon) {
        if (regon == null) {
            return false;
        }
        int rsize = regon.length();
        if (!((rsize == 9) || (rsize == 14))) {
            return false;
        }

        int[] weights = { 8, 9, 2, 3, 4, 5, 6, 7 };
        int[] weights14 = { 2, 4, 8, 5, 0, 9, 7, 3, 6, 1, 2, 4, 8 };

        if (rsize == 14) {
            weights = weights14;
        }

        int j, sum = 0, control;
        int csum = Integer.parseInt(regon.substring(rsize - 1));
        for (int i = 0; i < rsize - 1; i++) {
            char c = regon.charAt(i);
            j = Integer.parseInt(String.valueOf(c));
            sum += j * weights[i];
        }

        control = sum % 11;
        if (control == 10) {
            control = 0;
        }
        return (control == csum);
    }

    private void reassignValues(Studio studio, StudioDto studioDto) {
        if (!studio.getName().equals(studioDto.getName())) {
            studio.setName(studioDto.getName());
        }
        if (!studio.getNip().equals(studioDto.getNip())) {
            studio.setNip(studioDto.getNip());
        }
        if (!studio.getRegon().equals(studioDto.getRegon())) {
            studio.setRegon(studioDto.getRegon());
        }
        if (!studio.getPhoneNumber().equals(studioDto.getPhoneNumber())) {
            studio.setPhoneNumber(studioDto.getPhoneNumber());
        }
        if (!studio.getEmail().equals(studioDto.getEmail())) {
            studio.setEmail(studioDto.getEmail());
        }
    }

}