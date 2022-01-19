package com.pk.project_io.security.admin.post;

import com.pk.project_io.security.admin.AbstractRestController;
import com.pk.project_io.security.admin.dto.AdminActionResponseDto;
import com.pk.project_io.security.admin.dto.AdminPutDto;
import com.pk.project_io.security.admin.post.dto.AdminPostGetDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/posts")
public class AdminPostController extends AbstractRestController<AdminPostService, AdminPostGetDto, AdminPutDto, AdminActionResponseDto> {
    public AdminPostController(AdminPostService service) {
        super(service);
    }
}
