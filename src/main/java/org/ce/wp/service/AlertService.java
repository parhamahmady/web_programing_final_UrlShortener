package org.ce.wp.service;

import org.ce.wp.dto.AlertReportResponseDto;
import org.ce.wp.exception.CredentialsException;
import org.ce.wp.exception.InvalidUrlIdException;

/**
 * @author Parham Ahmadi
 * @since 22.01.23
 */
public interface AlertService {

    AlertReportResponseDto getAlertReport(String urlId, String username) throws InvalidUrlIdException, CredentialsException;
}
