package com.digitalPersona.UareU.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDto {
    private DeviceDescriptionDto description;
    private DeviceCapabilityDto capability;
}
