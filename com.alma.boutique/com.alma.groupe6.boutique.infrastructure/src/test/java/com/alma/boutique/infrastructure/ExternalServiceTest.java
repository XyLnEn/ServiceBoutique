package com.alma.boutique.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * @author Thomas Minier
 */
public class ExternalServiceTest {
    private final static String jsonPlaceholderURL = "https://jsonplaceholder.typicode.com";
    private ExternalService externalService;

    // private class used to store the data provided by jsonplaceholder' webservices
    private static class Comment {
        private int postId;
        private int id;
        private String name;
        private String email;
        private String body;

        public Comment() {
        }

        public Comment(int postId, int id, String name, String email, String body) {
            this.postId = postId;
            this.id = id;
            this.name = name;
            this.email = email;
            this.body = body;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Comment comment = (Comment) o;
            return postId == comment.postId && id == comment.id && name.equals(comment.name) && email.equals(comment.email) && body.equals(comment.body);
        }

        @Override
        public int hashCode() {
            return postId;
        }
    }
    @Before
    public void setUp() throws Exception {
        externalService = new ExternalService(jsonPlaceholderURL);
    }

    @Test
    public void getSingle() throws Exception {
        Comment expected = new Comment(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium");

        // test if the get method correctly fetch data from jsonplaceholder webservice
        Comment msg = externalService.get("/comments/1", Comment.class);
        assertThat(msg).as("we should received the expected message gfrom jsonplaceholder.com/comments/1").isEqualTo(expected);
    }

    @Test
    public void getList() throws Exception {
        Comment firstComment = new Comment(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium");

        // test if the get method correctly fetch data from jsonplaceholder webservice
        List<Comment> list = externalService.getList("/comments", Comment.class);
        assertThat(list).as("the retrieved list should contains the first two elements expected").contains(firstComment);
    }

}