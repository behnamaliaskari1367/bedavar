package com.behdavar.backservices.auth.controller;

import com.behdavar.backservices.common.model.backservice.TableRequestModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public abstract class BaseController {
    private static final Logger LOGGER = LogManager.getLogger(BaseController.class);

    final static String BASE_URL = "/api/v1/";

    PageRequest getPageRequest(TableRequestModel request) {
        EntryMessage traceEntry = LOGGER.traceEntry("tableRequestModel: {}", request);

        PageRequest result = PageRequest.of(request.getCurrentPage(),
                request.getPageSize(),
                Objects.equals(request.getSortDirection(), "ASC") ? Sort.Direction.ASC : Sort.Direction.DESC,
                request.getSortIndex());

        return LOGGER.traceExit(traceEntry, result);
    }
}
