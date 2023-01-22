package org.ce.wp.service;

import org.ce.wp.dto.CreateUrlRequestDto;
import org.ce.wp.dto.CreateUrlResponseDto;
import org.ce.wp.dto.FindUrlResponseDto;
import org.ce.wp.dto.UrlReportResponseDto;
import org.ce.wp.exception.CredentialsException;
import org.ce.wp.exception.InvalidUrlIdException;

/**
 * @author Parham Ahmadi
 * @since 22.01.23
 */
public interface EndpointService {

    CreateUrlResponseDto creatEndpoint(CreateUrlRequestDto requestDto, String username);

    FindUrlResponseDto findEndPoint(String username);

    UrlReportResponseDto reportUrl(String urlId, String username) throws InvalidUrlIdException, CredentialsException;
}
