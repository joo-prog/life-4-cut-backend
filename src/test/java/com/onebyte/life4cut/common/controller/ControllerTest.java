package com.onebyte.life4cut.common.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.onebyte.life4cut.config.TestSecurityConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class, SpringExtension.class})
@ImportAutoConfiguration(TestSecurityConfiguration.class)
public abstract class ControllerTest {

//  private static long time = 1000000;
//  private static String AUTHORITY_KEY = "auth";
//  private static String USER_ID = "userId";
//  private final String secretKey = "SnNvbldlYlRva2VuQXV0aGVudGljYXRpb25XaXRoU3ByaW5nQm9vdFRlc3RQcm9qZWN0U2VjcmV0S2V5";

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @BeforeEach
  void setUp(final WebApplicationContext context,
      final RestDocumentationContextProvider restDocumentation) {
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(documentationConfiguration(restDocumentation))
        .addFilters(new CharacterEncodingFilter("UTF-8", true))
        .alwaysDo(print())
        .build();
  }

  protected Object asParsedJson(Object obj) throws JsonProcessingException {
    String json = new ObjectMapper().writeValueAsString(obj);
    return JsonPath.read(json, "$");
  }

  protected ResultActions performGet(String uri) throws Exception {
    return mockMvc.perform(get(uri)
        .contentType(MediaType.APPLICATION_JSON)
    );
  }

  protected ResultActions performGet(String uri, MultiValueMap<String, String> params)
      throws Exception {
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

//  protected String generateToken(User user){
//    Map<String, Object> payload = new HashMap<>();
//    payload.put(USER_ID, user.getId());
//    payload.put(AUTHORITY_KEY, AuthorityUtils.createAuthorityList("ADMIN"));
//
//    long now = System.currentTimeMillis();
//    return Jwts.builder()
//        .setClaims(payload)
//        .setExpiration(new Date(now + time))
//        .signWith(SignatureAlgorithm.HS512, secretKey)
//        .compact();
//  }
}
