package edu.utoledo.panorama.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import edu.utoledo.panorama.vo.AvatarVO;
import edu.utoledo.panorama.vo.DiscussionVO;
import edu.utoledo.panorama.vo.ReplyVO;
import edu.utoledo.panorama.vo.ReplyVoteVO;
import edu.utoledo.panorama.vo.TagVO;
import edu.utoledo.panorama.vo.TopicTagVO;
import edu.utoledo.panorama.vo.UserVO;

public class DiscussionForumDAO extends BaseDAO {

	private static final DiscussionForumDAO INSTANCE = new DiscussionForumDAO();

	private DiscussionForumDAO(){}

	public static final DiscussionForumDAO getInstance() {

		return INSTANCE;
	}

	public UserVO validateUser(UserVO userVO) {

		SqlSession session = getSqlSessionFactory().openSession();
		UserVO user = session.selectOne( "validateUser", userVO );
		session.close();
		return user;
	}

	public String getUserPassword(double userId) {

		SqlSession session = getSqlSessionFactory().openSession();
		String password = session.selectOne( "getUserPassword", userId );
		session.close();
		return password;
	}
	
	public int getUserByUsername(String userName) {

		SqlSession session = getSqlSessionFactory().openSession();
		int count = session.selectOne( "getUserByUsername", userName );
		session.close();
		return count;
	}
	
	public List<UserVO> getUsers() {

		SqlSession session = getSqlSessionFactory().openSession();
		List<UserVO> users = session.selectList( "getUsers" );
		session.close();
		return users;
	}

	public List<DiscussionVO> getLatest50Discussions() {

		SqlSession session = getSqlSessionFactory().openSession();
		List<DiscussionVO> discussions = session.selectList( "getLatest50Discussions" );
		for ( DiscussionVO discussionVO : discussions ) {
			UserVO createdByUser = session.selectOne("getUserById", discussionVO.getCreatedBy());
			UserVO lastModifiedByUser = session.selectOne("getUserById", discussionVO.getLastModifiedBy());
			discussionVO.setCreatedByUser(createdByUser);
			discussionVO.setLastModifiedByUser(lastModifiedByUser);
		}
		session.close();
		return discussions;
	}

	public List<DiscussionVO> getTrendingDiscussions() {

		SqlSession session = getSqlSessionFactory().openSession();
		List<DiscussionVO> discussions = session.selectList( "getTrendingDiscussions" );
		for ( DiscussionVO discussionVO : discussions ) {
			UserVO createdByUser = session.selectOne("getUserById", discussionVO.getCreatedBy());
			UserVO lastModifiedByUser = session.selectOne("getUserById", discussionVO.getLastModifiedBy());
			discussionVO.setCreatedByUser(createdByUser);
			discussionVO.setLastModifiedByUser(lastModifiedByUser);
		}
		session.close();
		return discussions;
	}

	public List<DiscussionVO> getHot50OpenDiscussions() {

		SqlSession session = getSqlSessionFactory().openSession();
		List<DiscussionVO> discussions = session.selectList( "getHot50OpenDiscussions" );
		for ( DiscussionVO discussionVO : discussions ) {
			UserVO createdByUser = session.selectOne("getUserById", discussionVO.getCreatedBy());
			UserVO lastModifiedByUser = session.selectOne("getUserById", discussionVO.getLastModifiedBy());
			discussionVO.setCreatedByUser(createdByUser);
			discussionVO.setLastModifiedByUser(lastModifiedByUser);
		}
		session.close();
		return discussions;
	}

	public List<DiscussionVO> getLatest50ClosedDiscussions() {

		SqlSession session = getSqlSessionFactory().openSession();
		List<DiscussionVO> discussions = session.selectList( "getLatest50ClosedDiscussions" );
		for ( DiscussionVO discussionVO : discussions ) {
			UserVO createdByUser = session.selectOne("getUserById", discussionVO.getCreatedBy());
			UserVO lastModifiedByUser = session.selectOne("getUserById", discussionVO.getLastModifiedBy());
			discussionVO.setCreatedByUser(createdByUser);
			discussionVO.setLastModifiedByUser(lastModifiedByUser);
		}
		session.close();
		return discussions;
	}

	public List<DiscussionVO> getDiscussionsCreatedSince(Date inputDate) {

		SqlSession session = getSqlSessionFactory().openSession();
		List<DiscussionVO> discussions = session.selectList( "getDiscussionsCreatedSince", inputDate );
		for ( DiscussionVO discussionVO : discussions ) {
			UserVO createdByUser = session.selectOne("getUserById", discussionVO.getCreatedBy());
			UserVO lastModifiedByUser = session.selectOne("getUserById", discussionVO.getLastModifiedBy());
			discussionVO.setCreatedByUser(createdByUser);
			discussionVO.setLastModifiedByUser(lastModifiedByUser);
		}
		session.close();
		return discussions;
	}

	public List<DiscussionVO> searchDiscussionsByText( String text ) {

		SqlSession session = getSqlSessionFactory().openSession();
		List<DiscussionVO> discussions = session.selectList( "searchDiscussionsByText", text );
		for ( DiscussionVO discussionVO : discussions ) {
			UserVO createdByUser = session.selectOne("getUserById", discussionVO.getCreatedBy());
			UserVO lastModifiedByUser = session.selectOne("getUserById", discussionVO.getLastModifiedBy());
			discussionVO.setCreatedByUser(createdByUser);
			discussionVO.setLastModifiedByUser(lastModifiedByUser);
		}
		session.close();
		return discussions;
	}

	public List<DiscussionVO> getDiscussionsByUser( double userId ) {

		SqlSession session = getSqlSessionFactory().openSession();
		List<DiscussionVO> discussions = session.selectList( "getDiscussionsByUser", userId );
		for ( DiscussionVO discussionVO : discussions ) {
			UserVO createdByUser = session.selectOne("getUserById", discussionVO.getCreatedBy());
			UserVO lastModifiedByUser = session.selectOne("getUserById", discussionVO.getLastModifiedBy());
			discussionVO.setCreatedByUser(createdByUser);
			discussionVO.setLastModifiedByUser(lastModifiedByUser);
		}
		session.close();
		return discussions;
	}

	public List<DiscussionVO> getDiscussionsByTagId( double tagId ) {

		SqlSession session = getSqlSessionFactory().openSession();
		List<DiscussionVO> discussions = session.selectList( "getDiscussionsByTagId", tagId );
		for ( DiscussionVO discussionVO : discussions ) {
			UserVO createdByUser = session.selectOne("getUserById", discussionVO.getCreatedBy());
			UserVO lastModifiedByUser = session.selectOne("getUserById", discussionVO.getLastModifiedBy());
			discussionVO.setCreatedByUser(createdByUser);
			discussionVO.setLastModifiedByUser(lastModifiedByUser);
		}
		session.close();
		return discussions;
	}

	public DiscussionVO getDiscussionById( double discussionId ) {

		SqlSession session = getSqlSessionFactory().openSession();
		DiscussionVO discussionVO = session.selectOne( "getDiscussionById", discussionId );
		UserVO createdByUser = session.selectOne("getUserById", discussionVO.getCreatedBy());
		UserVO lastModifiedByUser = session.selectOne("getUserById", discussionVO.getLastModifiedBy());
		discussionVO.setCreatedByUser(createdByUser);
		discussionVO.setLastModifiedByUser(lastModifiedByUser);
		session.close();
		return discussionVO;
	}

	public List<ReplyVO> getRepliesForDiscussion( double discussionId ) {

		SqlSession session = getSqlSessionFactory().openSession();
		List<ReplyVO> replies = session.selectList( "getRepliesForDiscussion", discussionId );
		for ( ReplyVO reply : replies ) {
			UserVO createdByUser = session.selectOne("getUserById", reply.getCreatedBy());
			reply.setCreatedByUser(createdByUser);
		}
		session.close();
		return replies;
	}

	public double getNewDiscussionId() {

		SqlSession session = getSqlSessionFactory().openSession();
		double discussionId = session.selectOne( "getNewDiscussionId" );
		session.close();
		return discussionId;
	}

	public double getNewReplyId() {

		SqlSession session = getSqlSessionFactory().openSession();
		double replyId = session.selectOne( "getNewReplyId" );
		session.close();
		return replyId;
	}

	public int insertDiscussion( DiscussionVO discussion ) {

		int rows = 0;
		SqlSession session = getSqlSessionFactory().openSession();
		rows = session.insert( "insertDiscussion", discussion );
		session.commit();
		session.close();
		return rows;
	}

	public int updateDiscussion( DiscussionVO discussion ) {

		int rows = 0;
		SqlSession session = getSqlSessionFactory().openSession();
		rows = session.update( "updateDiscussion", discussion );
		session.commit();
		session.close();
		return rows;
	}

	public int closeDiscussion( DiscussionVO discussion ) {

		int rows = 0;
		SqlSession session = getSqlSessionFactory().openSession();
		rows = session.update( "closeDiscussion", discussion );
		session.commit();
		session.close();
		return rows;
	}

	public int createReply( ReplyVO reply ) {

		int rows = 0;
		SqlSession session = getSqlSessionFactory().openSession();
		rows = session.insert( "createReply", reply );
		session.commit();
		session.close();
		return rows;
	}

	public int updateReply( ReplyVO reply ) {

		int rows = 0;
		SqlSession session = getSqlSessionFactory().openSession();
		rows = session.update( "updateReply", reply );
		session.commit();
		session.close();
		return rows;
	}

	public int markReplyAsAnswer( ReplyVO reply ) {

		int rows = 0;
		SqlSession session = getSqlSessionFactory().openSession();
		rows = session.update( "markReplyAsAnswer", reply );
		session.commit();
		session.close();
		return rows;
	}

	public int updateDiscussionStatistics( ReplyVO reply ) {

		int rows = 0;
		SqlSession session = getSqlSessionFactory().openSession();
		rows = session.update( "updateDiscussionStatistics", reply );
		session.commit();
		session.close();
		return rows;
	}

	public int voteReply( ReplyVoteVO replyVote  ) {

		int rows = 0;
		SqlSession session = getSqlSessionFactory().openSession();
		rows = session.insert( "voteReply", replyVote );
		session.commit();
		session.close();
		return rows;
	}

	public int createUser( UserVO user  ) {

		int rows = 0;
		SqlSession session = getSqlSessionFactory().openSession();
		rows = session.insert( "createUser", user );
		session.commit();
		session.close();
		return rows;
	}

	public UserVO getTopContributor() {

		SqlSession session = getSqlSessionFactory().openSession();
		UserVO user = session.selectOne( "getTopContributor" );
		session.close();
		return user;
	}

	public UserVO getLatestUser() {

		SqlSession session = getSqlSessionFactory().openSession();
		UserVO user = session.selectOne( "getLatestUser" );
		session.close();
		return user;
	}

	public List<TagVO> getTopicTags(double objectId) {

		SqlSession session = getSqlSessionFactory().openSession();
		List<TagVO> tags = session.selectList( "getTopicTags", objectId );
		session.close();
		return tags;
	}

	public List<TagVO> getReplyTags(double objectId) {

		SqlSession session = getSqlSessionFactory().openSession();
		List<TagVO> tags = session.selectList( "getReplyTags", objectId );
		session.close();
		return tags;
	}

	public int updateUserScores(double userId) {

		int rows = 0;
		SqlSession session = getSqlSessionFactory().openSession();
		rows = session.update( "updateUserScores", userId );
		session.commit();
		session.close();
		return rows;
	}

	public int insertTopicTag(TopicTagVO topicTagVO) {

		int rows = 0;
		SqlSession session = getSqlSessionFactory().openSession();
		rows = session.insert( "insertTopicTag", topicTagVO );
		session.commit();
		session.close();
		return rows;
	}

	public int insertReplyTag(TopicTagVO topicTagVO) {

		int rows = 0;
		SqlSession session = getSqlSessionFactory().openSession();
		rows = session.insert( "insertReplyTag", topicTagVO );
		session.commit();
		session.close();
		return rows;
	}


	public List<TagVO> getUserTags(double userId) {

		SqlSession session = getSqlSessionFactory().openSession();
		List<TagVO> tags = session.selectList( "getUserTags", userId );
		session.close();
		return tags;
	}

	public List<TagVO> getTrendingTags() {

		SqlSession session = getSqlSessionFactory().openSession();
		List<TagVO> tags = session.selectList( "getTrendingTags" );
		session.close();
		return tags;
	}

	public List<AvatarVO> getAvatars() {

		SqlSession session = getSqlSessionFactory().openSession();
		List<AvatarVO> avatars = session.selectList( "getAvatars" );
		session.close();
		return avatars;
	}

	public List<TagVO> searchTagsByName(String tagName) {

		tagName = "%" + tagName + "%";
		SqlSession session = getSqlSessionFactory().openSession();
		List<TagVO> tags = session.selectList( "searchTagsByName", tagName );
		session.close();
		return tags;
	}

	public String getUserRole(double userId) {

		SqlSession session = getSqlSessionFactory().openSession();
		String userRole = session.selectOne( "getUserRole", userId );
		session.close();
		return userRole;
	}

	public int updateUser(UserVO userVO) {

		int rows = 0;
		SqlSession session = getSqlSessionFactory().openSession();
		rows = session.update( "updateUser", userVO );
		session.commit();
		session.close();
		return rows;
	}

	public UserVO getCurrentUser(double userId) {

		SqlSession session = getSqlSessionFactory().openSession();
		UserVO user = session.selectOne( "getCurrentUser", userId );
		session.close();
		return user;
	}

	public int deleteDiscussion(double id) {

		int rows = 0;
		SqlSession session = getSqlSessionFactory().openSession();
		rows = session.delete( "deleteDiscussion", id );
		session.commit();
		session.close();
		return rows;
	}
	
	public int deleteReplies(double id) {

		int rows = 0;
		SqlSession session = getSqlSessionFactory().openSession();
		rows = session.delete( "deleteReplies", id );
		session.commit();
		session.close();
		return rows;
	}

	public int deleteReply(double id) {

		int rows = 0;
		SqlSession session = getSqlSessionFactory().openSession();
		rows = session.delete( "deleteReply", id );
		session.commit();
		session.close();
		return rows;
	}
}
