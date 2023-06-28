package org.ce.wp.service.impl;

import org.ce.wp.service.QRCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Service
public class QRCodeServiceImpl implements QRCodeService {

    private static final String URL = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=";

    @Override
    public String generateQrCode(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(URL + url, byte[].class);
        return Base64.getEncoder().encodeToString(responseEntity.getBody());
    }
}
