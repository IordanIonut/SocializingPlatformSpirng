package com.example.demo;

import com.example.demo.Model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class SocializingPlatformApplicationTests {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void runUsersTestMethods() throws Exception {
		UsersTest usersTest = new UsersTest();
		UsersTest.setup();
		usersTest.setMvc(mvc, objectMapper);

		usersTest.testCreateUsers();
		usersTest.testDisplayAllUsers();
		usersTest.testDisplayUsersById();
		usersTest.testUpdateUser();
		usersTest.testDeleteUser();

		usersTest.testUsersCreateUserNameNull();
		usersTest.testUsersCreatePasswordNull();
		usersTest.testUsersCreateFirstNameNull();
		usersTest.testUsersCreateLastNameNull();
		usersTest.testUsersCreateEmailNull();
		usersTest.testUsersCreateDateOfBirthNull();
		usersTest.testUsersCreateAllDateNull();
		usersTest.testUsersCreateInvalidEmail();
	}

	@Test
	public void runPostsTestMethods() throws Exception{
		PostsTest postsTest = new PostsTest();
		postsTest.setMvc(mvc, objectMapper);
		PostsTest.setup();

		postsTest.testCreatePosts();
		postsTest.testDisplayAllPosts();
		postsTest.testDisplayPostsById();
		postsTest.testDeletePosts();
		postsTest.testUpdatePosts();

		postsTest.testPostsContentNull();
		postsTest.testPostsTimeStampNull();
	}

	@Test
	public void runLikesTestMethods() throws Exception{
		LikesTest likesTest = new LikesTest();
		likesTest.setMvc(mvc, objectMapper);
		LikesTest.setup();

		likesTest.testCreateLikes();
		likesTest.testDisplayAllLikes();
		likesTest.testDisplayByIdLikes();
		likesTest.testDeleteLikes();
		likesTest.testUpdateLikes();

		likesTest.testLikesNumberNull();
	}

	@Test
	public void runCommentsTestMethods() throws Exception{
		CommentsTest commentsTest = new CommentsTest();
		commentsTest.setMvc(mvc, objectMapper);
		CommentsTest.setup();

		commentsTest.testCreateComments();
		commentsTest.testDisplayAllComments();
		commentsTest.testCommentsById();
		commentsTest.testDeleteComments();
		commentsTest.testUpdateComments();

		commentsTest.testCommentsTimeStampNull();
		commentsTest.testCommentsContentNull();
	}

	@Test
	public void runFriendsTestMethods() throws Exception{
		FriendsTest friendsTest = new FriendsTest();
		friendsTest.setMvc(mvc, objectMapper);
		FriendsTest.setup();

		friendsTest.testCreateFriends();
		friendsTest.testDisplayAllFriends();
		friendsTest.testDisplayFriendsById();
		friendsTest.testDeleteFriends();
		friendsTest.testUpdateFriens();

		friendsTest.testFriendsStatusNull();
	}

	@Test
	public void runGroupsTestMethods() throws Exception{
		GroupsTest groupsTest = new GroupsTest();
		groupsTest.setMvc(mvc, objectMapper);
		GroupsTest.setup();

		groupsTest.testCreateGroups();
		groupsTest.testDisplayAllGroups();
		groupsTest.testDisplayGroupsById();
		groupsTest.testDeleteGroups();
		groupsTest.testUpdateGroups();

		groupsTest.testGroupsNameNull();
		groupsTest.testGroupsDescriptionNull();
	}

	@Test
	public void runGroupsMembersTestMethods() throws Exception{
		GrupsMembersTest grupsMembersTest = new GrupsMembersTest();
		grupsMembersTest.setMvc(mvc, objectMapper);
		GrupsMembersTest.setup();

		grupsMembersTest.testCreateGrupoMembers();
		grupsMembersTest.testDisplayAllGrupoMembers();
		grupsMembersTest.testDisplayGrupoMembersById();
		grupsMembersTest.testDeleteGrupoMembers();
		grupsMembersTest.testUpdateGrupoMembers();

	}
}

