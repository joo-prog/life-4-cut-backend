package com.onebyte.life4cut.sample.controller;

import com.onebyte.life4cut.common.web.ApiResponse;
import com.onebyte.life4cut.sample.controller.dto.SampleCreateRequest;
import com.onebyte.life4cut.sample.controller.dto.SampleCreateResponse;
import com.onebyte.life4cut.sample.service.SampleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/samples")
public class SampleController {

    private final SampleService sampleService;

    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<SampleCreateResponse> create(@Valid @RequestBody SampleCreateRequest request) {
        return ApiResponse.OK(
                new SampleCreateResponse(sampleService.save(request.getEmail(), request.nickname()))
        );

    }
}
