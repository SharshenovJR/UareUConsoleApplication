package com.digitalPersona.UareU.dto;

import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fmd;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDto {
    private Fmd.Format formatFmd;
    private Fid.Format formatFid;
}
