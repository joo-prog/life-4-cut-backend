package com.onebyte.life4cut.common;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MultiValueMap;

@AutoConfigureRestDocs
public abstract class ControllerTest {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  protected Object asParsedJson(Object obj) throws JsonProcessingException {
    String json = new ObjectMapper().writeValueAsString(obj);
    return JsonPath.read(json, "$");
  }

  protected ResultActions performGet(String uri) throws Exception {
    return mockMvc.perform(get(uri)
        .contentType(MediaType.APPLICATION_JSON)
    );
  }

  protected ResultActions performGet(String uri, MultiValueMap<String, String> params) throws Exception {
    return mockMvc.perform(get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .params(params)
    );
  }

  protected ResultActions performPost(String uri) throws Exception {
    return mockMvc.perform(post(uri)
        .contentType(MediaType.APPLICATION_JSON)
    );
  }

  protected ResultActions performPost(String uri, Object body) throws Exception {
    return mockMvc.perform(post(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
    );
  }

  protected ResultActions performPut(String uri, Object body) throws Exception {
    return mockMvc.perform(put(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
    );
  }

  protected ResultActions performDelete(String uri, Object body) throws Exception {
    return mockMvc.perform(delete(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
    );
  }
}
