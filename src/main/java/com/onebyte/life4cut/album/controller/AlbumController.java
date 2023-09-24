package com.onebyte.life4cut.album.controller;

import com.onebyte.life4cut.album.controller.dto.CreatePictureRequest;
import com.onebyte.life4cut.album.controller.dto.CreatePictureResponse;
import com.onebyte.life4cut.auth.dto.CustomUserDetails;
import com.onebyte.life4cut.common.web.ApiResponse;
import com.onebyte.life4cut.picture.service.PictureService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {

    private final PictureService pictureService;

    public AlbumController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @PostMapping("/{albumId}/pictures")
    public ApiResponse<CreatePictureResponse> uploadPicture(
            @Min(1) @PathVariable("albumId") Long albumId,
            @Valid @RequestPart("data") CreatePictureRequest request,
            @RequestPart("image") MultipartFile image,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long pictureId = pictureService.createInSlot(
                userDetails.getUserId(),
                albumId,
                request.slotId(),
                request.content(),
                request.picturedAt().atTime(0, 0),
                request.tags(),
                image
        );

        return ApiResponse.OK(new CreatePictureResponse(pictureId));
    }
}
