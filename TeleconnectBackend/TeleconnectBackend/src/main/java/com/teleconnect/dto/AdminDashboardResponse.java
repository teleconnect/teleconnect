package com.teleconnect.dto;

public class AdminDashboardResponse {

    private long currentActiveUsers; // Changed from totalActivatedPlans
    private long usersWithBasicPlan; // Changed from basicPlanCount
    private long usersWithStandardPlan; // Changed from standardPlanCount
    private long usersWithPremiumPlan; // Changed from premiumPlanCount
    private long usersWithVerifiedEmail; // Changed from totalVerifiedEmailCount
    private long totalRegisteredUsers; // Changed from totalRegisteredUserCount
    private long totalOnboardedUsers; // Changed from distinctEmailCount
    private long mobileNumbersAssigned; // Changed from mobileIdAssigned

    // Constructor
    public AdminDashboardResponse(long currentActiveUsers, long usersWithBasicPlan, long usersWithStandardPlan,
                                  long usersWithPremiumPlan, long usersWithVerifiedEmail,
                                  long totalRegisteredUsers, long totalOnboardedUsers,
                                  long mobileNumbersAssigned) { // Updated constructor parameters
        this.currentActiveUsers = currentActiveUsers;
        this.usersWithBasicPlan = usersWithBasicPlan;
        this.usersWithStandardPlan = usersWithStandardPlan;
        this.usersWithPremiumPlan = usersWithPremiumPlan;
        this.usersWithVerifiedEmail = usersWithVerifiedEmail;
        this.totalRegisteredUsers = totalRegisteredUsers;
        this.totalOnboardedUsers = totalOnboardedUsers;
        this.mobileNumbersAssigned = mobileNumbersAssigned; // Updated to new field name
    }

    // Getters and Setters

    public long getCurrentActiveUsers() {
        return currentActiveUsers;
    }

    public void setCurrentActiveUsers(long currentActiveUsers) {
        this.currentActiveUsers = currentActiveUsers;
    }

    public long getUsersWithBasicPlan() {
        return usersWithBasicPlan;
    }

    public void setUsersWithBasicPlan(long usersWithBasicPlan) {
        this.usersWithBasicPlan = usersWithBasicPlan;
    }

    public long getUsersWithStandardPlan() {
        return usersWithStandardPlan;
    }

    public void setUsersWithStandardPlan(long usersWithStandardPlan) {
        this.usersWithStandardPlan = usersWithStandardPlan;
    }

    public long getUsersWithPremiumPlan() {
        return usersWithPremiumPlan;
    }

    public void setUsersWithPremiumPlan(long usersWithPremiumPlan) {
        this.usersWithPremiumPlan = usersWithPremiumPlan;
    }

    public long getUsersWithVerifiedEmail() {
        return usersWithVerifiedEmail;
    }

    public void setUsersWithVerifiedEmail(long usersWithVerifiedEmail) {
        this.usersWithVerifiedEmail = usersWithVerifiedEmail;
    }

    public long getTotalRegisteredUsers() {
        return totalRegisteredUsers;
    }

    public void setTotalRegisteredUsers(long totalRegisteredUsers) {
        this.totalRegisteredUsers = totalRegisteredUsers;
    }

    public long getTotalOnboardedUsers() {
        return totalOnboardedUsers;
    }

    public void setTotalOnboardedUsers(long totalOnboardedUsers) {
        this.totalOnboardedUsers = totalOnboardedUsers;
    }

    public long getMobileNumbersAssigned() {
        return mobileNumbersAssigned;
    }

    public void setMobileNumbersAssigned(long mobileNumbersAssigned) {
        this.mobileNumbersAssigned = mobileNumbersAssigned;
    }

    // Override toString method for better readability (optional)
    @Override
    public String toString() {
        return "AdminDashboardResponse{" +
                "currentActiveUsers=" + currentActiveUsers +
                ", usersWithBasicPlan=" + usersWithBasicPlan +
                ", usersWithStandardPlan=" + usersWithStandardPlan +
                ", usersWithPremiumPlan=" + usersWithPremiumPlan +
                ", usersWithVerifiedEmail=" + usersWithVerifiedEmail +
                ", totalRegisteredUsers=" + totalRegisteredUsers +
                ", totalOnboardedUsers=" + totalOnboardedUsers +
                ", mobileNumbersAssigned=" + mobileNumbersAssigned +
                '}';
    }
}
