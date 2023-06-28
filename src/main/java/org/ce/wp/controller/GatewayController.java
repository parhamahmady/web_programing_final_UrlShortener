package org.ce.wp.controller;

import lombok.RequiredArgsConstructor;
import org.ce.wp.exception.InvalidUrlIdException;
import org.ce.wp.service.EndpointService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Objects;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/gw")
public class GatewayController {
    private final EndpointService endpointService;

    @GetMapping("/{urlId}")
    public RedirectView callUrl(@PathVariable String urlId) throws InvalidUrlIdException {
        String url = endpointService.callUrl(urlId);
        if (Objects.isNull(url)) {
            throw new InvalidUrlIdException("Not Found Url");
        }
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url);
        return redirectView;
    }
}
