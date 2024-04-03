package com.digitalPersona.UareU.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceVersionDto {
    public int major;
    public int minor;
    public int maintenance;
}
