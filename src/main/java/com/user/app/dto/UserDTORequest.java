package com.user.app.dto;

public record UserDTORequest(Long userId,
                             String emailId,
                             String fullName,
                             String password,
                             String role,
                             Long mobileNo,
                             String addressLine1,
                             String addressLine2,
                             String city,
                             String profilePicUrl
                                ) {
    public UserDTORequest(Long userId, String emailId, String fullName, String password, String role, Long mobileNo, String addressLine1, String addressLine2, String city, String profilePicUrl) {
        this.userId = userId;
        this.emailId = emailId;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
        this.mobileNo = mobileNo;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.profilePicUrl = profilePicUrl;
    }
}
