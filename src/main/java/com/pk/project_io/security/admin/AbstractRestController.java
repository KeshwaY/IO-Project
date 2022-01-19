package com.pk.project_io.security.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public abstract class AbstractRestController<
        ServiceT extends AbstractService<GetDtoT, PutDtoT, ResponseDtoT>,
        GetDtoT,
        PutDtoT,
        ResponseDtoT> {

    protected final ServiceT service;

    public AbstractRestController(ServiceT service) {
        this.service = service;
    }

    @GetMapping("/{component_data}")
    public ResponseEntity<List<GetDtoT>> getAllByUser(
            @PathVariable("component_data") String componentDat
    ) throws Exception {
        List<GetDtoT> adminCommentGetDto = service.getAllByData(componentDat);
        return new ResponseEntity<>(adminCommentGetDto, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<GetDtoT>> getAll() {
        List<GetDtoT> adminCommentGetDto = service.getAll();
        return new ResponseEntity<>(adminCommentGetDto, HttpStatus.OK);
    }

    @PutMapping("/{component_data}")
    public ResponseEntity<ResponseDtoT> editUserComment(
            @PathVariable("component_data") String componentDat,
            @RequestParam("component_id") Long componentId,
            @RequestBody @Valid PutDtoT adminPutDto
    ) throws Exception {
        ResponseDtoT responseDto = service.editUserComponent(componentDat, componentId, adminPutDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{component_data}")
    public ResponseEntity<ResponseDtoT> deleteUserComment(
            @PathVariable("component_data") String componentDat,
            @RequestParam("component_id") Long componentId
    ) throws Exception {
        ResponseDtoT responseDto = service.deleteUserComponent(componentDat, componentId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
