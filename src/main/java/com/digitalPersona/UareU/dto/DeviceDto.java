package com.digitalPersona.UareU.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDto {
    private long deviceSeq;
    private DeviceDescriptionDto description;
    private DeviceCapabilityDto capability;
}
