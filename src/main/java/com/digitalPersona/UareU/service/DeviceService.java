package com.digitalPersona.UareU.service;

import com.digitalPersona.UareU.dto.DeviceDto;
import com.digitalPersona.UareU.dto.FingerprintDto;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.UareUException;

import java.awt.image.BufferedImage;
import java.util.List;

public interface DeviceService {

    List<DeviceDto> getDevices() throws UareUException;
    BufferedImage getCapturedImage() throws UareUException, InterruptedException;
    FingerprintDto capture() throws UareUException, InterruptedException;
    Fmd enroll() throws UareUException, InterruptedException;
    boolean compare() throws UareUException, InterruptedException;
}
