package com.digitalPersona.UareU.controller;

import com.digitalPersona.UareU.dto.DeviceDto;
import com.digitalPersona.UareU.dto.FingerprintDto;
import com.digitalPersona.UareU.service.DeviceService;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.UareUException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v2/scanner")
@RequiredArgsConstructor
public class MainController {

    private final DeviceService deviceService;

    @GetMapping("/devices")
    public ResponseEntity<List<DeviceDto>> getDevices() throws UareUException {
        return ResponseEntity.ok(deviceService.getDevices());
    }

    @GetMapping("/capture/image")
    public ResponseEntity<byte[]> captureImage() throws UareUException, IOException, InterruptedException {
        BufferedImage image = deviceService.getCapturedImage();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/capture")
    public ResponseEntity<FingerprintDto> capture() throws UareUException, InterruptedException {
        return ResponseEntity.ok(deviceService.capture());
    }

    @GetMapping("/enroll")
    public ResponseEntity<byte[]> enroll() throws UareUException, InterruptedException {
        Fmd enroll = deviceService.enroll();
        return ResponseEntity.ok(enroll.getData());
    }

    @GetMapping("/compare")
    public ResponseEntity<Boolean> compare() throws UareUException, InterruptedException {
        return ResponseEntity.ok(deviceService.compare());
    }
}
