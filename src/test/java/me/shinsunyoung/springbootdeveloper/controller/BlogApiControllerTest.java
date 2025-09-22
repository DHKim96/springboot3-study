package me.shinsunyoung.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.shinsunyoung.springbootdeveloper.domain.Article;
import me.shinsunyoung.springbootdeveloper.dto.AddArticleRequest;
import me.shinsunyoung.springbootdeveloper.dto.UpdateArticleRequest;
import me.shinsunyoung.springbootdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 테스트용 애플리케이션 컨텍스트
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc; // MockMvc는 실제 톰캣 서버를 띄우지 않고도 Spring MVC의 요청/응답을 테스트할 수 있는 객체

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역직렬화 위한 클래스

    @Autowired
    private WebApplicationContext context; // Spring MVC 웹 애플리케이션 전용 IoC 컨테이너

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        // webAppContextSetup(context)를 통해 실제 DispatcherServlet, Controller, Service Bean 등이 포함된 웹 컨텍스트를 테스트 환경에 심어줌
        blogRepository.deleteAll();
    }

    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest request = new  AddArticleRequest(title, content);
        // 객체 JSON 으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(request);

        //when
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
                                                        .post(url).contentType(MediaType.APPLICATION_JSON)
                                                                  .content(requestBody));

        //then
        resultActions.andExpect(status().isCreated());
        List<Article> articles = blogRepository.findAll();
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
    }


    @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllArticles() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        blogRepository.save(Article.builder()
                                        .title(title)
                                        .content(content)
                                    .build());
        // when
        final ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].content").value(content));
    }

    @DisplayName("findArticle: 블로그 글 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()).accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));

    }

    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete(url, savedArticle.getId()))
                .andExpect(status().isOk());


        //then
        assertThat(blogRepository.findAll().size()).isEqualTo(0);

    }

    @DisplayName("updateArticle: 블로그 글 수정에 성공한다.")
    @Test
    public void updateArticle() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());

        final String newTitle = "newTitle";
        final String newContent = "newContent";

        UpdateArticleRequest request = new  UpdateArticleRequest(newTitle, newContent);

        //when
        ResultActions resultActions = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        //then
        resultActions.andExpect(status().isOk());

        Article updatedArticle = blogRepository.findById(savedArticle.getId()).get();

        assertThat(updatedArticle.getTitle()).isEqualTo(newTitle);
        assertThat(updatedArticle.getContent()).isEqualTo(newContent);
    }


}