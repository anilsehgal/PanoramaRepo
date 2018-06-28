package edu.utoledo.panorama.vo;

import java.util.Date;
import java.util.List;

public class ReplyVO {
	private double replyId;
	private double discussionId;
	private String replyText;
	private double createdBy;
	private UserVO createdByUser;
	private Date createdOn;
	private Date lastModifiedOn;
	private double isAnswer;
	private double voteUps;
	private double voteDowns;
	private List<TagVO> tags;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReplyVO [replyId=" + replyId + ", discussionId=" + discussionId + ", isAnswer=" + isAnswer + "]";
	}
	
	/**
	 * @return the createdByUser
	 */
	public UserVO getCreatedByUser() {
		return createdByUser;
	}

	/**
	 * @param createdByUser the createdByUser to set
	 */
	public void setCreatedByUser(UserVO createdByUser) {
		this.createdByUser = createdByUser;
	}

	/**
	 * @return the replyId
	 */
	public double getReplyId() {
		return replyId;
	}
	/**
	 * @param replyId the replyId to set
	 */
	public void setReplyId(double replyId) {
		this.replyId = replyId;
	}
	/**
	 * @return the discussionId
	 */
	public double getDiscussionId() {
		return discussionId;
	}
	/**
	 * @param discussionId the discussionId to set
	 */
	public void setDiscussionId(double discussionId) {
		this.discussionId = discussionId;
	}
	/**
	 * @return the replyText
	 */
	public String getReplyText() {
		return replyText;
	}
	/**
	 * @param replyText the replyText to set
	 */
	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}
	/**
	 * @return the createdBy
	 */
	public double getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(double createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}
	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	/**
	 * @return the lastModifiedOn
	 */
	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}
	/**
	 * @param lastModifiedOn the lastModifiedOn to set
	 */
	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	/**
	 * @return the isAnswer
	 */
	public double getIsAnswer() {
		return isAnswer;
	}
	/**
	 * @param isAnswer the isAnswer to set
	 */
	public void setIsAnswer(double isAnswer) {
		this.isAnswer = isAnswer;
	}
	/**
	 * @return the voteUps
	 */
	public double getVoteUps() {
		return voteUps;
	}
	/**
	 * @param voteUps the voteUps to set
	 */
	public void setVoteUps(double voteUps) {
		this.voteUps = voteUps;
	}
	/**
	 * @return the voteDowns
	 */
	public double getVoteDowns() {
		return voteDowns;
	}
	/**
	 * @param voteDowns the voteDowns to set
	 */
	public void setVoteDowns(double voteDowns) {
		this.voteDowns = voteDowns;
	}

	/**
	 * @return the tags
	 */
	public List<TagVO> getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(List<TagVO> tags) {
		this.tags = tags;
	}
	
	
}
