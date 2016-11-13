package com.alma.boutique.infrastructure;

import com.alma.boutique.infrastructure.util.Comment;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * @author Thomas Minier
 */
public class ExternalWebserviceTest {
    private final static String jsonPlaceholderURL = "https://jsonplaceholder.typicode.com";
    private ExternalWebservice externalWebservice;

    @Before
    public void setUp() throws Exception {
        externalWebservice = new ExternalWebservice(jsonPlaceholderURL);
    }

    @Test
    public void getSingle() throws Exception {
        Comment expected = new Comment(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium");

        // test if the get method correctly fetch data from jsonplaceholder webservice
        Comment msg = externalWebservice.get("/comments/1", Comment.class);
        assertThat(msg).as("we should received the expected message gfrom jsonplaceholder.com/comments/1").isEqualTo(expected);
    }

    @Test
    public void getList() throws Exception {
        Comment firstComment = new Comment(1, 1, "id labore ex et quam laborum", "Eliseo@gardner.biz", "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium");

        // test if the get method correctly fetch data from jsonplaceholder webservice
        List<Comment> list = externalWebservice.getList("/comments", Comment.class);
        assertThat(list).as("the retrieved list should contains the first two elements expected").contains(firstComment);
    }

}