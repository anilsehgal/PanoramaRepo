package edu.utoledo.panorama.vo;

import java.util.Date;

public class ReplyVoteVO {
	private double replyId;
	private double markedBy;
	private Date markedOn;
	private double vote;
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
	 * @return the markedBy
	 */
	public double getMarkedBy() {
		return markedBy;
	}
	/**
	 * @param markedBy the markedBy to set
	 */
	public void setMarkedBy(double markedBy) {
		this.markedBy = markedBy;
	}
	/**
	 * @return the markedOn
	 */
	public Date getMarkedOn() {
		return markedOn;
	}
	/**
	 * @param markedOn the markedOn to set
	 */
	public void setMarkedOn(Date markedOn) {
		this.markedOn = markedOn;
	}
	/**
	 * @return the vote
	 */
	public double getVote() {
		return vote;
	}
	/**
	 * @param vote the vote to set
	 */
	public void setVote(double vote) {
		this.vote = vote;
	}
	
	
}
