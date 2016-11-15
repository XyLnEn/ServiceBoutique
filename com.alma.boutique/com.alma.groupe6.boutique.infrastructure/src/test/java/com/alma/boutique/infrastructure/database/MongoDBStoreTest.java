package com.alma.boutique.infrastructure.database;

import com.alma.boutique.infrastructure.util.Comment;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Thomas Minier
 */
public class MongoDBStoreTest {
    private MongoDBStore<Comment> store;
    @Before
    public void setUp() throws Exception {
        store = MongoDBStore.getInstance(Comment.class);
    }

    @Test
    public void getInstance() throws Exception {
        MongoDBStore<Comment> otherStore = MongoDBStore.getInstance(Comment.class);
        assertThat(otherStore).as("getInstance should always return the same instance of MongoDBStore").isSameAs(store);
    }

    @Test
    public void crudOperations() throws Exception {
        Comment firstComment = new Comment(1, 1, "name", "sample@email.com", "body");
        Comment secondComment = new Comment(1, 2, "name", "sample@email.com", "body");
        // insert a new comment into the base
        store.create(firstComment.getId(), firstComment);

        // check if the element has been inserted in the base
        Comment mongoComment = store.retrieve(firstComment.getId());
        assertThat(mongoComment).as("the first comment should have been correctly inserted in the database").isEqualTo(firstComment);

        // test the retrieveAll method
        store.create(secondComment.getId(), secondComment);
        List<Comment> allComments = store.retrieveAll();
        assertThat(allComments).as("the two documents should be retrieved as a list from the database").contains(firstComment, secondComment);

        // test the update method
        firstComment.setBody("a new body");
        store.update(firstComment.getId(), firstComment);
        mongoComment = store.retrieve(firstComment.getId());
        assertThat(mongoComment).as("the comment should be updated in the database").isEqualTo(firstComment);

        // test the delete operator
        store.delete(firstComment.getId());
        store.delete(secondComment.getId());
        allComments = store.retrieveAll();
        assertThat(allComments).as("the database should be empty after the deletion").isEmpty();
    }
}