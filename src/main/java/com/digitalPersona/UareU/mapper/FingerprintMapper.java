package com.digitalPersona.UareU.mapper;

import com.digitalPersona.UareU.dto.ResponseDto;
import com.digitalpersona.uareu.Fmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FingerprintMapper {

    public ResponseDto toDto(Fmd fmd, byte[] image) {
        if (fmd == null) return null;
        return ResponseDto.builder()
                .format(fmd.getFormat())
                .data(fmd.getData())
                .image(image)
                .build();
    }

}
