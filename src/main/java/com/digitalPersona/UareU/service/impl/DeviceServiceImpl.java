package com.digitalPersona.UareU.service.impl;

import com.digitalPersona.UareU.dto.DeviceDto;
import com.digitalPersona.UareU.dto.RequestDto;
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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceMapper deviceMapper;
    private final CaptureService captureService;
    private final FingerprintMapper fingerprintMapper;
    private final Dpfj dpfj = new Dpfj();

    @Getter
    private Fmd fmd;

    @Override
    public List<DeviceDto> getDevices() throws UareUException {
        AtomicLong idCounter = new AtomicLong();
        ReaderCollection collection = UareUGlobal.GetReaderCollection();
        collection.GetReaders();
        return collection.stream()
                .map(reader -> deviceMapper.toDto(reader, idCounter.getAndIncrement()))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDto capture(RequestDto dto) throws UareUException, InterruptedException, IOException {
        checkParams(dto);
        Reader.CaptureResult captureResult = takeFingerprint(dto);
        if (captureResult == null || captureResult.image == null) throw new UareUException(96075807);
        Engine engine = UareUGlobal.GetEngine();
        Fmd fmd = engine.CreateFmd(captureResult.image, dto.getFormatFmd());
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
    public BufferedImage getCapturedImage(RequestDto dto) throws UareUException, InterruptedException {
        checkParams(dto);
        Reader.CaptureResult result = makeCapture(dto);
        if (result == null || result.image == null) throw new RuntimeException("Failed to capture");
        Fid.Fiv view = result.image.getViews()[0];
        BufferedImage bufferedImage = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        bufferedImage.getRaster().setDataElements(0, 0, view.getWidth(), view.getHeight(), view.getImageData());
        return bufferedImage;
    }

    @Override
    public Fmd enroll(RequestDto dto) throws UareUException, InterruptedException {
        checkParams(dto);
        Reader.CaptureResult result = makeCapture(dto);
        if (result == null || result.image == null) throw new RuntimeException("Failed to enroll");
        Engine engine = UareUGlobal.GetEngine();
        Fmd fmd = engine.CreateFmd(result.image, dto.getFormatFmd());
        this.fmd = fmd;
        return fmd;
    }

    @Override
    public boolean compare(RequestDto dto) throws UareUException, InterruptedException {
        checkParams(dto);
        Reader.CaptureResult result = makeCapture(dto);
        if (result == null || result.image == null) throw new RuntimeException("Failed to compare");
        Engine engine = UareUGlobal.GetEngine();
        Fmd fmd = engine.CreateFmd(result.image, dto.getFormatFmd());
        Fmd.Format format = fmd.getFormat();
        byte[] data = fmd.getData();
        Fmd imported = dpfj.import_fmd(data, format, format);
        int compared = engine.Compare(this.fmd, 0, imported, 0);
        int FALSE_POSITIVE_RATE = Engine.PROBABILITY_ONE / 100000;
        return compared < FALSE_POSITIVE_RATE;
    }

    public Reader.CaptureResult makeCapture(RequestDto dto) throws UareUException, InterruptedException {
        Reader reader = getFirstReader();
        return captureService.capture(reader, false, dto.getFormatFid());
    }

    public Reader.CaptureResult takeFingerprint(RequestDto dto) throws UareUException, InterruptedException {
        Reader reader = getReader(dto.getDeviceSeq());
        return captureService.capture(reader, false, dto.getFormatFid());
    }

    public Reader getReader(int number) throws UareUException {
        ReaderCollection collection = UareUGlobal.GetReaderCollection();
        collection.GetReaders();
        if (collection.isEmpty() || collection.get(number) == null) throw new RuntimeException("No fingerprint reader available!");
        return collection.get(number);
    }

    public Reader getFirstReader() throws UareUException {
        ReaderCollection collection = UareUGlobal.GetReaderCollection();
        collection.GetReaders();
        if (collection.isEmpty()) throw new RuntimeException("No fingerprint reader available!");
        return collection.get(0);
    }

    public void checkParams(RequestDto dto) throws UareUException {
        if (dto.getFormatFmd() == null || dto.getFormatFid() == null || Objects.deepEquals(dto.getDeviceSeq(), null)) throw new UareUException(96075796);
    }
}
