package com.devteam.module.base.exception;

import org.springframework.http.HttpStatus;

public class AppExceptionCode {
    //USER EXCEPTIONS
    public static AppException USER_ALREADY_REGISTERED_400_4000 = new AppException(HttpStatus.BAD_REQUEST, 4000, "User has already registered.", "", "");
    public static AppException USER_LOGIN_FAILED_400_4003 = new AppException(HttpStatus.BAD_REQUEST, 4003, "Login failed.", "", "");
    public static AppException USER_NOTFOUND_400_4005 = new AppException(HttpStatus.BAD_REQUEST, 4005, "User not found.", "", "");
    public static AppException USER_REGISTRATION_FAILED_500_4006 = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, 4006, "User registration failed.", "", "");
    public static AppException USER_RESET_PASSWORD_FAILED_500_4007 = new AppException(HttpStatus.INTERNAL_SERVER_ERROR, 4007, "Reset password failed.", "", "");
}
