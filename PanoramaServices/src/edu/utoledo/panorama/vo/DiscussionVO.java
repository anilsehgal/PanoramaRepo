package edu.utoledo.panorama.vo;

import java.util.Date;
import java.util.List;

public class DiscussionVO {

	private double discussionId;
	private String topic;
	private String description;
	private double createdBy;
	private UserVO createdByUser;
	private Date createdOn;
	private Date lastModifiedOn;
	private double lastModifiedBy;
	private UserVO lastModifiedByUser;
	private double replyCount;
	private double status;
	private double isEdited;
	private String discussionType;
	private List<TagVO> tags;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DiscussionVO [discussionId=" + discussionId + ", topic=" + topic
				+ ", replyCount=" + replyCount + ", status=" + status + "]";
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
	 * @return the lastModifiedByUser
	 */
	public UserVO getLastModifiedByUser() {
		return lastModifiedByUser;
	}

	/**
	 * @param lastModifiedByUser the lastModifiedByUser to set
	 */
	public void setLastModifiedByUser(UserVO lastModifiedByUser) {
		this.lastModifiedByUser = lastModifiedByUser;
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
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}
	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the lastModifiedBy
	 */
	public double getLastModifiedBy() {
		return lastModifiedBy;
	}
	/**
	 * @param lastModifiedBy the lastModifiedBy to set
	 */
	public void setLastModifiedBy(double lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	/**
	 * @return the replyCount
	 */
	public double getReplyCount() {
		return replyCount;
	}
	/**
	 * @param replyCount the replyCount to set
	 */
	public void setReplyCount(double replyCount) {
		this.replyCount = replyCount;
	}
	/**
	 * @return the status
	 */
	public double getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(double status) {
		this.status = status;
	}
	/**
	 * @return the isEdited
	 */
	public double getIsEdited() {
		return isEdited;
	}
	/**
	 * @param isEdited the isEdited to set
	 */
	public void setIsEdited(double isEdited) {
		this.isEdited = isEdited;
	}
	/**
	 * @return the tags
	 */
	public String getDiscussionType() {
		return discussionType;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setDiscussionType(String discussionType) {
		this.discussionType = discussionType;
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
