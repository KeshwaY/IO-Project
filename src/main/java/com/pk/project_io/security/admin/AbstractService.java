package com.pk.project_io.security.admin;

import java.util.List;

public interface AbstractService<GetDtoT, PutDtoT, ResponseDtoT> {

    List<GetDtoT> getAllByData(String userEmail) throws Exception;
    List<GetDtoT> getAll();
    ResponseDtoT editUserComponent(String userEmail, Long id, PutDtoT newComponent) throws Exception;
    ResponseDtoT deleteUserComponent(String email, Long id) throws Exception;

}
