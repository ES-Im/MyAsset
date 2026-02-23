package dev.es.myasset.adapter.security.auth;

import dev.es.myasset.adapter.security.auth.support.SecurityTestSupport;
import dev.es.myasset.adapter.security.auth.support.WithMockActiveUser;
import dev.es.myasset.adapter.security.auth.support.WithMockAdmin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoleAuthTest extends SecurityTestSupport {

    @Test
    @DisplayName("ROLE_USER는 /api/user/** 접근 가능")
    @WithMockActiveUser
    void user_can_access_user_api() throws Exception {
        mockMvc.perform(get("/api/user/"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("permitAll 경로는 인증 없이 접근 가능")
    void permit_all_can_access() throws Exception {
        mockMvc.perform(get("/api/demo/"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("ROLE_USER는 /api/admin/** 접근 불가(403)")
    @WithMockActiveUser
    void user_cannot_access_admin_api() throws Exception {
        mockMvc.perform(get("/api/admin/"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("ROLE_ADMIN은 /api/admin/** 접근 가능")
    @WithMockAdmin
    void admin_can_access_admin_api() throws Exception {
        mockMvc.perform(get("/api/admin/"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("ROLE_ADMIN은 /api/user/** 접근 가능")
    @WithMockAdmin
    void admin_can_access_user_api() throws Exception {
        mockMvc.perform(get("/api/user/"))
                .andExpect(status().isOk());
    }


}