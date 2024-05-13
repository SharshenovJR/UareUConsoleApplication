package com.digitalPersona.UareU.service.impl;

import com.digitalPersona.UareU.dto.DeviceDto;
import com.digitalPersona.UareU.dto.ResponseDto;
import com.digitalPersona.UareU.mapper.DeviceMapper;
import com.digitalPersona.UareU.mapper.FingerprintMapper;
import com.digitalPersona.UareU.service.CaptureService;
import com.digitalPersona.UareU.service.DeviceService;
import com.digitalpersona.uareu.*;
import com.digitalpersona.uareu.jni.Dpfj;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceMapper deviceMapper;
    private final CaptureService captureService;
    private final FingerprintMapper fingerprintMapper;
    private final Dpfj dpfj = new Dpfj();
    private final Fmd.Format FORMAT = Fmd.Format.ISO_19794_2_2005;

    @Getter
    private Fmd fmd;

    @Override
    public List<DeviceDto> getDevices() throws UareUException {
        ReaderCollection collection = UareUGlobal.GetReaderCollection();
        collection.GetReaders();
        return collection.stream()
                .map(deviceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BufferedImage getCapturedImage() throws UareUException, InterruptedException {
        Reader.CaptureResult result = makeCapture();
        if (result == null || result.image == null) throw new RuntimeException("Failed to capture");
        Fid.Fiv view = result.image.getViews()[0];
        BufferedImage bufferedImage = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        bufferedImage.getRaster().setDataElements(0, 0, view.getWidth(), view.getHeight(), view.getImageData());
        return bufferedImage;
    }

    @Override
    public ResponseDto capture() throws UareUException, InterruptedException, IOException {
        Reader.CaptureResult captureResult = makeCapture();
        if (captureResult == null || captureResult.image == null) throw new UareUException(96075807);
        Engine engine = UareUGlobal.GetEngine();
        Fmd fmd = engine.CreateFmd(captureResult.image, FORMAT);
        Fid.Fiv view = captureResult.image.getViews()[0];
        BufferedImage bufferedImage = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        bufferedImage.getRaster().setDataElements(0, 0, view.getWidth(), view.getHeight(), view.getImageData());
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", outputStream);
            byte[] byteArray = outputStream.toByteArray();
            return fingerprintMapper.toDto(fmd, byteArray);
        }
    }

    @Override
    public Fmd enroll() throws UareUException, InterruptedException {
        Reader.CaptureResult result = makeCapture();
        if (result == null || result.image == null) throw new RuntimeException("Failed to capture");
        Engine engine = UareUGlobal.GetEngine();
        Fmd fmd = engine.CreateFmd(result.image, FORMAT);
        this.fmd = fmd;
        return fmd;
    }

    @Override
    public boolean compare() throws UareUException, InterruptedException {
        Reader.CaptureResult result = makeCapture();
        if (result == null || result.image == null) throw new RuntimeException("Failed to capture");
        Engine engine = UareUGlobal.GetEngine();
        Fmd fmd = engine.CreateFmd(result.image, FORMAT);
        Fmd.Format format = fmd.getFormat();
        byte[] data = fmd.getData();
        Fmd imported = dpfj.import_fmd(data, format, format);
        int compared = engine.Compare(this.fmd, 0, imported, 0);
        int FALSE_POSITIVE_RATE = Engine.PROBABILITY_ONE / 100000;
        return compared < FALSE_POSITIVE_RATE;
    }

    public Reader.CaptureResult makeCapture() throws UareUException, InterruptedException {
        ReaderCollection collection = UareUGlobal.GetReaderCollection();
        collection.GetReaders();
        if (collection.isEmpty()) throw new RuntimeException("No fingerprint reader available!");
        Reader reader = collection.get(0);
        return captureService.capture(reader, false);
    }
}
