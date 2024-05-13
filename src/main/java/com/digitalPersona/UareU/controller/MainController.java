package com.digitalPersona.UareU.controller;

import com.digitalPersona.UareU.dto.DeviceDto;
import com.digitalPersona.UareU.dto.RequestDto;
import com.digitalPersona.UareU.dto.ResponseDto;
import com.digitalPersona.UareU.service.DeviceService;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.UareUException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/capture/image")
    public ResponseEntity<byte[]> captureImage(@RequestBody RequestDto dto) throws UareUException, IOException, InterruptedException {
        BufferedImage image = deviceService.getCapturedImage(dto);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        }
    }

    @PostMapping("/capture")
    public ResponseEntity<ResponseDto> capture(@RequestBody RequestDto dto) throws UareUException, InterruptedException, IOException {
        return ResponseEntity.ok(deviceService.capture(dto));
    }

    @PostMapping("/enroll")
    public ResponseEntity<byte[]> enroll(@RequestBody RequestDto dto) throws UareUException, InterruptedException {
        Fmd enroll = deviceService.enroll(dto);
        return ResponseEntity.ok(enroll.getData());
    }

    @PostMapping("/compare")
    public ResponseEntity<Boolean> compare(@RequestBody RequestDto dto) throws UareUException, InterruptedException {
        return ResponseEntity.ok(deviceService.compare(dto));
    }
}
