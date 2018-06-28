package edu.utoledo.panorama.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import edu.utoledo.panorama.dao.DiscussionForumDAO;
import edu.utoledo.panorama.services.PanoramaCipher;
import edu.utoledo.panorama.vo.AvatarVO;
import edu.utoledo.panorama.vo.DiscussionVO;
import edu.utoledo.panorama.vo.ExceptionResponseVO;
import edu.utoledo.panorama.vo.ReplyVO;
import edu.utoledo.panorama.vo.ReplyVoteVO;
import edu.utoledo.panorama.vo.TagVO;
import edu.utoledo.panorama.vo.TopicTagVO;
import edu.utoledo.panorama.vo.UserVO;

/**
 * The Service Class having all service methods for Client and File Interactions
 * @author Anil Sehgal
 * @version 0.1
 */
@Path("/json")
public class JSONService {

	/**
	 * The logger variable to log debugging statements
	 * @see Logger for more details
	 */
	private static final Logger logger = Logger.getLogger(JSONService.class);
	
	protected static final String SESSION_USER = "userId";
	
	private static final String UNAUTHORIZED_ERROR = "You are not authorized to perform this operation";
	
	/**
	 * Gets latest 50 discussions
	 * @return the list of {@link DiscussionVO} records
	 */
	@POST
	@PermitAll
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response authenticateUser(@FormParam("username") String username, 
			@FormParam("password") String password,
			@Context HttpServletRequest request) {

		try {

			PanoramaCipher panoramaCipher = new PanoramaCipher();
			UserVO userVO = new UserVO();
			userVO.setUserName(username);
			UserVO requestedUser = DiscussionForumDAO.getInstance().validateUser(userVO);
			if (requestedUser == null) {
				
				ExceptionResponseVO exceptionResponse = new ExceptionResponseVO();
				exceptionResponse.setMessage("Username not recognized");
				return Response.status(Response.Status.UNAUTHORIZED).entity(exceptionResponse).build();
			}
			
			String storedPassword = requestedUser.getPassword();
			String checkPassword = panoramaCipher.decrypt(storedPassword);
			if(checkPassword!= null && password != null && password.equals(checkPassword)) {

				String token = panoramaCipher.encrypt( requestedUser.getUserId() + "===" + requestedUser.getUserName() + "===" + requestedUser.getRole() );
				HttpSession session = request.getSession(true);
				session.setAttribute(SESSION_USER, requestedUser.getUserId());
				return Response.ok("{\"token\": \"" + token + "\",\"accomodation\": \"" + requestedUser.getRole() + "\",\"identifier\": \"" + requestedUser.getFullName() + "\"}").build();
			} else {
				
				ExceptionResponseVO exceptionResponse = new ExceptionResponseVO();
				exceptionResponse.setMessage("Incorrect username or password");
				return Response.status(Response.Status.UNAUTHORIZED).entity(exceptionResponse).build();
			}
		} catch (Exception e) {
			ExceptionResponseVO exceptionResponse = new ExceptionResponseVO();
			exceptionResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.UNAUTHORIZED).entity(exceptionResponse)
					.build();
		}      
	}

	/**
	 * Gets latest 50 discussions
	 * @return the list of {@link DiscussionVO} records
	 */
	@GET
	@PermitAll
	@Path("/getLatest50Discussions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLatest50Discussions() {

		List<DiscussionVO> data = new ArrayList<>(); 
		try {

			data = DiscussionForumDAO.getInstance().getLatest50Discussions();
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}

	/**
	 * Gets top 50 open discussions
	 * @return the list of {@link DiscussionVO} records
	 */
	@GET
	@PermitAll
	@Path("/getHot50OpenDiscussions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHot50OpenDiscussions() {

		List<DiscussionVO> data = new ArrayList<>(); 
		try {

			data = DiscussionForumDAO.getInstance().getHot50OpenDiscussions();
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}

	/**
	 * Gets all the discussions which are closed
	 * @return the list of {@link DiscussionVO} records
	 */
	@GET
	@PermitAll
	@Path("/getLatest50ClosedDiscussions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLatest50ClosedDiscussions() {

		List<DiscussionVO> data = new ArrayList<>(); 
		try {

			data = DiscussionForumDAO.getInstance().getLatest50ClosedDiscussions();
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}

	/**
	 * Gets all the discussions which are closed
	 * @return the list of {@link DiscussionVO} records
	 */
	@GET
	@PermitAll
	@Path("/getTrendingDiscussions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response trendingDiscussions() {

		List<DiscussionVO> data = new ArrayList<>(); 
		try {

			data = DiscussionForumDAO.getInstance().getTrendingDiscussions();
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}

	@GET
	@PermitAll
	@Path("/getDiscussionsByTagId/{tagId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiscussionsByTagId( @PathParam("tagId") double tagId ) {

		List<DiscussionVO> data = new ArrayList<>();
		try {

			data = DiscussionForumDAO.getInstance().getDiscussionsByTagId(tagId);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}

	/**
	 * Gets all the tags for given topic
	 * @param objectId objectId
	 * @return the list of {@link TagVO} records
	 */
	@GET
	@PermitAll
	@Path("/getDiscussionById/{discussionId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiscussionById( @PathParam("discussionId") double discussionId ) {

		DiscussionVO data = new DiscussionVO(); 
		try {

			data = DiscussionForumDAO.getInstance().getDiscussionById(discussionId);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}


	/**
	 * Gets all the discussion for the current week 
	 * @return the list of {@link DiscussionVO} records
	 */
	@GET
	@PermitAll
	@Path("/getDiscussionsCreatedSinceWeek")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiscussionsCreatedSinceWeek() {
		Date inputDate = new Date();

		List<DiscussionVO> data = new ArrayList<>(); 
		try {

			data = DiscussionForumDAO.getInstance().getDiscussionsCreatedSince(inputDate);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}

	/**
	 * Gets all the discussion for the current month
	 * @return the list of {@link DiscussionVO} records
	 */
	@GET
	@PermitAll
	@Path("/getDiscussionsCreatedSinceMonth")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiscussionsCreatedSinceMonth() {
		Date inputDate = new Date();

		List<DiscussionVO> data = new ArrayList<>(); 
		try {

			data = DiscussionForumDAO.getInstance().getDiscussionsCreatedSince(inputDate);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}

	/**
	 * Gets all the discussion for the current month
	 * @return the list of {@link DiscussionVO} records
	 */
	@GET
	@PermitAll
	@Path("/getTopContributor")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopContributor() {

		UserVO userVO = null; 
		try {

			userVO = DiscussionForumDAO.getInstance().getTopContributor();
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(userVO)
				.build();
	}
	
	@GET
	@PermitAll
	@Path("/getUserById/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById( @PathParam("userId") double userId ) {

		UserVO userVO = null; 
		try {

			userVO = DiscussionForumDAO.getInstance().getCurrentUser(userId);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(userVO)
				.build();
	}
	
	@GET
	@Path("/getCurrentUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentUser( @Context HttpServletRequest request) {
		
		double userId = getUserId(request);
		if (userId == -1) {
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(UNAUTHORIZED_ERROR);
			return Response.status(Response.Status.UNAUTHORIZED).entity( errResponse )
					.build(); 
		}
		UserVO userVO = null; 
		try {

			userVO = DiscussionForumDAO.getInstance().getCurrentUser(userId);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(userVO)
				.build();
	}
	
	@GET
	@PermitAll
	@Path("/getLatestUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLatestUser() {

		UserVO userVO = null; 
		try {

			userVO = DiscussionForumDAO.getInstance().getLatestUser();
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(userVO)
				.build();
	}
	
	/**
	 * Gets all the tags for given topic
	 * @param objectId objectId
	 * @return the list of {@link TagVO} records
	 */
	@GET
	@PermitAll
	@Path("/getTopicTags/{objectId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopicTags( @PathParam("objectId") double objectId ) {

		List<TagVO> data = new ArrayList<>(); 
		try {

			data = DiscussionForumDAO.getInstance().getTopicTags(objectId);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}

	/**
	 * Gets all the tags for given reply
	 * @param objectId objectId
	 * @return the list of {@link TagVO} records
	 */
	@GET
	@PermitAll
	@Path("/getReplyTags/{objectId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReplyTags( @PathParam("objectId") double objectId ) {

		List<TagVO> data = new ArrayList<>(); 
		try {

			data = DiscussionForumDAO.getInstance().getReplyTags(objectId);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}

	@GET
	@PermitAll
	@Path("/searchTagsByName/{tagName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchTagsByName( @PathParam("tagName") String tagName ) {

		List<TagVO> data = new ArrayList<>(); 
		try {

			data = DiscussionForumDAO.getInstance().searchTagsByName(tagName);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}

	@GET
	@PermitAll
	@Path("/getUserTags")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserTags() {

		List<TagVO> data = new ArrayList<>(); 
		try {
			UserVO user = DiscussionForumDAO.getInstance().getTopContributor();
			data = DiscussionForumDAO.getInstance().getUserTags(user.getUserId());
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}
	
	@GET
	@PermitAll
	@Path("/getUserTagsByUserId/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserTagsByUserId(@PathParam("userId") double userId) {

		List<TagVO> data = new ArrayList<>(); 
		try {
			data = DiscussionForumDAO.getInstance().getUserTags(userId);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}

	@GET
	@PermitAll
	@Path("/getTrendingTags")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTrendingTags() {

		List<TagVO> data = new ArrayList<>(); 
		try {
			data = DiscussionForumDAO.getInstance().getTrendingTags();
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}


	/**
	 * Gets all the discussion for the given text
	 * @param text text
	 * @return the list of {@link DiscussionVO} records
	 */
	@GET
	@PermitAll
	@Path("/searchDiscussionsByText/{text}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchDiscussionsByText( @PathParam("text") String text ) {

		text = "%" + text + "%";

		List<DiscussionVO> data = new ArrayList<>(); 
		try {

			data = DiscussionForumDAO.getInstance().searchDiscussionsByText(text);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}

	/**
	 * Gets all the discussion for the given user
	 * @param userId userId
	 * @return the list of {@link DiscussionVO} records
	 */
	@GET
	@PermitAll
	@Path("/getDiscussionsByUser/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiscussionsByUser( @PathParam("userid") double userId, @Context HttpServletRequest request ) {
		
		List<DiscussionVO> data = new ArrayList<>(); 
		try {

			data = DiscussionForumDAO.getInstance().getDiscussionsByUser(userId);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}

	/**
	 * Gets all the replies for the given discussion
	 * @param discussionId discussionId
	 * @return the list of {@link ReplyVO} records
	 */
	@GET
	@PermitAll
	@Path("/getRepliesForDiscussion/{discussionId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRepliesForDiscussion( @PathParam("discussionId") double discussionId ) {

		List<ReplyVO> data = new ArrayList<>(); 
		try {

			data = DiscussionForumDAO.getInstance().getRepliesForDiscussion(discussionId);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}

	/**
	 * Gets all the replies for the given discussion
	 * @param discussionId discussionId
	 * @return the list of {@link ReplyVO} records
	 */
	@GET
	@PermitAll
	@Path("/getAvatars")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAvatars() {

		List<AvatarVO> data = new ArrayList<>(); 
		try {

			data = DiscussionForumDAO.getInstance().getAvatars();
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}
	
	@GET
	@PermitAll
	@Path("/getUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {

		List<UserVO> data = new ArrayList<>(); 
		try {

			data = DiscussionForumDAO.getInstance().getUsers();
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}

		return Response.ok(data)
				.build();
	}

	@POST
	@Path("/insertDiscussion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertDiscussion( DiscussionVO discussion, @Context HttpServletRequest request ) {
		
		double userId = getUserId(request);
		if (userId == -1) {
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(UNAUTHORIZED_ERROR);
			return Response.status(Response.Status.UNAUTHORIZED).entity( errResponse )
					.build(); 
		}
		int rows = 0;
		double discussionId = 0;
		try {
			discussionId = DiscussionForumDAO.getInstance().getNewDiscussionId();
			discussion.setDiscussionId(discussionId);
			discussion.setCreatedBy(userId);
			discussion.setLastModifiedBy(userId);
			rows = DiscussionForumDAO.getInstance().insertDiscussion(discussion);
			if (discussion.getTags()!=null && discussion.getTags().size() > 0) {

				for (TagVO tagVO : discussion.getTags()) {

					TopicTagVO topicTagVO = new TopicTagVO();
					topicTagVO.setObjectId(discussionId);
					topicTagVO.setTagId(tagVO.getTagId());
					DiscussionForumDAO.getInstance().insertTopicTag(topicTagVO);
				}
			}
			if (rows > 0) {

				rows = DiscussionForumDAO.getInstance().updateUserScores(discussion.getCreatedBy());
			}
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}
		return Response.ok("{\"rows\":\"" + rows + "\", \"discussionId\":\"" + discussionId + "\"}")
				.build(); 
	}

	@POST
	@Path("/updateUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateUser( 
			@FormParam("fullName") String fullName,
			@FormParam("password") String password,
			@FormParam("newPassword") String newPassword,
			@FormParam("email") String email, @Context HttpServletRequest request ) {

		double userId = getUserId(request);
		if (userId == -1) {
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(UNAUTHORIZED_ERROR);
			return Response.status(Response.Status.UNAUTHORIZED).entity( errResponse )
					.build(); 
		}
		int rows = 0;
		try {
			PanoramaCipher panoramaCipher = new PanoramaCipher();
			
			String storedPassword = DiscussionForumDAO.getInstance().getUserPassword(userId);
			String checkPassword = panoramaCipher.decrypt(storedPassword);
			if (!checkPassword.equals(password)) {
				
				ExceptionResponseVO errResponse = new ExceptionResponseVO();
				errResponse.setMessage("Incorrect current password entered");
				return Response.status(Response.Status.UNAUTHORIZED).entity(errResponse).build();
			}
			
			UserVO userVO = new UserVO();
			userVO.setUserId(userId);
			userVO.setFullName(fullName);
			PanoramaCipher cipher = new PanoramaCipher();
			String encryptedPassword = cipher.encrypt(newPassword);
			userVO.setPassword(encryptedPassword);
			userVO.setEmail(email);
			rows = DiscussionForumDAO.getInstance().updateUser(userVO);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}
		return Response.ok("{\"rows\":\"" + rows + "\"}")
				.build(); 
	}
	
	@POST
	@Path("/updateDiscussion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateDiscussion( DiscussionVO discussion, @Context HttpServletRequest request ) {
		
		double userId = getUserId(request);
		if (userId == -1) {
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(UNAUTHORIZED_ERROR);
			return Response.status(Response.Status.UNAUTHORIZED).entity( errResponse )
					.build(); 
		}
		int rows = 0;
		try {
			discussion.setLastModifiedBy(userId);
			rows = DiscussionForumDAO.getInstance().updateDiscussion(discussion);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}
		return Response.ok("{\"rows\":\"" + rows + "\"}")
				.build();
	}

	@POST
	@RolesAllowed("ADMIN")
	@Path("/closeDiscussion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response closeDiscussion( DiscussionVO discussion, @Context HttpServletRequest request ) {
		
		double userId = getUserId(request);
		if (userId == -1) {
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(UNAUTHORIZED_ERROR);
			return Response.status(Response.Status.UNAUTHORIZED).entity( errResponse )
					.build(); 
		}
		int rows = 0;
		try {
			discussion.setLastModifiedBy(userId);
			rows = DiscussionForumDAO.getInstance().closeDiscussion(discussion);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}
		return Response.ok("{\"rows\":\"" + rows + "\"}")
				.build();
	}

	@POST
	@RolesAllowed("ADMIN")
	@Path("/deleteDiscussion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteDiscussion( DiscussionVO discussion, @Context HttpServletRequest request ) {
		
		double userId = getUserId(request);
		if (userId == -1) {
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(UNAUTHORIZED_ERROR);
			return Response.status(Response.Status.UNAUTHORIZED).entity( errResponse )
					.build(); 
		}
		int rows = 0;
		try {
			rows = DiscussionForumDAO.getInstance().deleteDiscussion(discussion.getDiscussionId());
			if (rows == 1) {
				rows = DiscussionForumDAO.getInstance().deleteReplies(discussion.getDiscussionId());
			}
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}
		return Response.ok("{\"rows\":\"" + rows + "\"}")
				.build();
	}
	
	@POST
	@RolesAllowed("ADMIN")
	@Path("/deleteReply")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteReply( ReplyVO reply, @Context HttpServletRequest request ) {
		
		double userId = getUserId(request);
		if (userId == -1) {
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(UNAUTHORIZED_ERROR);
			return Response.status(Response.Status.UNAUTHORIZED).entity( errResponse )
					.build();  
		}
		int rows = 0;
		try {
			rows = DiscussionForumDAO.getInstance().deleteReply(reply.getReplyId());
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}
		return Response.ok("{\"rows\":\"" + rows + "\"}")
				.build();
	}
	
	
	
	
	@POST
	@Path("/createReply")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createReply( ReplyVO reply, @Context HttpServletRequest request ) {
		
		double userId = getUserId(request);
		if (userId == -1) {
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(UNAUTHORIZED_ERROR);
			return Response.status(Response.Status.UNAUTHORIZED).entity( errResponse )
					.build(); 
		}
		int rows = 0;
		try {
			double replyId = DiscussionForumDAO.getInstance().getNewReplyId();
			reply.setReplyId(replyId);
			reply.setCreatedBy(userId);
			rows = DiscussionForumDAO.getInstance().createReply(reply);
			if (reply.getTags()!=null && reply.getTags().size() > 0) {
				for (TagVO tagVO : reply.getTags()) {

					TopicTagVO topicTagVO = new TopicTagVO();
					topicTagVO.setObjectId(replyId);
					topicTagVO.setTagId(tagVO.getTagId());
					DiscussionForumDAO.getInstance().insertReplyTag(topicTagVO);
				}
			}
			if (rows > 0) {
				DiscussionForumDAO.getInstance().updateDiscussionStatistics(reply);
			}
			if (rows > 0) {
				rows = DiscussionForumDAO.getInstance().updateUserScores(reply.getCreatedBy());
			}
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}
		return Response.ok("{\"rows\":\"" + rows + "\"}")
				.build();
	}

	@POST
	@Path("/updateReply")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateReply( ReplyVO reply, @Context HttpServletRequest request ) {
		
		double userId = getUserId(request);
		if (userId == -1) {
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(UNAUTHORIZED_ERROR);
			return Response.status(Response.Status.UNAUTHORIZED).entity( errResponse )
					.build(); 
		}
		int rows = 0;
		try {
			
			rows = DiscussionForumDAO.getInstance().updateReply(reply);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}
		return Response.ok("{\"rows\":\"" + rows + "\"}")
				.build();
	}

	@POST
	@Path("/markReplyAsAnswer")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response markReplyAsAnswer( ReplyVO reply, @Context HttpServletRequest request ) {
		
		double userId = getUserId(request);
		if (userId == -1) {
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(UNAUTHORIZED_ERROR);
			return Response.status(Response.Status.UNAUTHORIZED).entity( errResponse )
					.build(); 
		}
		int rows = 0;
		try {
			
			rows = DiscussionForumDAO.getInstance().markReplyAsAnswer(reply);
			if (rows > 0) {
				DiscussionVO discussion = new DiscussionVO();
				discussion.setDiscussionId(reply.getDiscussionId());
				rows = DiscussionForumDAO.getInstance().closeDiscussion(discussion);
			}
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}
		return Response.ok("{\"rows\":\"" + rows + "\"}")
				.build();
	}

	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logout( Object container, @Context HttpServletRequest request ) {
		
		double userId = getUserId(request);
		if (userId == -1) {
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(UNAUTHORIZED_ERROR);
			return Response.status(Response.Status.UNAUTHORIZED).entity( errResponse )
					.build();  
		}
		try {
			
			HttpSession session = request.getSession(false);
			if ( session != null ) {
				
				session.invalidate();
			}
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}
		return Response.ok()
				.build();
	}
	
	@POST
	@Path("/voteReply")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response voteReply( ReplyVoteVO replyVote, @Context HttpServletRequest request ) {
		
		double userId = getUserId(request);
		if (userId == -1) {
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(UNAUTHORIZED_ERROR);
			return Response.status(Response.Status.UNAUTHORIZED).entity( errResponse )
					.build(); 
		}
		int rows = 0;
		try {
			
			replyVote.setMarkedBy(userId);
			rows = DiscussionForumDAO.getInstance().voteReply(replyVote);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}
		return Response.ok("{\"rows\":\"" + rows + "\"}")
				.build();
	}

	@POST
	@PermitAll
	@Path("/createUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createUser( @FormParam("userName") String userName, @FormParam("fullName") String fullName,
			@FormParam("password") String password, @FormParam("avatar") String avatar, @FormParam("email") String email  ) {

		int rows = 0;
		try {
			
			if (DiscussionForumDAO.getInstance().getUserByUsername(userName) > 0) {
				logger.error("Exception: Username taken");
				ExceptionResponseVO errResponse = new ExceptionResponseVO();
				errResponse.setMessage("The username " + userName + " is already taken");
				return Response.status(Response.Status.FORBIDDEN).entity( errResponse )
						.build(); 
			}
			
			UserVO user = new UserVO();
			user.setUserName(userName);
			PanoramaCipher cipher = new PanoramaCipher();
			String encryptedPassword = cipher.encrypt(password);
			user.setPassword(encryptedPassword);
			user.setAvatar(avatar);
			user.setFullName(fullName);
			user.setEmail(email);
			rows = DiscussionForumDAO.getInstance().createUser(user);
		} catch (Exception e) {

			logger.error("Exception: " + e.getMessage(), e);
			ExceptionResponseVO errResponse = new ExceptionResponseVO();
			errResponse.setMessage(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( errResponse )
					.build(); 
		}
		return Response.ok("{\"rows\":\"" + rows + "\"}")
				.build();
	}
	
	private double getUserId(HttpServletRequest request) {
		
		double userId = getUserIdFromRequest(request);
		return userId;
	}
	
	private double getUserIdFromRequest(HttpServletRequest request) {
		
		double userId = -1;
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        String token = authorizationHeader.substring("Bearer".length()).trim();
        try {
        	
        	PanoramaCipher panoramaCipher = new PanoramaCipher();
        	String userCreds = panoramaCipher.decrypt(token);
        	StringTokenizer stringTokenizer = new StringTokenizer(userCreds, "===");
        	String userIdFromToken = "";
        	if (stringTokenizer.hasMoreTokens()) {
        		userIdFromToken = stringTokenizer.nextToken();
        	}
        	userId = Double.parseDouble(userIdFromToken);
        } catch (Exception e) {
        	
        	logger.error(e.getMessage(),e);
        }
        return userId;
	}
	
	@GET
	@PermitAll
	@Path("/fakeCall")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fakeCall() {

		return Response.ok("{\"success\":\"dedication\"}")
				.build();
	}
	
	/**
	 * This method intercepts every POST method call and adds CORS registration for the service call responses
	 *  as otherwise, the web service calls are denied access in the mobile app frame
	 * @param request the POST request 
	 * @see HttpServletRequest
	 * @return a blank response with the CORS headers added
	 */
/*	@OPTIONS
	@Path("")
	public Response CORSregisterInstallation(@Context HttpServletRequest request) {
		ResponseBuilder response = Response.ok();

		response.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Headers", "accept, origin, ag-mobile-variant, content-type");
		response.header("Content-Type", "text/plain; charset=us-ascii"); 
		return response.build();
	}*/
}