package com.digitalPersona.UareU.mapper;

import com.digitalPersona.UareU.dto.DeviceDto;
import com.digitalpersona.uareu.Reader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceMapper {
    private final DeviceDescriptionMapper deviceDescriptionMapper;
    private final DeviceCapabilityMapper deviceCapabilityMapper;

    public DeviceDto toDto(Reader reader, long seq) {
        if (reader == null) return null;
        return DeviceDto.builder()
                .deviceSeq(seq)
                .description(deviceDescriptionMapper.toDto(reader.GetDescription()))
                .capability(deviceCapabilityMapper.toDto(reader.GetCapabilities()))
                .build();
    }
}
