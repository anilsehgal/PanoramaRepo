package edu.utoledo.panorama.vo;

import java.util.Date;

public class UserVO {
	private double userId;
	private String userName;
	private String fullName;
	private String password;
	private Date createdOn;
	private String role;
	private double isActive;
	private double topicCount;
	private double replyCount;
	private String avatar;
	private String email;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserVO [userId=" + userId + ", userName=" + userName + ", fullName=" + fullName + ", password="
				+ password + ", role=" + role + ", topicCount=" + topicCount
				+ ", replyCount=" + replyCount + "]";
	}
	/**
	 * @return the userId
	 */
	public double getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(double userId) {
		this.userId = userId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * @return the isActive
	 */
	public double getIsActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(double isActive) {
		this.isActive = isActive;
	}
	/**
	 * @return the topicCount
	 */
	public double getTopicCount() {
		return topicCount;
	}
	/**
	 * @param topicCount the topicCount to set
	 */
	public void setTopicCount(double topicCount) {
		this.topicCount = topicCount;
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
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}
	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
}
