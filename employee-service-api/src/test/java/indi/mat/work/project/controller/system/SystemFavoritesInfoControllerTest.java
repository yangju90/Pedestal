package indi.mat.work.project.controller.system;

import indi.mat.work.project.controller.BaseTestController;
import indi.mat.work.project.model.system.SystemFavoritesInfo;
import indi.mat.work.project.service.system.ISystemFavoritesInfoService;
import indi.mat.work.project.util.JsonUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SystemFavoritesInfoControllerTest extends BaseControllerTest {

    private static final String PREFIX = "/api/system/favorites/info/list";

    @Autowired
    private ISystemFavoritesInfoService service;


    private Integer companyid = -1;

    @BeforeAll
    void setup() {
        int number = 3;
        for (int i = 1; i <= number; i++) {
            Integer id = addCompanyInfoTest(i);
            companyid = id;
        }
    }

    private Integer addCompanyInfoTest(int i) {
        return i;
    }

    @Test
    @Order(1)
    void testInsertForm() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountId", "1");
        map.put("favoriteBusinessType", "company");
        map.put("businessTypeFavoriteId", companyid);

        mvc.perform(put(PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("success")));
    }

    @Test
    @Order(2)
    void testCheckForm() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("favoriteBusinessType", "COMPANY");
        map.put("businessTypeFavoriteId", -1001);

        mvc.perform(put(PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJsonString(map)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("businessTypeFavoriteId must be greater than or equal to 1")));
    }


    @Test
    @Order(3)
    void testUpdateForm() throws Exception {

        List<SystemFavoritesInfo> list =service.selectList(null);
        if(list.size() == 0) return;
        Long id = list.get(0).getId();

        SystemFavoritesInfo old = service.selectById(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("accountId", "1");
        map.put("favoriteBusinessType", "COMPANY");
        map.put("businessTypeFavoriteId", companyid - 1);

        mvc.perform(put(PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("success")));

        SystemFavoritesInfo nld = service.selectById(id);
        assertNotEquals(old.getBusinessTypeFavoriteId(), nld.getBusinessTypeFavoriteId());
    }


    @Test
    @Order(4)
    void testRepeatPage() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountId", "1");
        map.put("favoriteBusinessType", "company");
        map.put("businessTypeFavoriteId", companyid);

        MvcResult mvcResult = mvc.perform(put(PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("success")))
                .andReturn();
        Map<String, String> s = JsonUtil.jsonToMap(mvcResult.getResponse().getContentAsString(), String.class, String.class);

        mvc.perform(put(PREFIX)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJsonString(map)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message", is("success")))
            .andExpect(jsonPath("$.data", is(Integer.parseInt(s.get("data")))));
    }


    @Test
    @Order(5)
    void testSelectPage() throws Exception {
        mvc.perform(get(PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .param("accountId", "1")
                .param("size", "5")
                .param("current", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("success")));
    }

    @Test
    @Order(6)
    void testUnExistsFavoritesId() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("accountId", "1");
        map.put("favoriteBusinessType", "company");
        map.put("businessTypeFavoriteId", 77777);

        mvc.perform(put(PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJsonString(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("favoritesId 77777 does not exists")));
    }


    @Test
    @Order(100)
    void testDeleteId() throws Exception {

        List<SystemFavoritesInfo> list =service.selectList(null);
        if(list.size() == 0) return;
        Long id = list.get(0).getId();

        mvc.perform(delete(PREFIX + "/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("success")));

        SystemFavoritesInfo nld = service.selectById(id);
        assertEquals(null, nld);
    }

}
