package com.digitalPersona.UareU.service;

import com.digitalPersona.UareU.dto.DeviceDto;
import com.digitalPersona.UareU.dto.ResponseDto;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.UareUException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public interface DeviceService {

    List<DeviceDto> getDevices() throws UareUException;
    BufferedImage getCapturedImage() throws UareUException, InterruptedException;
    ResponseDto capture() throws UareUException, InterruptedException, IOException;
    Fmd enroll() throws UareUException, InterruptedException;
    boolean compare() throws UareUException, InterruptedException;
}
