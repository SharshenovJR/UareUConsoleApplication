package com.digitalPersona.UareU.service.impl;

import com.digitalPersona.UareU.service.CaptureService;
import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.UareUException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaptureServiceImpl implements CaptureService {

    @Override
    public Reader.CaptureResult capture(Reader reader, boolean stream, Fid.Format format) throws UareUException, InterruptedException {
        try {
            reader.Open(Reader.Priority.EXCLUSIVE);
        } catch (UareUException e) {
            if (e.getCode() == 96075807) reader.Close();
            reader.Open(Reader.Priority.EXCLUSIVE);
        }
        boolean fingerDetected = false;
        while (!fingerDetected) {
            Reader.Status rs = reader.GetStatus();
            if (rs.status == Reader.ReaderStatus.READY) {
                fingerDetected = true;
            } else {
                Thread.sleep(500);
            }
        }
        return reader.Capture(format, Reader.ImageProcessing.IMG_PROC_DEFAULT, 500, 10000);
    }
}
