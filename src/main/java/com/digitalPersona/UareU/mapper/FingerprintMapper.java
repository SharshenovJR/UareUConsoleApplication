package com.digitalPersona.UareU.mapper;

import com.digitalPersona.UareU.dto.FingerprintDto;
import com.digitalpersona.uareu.Fmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FingerprintMapper {

    public FingerprintDto toDto(Fmd fmd) {
        if (fmd == null) return null;
        return FingerprintDto.builder()
                .format(fmd.getFormat())
                .data(fmd.getData())
                .build();
    }

}
