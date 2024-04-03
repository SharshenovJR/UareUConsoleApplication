package com.digitalPersona.UareU.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDescriptionDto {
    private String name;
    private String serialNumber;
    private int productId;
    private int vendorId;
    private String productName;
    private String vendorName;
    private String modality;
    private String technology;
    private DeviceVersionDto firmwareVersion;
    private DeviceVersionDto hardwareVersion;
    private int bcdRevision;
}
