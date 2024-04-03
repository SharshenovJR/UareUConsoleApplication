package com.digitalPersona.UareU.mapper;

import com.digitalPersona.UareU.dto.DeviceDescriptionDto;
import com.digitalpersona.uareu.Reader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceDescriptionMapper {
    private final DeviceVersionMapper readerVersionMapper;

    public DeviceDescriptionDto toDto(Reader.Description description) {
        if (description == null) return null;
        return DeviceDescriptionDto.builder()
                .name(description.name)
                .serialNumber(description.serial_number)
                .productId(description.id.product_id)
                .vendorId(description.id.vendor_id)
                .productName(description.id.product_name)
                .vendorName(description.id.vendor_name)
                .modality(description.modality.toString())
                .technology(description.technology.toString())
                .firmwareVersion(readerVersionMapper.toDto(description.version.firmware_version))
                .hardwareVersion(readerVersionMapper.toDto(description.version.hardware_version))
                .bcdRevision(description.version.bcd_revision)
                .build();
    }
}
