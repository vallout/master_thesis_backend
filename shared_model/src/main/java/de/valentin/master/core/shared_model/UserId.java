package de.valentin.master.core.shared_model;

public class UserId {
	
	private String userId;

	public UserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		return userId;
	}
	
	@Override
	public boolean equals(Object o) {
        if (o == this) { 
            return true; 
        } 
        if (!(o instanceof UserId)) { 
            return false; 
        }
        UserId other = (UserId) o;
		return this.userId.equals(other.userId);
	}
}
