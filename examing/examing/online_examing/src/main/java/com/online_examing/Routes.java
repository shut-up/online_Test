package com.online_examing;

public interface Routes {

    /**
     * Comment
     */
    String COMMENT_BASE_ROUTE = "/comment";
    String COMMENT_ADD = "/commentAdd";
    String COMMENT_GET_BY_ID = "/getByComment";
    String COMMENT_GET_ALL = "/getAllComment";
    String COMMENT_SEARCH = "/search";
    String COMMENT_UPDATE_SELECT_COMMENT = "/updateSelectComment";

    /**
     * User
     */
    String USER_BASE_ROUTE = "/user";
    String USER_LOGIN = "/login";
    String USER_REGISTER = "/register";

}
