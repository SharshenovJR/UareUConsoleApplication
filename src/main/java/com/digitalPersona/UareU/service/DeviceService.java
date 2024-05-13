package com.digitalPersona.UareU.service;

import com.digitalPersona.UareU.dto.DeviceDto;
import com.digitalPersona.UareU.dto.RequestDto;
import com.digitalPersona.UareU.dto.ResponseDto;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.UareUException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public interface DeviceService {

    List<DeviceDto> getDevices() throws UareUException;
    BufferedImage getCapturedImage(RequestDto dto) throws UareUException, InterruptedException;
    ResponseDto capture(RequestDto dto) throws UareUException, InterruptedException, IOException;
    Fmd enroll(RequestDto dto) throws UareUException, InterruptedException;
    boolean compare(RequestDto dto) throws UareUException, InterruptedException;
}
