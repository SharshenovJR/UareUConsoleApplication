package com.digitalPersona.UareU.mapper;

import com.digitalPersona.UareU.dto.DeviceVersionDto;
import com.digitalpersona.uareu.Reader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceVersionMapper {

    public DeviceVersionDto toDto(Reader.VersionInfo versionInfo) {
        if (versionInfo == null) return null;
        return DeviceVersionDto.builder()
                .major(versionInfo.major)
                .minor(versionInfo.minor)
                .maintenance(versionInfo.maintenance)
                .build();
    }

}
