package com.digitalPersona.UareU.dto;

import com.digitalpersona.uareu.Fmd;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto {
    private Fmd.Format format;
    private byte[] data;
    private byte[] image;
 }
